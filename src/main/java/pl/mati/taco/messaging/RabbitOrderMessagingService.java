package pl.mati.taco.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mati.taco.Order;

public class RabbitOrderMessagingService implements OrderMessagingService {

    private RabbitTemplate rabbitTemplate;


    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {

        rabbitTemplate.convertAndSend("tacoCloud.order.queue", order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
                return message;

            }
        });

    }
}
