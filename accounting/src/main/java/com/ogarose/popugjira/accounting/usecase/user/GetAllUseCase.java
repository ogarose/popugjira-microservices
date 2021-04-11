package com.ogarose.popugjira.accounting.usecase.user;

import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import com.ogarose.popugjira.accounting.usecase.user.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllUseCase {
    private final UserRepository userRepository;

    public List<UserResponse> execute() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getBalance()))
                .collect(Collectors.toList());
    }
}
