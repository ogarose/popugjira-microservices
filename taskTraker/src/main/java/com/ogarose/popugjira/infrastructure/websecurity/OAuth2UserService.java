package com.ogarose.popugjira.infrastructure.websecurity;

import com.ogarose.popugjira.domain.user.Role;
import com.ogarose.popugjira.domain.user.User;
import com.ogarose.popugjira.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Optional<User> foundOauthUserOptional = userRepository.findByName(oAuth2User.getName());

        if (foundOauthUserOptional.isEmpty()) {
            User newUser = new User(
                    UUID.fromString(oAuth2User.getAttribute("publicId")),
                    oAuth2User.getName(),
                    Role.valueOf(oAuth2User.getAttribute("role")),
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("phone")
            );
            userRepository.save(newUser);

            return newUser;
        }

        return foundOauthUserOptional.get();
    }
}
