package com.email.emailapi.service;

import com.email.emailapi.dto.UserEmailDto;
import com.email.emailapi.repository.UserEmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserEmailService {
    @Autowired
    private final UserEmailRepository userEmailRepository;
    @Autowired
    private final UserEmailDto userEmailDto;

    public UserEmailService(UserEmailRepository userEmailRepository, UserEmailDto userEmailDto) {
        this.userEmailRepository = userEmailRepository;
        this.userEmailDto = userEmailDto;
    }

    public List<UserEmailDto> findByUserEmail(String email)
    {

        return userEmailRepository.findByUserEmail(email);
    }


}
