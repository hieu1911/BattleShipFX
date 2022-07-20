package com.example.battleshipfx.helpz;

import javafx.concurrent.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoadSave {
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(e -> continuation.run());
        new Thread(sleeper).start();
    }

    public static void writeToFile(File f, int[] arr) {
        try {
            PrintWriter pv = new PrintWriter(f);
            for (int i : arr) {
                pv.println(i);
            }
            pv.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int[] readFromFile(File f) {
        int[] arr = new int[6];
        int i = 0;
        try {
            Scanner scanner = new Scanner(f);
            while(scanner.hasNextLine()) {
                arr[i] = Integer.parseInt(scanner.nextLine());
                ++i;
//                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arr;
    }

}
