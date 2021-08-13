package com.felypeganzert.cacapalavras.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TabuleiroPostDTO {

    private int largura;
    private int altura;

}