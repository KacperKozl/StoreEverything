package com.example.storeeverything.services;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.*;


public class crypto {
    private SecretKeySpec key=new SecretKeySpec((new String("klucz123klucz123")).getBytes(), "AES");;
    private final int KEY_SIZE = 128;
    private final int DATA_LENGTH = 128;
    private Cipher encryptionCipher;
    IvParameterSpec ivParameterSpec;

    public crypto(){
        try{
            byte[] iv = {
                1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4
            };
            ivParameterSpec=new IvParameterSpec(iv);
        } catch (Exception e){}
    }

    public String encrypt(String str) throws Exception{

        byte[] dataInBytes = str.getBytes();
        encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key,ivParameterSpec);
        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
        return Base64.encodeBase64String(encryptedBytes);
    }

    public String decrypt(String str) throws Exception{

        byte[] dataInBytes = Base64.decodeBase64(str);
        Cipher decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key,ivParameterSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }
}
