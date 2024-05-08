package org.zerogravitysolutions.digitalschool.rabbitmq_sample;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SenderExample {

    RabbitTemplate rabbitTemplate;

    public SenderExample(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDirectMessage() {

        rabbitTemplate.convertAndSend("direct_exchange", "direct_routing_key", "Direct message");
    }

    public void sendFanoutMessage() {

        rabbitTemplate.convertAndSend("fanout_exchange", "", "Fanout message");
    }

    public void sendTopicMessage() {

        rabbitTemplate.convertAndSend("topic_exchange", "topic.routing.custom", "Topic message.");
    }
}
