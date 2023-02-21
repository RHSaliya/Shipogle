package com.shipogle.app.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "gov_id_url")
    private String gov_id_url;
    @Column(name = "profile_pic_url")
    private String profile_pic_url;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "postal_code")
    private String postal_code;
    @Column(name = "country")
    private String country;
    @Column(name = "is_active")
    private Boolean is_activated;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;

}