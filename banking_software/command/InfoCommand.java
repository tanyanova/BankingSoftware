package com.javarush.task.task26.task2613.command;


import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.
            getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() throws InterruptOperationException {

        List<CurrencyManipulator> manipulators = new ArrayList<>
                (CurrencyManipulatorFactory.getAllCurrencyManipulators());

        if (manipulators.isEmpty()){
            ConsoleHelper.writeMessage(res.getString("no.money"));
            return;
        }

        ConsoleHelper.writeMessage(res.getString("before"));

        for (CurrencyManipulator manipulator : manipulators){
            if (manipulator.hasMoney()){
                ConsoleHelper.writeMessage(manipulator.getCurrencyCode() + " - " +
                        manipulator.getTotalAmount());
            }
            else ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
