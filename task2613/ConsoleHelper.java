package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.
            getBundle(CashMachine.class.getPackage().getName() + ".resources.common_en");

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException{
        try {
            String string = bis.readLine();
            if (string.equalsIgnoreCase("EXIT")){
            throw new InterruptOperationException();
           }
            return string;
        } catch (IOException e) {}
            return null;
    }
    public static String askCurrencyCode() throws InterruptOperationException{
        String data = null;
        while (data == null){
           writeMessage("Введите код валюты");
            data = readString();
            if (data != null)
                if (data.length() != 3) {
                    data = null;
                    writeMessage("Данные не верны!");
                }
        }
        return data.toUpperCase();
    }
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException{
        String data = null;
        while (data == null){
            writeMessage("Введите номинал и количество банкнот");
            data = readString();
            if (data != null)
                if (!data.matches("\\d+ \\d+")){
                   data = null;
                   writeMessage("Некорректные данные!");
                }
        }
        return data.split(" ");
    }

    public static Operation askOperation() throws InterruptOperationException{
        Operation operation = null;
        while (operation == null) {
            try {
                writeMessage("Введите код операции");
                int code = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(code);
            } catch (IllegalArgumentException e) {
                writeMessage("Даные введены некорректно!");
                operation = null;
            }
        }
        return operation;
    }

    public static void printExitMessage(){
        ConsoleHelper.writeMessage("Good luck!");
    }
}
