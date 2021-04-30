package pl.mati.taco.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mati.taco.Ingredient;

import java.util.Optional;
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
