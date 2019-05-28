package com.reeching.hotfixlibrary;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by lenovo on 2019/5/28.
 * auther:lenovo
 * Date：2019/5/28
 */
public class FixDexUtils {

    private static HashSet<File> loadDex = new HashSet<File>();

    static {
        loadDex.clear();
    }

    /**加载热修复文件
     * @param context
     */
    public static void laodDex(Context context){
        File dex_dir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        File[] files = dex_dir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX)&&!"class.dex".equals(file.getName())){
                loadDex.add(file);
            }
        }

//        模拟加载器
        createDexClassLoader(context,dex_dir);
    }

    /**创建加载补丁的类加载器
     * @param context
     * @param dex_dir
     */
    private static void createDexClassLoader(Context context, File dex_dir) {
        //创建解压目录

        String optimizedDir = dex_dir.getAbsolutePath()+File.separator+"opt_dex";
        File fopt = new File(optimizedDir);
        if (!fopt.exists()){
            fopt.mkdirs();
        }
        for (File dex : loadDex) {
//            自有的classLoader
            DexClassLoader dexClassLoader = new DexClassLoader(dex.getAbsolutePath(), optimizedDir, null, context.getClassLoader());

            hotFix(context,dexClassLoader);
        }
    }

    private static void hotFix(Context context, DexClassLoader dexClassLoader) {
        //获取系统的pathClassLoader
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            //获取自有的DexElements数组
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(dexClassLoader));
            //获取系统的DexElements数组
            Object systemDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));

            //合并并生成新的数组
            Object newElement = ArrayUtil.combineArray(myDexElements, systemDexElements);
            //获取系统的pathList
            Object systemPathList = ReflectUtils.getPathList(pathClassLoader);

            //反射 赋值新的DexElements給pathlist

            ReflectUtils.setField(systemPathList,systemPathList.getClass(),newElement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }




    }
}
