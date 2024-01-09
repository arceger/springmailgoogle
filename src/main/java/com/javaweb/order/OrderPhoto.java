package com.javaweb.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table
@Getter
@Setter
public class OrderPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Orders order;


    @Lob
    @Column(nullable = false)
    private byte[] photo;

}
