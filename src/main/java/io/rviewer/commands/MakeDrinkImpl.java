package io.rviewer.commands;

import io.rviewer.Drink;
import io.rviewer.DrinkType;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import io.rviewer.repository.DrinkRepository;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import io.rviewer.utils.exception.MoneyRequestException;
import io.rviewer.utils.exception.SugarRequestException;
import org.springframework.shell.standard.ShellComponent;
import io.rviewer.utils.exception.DrinkTypeRequestException;

@ShellComponent
@AllArgsConstructor
public class MakeDrinkImpl implements MakeDrink {

    private final String RIGHT_ORDER_ARGS = "You have ordered a #drinkType ";
    private final String EARNING_BY_DRINK_TYPE = "We had earn #totalEarn selling ";
    private final String ORDER_EXTRA_HOT = RIGHT_ORDER_ARGS.concat("extra hot.");
    private final String ORDER_WITH_SUGAR = RIGHT_ORDER_ARGS.concat("with #amountSugar sugars (stick included).");
    private final String ORDER_WITH_SUGAR_AND_HOT = RIGHT_ORDER_ARGS.concat("extra hot with #amountSugar sugars (stick included).");

    private final DrinkRepository drinkRepo;

    @Override
    @ShellMethod(key = "makedrink", value = "Start the process to make the drink.")
    public String makeDrink(@ShellOption(value = {"-t", "--type"}, help = "tea, coffee or chocolate") String drinktype,
                          @ShellOption(value = {"-m", "--money"}, help = "Ex: 0.4") Float money,
                          @ShellOption(value = {"-s", "--sugar"}, help = "values allow 1, 2 or 3", defaultValue = "0") Integer sugar,
                          @ShellOption(value = {"-e", "--extra-hot"}) Boolean extraHot) throws DrinkTypeRequestException, MoneyRequestException, SugarRequestException {

        var drink = new Drink(drinktype, money, sugar, extraHot);
        validateDrinkValues(drink);

        drinkRepo.save(drink);

        if(sugar > 0 && extraHot) {
            return ORDER_WITH_SUGAR_AND_HOT.replaceFirst("#drinkType", drinktype)
                                           .replaceFirst("#amountSugar", String.valueOf(sugar));

        } else if(sugar > 0) {
            return ORDER_WITH_SUGAR.replaceFirst("#drinkType", drinktype)
                                   .replaceFirst("#amountSugar", String.valueOf(sugar));

        } else if(extraHot) {
            return ORDER_EXTRA_HOT.replaceFirst("#drinkType", drinktype);
        }

        return RIGHT_ORDER_ARGS.replaceFirst("#drinkType", drinktype);
    }

    @Override
    @ShellMethod(key = "moneymade", value = "Get the amount earn by drink type.")
    public String moneyMadeByDrinkType(@ShellOption(value = {"-t", "--type"}, help = "tea, coffee or chocolate") String drinktype) throws DrinkTypeRequestException {

        var drinkType = isDrinkTypeValid(drinktype);

        var orders = drinkRepo.getAllByDrinkType(drinkType.type);
        var ordersEarning = orders.stream()
                .mapToDouble(Drink::getMoney)
                .sum();
        var formattedEarning = new DecimalFormat("#.###").format(ordersEarning);
        return EARNING_BY_DRINK_TYPE.replaceFirst("#totalEarn", formattedEarning).concat(drinkType.type);
    }

    protected void validateDrinkValues(Drink drink) throws DrinkTypeRequestException, MoneyRequestException, SugarRequestException {

        var drinkType = isDrinkTypeValid(drink.getDrinkType());
        isMoneyValid(drinkType, drink.getMoney());
        isSugarValid(drink.getSugar());

    }

    protected DrinkType isDrinkTypeValid(String drinkType) throws DrinkTypeRequestException {

        try {
        return DrinkType.valueOf(drinkType.toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new DrinkTypeRequestException("The drink type should be tea, coffee or chocolate.");
        }

    }

    protected void isMoneyValid(DrinkType drinkType, Float money) throws MoneyRequestException {

        if (money < drinkType.price) {
            throw new MoneyRequestException("The ".concat(drinkType.type).concat(" costs: ")
                                            .concat(String.valueOf(drinkType.price)));
        }

    }

    protected void isSugarValid(Integer sugar) throws SugarRequestException {

        if (sugar < 0 || sugar > 2) {
            throw new SugarRequestException("The number of sugars should be between 0 and 2.");
        }
    }
}
