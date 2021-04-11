package com.ogarose.popugjira.accounting.usecase.user;

import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculateManagerEarnTodayUseCase {
    private final UserRepository userRepository;

    public Integer execute() {
        return userRepository.getManagerEarnToday();
    }
}
