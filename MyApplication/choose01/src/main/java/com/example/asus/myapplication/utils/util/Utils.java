package com.example.asus.myapplication.utils.util;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/18
 * Description:
 */
public class Utils {
    public static boolean stringIsUsed(String string) {

        return string == null ? false : true == "".equals(string) ? false : true;
    }

}
