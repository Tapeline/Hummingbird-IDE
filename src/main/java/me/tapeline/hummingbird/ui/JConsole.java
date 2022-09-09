package me.tapeline.hummingbird.ui;

import me.tapeline.hummingbird.expansions.Registry;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Scanner;

public class JConsole extends JTextPane {
    private final JTextPane console = this;
    public Process boundProcess;
    public Scanner inputReader;
    public Scanner errorReader;
    public PrintWriter writer;
    public int lastPutOut = 0;
    public JConsole() {
        console.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN){
                    e.consume();
                    return;
                } else if (code == KeyEvent.VK_ENTER) {
                    if (writer == null || boundProcess == null || !boundProcess.isAlive())
                        e.consume();
                    String text = console.getText();
                    text = text.substring(lastPutOut, console.getCaretPosition());
                    text = text.substring(text.lastIndexOf('\n') + 1);
                    /*if(Main.)
                        outputArea.append("\n");*/
                    writer.println(text);
                    writer.flush();
                }
            }
        });
    }

    public void bindToProcess(Process process) {
        boundProcess = process;
        new Thread(()->{
            try {
                inputReader = new Scanner(process.getInputStream());
                errorReader = new Scanner(process.getErrorStream());
                writer = new PrintWriter(process.getOutputStream());
                inputReader.useDelimiter("");
                StyledDocument document = getStyledDocument();
                new Thread(()->{
                    while (boundProcess.isAlive()) {
                        while (errorReader.hasNextLine()) {
                            try {
                                document.insertString(
                                        document.getLength(),
                                        errorReader.nextLine() + "\n",
                                        Registry.currentTheme.scheme().error.attributeSet()
                                );
                            } catch (BadLocationException ignore) { }
                            console.setCaretPosition(console.getText().length());
                            lastPutOut = console.getCaretPosition();
                        }
                    }
                }).start();
                while (boundProcess.isAlive()) {
                    while (inputReader.hasNext()) {
                        try {
                            document.insertString(
                                    document.getLength(),
                                    inputReader.nextLine(),
                                    Registry.currentTheme.scheme().error.attributeSet()
                            );
                        } catch (BadLocationException ignore) { }
                        console.setCaretPosition(console.getText().length());
                        lastPutOut = console.getCaretPosition();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
