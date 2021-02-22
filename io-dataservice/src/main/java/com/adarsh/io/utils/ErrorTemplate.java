package com.adarsh.io.utils;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

@Component
public class ErrorTemplate {

	private static final String DOES_NOT_EXIST = " does not exist";

	public <T> Supplier<? extends NoSuchElementException> notExistSupplier(T value) {
		return () -> notExist(value);
	}

	public <T> NoSuchElementException notExist(T value) {
		return new NoSuchElementException(value + DOES_NOT_EXIST);
	}

	public IllegalArgumentException illegalArgs(String message) {
		return new IllegalArgumentException(message);
	}

	public IllegalStateException illegalState(String message) {
		return new IllegalStateException(message);
	}

}
