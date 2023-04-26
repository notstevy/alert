package me.obsilabor.alert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to subscribe to events
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {

    /**
     * 1 - lowest
     * 5 - highest
     * @return The event priority set for this listener
     */
    int priority() default EventPriority.MEDIUM;

}
