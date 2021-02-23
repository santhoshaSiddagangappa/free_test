package com.freenow.service.security;

import com.freenow.dataaccessobject.UserRepository;
import com.freenow.domainobject.UserDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDO findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
}
