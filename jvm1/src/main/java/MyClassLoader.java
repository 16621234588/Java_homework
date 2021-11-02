import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final String className = "Hello";
        final String methodName = "hello";

        ClassLoader classLoader = new MyClassLoader();
        Class<?> aClass = classLoader.loadClass(className);
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            System.out.println(aClass.getSimpleName() + "." + declaredMethod.getName());
        }

        Object newInstance = aClass.getDeclaredConstructor().newInstance();
        Method method = aClass.getMethod(methodName);
        method.invoke(newInstance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String resourcePath = name.replace(".", "/");
        final String suffix = ".xlass";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourcePath + suffix);
        try {
            int length = resourceAsStream.available();
            byte[] bytes = new byte[length];
            resourceAsStream.read(bytes);
            byte[] decodeBytes = decode(bytes);
            return defineClass(name, decodeBytes, 0, decodeBytes.length);

        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static byte[] decode(byte[] bytes) {
        byte[] targetBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            targetBytes[i] = (byte) (255 - bytes[i]);
        }
        return targetBytes;
    }


}
