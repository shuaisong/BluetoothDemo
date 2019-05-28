package com.reeching.hotfixlibrary;

import java.lang.reflect.Array;

/**
 * Created by lenovo on 2019/5/28.
 * auther:lenovo
 * Date：2019/5/28
 */
class ArrayUtil {
    public static Object combineArray(Object myDexElements, Object systemDexElements) {
        Class<?> componentType = myDexElements.getClass().getComponentType();
        int length = Array.getLength(myDexElements);

        int newLength = length + Array.getLength(systemDexElements);
        Object result = Array.newInstance(componentType, newLength);
        for (int i = 0; i < newLength; i++) {
            if (i < length) {
                Array.set(result, i, Array.get(myDexElements, i));
            } else {
                Array.set(result, i, Array.get(systemDexElements, i - length));
            }
        }
        return result;
    }
}
