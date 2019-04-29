package com.reeching.bluetoothdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by lenovo on 2019/4/24.
 * auther:lenovo
 * Date：2019/4/24
 */
public class MainActivityTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("测试开始");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("测试结束");
    }

    @Test
    public void onCreate() {
        System.out.println("onCreate");
    }
}