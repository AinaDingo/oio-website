package com.inclusiv.oio_project.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
    public static String encryptString(String input) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] bs = messageDigest.digest(input.getBytes());
            BigInteger no = new BigInteger(1,bs);
            String hasString = no.toString(16);
            while(hasString.length()<32){
                hasString = "0"+hasString;
            }
        return hasString;
    }

    public static String encryptStringMD5(String input) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bs = messageDigest.digest(input.getBytes());
            BigInteger no = new BigInteger(1,bs);
            String hasString = no.toString(16);
            while(hasString.length()<32){
                hasString = "0"+hasString;
            }
        return hasString;
    }
}
