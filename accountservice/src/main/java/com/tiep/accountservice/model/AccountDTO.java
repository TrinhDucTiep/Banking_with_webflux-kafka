package com.tiep.accountservice.model;

import com.tiep.accountservice.data.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private String id;
    private String email;
    private String currency;
    private double balance;
    private double reversed;

    public static AccountDTO entityToDto(Account account) {
        return AccountDTO.builder()
                .email(account.getEmail())
                .id(account.getId())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .reversed(account.getReserved())
                .build();
    }

    public static Account dtoToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setEmail(dto.getEmail());
        account.setCurrency(dto.getCurrency());
        account.setBalance(dto.getBalance());
        account.setReserved(dto.getReversed());

        return account;
    }
}
