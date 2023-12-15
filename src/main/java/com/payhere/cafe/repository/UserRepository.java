package com.payhere.cafe.repository;

import com.payhere.cafe.domain.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, Long> {
    UserDTO save(UserDTO userDTO);

    UserDTO findByPhoneNum(String phoneNum);
}
