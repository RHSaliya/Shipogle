package com.shipogle.app.model;

import javax.persistence.*;

@Entity
@Table(name="jwtToken")
public class JwtToken {
    @Id
    @GeneratedValue
    @Column(name="token_id")
    private Integer token_id;

    @Column(name="token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "user_id")
    private User user;

}
