package me.tapeline.rnd;

import org.apache.commons.io.Charsets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class QuickTerminal {

    public static void main(String[] args) {
        new QuickTerminal();
    }

    public QuickTerminal() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new ConsolePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public interface CommandListener {
        void commandOutput(String text);
        void commandCompleted(String cmd, int result);
        void commandFailed(Exception exp);
    }

    public class ConsolePane extends JPanel implements CommandListener, Terminal {

        private JTextArea textArea;
        private int userInputStart = 0;
        private Command cmd;

        public ConsolePane() {

            cmd = new Command(this);

            setLayout(new BorderLayout());
            textArea = new JTextArea(20, 30);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
            ((AbstractDocument) textArea.getDocument()).setDocumentFilter(new ProtectedDocumentFilter(this));
            add(new JScrollPane(textArea));

            InputMap im = textArea.getInputMap(WHEN_FOCUSED);
            ActionMap am = textArea.getActionMap();

            Action oldAction = am.get("insert-break");
            am.put("insert-break", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int range = textArea.getCaretPosition() - userInputStart;
                    try {
                        String text = textArea.getText(userInputStart, range).trim();
                        System.out.println("[" + text + "]");
                        userInputStart += range;
                        if (!cmd.isRunning()) {
                            cmd.execute(text);
                        } else {
                            try {
                                cmd.send(text + "\n");
                            } catch (IOException ex) {
                                appendText("!! Failed to send command to process: " + ex.getMessage() + "\n");
                            }
                        }
                    } catch (BadLocationException ex) {
                        Logger.getLogger(QuickTerminal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    oldAction.actionPerformed(e);
                }
            });

            cmd.execute("powershell");
        }

        @Override
        public void commandOutput(String text) {
            //SwingUtilities.invokeLater(new AppendTask(this, text));
            appendText(text);
        }

        @Override
        public void commandFailed(Exception exp) {
            SwingUtilities.invokeLater(new AppendTask(this, "Command failed - " + exp.getMessage()));
        }

        @Override
        public void commandCompleted(String cmd, int result) {
            appendText("\n> " + cmd + " exited with " + result + "\n");
            appendText("\n");
        }

        protected void updateUserInputPos() {
            int pos = textArea.getCaretPosition();
            textArea.setCaretPosition(textArea.getText().length());
            userInputStart = pos;

        }

        @Override
        public int getUserInputStart() {
            return userInputStart;
        }

        @Override
        public void appendText(String text) {
            textArea.append(text);
            updateUserInputPos();
        }
    }

    public interface UserInput {

        public int getUserInputStart();
    }

    public interface Terminal extends UserInput {
        void appendText(String text);
    }

    public class AppendTask implements Runnable {

        private Terminal terminal;
        private String text;

        public AppendTask(Terminal textArea, String text) {
            this.terminal = textArea;
            this.text = text;
        }

        @Override
        public void run() {
            terminal.appendText(text);
        }
    }

    public class Command {

        private CommandListener listener;
        private ProcessRunner runner;

        public Command(CommandListener listener) {
            this.listener = listener;
        }

        public boolean isRunning() {

            return runner != null && runner.isAlive();

        }

        public void execute(String cmd) {
            if (!cmd.trim().isEmpty()) {
                List<String> values = new ArrayList<>(25);
                if (cmd.contains("\"")) {
                    while (cmd.contains("\"")) {
                        String start = cmd.substring(0, cmd.indexOf("\""));
                        cmd = cmd.substring(start.length());
                        String quote = cmd.substring(cmd.indexOf("\"") + 1);
                        cmd = cmd.substring(cmd.indexOf("\"") + 1);
                        quote = quote.substring(0, cmd.indexOf("\""));
                        cmd = cmd.substring(cmd.indexOf("\"") + 1);
                        if (!start.trim().isEmpty()) {
                            String parts[] = start.trim().split(" ");
                            values.addAll(Arrays.asList(parts));
                        }
                        values.add(quote.trim());
                    }
                    if (!cmd.trim().isEmpty()) {
                        String parts[] = cmd.trim().split(" ");
                        values.addAll(Arrays.asList(parts));
                    }
                    for (String value : values) {
                        System.out.println("[" + value + "]");
                    }
                } else {
                    if (!cmd.trim().isEmpty()) {
                        String parts[] = cmd.trim().split(" ");
                        values.addAll(Arrays.asList(parts));
                    }
                }
                runner = new ProcessRunner(listener, values);
            }
        }

        public void send(String cmd) throws IOException {
            runner.write(cmd);
        }
    }

    public static class ProcessRunner extends Thread {
        private List<String> cmds;
        private CommandListener listener;
        private Process process;

        public ProcessRunner(CommandListener listener, List<String> cmds) {
            this.cmds = cmds;
            this.listener = listener;
            start();
        }

        @Override
        public void run() {
            try {
                System.out.println("cmds = " + cmds);
                ProcessBuilder pb = new ProcessBuilder(cmds);
                pb.redirectErrorStream();
                process = pb.start();
                StreamReader reader = new StreamReader(listener, process.getInputStream());
                // Need a stream writer...

                int result = process.waitFor();

                // Terminate the stream writer
                reader.join();

                StringJoiner sj = new StringJoiner(" ");
                cmds.forEach(sj::add);

                listener.commandCompleted(sj.toString(), result);
            } catch (Exception exp) {
                exp.printStackTrace();
                listener.commandFailed(exp);
            }
        }

        public void write(String text) throws IOException {
            if (process != null && process.isAlive()) {
                process.getOutputStream().write(text.getBytes());
                process.getOutputStream().flush();
            }
        }
    }

    public static class StreamReader extends Thread {
        private InputStream is;
        private CommandListener listener;

        public StreamReader(CommandListener listener, InputStream is) {
            this.is = is;
            this.listener = listener;
            start();
        }

        @Override
        public void run() {
            /*//try {
                int value = -1;
                //Scanner scanner = new Scanner(is, "cp866");
                /*while ((value = is.read()) != -1) {
                    listener.commandOutput(Character.toString((char) value));
                }*/
                //while (scanner.hasNext())
            /*try {
                Vector<Byte> bytes = new Vector<>();
                while ((value = is.read()) != -1)
                    bytes.add((byte) value);
                System.out.println(bytes);
                byte[] contents = new byte[bytes.size()];
                for (int i = 0; i < contents.length; i++)
                    contents[i] = bytes.get(i);
                listener.commandOutput(new String(contents, Charset.forName("cp866")));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            /*} catch (IOException exp) {
                exp.printStackTrace();
            }*/
            Scanner scanner = new Scanner(is, "cp866");
            scanner.useDelimiter("");
            while (scanner.hasNext())
                listener.commandOutput(scanner.next());
        }
    }

    public static class ProtectedDocumentFilter extends DocumentFilter {
        private UserInput userInput;

        public ProtectedDocumentFilter(UserInput userInput) {
            this.userInput = userInput;
        }

        public UserInput getUserInput() {
            return userInput;
        }

        @Override
        public void insertString(FilterBypass fb, int offset,
                                 String string, AttributeSet attr) throws BadLocationException {
            if (offset >= getUserInput().getUserInputStart()) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            if (offset >= getUserInput().getUserInputStart()) {
                super.remove(fb, offset, length);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs) throws BadLocationException {
            if (offset >= getUserInput().getUserInputStart()) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}