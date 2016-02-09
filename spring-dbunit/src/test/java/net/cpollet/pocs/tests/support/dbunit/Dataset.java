package net.cpollet.pocs.tests.support.dbunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Christophe Pollet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dataset {
    String value() default "";
    String[] values() default {};
    boolean commit() default false;
}
