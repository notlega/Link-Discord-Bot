package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.IntStream;

public abstract class MethodInvocator<K, V> {

	private final V[] parameters;
	private final Class<?>[] parameterTypes;

	public MethodInvocator(V[] parameters) {
		this.parameters = parameters;
		this.parameterTypes = new Class<?>[parameters.length];
		IntStream.range(0, parameters.length)
				.forEach(i -> parameterTypes[i] = parameters[i].getClass());
	}

	public ArrayList<K> invokeCommands(String[] classNames, String[] methodNames) {
		ArrayList<K> results = new ArrayList<>();
		try {
			for (String className : classNames) {
				Class<?> clazz = CommandHandler.getCommands().get(className);
				Object newClazzInstance = clazz.getDeclaredConstructors()[0].newInstance();

				for (String methodName : methodNames) {
					Method clazzMethod = clazz.getMethod(methodName, parameterTypes);
					results.add(returnValue((K) clazzMethod.invoke(newClazzInstance, (Object) parameters)));
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			e.printStackTrace();
		}

		return results;
	}

	public abstract K returnValue(K commandData);
}
