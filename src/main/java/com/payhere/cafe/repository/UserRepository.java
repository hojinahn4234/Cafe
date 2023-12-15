package com.payhere.cafe.repository;

import com.payhere.cafe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User userDTO);

    User findByPhoneNum(String phoneNum);
}
