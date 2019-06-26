package com.kris.classsystem.Util;


import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 暂时无法将js中md5加密代码转为java版
 **/
public class Md5Util {

    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
