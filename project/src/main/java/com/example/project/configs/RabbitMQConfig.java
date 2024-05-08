package org.zerogravitysolutions.digitalschool.configs;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    Queue myQueue() {

        return new Queue("myQueue");
    }

    @Bean
    Queue studentUpdated() {

        return new Queue("studentUpdated");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    Queue directQueue() {
        return new Queue("direct_queue");
    }

    @Bean
    Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct_routing_key");
    }

    @Bean
    Binding directBinding2() {
        return BindingBuilder.bind(myQueue()).to(directExchange()).with("direct_routing_key");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }

    @Bean
    Queue fanoutQueue() {
        return new Queue("fanout_queue");
    }

    @Bean
    Queue fanoutQueue2() {
        return new Queue("fanout_queue2");
    }

    @Bean
    Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    @Bean
    Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }

    @Bean
    Queue topicQueue() {
        return new Queue("topic_queue");
    }

    @Bean
    Queue topicQueue2() {
        return new Queue("topic_queue2");
    }

    @Bean
    Queue topicQueue3() {
        return new Queue("topic_queue3");
    }

    @Bean
    Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with("topic.routing.#");
    }

    @Bean
    Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    @Bean
    Binding topicBinding3() {
        return BindingBuilder.bind(topicQueue3()).to(topicExchange()).with("topic.*.custom.#");
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
