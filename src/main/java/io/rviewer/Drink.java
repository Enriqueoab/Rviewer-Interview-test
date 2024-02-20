package io.rviewer;

import lombok.Data;
import java.io.Serializable;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
@NoArgsConstructor
public class Drink implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String drinkType;

    private Float money;

    private Integer sugar;

    private Boolean extraHot;

    public Drink(String drinkType, Float money, Integer sugar, Boolean extraHot) {
        this.drinkType = drinkType;
        this.money = money;
        this.sugar = sugar;
        this.extraHot = extraHot;
    }
}
