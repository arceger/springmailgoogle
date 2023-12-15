package com.javaweb.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
