package me.tapeline.carousellib.configuration.exceptions;

import me.tapeline.carousellib.exceptions.CarouselException;
import me.tapeline.carousellib.configuration.ConfigurationSection;

public class FieldNotFoundException extends CarouselException {

    private final ConfigurationSection targetSection;
    private final String targetField;

    public FieldNotFoundException(ConfigurationSection targetSection, String targetField) {
        super("Section " + targetSection.getName() + " field " + targetField);
        this.targetSection = targetSection;
        this.targetField = targetField;
    }

    public ConfigurationSection getTargetSection() {
        return targetSection;
    }

    public String getTargetField() {
        return targetField;
    }

}
