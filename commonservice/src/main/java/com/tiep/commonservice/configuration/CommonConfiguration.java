package com.tiep.commonservice.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommonConfiguration {
    @Autowired
    private ReactiveKafkaAppProperties reactiveKafkaAppProperties;

    @Bean
    KafkaSender<String, String> kafkaSender() {
        // tạo map các props
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, reactiveKafkaAppProperties.bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // đợi xác nhận từ mọi phân vùng
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // tạo senderOptions
        SenderOptions<String, String> senderOptions = SenderOptions.create(props);
        // tạo kafkaSender từ senderOptions
        return KafkaSender.create(senderOptions);
    }

    @Bean
    ReceiverOptions<String, String> receiverOptions() {
        // tạo map các props cho receiver
        Map<String, Object> propsReceiver = new HashMap<>();
        propsReceiver.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, reactiveKafkaAppProperties.bootstrapServers);
        propsReceiver.put(ConsumerConfig.GROUP_ID_CONFIG, reactiveKafkaAppProperties.consumerGroupId);
        propsReceiver.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsReceiver.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // tạo receiverOptions
        return ReceiverOptions.create(propsReceiver);
    }
}
