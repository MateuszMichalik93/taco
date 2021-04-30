package pl.mati.taco;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date placedAt;
    @NotBlank(message = "Podanie imienia i nazwiska jest obowiązkowe")
    private String Deliveryname;
    @NotBlank(message = "Podanie ulicy jest obowiązkowe")
    private String Deliverystreet;
    @NotBlank(message = "Podanie miejscowości jest obowiązkowe")
    private String Deliverycity;
    @NotBlank(message = "Podanie Województwa jest obowiązkowe ")
    private String Deliverystate;
    @NotBlank(message = "Podanie kodu pocztowego jest obowiązkowe")
    private String Deliveryzip;
    @CreditCardNumber(message = "To nie jest prawidłowy numer karty kredytowej")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$" ,
            message ="wartość musi być w formacie MM/RR.")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "nieprawidlowy kod CVV")
    private String ccCVV;
    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();
    @ManyToOne
    private User user;

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placeAt() {
        this.placedAt = new Date();
    }



}
