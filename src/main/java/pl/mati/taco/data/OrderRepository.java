package pl.mati.taco.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mati.taco.Order;
import pl.mati.taco.User;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Order save(Order order);

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

     //List<Order> findByDeliveryZip(String deliveryZip);

    //List<Order> readOrdersByDeliveryZipAnAndPlaceAtBetween(String deliveryZip, Date startDate, Date endDate);




}
