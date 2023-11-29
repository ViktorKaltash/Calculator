package com.calculator;

public class ConsoleLogger {
    public static boolean isRunning = true;
//    public static boolean permitDigits;
//    public static boolean permitCommands;

    static public void printlnToConsole(String str) {
        if (!isRunning) {
            return;
        }
        System.out.println(str);
    }

    static public void printToConsole(String str) {
        if (!isRunning) {
            return;
        }
        System.out.print(str);
    }

    static public void printlnToConsole(String str, String mode) {
        if (!isRunning) {
            return;
        }
        System.out.println("[" + mode + "] : " + str);
    }

    static public void printToConsole(String str, String mode) {
        if (!isRunning) {
            return;
        }
        System.out.print("[" + mode + "] : " + str);
    }
}
