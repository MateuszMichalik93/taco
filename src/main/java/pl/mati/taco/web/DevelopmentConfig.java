package pl.mati.taco.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mati.taco.data.IngredientRepository;
import pl.mati.taco.data.UserRepository;

@Profile({"!prod", "!qa"})
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        return null;
    }
}
