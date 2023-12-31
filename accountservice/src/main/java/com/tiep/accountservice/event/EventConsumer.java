package com.tiep.accountservice.event;

import com.google.gson.Gson;
import com.tiep.accountservice.model.AccountDTO;
import com.tiep.accountservice.service.AccountService;
import com.tiep.commonservice.model.ProfileDTO;
import com.tiep.commonservice.utils.Constant;
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
    private AccountService accountService;
    @Autowired
    private EventProducer eventProducer;

    public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PROFILE_ONBOARDING_TOPIC)))
                .receive()
                .subscribe(this::profileOnboarding);
    }

    public void profileOnboarding(ReceiverRecord<String, String> receiverRecord) {
        log.info("ProfileOnboarding event");
        ProfileDTO profileDTO = gson.fromJson(receiverRecord.value(), ProfileDTO.class);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(profileDTO.getEmail());
        accountDTO.setReversed(0);
        accountDTO.setBalance(profileDTO.getInitialBalance());
        accountDTO.setCurrency("USD");

        accountService.createNewAccount(accountDTO).subscribe(
                res -> {
                    // set trạng thái cho profile
                    profileDTO.setStatus(Constant.STATUS_PROFILE_ACTIVE);
                    eventProducer
                            .send(Constant.PROFILE_ONBOARDED_TOPIC, gson.toJson(profileDTO))
                            .subscribe();
                }
        );
    }
}
