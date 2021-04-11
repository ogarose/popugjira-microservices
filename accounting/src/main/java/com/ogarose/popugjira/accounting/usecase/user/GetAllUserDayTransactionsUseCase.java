package com.ogarose.popugjira.accounting.usecase.user;

import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import com.ogarose.popugjira.accounting.usecase.user.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllUserDayTransactionsUseCase {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<TransactionResponse> execute(UUID userId) {
        User searchedUser = userRepository.find(userId).orElseThrow();

        return transactionRepository.findAllOfDayByUser(searchedUser).stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getDebit(),
                        transaction.getCredit(),
                        transaction.getDescription(),
                        transaction.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
