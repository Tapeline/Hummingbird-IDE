package me.tapeline.hummingbird.ide.frames.editor.tooltabs;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.elements.card.CCard;
import me.tapeline.carousellib.elements.cardlist.CCardList;
import me.tapeline.carousellib.elements.dynamiclist.CDynamicList;
import me.tapeline.carousellib.elements.logslist.CLogsList;
import me.tapeline.carousellib.icons.navigation.CMenuIcon;
import me.tapeline.carousellib.icons.state.CErrorIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class EventsToolTab extends JTabbedPane implements AbstractToolTab {

    private CCardList errors;
    private CLogsList logs;
    private EditorWindow editor;

    public EventsToolTab(EditorWindow editor) {
        super();
        this.editor = editor;
        errors = new CCardList();
        int maxErrors = 40;
        int maxLogs = 70;
        try {
            maxLogs = Application.instance.getConfiguration().logs().getInt("keepLogsInPane");
            maxErrors = Application.instance.getConfiguration().logs().getInt("keepErrorsInPane");
        } catch (FieldNotFoundException e) { e.printStackTrace(); }
        logs = new CLogsList(maxLogs);
        final int finalMaxErrors = maxErrors;
        Application.instance.getLogger().addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                logs.addLog(record);
                if (record.getLevel().equals(Level.SEVERE)) {
                    JTextPane area = new JTextPane();
                    area.setEditable(false);
                    if (errors.getCards().size() == finalMaxErrors)
                        errors.removeCard(errors.getCards().get(0));
                    if (record.getThrown() != null) {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        record.getThrown().printStackTrace(pw);
                        pw.close();
                        area.setText(
                                "Exception " + record.getThrown() + "\n" +
                                        record.getThrown().getLocalizedMessage() + "\n" +
                                        "\n" +
                                        "Stack Traceback (most recent call last):\n" +
                                        sw
                        );
                    }
                    area.setText(record.getMessage() + "\n" + area.getText());
                    errors.addCard(new CCard(
                            new CErrorIcon(16, 16),
                            new SimpleDateFormat("HH:mm:ss .SSS").format(new Date(record.getMillis())),
                            area
                    ));
                }
            }

            @Override
            public void flush() { }

            @Override
            public void close() throws SecurityException { }
        });
    }

    @Override
    public String name() {
        return "Events";
    }

    @Override
    public Icon icon() {
        return new CMenuIcon(14);
    }

    @Override
    public JComponent buildToolTab(EditorWindow editor, JButton hideToolTabButton) {
        addTab("Errors", errors);
        addTab("Logs", logs);
        return this;
    }

    public CCardList getNotifications() {
        return errors;
    }

    public CLogsList getLogs() {
        return logs;
    }

    public EditorWindow getEditor() {
        return editor;
    }

}
