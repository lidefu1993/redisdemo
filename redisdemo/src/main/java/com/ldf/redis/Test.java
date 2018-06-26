package com.ldf.redis;

import java.util.regex.Pattern;

/**
 * Created by ldf on 2018/6/26.
 */
public class Test {

    public static void main(String[] args){
        String content = "ldf1";
        String pre = ".*ldf.*";
        boolean matches = Pattern.matches(pre, content);
        System.out.println(matches);
    }

}
