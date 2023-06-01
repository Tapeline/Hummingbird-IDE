package me.tapeline.carousellib.configuration.exceptions;

import me.tapeline.carousellib.configuration.ConfigurationSection;
import me.tapeline.carousellib.exceptions.CarouselException;

import java.io.File;

public class SectionCorruptedException extends CarouselException {

    private final ConfigurationSection targetSection;

    public SectionCorruptedException(ConfigurationSection targetSection) {
        super(targetSection.getName());
        this.targetSection = targetSection;
    }

    public ConfigurationSection getTargetSection() {
        return targetSection;
    }

}
