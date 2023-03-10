package com.shipogle.app.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="package")
public class Package {
    @Id
    @GeneratedValue
    @Column(name="package_id")
    private Integer package_id;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "user_id")
    private User sender;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="width")
    private float width;

    @Column(name="depth")
    private float depth;

    @Column(name="height")
    private float heigth;

    @Column(name="pickup_address")
    private String pickup_address;

    @Column(name="drop_address")
    private String drop_address;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
