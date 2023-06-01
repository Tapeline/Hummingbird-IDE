package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;

public class CSeparatorAction extends CAbstractAction {

    @Override
    public JComponent buildComponent() {
        return new JToolBar.Separator();
    }

}
