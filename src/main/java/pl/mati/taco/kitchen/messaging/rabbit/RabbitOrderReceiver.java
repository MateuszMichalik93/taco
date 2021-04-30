package pl.mati.taco.kitchen.messaging.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import pl.mati.taco.Order;

@Component
public class RabbitOrderReceiver {

    private RabbitTemplate rabbitTemplate;
    private MessageConverter messageConverter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = rabbitTemplate.getMessageConverter();
    }

    public Order reciverOrder() {
        return rabbitTemplate.receiveAndConvert("tacocloud.order.queue"
                , new ParameterizedTypeReference<Order>() {
        });
    }

}
