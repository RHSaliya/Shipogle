package com.shipogle.app.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="package_request")
public class PackageRequest {
    @Id
    @GeneratedValue
    @Column(name="package_request_id")
    private Integer package_request_id;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "deliverer_id",referencedColumnName = "user_id")
    private User deliverer;

    @ManyToOne
    @JoinColumn(name = "package_id",referencedColumnName = "package_id")
    private Package _package;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;
}
