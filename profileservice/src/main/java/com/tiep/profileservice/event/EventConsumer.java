package com.tiep.profileservice.event;

import com.google.gson.Gson;
import com.tiep.commonservice.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
public class EventConsumer {
    Gson gson = new Gson();

    public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDING_TOPIC))) // đăng ký lắng nghe sự kiện
                .receive() // nhận về
                .subscribe(this::profileOnboarding); // thực hiện hành động
    }

    private void profileOnboarding(ReceiverRecord<String, String> receiverRecord) {
        log.info(receiverRecord.value());
    }

}
