package io.rviewer.repository;

import java.util.List;
import io.rviewer.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    List<Drink> getAllByDrinkType(String type);
}
