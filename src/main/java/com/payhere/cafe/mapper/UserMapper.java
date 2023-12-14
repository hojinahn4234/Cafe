package com.payhere.cafe.mapper;

import com.payhere.cafe.domain.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int join(UserDTO user);

    int login(@Param("phoneNum")String phoneNum, @Param("pw")String pw);
}
