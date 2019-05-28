package com.reeching.hotfixlibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lenovo on 2019/5/28.
 * auther:lenovo
 * Date：2019/5/28
 */
public class FileUtils {
    public static void copyDexFile(File sourceFile,File targetFlie) throws IOException {
        FileInputStream inputStream = new FileInputStream(sourceFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        FileOutputStream outputStream = new FileOutputStream(targetFlie);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        byte[] bytes = new byte[1024 * 5];

        int len;
        while ((len = bufferedInputStream.read(bytes))!=-1){
            bufferedOutputStream.write(bytes,0,len);
        }
        bufferedOutputStream.flush();
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }
}
