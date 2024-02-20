package io.rviewer.commands;

import io.rviewer.Drink;
import org.mockito.Mock;
import io.rviewer.DrinkType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import io.rviewer.repository.DrinkRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import io.rviewer.utils.exception.MoneyRequestException;
import io.rviewer.utils.exception.SugarRequestException;
import io.rviewer.utils.exception.DrinkTypeRequestException;

@ExtendWith(MockitoExtension.class)
class MakeDrinkTest {

    @Mock
    private DrinkRepository drinkRepo;

    /**
     * Method under test:
     * {@link MakeDrinkImpl#makeDrink(String, Float, Integer, Boolean)}
     */
    @Test
    void makeDrink_DrinkTypeRequestException() {
        Assertions.assertThrows(DrinkTypeRequestException.class, () -> (new MakeDrinkImpl(drinkRepo)).makeDrink("water", 10.0f, 1, true));
    }

    /**
     * Method under test: {@link MakeDrinkImpl#moneyMadeByDrinkType(String)}
     */
    @Test
    void moneyMadeByDrinkType_success() throws DrinkTypeRequestException {
        var response = new MakeDrinkImpl(drinkRepo).moneyMadeByDrinkType("coffee");
        Assertions.assertEquals(response, "We had earn 0 selling coffee");
    }

    /**
     * Method under test: {@link MakeDrinkImpl#moneyMadeByDrinkType(String)}
     */
    @Test
    void moneyMadeByDrinkType_DrinkTypeRequestException() {
        Assertions.assertThrows(DrinkTypeRequestException.class,
                () -> (new MakeDrinkImpl(drinkRepo)).moneyMadeByDrinkType("Drinktype"));
    }

    /**
     * Method under test: {@link MakeDrinkImpl#validateDrinkValues(Drink)}
     */
    @Test
    void makeDrink_sugar_and_hot_success() throws SugarRequestException, DrinkTypeRequestException, MoneyRequestException {

        var response = new MakeDrinkImpl(drinkRepo).makeDrink("tea", 10.0f, 1, true);
        Assertions.assertEquals(response, "You have ordered a tea extra hot with 1 sugars (stick included).");
    }

    @Test
    void makeDrink_sugar_success() throws SugarRequestException, DrinkTypeRequestException, MoneyRequestException {

        var response = new MakeDrinkImpl(drinkRepo).makeDrink("tea", 10.0f, 1, false);
        Assertions.assertEquals(response, "You have ordered a tea with 1 sugars (stick included).");
    }

    @Test
    void makeDrink_extraHot_success() throws SugarRequestException, DrinkTypeRequestException, MoneyRequestException {

        var response = new MakeDrinkImpl(drinkRepo).makeDrink("chocolate", 10.0f, 0, true);
        Assertions.assertEquals(response, "You have ordered a chocolate extra hot.");
    }

    @Test
    void makeDrink_success() throws SugarRequestException, DrinkTypeRequestException, MoneyRequestException {

        var response = new MakeDrinkImpl(drinkRepo).makeDrink("coffee", 10.0f, 0, false);
        Assertions.assertEquals(response, "You have ordered a coffee ");
    }

    /**
     * Method under test: {@link MakeDrinkImpl#isMoneyValid(DrinkType, Float)}
     */
    @Test
    void isMoneyValid_MoneyRequestException() {
        Assertions.assertThrows(MoneyRequestException.class, () -> (new MakeDrinkImpl(drinkRepo))
                                                        .isMoneyValid(DrinkType.TEA, 0.3f));
    }

    /**
     * Method under test: {@link MakeDrinkImpl#isSugarValid(Integer)}
     */
    @Test
    void testIsSugarValid() {
        Assertions.assertThrows(SugarRequestException.class, () -> (new MakeDrinkImpl(drinkRepo)).isSugarValid(-1));
        Assertions. assertThrows(SugarRequestException.class, () -> (new MakeDrinkImpl(drinkRepo)).isSugarValid(3));
    }

}
