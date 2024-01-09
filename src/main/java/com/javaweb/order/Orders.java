package com.javaweb.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime abertura;
    @PrePersist
    public void prePersist() {
        this.abertura = LocalDateTime.now();
    }

    @Column (nullable = true, length=100)
    private String tecnico;


    @Column (nullable = false, length=70)
    private String contato;

    @Column (nullable = false, length=100)
    private String cliente;

    @Column(nullable=false, length=50)
    private String equipamento;

    @Column (nullable = false, unique=true,  length=30)
    private String serie;

    @Column (nullable = true, length=20)
    private float sla;

    @Column (nullable = false, length=500)
    private String defeito;

    @Column (nullable = true, length=200)
    private String endereco;
    @Column (nullable = true, length=100)
    private String city;

    @Column (nullable = false, length=70)
    private float cep;
    @Column (nullable = false, length=50)
    private String status;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderPhoto> photos = new ArrayList<>();
}


