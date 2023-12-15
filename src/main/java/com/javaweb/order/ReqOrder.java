package com.javaweb.order;

import jakarta.validation.constraints.NotBlank;

public record ReqOrder(
        String id,

        String tecnico,
        @NotBlank
        String contato,
       @NotBlank
        String cliente,
       @NotBlank
        String equipamento,
       @NotBlank
        String serie,
       @NotBlank
        Float sla,
       @NotBlank
        String defeito,
       @NotBlank
        String endereco,
       @NotBlank
        String city,
       @NotBlank
        Float cep,
       @NotBlank
        String status
) {
}
