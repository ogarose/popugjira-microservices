package com.ogarose.popugjira.accounting.usecase.user;

import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import com.ogarose.popugjira.accounting.infrastructure.messaging.MessageBus;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.accounting.biz.BalancePaid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PayDayBalance {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final MessageBus messageBus;


    public void execute() {
        List<User> userWithBalance = userRepository.findAllWithPositiveBalance();

        userWithBalance.forEach(user -> {
            Transaction payBalanceTransaction = Transaction.createCreditTransaction(user.getBalance(), "Pay Balance to user", user);
            transactionRepository.save(payBalanceTransaction);

            user.payBalance();
            userRepository.save(user);

            messageBus.sendMessage(MessageTopics.USER_ACCOUNTING_BIZ, new BalancePaid(user.getId(), payBalanceTransaction.getCredit()));
        });
    }
}
