package me.tapeline.carousellib.elements.logslist;

import me.tapeline.carousellib.elements.card.CCard;
import me.tapeline.carousellib.elements.cardlist.CCardList;
import me.tapeline.carousellib.icons.state.CErrorIcon;
import me.tapeline.carousellib.icons.state.CWarningIcon;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CLogsList extends CCardList {

    private int maxRecords;

    public CLogsList(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    public void addLog(LogRecord record) {
        if (getCards().size() == maxRecords)
            removeCard(getCards().get(0));
        Icon icon = null;
        if (record.getLevel().equals(Level.SEVERE))
            icon = new CErrorIcon(16, 16);
        else if (record.getLevel().equals(Level.WARNING))
            icon = new CWarningIcon(16, 16);
        JTextPane area = new JTextPane();
        area.setEditable(false);
        area.setText(record.getMessage());
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            area.setText(
                    record.getMessage() + "\n" +
                    "Exception " + record.getThrown() + "\n" +
                    record.getThrown().getLocalizedMessage() + "\n"
                    + "\n"
                    + "Stack Traceback (most recent call last):\n"
                    + sw
            );
        }
        addCard(new CCard(
                icon,
                new SimpleDateFormat("HH:mm:ss .SSS").format(new Date(record.getMillis())),
                area
        ));
    }

}
