package com.johanwork.job.service.impl;

import com.johanwork.job.constant.AppConstant;
import com.johanwork.job.exception.CustomException;
import com.johanwork.job.model.User;
import com.johanwork.job.repository.UserRepository;
import com.johanwork.job.service.IUserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService implements IUserDomainService {

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "User"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "User", email)
                ));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND,
                        String.format(AppConstant.Error.TITLE_NOT_FOUND, "User"),
                        String.format(AppConstant.Error.MESSAGE_NOT_FOUND, "User", id)
                ));
    }

}
