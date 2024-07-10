package com.revature.bankingapp;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("Hello texas?");

        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(10);
        arr.add(20);
        arr.add(30);

        Integer sum = 0;
        for(int e: arr)
        {
            sum += e;
        }
        System.out.println(sum);


    }
}