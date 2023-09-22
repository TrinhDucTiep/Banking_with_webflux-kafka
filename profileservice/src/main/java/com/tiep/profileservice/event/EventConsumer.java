package com.tiep.profileservice.event;

import com.google.gson.Gson;
import com.tiep.commonservice.utils.Constant;
import com.tiep.profileservice.model.ProfileDTO;
import com.tiep.profileservice.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
public class EventConsumer {
    Gson gson = new Gson();

    @Autowired
    private ProfileService profileService;

    public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDED_TOPIC)))
                .receive()
                .subscribe(this::profileOnboarded);
    }

    private void profileOnboarded(ReceiverRecord<String, String> receiverRecord) {
        log.info("Profile Onboarded event");
        profileService.updateStatusProfile(gson.fromJson(receiverRecord.value(), ProfileDTO.class))
            .subscribe();
    }

}
