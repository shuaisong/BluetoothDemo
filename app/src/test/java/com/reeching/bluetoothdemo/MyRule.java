package com.reeching.bluetoothdemo;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;

/**
 * Created by lenovo on 2019/4/24.
 * auther:lenovo
 * Date：2019/4/24
 */
public class MyRule implements TestRule {



    @Override
    public Statement apply(Statement base, Description description) {
        // evaluate前执行方法相当于@Before
        String methodName = description.getMethodName(); // 获取测试方法的名字
        System.out.println(methodName + "测试开始！");

        try {
            base.evaluate();  // 运行的测试方法
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // evaluate后执行方法相当于@After
        System.out.println(methodName + "测试结束！");
        return base;
    }
}
