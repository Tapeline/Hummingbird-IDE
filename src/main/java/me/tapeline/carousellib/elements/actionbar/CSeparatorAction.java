package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;

public class CSeparatorAction extends CAbstractAction {

    @Override
    public Component buildComponent() {
        return new JToolBar.Separator();
    }

}
