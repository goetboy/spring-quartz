package pers.goetboy.common.utils;


import pers.goetboy.common.exception.CommonsAssistantException;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * class工具类
 *
 * @author goetb
 */
public class ClassUtils {

    /**
     * Map keyed by class containing CachedIntrospectionResults.
     * Needs to be a WeakHashMap with WeakReferences as values to allow
     * for proper garbage collection in case of multiple class loaders.
     */
    private static final Map<Class<?>, BeanInfo> CLASS_CACHE = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());

    public static Field findField(Class<?> clazz, String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            return findDeclaredField(clazz, name);
        }
    }

    public static Field findDeclaredField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredField(clazz.getSuperclass(), name);
            }
            return null;
        }
    }

    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return findDeclaredMethod(clazz, methodName, paramTypes);
        }
    }

    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
            }
            return null;
        }
    }

    public static Object getProperty(Object obj, String name) throws NoSuchFieldException {
        Object value = null;
        Field field = findField(obj.getClass(), name);
        if (field == null) {
            throw new NoSuchFieldException("no such field [" + name + "]");
        }
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            value = field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(accessible);
        return value;
    }

    public static void setProperty(Object obj, String name, Object value) throws NoSuchFieldException {
        Field field = findField(obj.getClass(), name);
        if (field == null) {
            throw new NoSuchFieldException("no such field [" + name + "]");
        }
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(accessible);
    }

    public static Map<String, Object> obj2Map(Object obj, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap();
        }
        if (obj != null) {
            try {
                Class<?> clazz = obj.getClass();
                do {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        int mod = field.getModifiers();
                        if (Modifier.isStatic(mod)) {
                            continue;
                        }
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        map.put(field.getName(), field.get(obj));
                        field.setAccessible(accessible);
                    }
                    clazz = clazz.getSuperclass();
                } while (clazz != null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    /**
     * 获得父类集合，包含当前class
     *
     * @param clazz
     * @return
     */
    public static List<Class<?>> getSuperclassList(Class<?> clazz) {
        List<Class<?>> clazzes = new ArrayList<Class<?>>(3);
        clazzes.add(clazz);
        clazz = clazz.getSuperclass();
        while (clazz != null) {
            clazzes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return Collections.unmodifiableList(clazzes);
    }

    /**
     * 获取类本身的BeanInfo，不包含父类属性
     *
     * @param clazz
     * @return
     */
    public static BeanInfo getBeanInfo(Class<?> clazz, Class<?> stopClazz) {
        try {
            BeanInfo beanInfo;
            if (CLASS_CACHE.get(clazz) == null) {
                beanInfo = Introspector.getBeanInfo(clazz, stopClazz);
                CLASS_CACHE.put(clazz, beanInfo);
                // Immediately remove class from Introspector cache, to allow for proper
                // garbage collection on class loader shutdown - we cache it here anyway,
                // in a GC-friendly manner. In contrast to CachedIntrospectionResults,
                // Introspector does not use WeakReferences as values of its WeakHashMap!
                Class<?> classToFlush = clazz;
                do {
                    Introspector.flushFromCaches(classToFlush);
                    classToFlush = classToFlush.getSuperclass();
                } while (classToFlush != null);
            } else {
                beanInfo = CLASS_CACHE.get(clazz);
            }
            return beanInfo;
        } catch (IntrospectionException e) {
            throw new CommonsAssistantException("获取BeanInfo失败", e);
        }
    }

    /**
     * 获取类的BeanInfo,包含父类属性
     *
     * @param clazz
     * @return
     */
    public static BeanInfo getBeanInfo(Class<?> clazz) {

        return getBeanInfo(clazz, Object.class);
    }

    /**
     * 获取类本身的BeanInfo，不包含父类属性
     *
     * @param clazz
     * @return
     */
    public static BeanInfo getSelfBeanInfo(Class<?> clazz) {

        return getBeanInfo(clazz, clazz.getSuperclass());
    }

    /**
     * 获取类属性的PropertyDescriptor
     *
     * @param clazz
     * @param name
     * @return
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String name) {
        BeanInfo beanInfo = getBeanInfo(clazz);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors == null) {
            return null;
        }
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (StringUtils.equals(pd.getName(), name)) {
                return pd;
            }
        }
        return null;
    }

    /**
     * bean属性转换为map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> getBeanPropMap(Object object) {

        BeanInfo beanInfo = getBeanInfo(object.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors == null) {
            return null;
        }
        Map<String, Object> propMap = new HashMap();
        for (PropertyDescriptor pd : propertyDescriptors) {

            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                continue;
            }
            Object value = invokeMethod(readMethod, object);
            propMap.put(pd.getName(), value);
        }
        return propMap;
    }

    /**
     * invokeMethod
     *
     * @param method
     * @param bean
     * @param value
     */
    public static void invokeMethod(Method method, Object bean, Object value) {
        try {
            methodAccessible(method);
            method.invoke(bean, value);
        } catch (Exception e) {
            throw new CommonsAssistantException("执行invokeMethod失败:" + (method == null ? "null" : method.getName()), e);
        }
    }

    /**
     * invokeMethod
     *
     * @param method
     * @param bean
     */
    public static Object invokeMethod(Method method, Object bean) {
        try {
            methodAccessible(method);
            return method.invoke(bean);
        } catch (Exception e) {
            throw new CommonsAssistantException("执行invokeMethod失败:" + (method == null ? "null" : method.getName()), e);
        }
    }

    /**
     * 设置method访问权限
     *
     * @param method
     */
    public static void methodAccessible(Method method) {
        if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            method.setAccessible(true);
        }
    }

    /**
     * 初始化实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new CommonsAssistantException("根据class创建实例失败:" + (clazz == null ? "null" : clazz.getName()), e);
        }
    }

    /**
     * 初始化实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(String clazz) {

        try {
            Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
            return loadClass.newInstance();
        } catch (Exception e) {
            throw new CommonsAssistantException("根据class创建实例失败:" + clazz, e);
        }
    }

    /**
     * 加载类
     *
     * @param clazz
     * @return
     */
    public static Class<?> loadClass(String clazz) {
        try {
            Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
            return loadClass;
        } catch (Exception e) {
            throw new CommonsAssistantException("根据class名称加载class失败:" + clazz, e);
        }
    }

    /**
     * 将value的数据类型转换到实际目标类型
     *
     * @param value
     * @return
     */
    public static Object toTargetTypeValue(Object value, Class<?> targetType) {
        String typeName = targetType.getName();
        if (StringUtils.equals(typeName, boolean.class.getName()) || StringUtils.equals(typeName, Boolean.class.getName())) {
            return Boolean.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, int.class.getName()) || StringUtils.equals(typeName, Integer.class.getName())) {
            return Integer.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, long.class.getName()) || StringUtils.equals(typeName, Long.class.getName())) {
            return Long.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, short.class.getName()) || StringUtils.equals(typeName, Short.class.getName())) {
            return Short.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, float.class.getName()) || StringUtils.equals(typeName, Float.class.getName())) {
            return Float.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, double.class.getName()) || StringUtils.equals(typeName, Double.class.getName())) {
            return Double.valueOf(value.toString());
        }
        if (StringUtils.equals(typeName, byte.class.getName()) || StringUtils.equals(typeName, Byte.class.getName())) {
            return Byte.valueOf(value.toString());
        }
        return value;
    }

    /**
     * 当前线程的classLoader
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}