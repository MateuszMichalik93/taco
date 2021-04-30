package pl.mati.taco.messaging;

import pl.mati.taco.Order;

public interface OrderMessagingService {

    void sendOrder(Order order);
}
