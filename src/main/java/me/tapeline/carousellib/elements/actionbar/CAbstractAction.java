package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public abstract class CAbstractAction {

    private Consumer<Component> customizationConsumer;

    public Consumer<Component> getCustomizationConsumer() {
        return customizationConsumer;
    }

    public void setCustomization(Consumer<Component> customizationConsumer) {
        this.customizationConsumer = customizationConsumer;
    }

    public void customize(Component component) {
        if (customizationConsumer != null)
            customizationConsumer.accept(component);
    }

    public abstract Component buildComponent();

}
