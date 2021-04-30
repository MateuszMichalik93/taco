package pl.mati.taco.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mati.taco.Ingredient;
import pl.mati.taco.data.IngredientRepository;

@RestController
@RequestMapping(path="/ingredientsx", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository repo) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }
}