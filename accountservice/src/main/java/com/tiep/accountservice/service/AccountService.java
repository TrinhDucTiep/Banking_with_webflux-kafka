package com.tiep.accountservice.service;

import com.tiep.accountservice.model.AccountDTO;
import com.tiep.accountservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Mono<AccountDTO> createNewAccount(AccountDTO accountDTO) {
        return Mono.just(accountDTO)
                .map(AccountDTO::dtoToEntity)
                .flatMap(account -> accountRepository.save(account))
                .map(AccountDTO::entityToDto)
                .doOnError(
                        throwable -> log.error(throwable.getMessage())
                );
    }
}
