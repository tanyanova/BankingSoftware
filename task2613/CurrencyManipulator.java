package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count){
        if (denominations.containsKey(denomination)){
            int tempCount = denominations.get(denomination);
            tempCount += count;
            denominations.put(denomination, tempCount);
        }
        else denominations.put(denomination, count);
    }

    public int getTotalAmount(){
        int summ = 0;
        for (Map.Entry<Integer, Integer> m : denominations.entrySet()){
            summ += m.getKey() * m.getValue();
        }
        return summ;
    }

    public boolean hasMoney(){
        if (this.getTotalAmount() > 0)
            return true;
        else return false;
    }

    public boolean isAmountAvailable(int expectedAmount){
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> nominalMap = new TreeMap<>(Collections.reverseOrder());
        Map<Integer, Integer> result = new TreeMap<>(Collections.reverseOrder());

        putListInMap(nominalMap);

        for(int i = 0; i < nominalMap.size(); ++i) {
            int tempMoney = expectedAmount;
            if(i != 0)
                nominalMap = returnedMap(result, nominalMap, i);
            for (Map.Entry<Integer, Integer> entry : nominalMap.entrySet()) {
                int nominal = entry.getKey();
                int count = entry.getValue();
                for(int j = 0; j < count; ++j) {
                    if(tempMoney - nominal >= 0) {
                        tempMoney -= nominal;
                        if(result.containsKey(nominal))
                            result.put(nominal, result.get(nominal) + 1);
                        else
                            result.put(nominal, j + 1);
                    }
                    else
                        break;
                }

                if(tempMoney == 0) {
                    toRemoveTheBanknotes(result);
                    return result;
                }

            }
        }

        throw new NotEnoughMoneyException();
    }

    private void putListInMap(Map<Integer, Integer> map) {
        CurrencyManipulator cm = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        for(Map.Entry<Integer, Integer> entry : cm.denominations.entrySet())
            if(entry.getValue() != 0)
                map.put(entry.getKey(), entry.getValue());
            else
                map.remove(entry.getKey());
    }

    private static Map<Integer, Integer> returnedMap(Map<Integer, Integer> result, Map<Integer, Integer> map, int i) {
        Map<Integer, Integer> tmpMap = new TreeMap<>(Collections.reverseOrder());
        tmpMap.putAll(map);
        map.clear();
        result.clear();
        for(Map.Entry<Integer, Integer> entry : tmpMap.entrySet()) {
            if(i == 0)
                map.put(entry.getKey(), entry.getValue());
            else --i;
        }
        return map;
    }

    private void toRemoveTheBanknotes(Map<Integer, Integer> result) {
        for(Map.Entry<Integer, Integer> entry : result.entrySet()) {
            denominations.put(entry.getKey(), denominations.get(entry.getKey()) - entry.getValue());
            if(denominations.get(entry.getKey()) == 0)
                denominations.remove(entry.getKey());
        }

    }

}
