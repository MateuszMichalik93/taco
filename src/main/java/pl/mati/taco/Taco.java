package pl.mati.taco;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@RestResource(rel = "tacos", path = "tacos")
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min = 5, message = "Nazwa musi składać się z minimum 5 znaków.")
    private String name;
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "musisz wybrać conajmniej jeden składnik")
    private List<Ingredient> ingredients;

    @PrePersist
    void createAt(){
        this.createdAt = new Date();
    }


}
