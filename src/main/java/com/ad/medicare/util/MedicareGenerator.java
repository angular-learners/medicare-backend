package com.ad.medicare.util;

import java.util.Random;

public class MedicareGenerator {

    public static String generateId(String roleName){
        Random random=new Random();
        return roleName.substring(5,9)+random.nextInt(10000);
    }
}
