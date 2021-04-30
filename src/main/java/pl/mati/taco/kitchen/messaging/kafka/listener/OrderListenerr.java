package pl.mati.taco.kitchen.messaging.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mati.taco.Order;

@Component
public class OrderListenerr {

    private KitchenUI ui;

    @Autowired
    public OrderListenerr(KitchenUI ui) {
        this.ui = ui;
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order) {
        ui.displayOrder(order);
    }
}
