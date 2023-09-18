package com.tiep.accountservice.repository;

import com.tiep.accountservice.data.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
}
