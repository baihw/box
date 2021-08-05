package com.wee0.box.util.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.IClassUtils;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.Closeable;
import java.io.Externalizable;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/2/27 7:12
 * @Description 一个简单的类处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleClassUtils implements IClassUtils {

    @Override
    public Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotation) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public Set<Class<?>> getClassesByInterface(String packageName, Class<?> interfaceCla) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public Class<?> resolveClassName(String className, ClassLoader classLoader) {
        if (null == className || 0 == (className = className.trim()).length())
            throw new BoxRuntimeException("className can't be empty!");
        if (null == classLoader)
            classLoader = getDefaultClassLoader();
        try {
            return forName(className, classLoader);
        } catch (ClassNotFoundException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Class<?> resolveClassName(String className) {
        return resolveClassName(className, getDefaultClassLoader());
    }

    @Override
    public Set<Class<?>> getClassAllInterfaces(Class<?> cla, ClassLoader classLoader) {
        return _getAllInterfacesForClassAsSet(cla, classLoader);
    }

    @Override
    public Set<Class<?>> getClassAllInterfaces(Class<?> cla) {
        return _getAllInterfacesForClassAsSet(cla, getDefaultClassLoader());
    }

    @Override
    public boolean hasMethod(Class<?> cla, String methodName, Class<?>... paramTypes) {
        return _hasMethod(cla, methodName, paramTypes);
    }

    @Override
    public Method getMethod(Class<?> cla, String methodName, Class<?>... paramTypes) {
        return _getMethod(cla, methodName, paramTypes);
    }

    @Override
    public Class<?>[] getMethodGenericReturnType(Method method, Class clazz) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public String getClassShortNameAsProperty(Class<?> cla) {
        if (null == cla)
            throw new BoxRuntimeException("cla can't be null!");
        return getShortNameAsProperty(cla);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleClassUtils() {
        if (null != SimpleClassUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleClassUtilsHolder {
        private static final SimpleClassUtils _INSTANCE = new SimpleClassUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleClassUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleClassUtils me() {
        return SimpleClassUtilsHolder._INSTANCE;
    }

    /*******************************************************************************
     * 暂借spring中的ClassUtils部分
     *******************************************************************************/

    /**
     * Suffix for array class names: {@code "[]"}.
     */
    public static final String ARRAY_SUFFIX = "[]";

    /**
     * Prefix for internal array class names: {@code "["}.
     */
    private static final String INTERNAL_ARRAY_PREFIX = "[";

    /**
     * Prefix for internal non-primitive array class names: {@code "[L"}.
     */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    /**
     * The package separator character: {@code '.'}.
     */
    private static final char PACKAGE_SEPARATOR = '.';

    /**
     * The path separator character: {@code '/'}.
     */
    private static final char PATH_SEPARATOR = '/';

    /**
     * The inner class separator character: {@code '$'}.
     */
    private static final char INNER_CLASS_SEPARATOR = '$';

    /**
     * The CGLIB class separator: {@code "$$"}.
     */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * The ".class" file suffix.
     */
    public static final String CLASS_FILE_SUFFIX = ".class";

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap<>(8);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap<>(8);
    private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);
    private static final Set<Class<?>> javaLanguageInterfaces;

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
        primitiveWrapperTypeMap.put(Void.class, void.class);

        // Map entry iteration is less expensive to initialize than forEach with lambdas
        for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperTypeMap.entrySet()) {
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
            registerCommonClasses(entry.getKey());
        }

        Set<Class<?>> primitiveTypes = new HashSet<>(32);
        primitiveTypes.addAll(primitiveWrapperTypeMap.values());
        Collections.addAll(primitiveTypes, boolean[].class, byte[].class, char[].class,
                double[].class, float[].class, int[].class, long[].class, short[].class);
        primitiveTypes.add(void.class);
        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }

        registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class,
                Float[].class, Integer[].class, Long[].class, Short[].class);
        registerCommonClasses(Number.class, Number[].class, String.class, String[].class,
                Class.class, Class[].class, Object.class, Object[].class);
        registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class,
                Error.class, StackTraceElement.class, StackTraceElement[].class);
        registerCommonClasses(Enum.class, Iterable.class, Iterator.class, Enumeration.class,
                Collection.class, List.class, Set.class, Map.class, Map.Entry.class, Optional.class);

        Class<?>[] javaLanguageInterfaceArray = {Serializable.class, Externalizable.class,
                Closeable.class, AutoCloseable.class, Cloneable.class, Comparable.class};
        registerCommonClasses(javaLanguageInterfaceArray);
        javaLanguageInterfaces = new HashSet<>(Arrays.asList(javaLanguageInterfaceArray));
    }


    /**
     * Register the given common classes with the ClassUtils cache.
     */
    private static void registerCommonClasses(Class<?>... commonClasses) {
        for (Class<?> clazz : commonClasses) {
            commonClassCache.put(clazz.getName(), clazz);
        }
    }

    // 获取默认的类加载器
    static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {
            cl = SimpleClassUtils.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }
        return cl;
    }

    static Class<?> resolvePrimitiveClassName(String name) {
        Class<?> result = null;
        // Most class names will be quite long, considering that they
        // SHOULD sit in a package, so a length check is worthwhile.
        if (name != null && name.length() <= 8) {
            // Could be a primitive - likely.
            result = primitiveTypeNameMap.get(name);
        }
        return result;
    }

    static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
        name = CheckUtils.checkNotTrimEmpty(name, "name can't be empty!");

        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz == null) {
            clazz = commonClassCache.get(name);
        }
        if (clazz != null) {
            return clazz;
        }

        // "java.lang.String[]" style arrays
        if (name.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[Ljava.lang.String;" style arrays
        if (name.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && name.endsWith(";")) {
            String elementName = name.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // "[[I" or "[[Ljava.lang.String;" style arrays
        if (name.startsWith(INTERNAL_ARRAY_PREFIX)) {
            String elementName = name.substring(INTERNAL_ARRAY_PREFIX.length());
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        ClassLoader clToUse = classLoader;
        if (clToUse == null) {
            clToUse = getDefaultClassLoader();
        }
        try {
            return Class.forName(name, false, clToUse);
        } catch (ClassNotFoundException ex) {
            int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
            if (lastDotIndex != -1) {
                String innerClassName =
                        name.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR + name.substring(lastDotIndex + 1);
                try {
                    return Class.forName(innerClassName, false, clToUse);
                } catch (ClassNotFoundException ex2) {
                    // Swallow - let original exception get through
                }
            }
            throw ex;
        }
    }

    static boolean isLoadable(Class<?> clazz, ClassLoader classLoader) {
        try {
            return (clazz == classLoader.loadClass(clazz.getName()));
            // Else: different class with same name found
        } catch (ClassNotFoundException ex) {
            // No corresponding class found at all
            return false;
        }
    }

    static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        CheckUtils.checkNotNull(classLoader, "classLoader can't be null!");

        if (classLoader == null) {
            return true;
        }
        try {
            if (clazz.getClassLoader() == classLoader) {
                return true;
            }
        } catch (SecurityException ex) {
            // Fall through to loadable check below
        }

        // Visible if same Class can be loaded from given ClassLoader
        return isLoadable(clazz, classLoader);
    }

    static Set<Class<?>> _getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader) {
        CheckUtils.checkNotNull(classLoader, "classLoader can't be null!");

        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return Collections.singleton(clazz);
        }
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        Class<?> current = clazz;
        while (current != null) {
            Class<?>[] ifcs = current.getInterfaces();
            for (Class<?> ifc : ifcs) {
                if (isVisible(ifc, classLoader)) {
                    interfaces.add(ifc);
                }
            }
            current = current.getSuperclass();
        }
        return interfaces;
    }

    static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        CheckUtils.checkNotNull(clazz, "Class must not be null");
        CheckUtils.checkNotNull(methodName, "Method name must not be null");
        if (paramTypes != null) {
            try {
                return clazz.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException ex) {
                return null;
            }
        } else {
            Set<Method> candidates = new HashSet<>(1);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    candidates.add(method);
                }
            }
            if (candidates.size() == 1) {
                return candidates.iterator().next();
            }
            return null;
        }
    }

    static boolean _hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        return (getMethodIfAvailable(clazz, methodName, paramTypes) != null);
    }

    static Method _getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        CheckUtils.checkNotNull(clazz, "Class must not be null");
        CheckUtils.checkNotNull(methodName, "Method name must not be null");
        if (paramTypes != null) {
            try {
                return clazz.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException ex) {
                throw new IllegalStateException("Expected method not found: " + ex);
            }
        } else {
            Set<Method> candidates = new HashSet<>(1);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    candidates.add(method);
                }
            }
            if (candidates.size() == 1) {
                return candidates.iterator().next();
            } else if (candidates.isEmpty()) {
                throw new IllegalStateException("Expected method not found: " + clazz.getName() + '.' + methodName);
            } else {
                throw new IllegalStateException("No unique method found: " + clazz.getName() + '.' + methodName);
            }
        }
    }

    static String getQualifiedName(Class<?> clazz) {
        CheckUtils.checkNotNull(clazz, "Class must not be null");
        return clazz.getTypeName();
    }

    static String getShortName(String className) {
        CheckUtils.checkNotTrimEmpty(className, "Class name must not be empty");
        int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
        return shortName;
    }

    static String getShortName(Class<?> clazz) {
        return getShortName(getQualifiedName(clazz));
    }

    static String getShortNameAsProperty(Class<?> clazz) {
        String shortName = getShortName(clazz);
        int dotIndex = shortName.lastIndexOf(PACKAGE_SEPARATOR);
        shortName = (dotIndex != -1 ? shortName.substring(dotIndex + 1) : shortName);
        return _decapitalize(shortName);
    }

    static String _decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) &&
                Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

}
