package com.ogarose.popugjira.accounting.application.usecase.user;

import com.ogarose.popugjira.accounting.application.notification.EmailNotificator;
import com.ogarose.popugjira.accounting.domain.user.Transaction;
import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PayDayBalance {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EmailNotificator emailNotificator;

    public void execute() {
        List<User> userWithBalance = userRepository.findAllWithPositiveBalance();

        userWithBalance.forEach(user -> {
            Transaction payBalanceTransaction = Transaction.createCreditTransaction(user.getBalance(), "Pay Balance to user", user);
            transactionRepository.save(payBalanceTransaction);

            user.payBalance();
            userRepository.save(user);

            emailNotificator.sendNotification(user.getEmail(), "You balance has been paid to you.");
        });
    }
}
