package me.tapeline.hummingbird.configuration;

import javax.xml.ws.ServiceMode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Config {
    String configurationField() default "";
    String section() default "general";
    boolean showInSettings() default true;
}
