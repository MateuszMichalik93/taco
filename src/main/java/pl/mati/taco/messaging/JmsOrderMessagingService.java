package pl.mati.taco.messaging;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pl.mati.taco.Order;

import javax.jms.Destination;


@Service
public class JmsOrderMessagingService implements OrderMessagingService{

   private JmsTemplate jmsTemplate;
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate, Destination orderQueue) {
        this.jmsTemplate = jmsTemplate;
        this.orderQueue = orderQueue;
    }

    @Bean
    public Destination orderQueue() {
       return new ActiveMQQueue("tacocloud.order.queue");
    }

    @Override
    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }

    // @Override
   // public void sendOrder(Order order) {
     //  jmsTemplate.send(orderQueue, session -> session.createObjectMessage(order));
    //}



}
