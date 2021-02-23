package com.freenow.service.security;

import com.freenow.domainobject.UserDO;

public interface UserService {
    UserDO findByUserName(String userName);
}
