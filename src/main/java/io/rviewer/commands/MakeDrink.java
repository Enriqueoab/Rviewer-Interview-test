package io.rviewer.commands;

import io.rviewer.utils.exception.MoneyRequestException;
import io.rviewer.utils.exception.SugarRequestException;
import io.rviewer.utils.exception.DrinkTypeRequestException;

public interface MakeDrink {
    String makeDrink(String drinktype, Float money, Integer sugar, Boolean extraHot) throws DrinkTypeRequestException,
                                                                          MoneyRequestException, SugarRequestException;
    String moneyMadeByDrinkType( String drinktype) throws DrinkTypeRequestException;
}
