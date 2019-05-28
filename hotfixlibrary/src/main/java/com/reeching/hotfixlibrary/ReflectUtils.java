package com.reeching.hotfixlibrary;

import java.lang.reflect.Field;

/**
 * Created by lenovo on 2019/5/28.
 * auther:lenovo
 * Date：2019/5/28
 */
class ReflectUtils {

    /**
     * 通过反射机制获取对象，并设置私有可访问
     * @param object
     * @param clazz
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object object,Class<?> clazz,String field)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = clazz.getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }

    /**给属性赋值，并私有可访问
     * @param object
     * @param clazz
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setField(Object object,Class<?> clazz,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field dexElements = clazz.getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        dexElements.set(object,value);
    }

    public static Object getPathList(Object baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    public static Object getDexElements(Object pathList) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        return getField(pathList,pathList.getClass(),"dexElements");
    }
}
