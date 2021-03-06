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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.felypeganzert.cacapalavras.exception.RegraNegocioException;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_tabuleiro")
public class Tabuleiro {

    public static final int LARGURA_MINIMA = 5;
    public static final int ALTURA_MINIMA = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Setter(value = AccessLevel.NONE)
    private int largura;

    @Setter(value = AccessLevel.NONE)
    private int altura;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "id_caca_palavras", nullable = false)
    private CacaPalavras cacaPalavras;

    @OneToMany(mappedBy = "tabuleiro", cascade = CascadeType.ALL)
    private List<Letra> letras = new ArrayList<Letra>();

    public Tabuleiro(int largura, int altura) {
        this(null, largura, altura);
    }

    public Tabuleiro(Integer id, int largura, int altura) {
        validarDimensoes(largura, altura);
        this.id = id;
        this.largura = largura;
        this.altura = altura;
    }

    private void validarDimensoes(int largura, int altura) {
        if (isLarguraMenorQueAMinima(largura) && isAlturaMenorQueAMinima(altura)) {
            throw new RegraNegocioException(
                    "Largura e Altura são menores que as mínimas (" + LARGURA_MINIMA + ", " + ALTURA_MINIMA + ")");
        }
        if (isLarguraMenorQueAMinima(largura))
            throw new RegraNegocioException("A Largura é menor que a mínima (" + LARGURA_MINIMA + ")");
        if (isAlturaMenorQueAMinima(altura))
            throw new RegraNegocioException("A Altura é menor que a mínima (" + ALTURA_MINIMA + ")");
    }

    private boolean isLarguraMenorQueAMinima(int largura) {
        return largura < LARGURA_MINIMA;
    }

    private boolean isAlturaMenorQueAMinima(int altura) {
        return altura < ALTURA_MINIMA;
    }

    public Letra getLetraDaPosicaoOuRetorneNull(Posicao posicao) {
        try{
            return getLetraDaPosicao(posicao);
        } catch(RegraNegocioException exc){
            // não encontrou nenhuma letra
        }
        return null;
    }

    protected Letra getLetraDaPosicao(Posicao posicao) {
        if (posicaoExiste(posicao)) {
            return letras.stream().filter(l -> isLetraNaPosicao(l, posicao)).findFirst()
                    .orElseThrow(() -> new RegraNegocioException(
                            "Nenhuma letra encontra na Posição (" + posicao.getX() + ", " + posicao.getY() + ")"));
        } else {
            throw new RegraNegocioException(
                    "Posição (" + posicao.getX() + ", " + posicao.getY() + ") não existe no Tabuleiro");
        }
    }

    public boolean posicaoExiste(Posicao posicao) {
        return posicao.getX() > 0 && posicao.getX() <= this.largura && posicao.getY() > 0 && posicao.getY() <= this.altura;
    }

    public boolean posicaoNaoExiste(Posicao posicao) {
        return !posicaoExiste(posicao);
    }

    private boolean isLetraNaPosicao(Letra letra, Posicao posicao) {
        Posicao posicaoLetra = letra.getPosicao();
        return posicaoLetra.equals(posicao);
    }

}
