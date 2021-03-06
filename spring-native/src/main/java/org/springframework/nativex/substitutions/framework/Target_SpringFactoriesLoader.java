package org.springframework.nativex.substitutions.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import org.apache.commons.logging.Log;

import org.springframework.lang.Nullable;
import org.springframework.nativex.substitutions.OnlyIfPresent;
import org.springframework.nativex.substitutions.WithAot;
import org.springframework.util.Assert;

@TargetClass(className="org.springframework.core.io.support.SpringFactoriesLoader", onlyWith = { WithAot.class, OnlyIfPresent.class })
final class Target_SpringFactoriesLoader {

	@Alias
	private static Log logger;

	@SuppressWarnings("unchecked")
	@Substitute
	public static <T> List<T> loadFactories(Class<T> factoryType, @Nullable ClassLoader classLoader) {
		Assert.notNull(factoryType, "'factoryType' must not be null");
		List<Supplier<Object>> result = Target_StaticSpringFactories.factories.get(factoryType);
		if (result == null) {
			return new ArrayList<>(0);
		}
		List<T> factories = new ArrayList<>(result.size());
		for (Supplier<Object> supplier: result) {
			// TODO: protect against factories that fail during instantiation
			try {
				factories.add((T) supplier.get());
			}
			catch (Throwable throwable) {
				logger.trace("Could not instantiate factory for " + factoryType, throwable);
			}
		}
		return factories;
	}

	@Substitute
	public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
		List<String> result = new ArrayList<>();
		List<String> names = Target_StaticSpringFactories.names.get(factoryType);
		if (names != null) {
			result.addAll(names);
		}
		List<Supplier<Object>> stored = Target_StaticSpringFactories.factories.get(factoryType);
		if (stored != null) {
			for (Supplier<Object> supplier: stored) {
				try {
					result.add(supplier.get().getClass().getName());
				}
				catch (Throwable throwable) {
					logger.trace("Could not get factory name for " + factoryType, throwable);
				}
			}
		}
		return result;
	}

}