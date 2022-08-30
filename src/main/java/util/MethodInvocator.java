package util;

import java.lang.reflect.Method;

public class MethodInvocator<T> {

    private final Class<?> clazz;

    public MethodInvocator(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object invokeCommands(String methodName, Object[] parameters) {
        try {
            Object newClazzInstance = clazz.getDeclaredConstructors()[0].newInstance();
            Class<?>[] parameterTypes = new Class<?>[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }

            Method clazzMethod = clazz.getMethod(methodName, parameterTypes);
            return clazzMethod.invoke(newClazzInstance, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
