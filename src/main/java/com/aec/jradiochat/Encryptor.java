package com.aec.jradiochat;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor  {

    private Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    private SecretKeySpec key;
    public Encryptor(String sKey) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException  {
        // TODO Auto-generated constructor stub
        Security.addProvider(new BouncyCastleProvider());
        key = new SecretKeySpec(sKey.getBytes("UTF-8"), "AES/ECB/PKCS5Padding");
    }
    
    public byte[] encrypt(String msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(msg.getBytes("UTF-8"));
    }
    
    public byte[] decrypt(byte[] msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(msg);
    }
    

}
