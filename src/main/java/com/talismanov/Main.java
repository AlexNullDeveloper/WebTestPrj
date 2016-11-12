package com.talismanov;

import com.talismanov.apiparsers.testHHVacancies;

public class Main {
    public static void main(String[] args) {
        testHHVacancies test = new testHHVacancies();
        try {
            test.sendGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}