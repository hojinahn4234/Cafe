package com.payhere.cafe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phonenum")
    private String phonenum;

    @Column(name = "pw")
    private String pw;
}
