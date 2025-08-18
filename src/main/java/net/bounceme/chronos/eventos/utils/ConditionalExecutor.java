package net.bounceme.chronos.eventos.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 
 */
public class ConditionalExecutor {

	/**
	 * @param <T>
	 * @param condition
	 * @param input
	 * @param action
	 * @param actionElse
	 */
	public static <T> void executeIfOrElse(Predicate<T> condition, T input, Consumer<T> action, Consumer<T> actionElse) {
		if (condition.test(input)) {
			action.accept(input);
		}
		else {
			actionElse.accept(input);
		}
	}
}
