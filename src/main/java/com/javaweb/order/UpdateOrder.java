package com.javaweb.order;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public record UpdateOrder(String id,

                          String tecnico,
                          String contato,
                          String cliente,
                          String equipamento,
                          String serie,
                          Float sla,
                         @NotBlank
                          String defeito,
                          String endereco,
                          String city,
                          Float cep,
                         @NotBlank
                          String status
                          ) {
}
