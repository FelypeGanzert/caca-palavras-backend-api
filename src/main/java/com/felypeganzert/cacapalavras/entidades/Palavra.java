package com.felypeganzert.cacapalavras.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_palavra")
public class Palavra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String palavra;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name =  "id_palavra")
    private List<LocalizacaoPalavraNoTabuleiro> localizacoesNoTabuleiro = new ArrayList<LocalizacaoPalavraNoTabuleiro>();

}
