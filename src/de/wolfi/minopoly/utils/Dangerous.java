package de.wolfi.minopoly.utils;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target(value={METHOD,FIELD})
public @interface Dangerous {
	String y() default "BC OF THAT";
}
