package com.hunter.MailTest;

public class test {
    public static void main(String[] args) {
        System.out.println("开始=====");
        String s = demo();
        System.out.println(s);
    }

    public static String demo() {
        try {
            int i = 1 / 0;
            return "0";
        } catch (Exception e) {
            System.out.println("异常");
            return "1";
        } finally {
            System.out.println("finally");
            return "2";
        }
    }
}
