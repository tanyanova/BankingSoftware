package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command{

    private ResourceBundle res = ResourceBundle.
            getBundle(CashMachine.RESOURCE_PATH + "login_en");

    private ResourceBundle validCreditCards = ResourceBundle.
            getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");

    @Override
    public void execute() throws InterruptOperationException {
        String stringNumber = null;
        String stringPin = null;
        ConsoleHelper.writeMessage(res.getString("before"));
        while (stringNumber == null){
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            stringNumber = ConsoleHelper.readString();
            if (!stringNumber.matches("\\d{12}")){
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), stringNumber));
                stringNumber = null;
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
            else {
                if (!validCreditCards.containsKey(stringNumber)){
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), stringNumber));
                    stringNumber = null;
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            }
        }

        while (stringPin == null){
            stringPin = ConsoleHelper.readString();
            if (!stringPin.matches("\\d{4}")){
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), stringPin));
                stringPin = null;
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
            else {
                if (!validCreditCards.getString(stringNumber).equals(stringPin)){
                    ConsoleHelper.writeMessage(res.getString("not.verified.format"));
                    stringPin = null;
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            }
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), stringNumber));
    }
}
