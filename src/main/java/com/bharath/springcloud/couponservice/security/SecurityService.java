package com.bharath.springcloud.couponservice.security;

public interface SecurityService {

    boolean login(String userName, String password);
}
