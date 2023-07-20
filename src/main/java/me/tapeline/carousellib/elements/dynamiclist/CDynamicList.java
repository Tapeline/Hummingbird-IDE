package me.tapeline.carousellib.elements.dynamiclist;

import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.elements.actionbar.CAbstractAction;
import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.icons.commonactions.CAddIcon;
import me.tapeline.carousellib.icons.commonactions.CClearIcon;
import me.tapeline.carousellib.icons.commonactions.CRemoveIcon;
import me.tapeline.carousellib.icons.navigation.CDownIcon;
import me.tapeline.carousellib.icons.navigation.CUpIcon;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.function.Function;
import static me.tapeline.carousellib.utils.Flags.hasFlag;

public class CDynamicList<E> extends JPanel {

    public static final int DEFAULT = 0;
    public static final int CLEARABLE = 1;
    public static final int ORDERABLE = 2;
    public static final int ASK_ON_CLEAR = 4;

    public static String confirmTitle = "Clearing list";
    public static String confirmMessage = "Are you sure you want to clear elements?";

    protected CActionBar actionBar;
    protected JList<E> list;
    protected Vector<E> listData;
    protected int flags;
    protected JScrollPane scrollPane;

    protected CButtonAction addAction;
    protected CButtonAction removeAction;
    protected CButtonAction clearAction;
    protected CButtonAction upAction;
    protected CButtonAction downAction;

    protected Function<CDynamicList<E>, E> onAddCall;
    protected Function<E, Boolean> onRemoveCall;

    public CDynamicList() {
        this(DEFAULT);
    }

    public CDynamicList(int flags) {
        super();
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        this.flags = flags;
        list = new JList<>();
        listData = new Vector<>();
        list.setListData(listData);
        actionBar = new CActionBar();
        addAction = new CButtonAction(null, new CAddIcon(12, 12), this::onAdd);
        removeAction = new CButtonAction(null, new CRemoveIcon(12, 12), this::onRemove);
        actionBar.addAction(addAction);
        actionBar.addAction(removeAction);
        if (hasFlag(flags, CLEARABLE)) {
            clearAction = new CButtonAction(null, new CClearIcon(12, 12), this::onClear);
            actionBar.addAction(clearAction);
        }
        if (hasFlag(flags, ORDERABLE)) {
            upAction = new CButtonAction(null, new CUpIcon(12), this::onUp);
            downAction = new CButtonAction(null, new CDownIcon(12), this::onDown);
            actionBar.addAction(upAction);
            actionBar.addAction(downAction);
        }
        add(actionBar, BorderLayout.PAGE_START);
        scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);
    }

    protected void onAdd(CAbstractAction action, JComponent component) {
        E result = onAddCall.apply(this);
        if (result != null)
            listData.add(result);
        list.setListData(listData);
        list.updateUI();
    }

    protected void onRemove(CAbstractAction action, JComponent component) {
        if (list.getSelectedValue() != null) {
            Boolean result = onRemoveCall.apply(list.getSelectedValue());
            if (result)
                listData.remove(list.getSelectedIndex());
            list.setListData(listData);
            list.updateUI();
        }
    }

    protected void onClear(CAbstractAction action, JComponent component) {
        if (hasFlag(flags, ASK_ON_CLEAR))
            if (!Dialogs.confirmYesNo(this, confirmTitle, confirmMessage))
                return;
        Vector<E> copy = (Vector<E>) listData.clone();
        for (E e : copy) {
            Boolean result = onRemoveCall.apply(e);
            if (result) listData.remove(e);
        }
        list.setListData(listData);
        list.updateUI();
    }

    protected void onUp(CAbstractAction action, JComponent component) {
        if (list.getSelectedValue() != null) {
            if (list.getSelectedIndex() > 0) {
                int index = list.getSelectedIndex();
                E elem = listData.remove(index);
                listData.add(index - 1, elem);
                list.setListData(listData);
                list.setSelectedIndex(index - 1);
                list.updateUI();
            }
        }
    }

    protected void onDown(CAbstractAction action, JComponent component) {
        if (list.getSelectedValue() != null) {
            if (list.getSelectedIndex() + 1 < listData.size()) {
                int index = list.getSelectedIndex();
                E elem = listData.remove(index);
                listData.add(index + 1, elem);
                list.setListData(listData);
                list.setSelectedIndex(index + 1);
                list.updateUI();
            }
        }
    }

    public CActionBar getActionBar() {
        return actionBar;
    }

    public JList<E> getList() {
        return list;
    }

    public Vector<E> getListData() {
        return listData;
    }

    public void setListData(Vector<E> listData) {
        this.listData = listData;
        list.setListData(listData);
    }

    public int getFlags() {
        return flags;
    }

    public CButtonAction getAddAction() {
        return addAction;
    }

    public CButtonAction getRemoveAction() {
        return removeAction;
    }

    public CButtonAction getClearAction() {
        return clearAction;
    }

    public CButtonAction getUpAction() {
        return upAction;
    }

    public CButtonAction getDownAction() {
        return downAction;
    }

    public Function<CDynamicList<E>, E> getOnAddCall() {
        return onAddCall;
    }

    public Function<E, Boolean> getOnRemoveCall() {
        return onRemoveCall;
    }

    public void setOnAddCall(Function<CDynamicList<E>, E> onAddCall) {
        this.onAddCall = onAddCall;
    }

    public void setOnRemoveCall(Function<E, Boolean> onRemoveCall) {
        this.onRemoveCall = onRemoveCall;
    }

}
