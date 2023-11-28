package com.finova.finovabackendcommon.utils;


import java.util.Random;

/**
 * 随机生成验证码工具类
 */
public class GenerateRandomCode {

    public static final String VERIFY_CODES = "1234567890";

    /**
     * 随机生成验证码，不指定字符源(使用默认字符源)
     * @param verifySize 验证码长度
     * @return 验证码
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 随机生成验证码，指定字符源
     * @param verifySize 验证码长度
     * @param sources 字符源
     * @return 验证码
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) { // 若字符源为空则使用默认字符源
            sources = VERIFY_CODES;
        }

        int codeLen = sources.length(); // 字符源长度
        Random rand = new Random(System.currentTimeMillis()); // 随机数种子
        StringBuilder verifyCode = new StringBuilder(verifySize); // 随机数码
        for (int i = 0; i < verifySize; i++) {
            // 每位随机填入一个字符(来自字符源)
            verifyCode.append(sources.charAt(rand.nextInt(codeLen - 1)));
        }

        return verifyCode.toString();
    }
}
