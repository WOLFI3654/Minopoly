package de.wolfi.minopoly.utils;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target(METHOD)
public @interface Dangerous {
	String y() default "BC OF THAT";
}
