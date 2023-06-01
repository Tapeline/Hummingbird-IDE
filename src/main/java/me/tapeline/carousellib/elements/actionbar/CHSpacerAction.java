package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;

public class CHSpacerAction extends CAbstractAction {

    @Override
    public Component buildComponent() {
        return Box.createHorizontalGlue();
    }

}
