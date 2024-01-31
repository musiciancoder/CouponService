package com.bharath.springcloud.couponservice.repos;

import com.bharath.springcloud.couponservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
