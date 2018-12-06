package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {

    private ResourceBundle res = ResourceBundle.
            getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute()throws InterruptOperationException {
        String currency = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator cm = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
        ConsoleHelper.writeMessage(res.getString("before"));
        String sum = null;
        while (sum == null){
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            sum = ConsoleHelper.readString();

            if (!sum.matches("\\d+")){
                sum = null;
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            }
            else if (!cm.isAmountAvailable(Integer.parseInt(sum))){
                sum = null;
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            }
        }

        boolean available;
        do {
            try {
                Map<Integer, Integer> map = cm.withdrawAmount(Integer.parseInt(sum));
                map.entrySet().forEach(i -> System.out.println("\t" + i.getKey() + " - " + i.getValue()));
                available = true;
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"),
                        Integer.parseInt(sum), cm.getCurrencyCode()));
            } catch (NotEnoughMoneyException ex) {
                available = true;
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        } while(!available);

    }
}
