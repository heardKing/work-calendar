package com.cuixx.wday.util;

public class EveryDayUtils {

    public static boolean strIsNum(String str){
        try {
            Integer.valueOf(str);//把字符串强制转换为数字
            return true;//如果是数字，返回True
        } catch (Exception e) {
            return false;//如果抛出异常，返回False
        }
    }

}
