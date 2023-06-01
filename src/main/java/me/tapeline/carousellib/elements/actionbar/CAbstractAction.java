package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.util.function.Consumer;

public abstract class CAbstractAction {

    private Consumer<JComponent> customizationConsumer;

    public Consumer<JComponent> getCustomizationConsumer() {
        return customizationConsumer;
    }

    public void setCustomization(Consumer<JComponent> customizationConsumer) {
        this.customizationConsumer = customizationConsumer;
    }

    public void customize(JComponent component) {
        if (customizationConsumer != null)
            customizationConsumer.accept(component);
    }

    public abstract JComponent buildComponent();

}
