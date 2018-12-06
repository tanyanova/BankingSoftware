package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {

    private ResourceBundle res = ResourceBundle.
            getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.
                getManipulatorByCurrencyCode(currencyCode);
        String[] strings = ConsoleHelper.getValidTwoDigits(currencyCode);
        try {
            int amount = Integer.parseInt(strings[0]) * Integer.parseInt(strings[1]);
            CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode).addAmount(Integer.parseInt(strings[0]), Integer.parseInt(strings[0]));
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, currencyCode));

        }catch (NumberFormatException e) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
        manipulator.addAmount(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
    }
}
