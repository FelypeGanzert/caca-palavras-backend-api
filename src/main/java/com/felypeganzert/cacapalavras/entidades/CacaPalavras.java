package com.felypeganzert.cacapalavras.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_caca_palavras")
public class CacaPalavras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @OneToOne(mappedBy = "cacaPalavras", cascade = CascadeType.ALL)
    private Tabuleiro tabuleiro;

    @OneToMany(mappedBy = "cacaPalavras", cascade = CascadeType.ALL)
    private List<Palavra> palavras = new ArrayList<Palavra>();

    @NotNull(message = "Data de Criação é obrigatório")
    private LocalDateTime dataCriacao;

    @NotBlank(message = "Criador não pode ser vazio")
    private String criador;

    @NotBlank(message = "Título não pode ser vazio")
    private String titulo;

}
