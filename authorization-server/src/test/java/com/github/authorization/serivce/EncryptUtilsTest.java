package com.github.authorization.serivce;

import com.github.core.encryption.EncryptUtils;

/**
 * 加密测试类
 * @author Rong.Jia
 * @date 2020/02/21 22:34
 */
public class EncryptUtilsTest {

    public static void main(String[] args) {

        String generateSecretKey = EncryptUtils.generateSecretKey(EncryptUtils.AES, "123456", 128);

        System.out.println(generateSecretKey);

        String encodeAES = EncryptUtils.encodeAES("a", generateSecretKey);

        System.out.println("encodeAES:"+ encodeAES);

        String decodeAES = EncryptUtils.decodeAES(encodeAES, generateSecretKey);

        System.out.println("decodeAES:" +decodeAES);


    }


}
