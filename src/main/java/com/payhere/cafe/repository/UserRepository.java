package com.payhere.cafe.repository;

import com.payhere.cafe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {

    User findByPhonenum(String phonenum);
}
