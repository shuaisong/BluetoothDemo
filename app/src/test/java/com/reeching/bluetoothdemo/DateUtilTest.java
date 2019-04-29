package com.reeching.bluetoothdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by lenovo on 2019/4/24.
 * auther:lenovo
 * Date：2019/4/24
 */
//@RunWith(Parameterized.class)
public class DateUtilTest {
    private String time = "2019-04-24 18:19:38";
    private long timeStamp = 1556101178000L;
    private Date date;
    @Rule
    public MyRule myRule = new MyRule();
   /* public DateUtilTest(String time) {
        this.time = time;
    }*/

    @Before
    public void setUp() throws Exception {
        System.out.println("测试开始");
        date = new Date();
        date.setTime(timeStamp);
    }

//    @Parameterized.Parameters
//    public static Collection priNums() {
////        return Arrays.asList(new Object[]{"2019-04-24", 1556101178000L}, new Object[]{"2019-04-24 18:19:38", 1556101178000L}, new Object[]{"2019-04-24 11:22:22", 1556101178000L});
//
//        return Arrays.asList("2019-04-24 18:19:38", "2019-04-24 18:19:38", "2019-04-24 11:22:22");
//    }

    @After
    public void tearDown() throws Exception {
        System.out.println("测试结束");
    }

    @Test/*(expected = ParseException.class)*/
    public void dateToStamp() throws ParseException {
//        System.out.println(time);
//        DateUtil.dateToStamp(time);
        assertEquals("预期时间", DateUtil.dateToStamp(time), timeStamp);
    }

    @Test
    public void stampToDate() {
        assertEquals("预期时间", DateUtil.stampToDate(timeStamp), time);
    }
}