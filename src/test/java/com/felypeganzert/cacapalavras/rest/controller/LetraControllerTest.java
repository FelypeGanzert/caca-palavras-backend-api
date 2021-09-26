package com.felypeganzert.cacapalavras.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;
import com.felypeganzert.cacapalavras.services.LetraService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LetraControllerTest {

    @InjectMocks
    private LetraController controller;

    @Mock
    private LetraService service;

    @Mock
    private CacaPalavrasMapper cacaPalavrasMapper;

    @Mock
    private CacaPalavrasPayloadMapper payloadMapper;

    private static final int ID_LETRA = 1;
    private static final int ID_TABULEIRO = 1;
    private static final int ID_CACA_PALAVRAS = 1;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.adicionarLetra(
                    ArgumentMatchers.any(Letra.class),
                    ArgumentMatchers.any(Integer.class),
                    ArgumentMatchers.any(Integer.class))
                ).thenReturn(criarLetraValida());
    }

    @Test
    void deveChamarAdicionarLetraDoServiceComSucesso() {
        BDDMockito.when(payloadMapper.toLetra(ArgumentMatchers.any(LetraPostDTO.class))).thenReturn(criarLetraValida());
        
        LetraPostDTO dto = LetraPostDTO.builder().letra('c').posicaoX(1).posicaoY(1).build();

        Letra letra = criarLetraValida();
        controller.adicionarLetra(dto, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).adicionarLetra(letra, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarAdicionarLetrasDoServiceComSucesso() {
        
        List<LetraPostDTO> letrasParaAdicionar = new ArrayList<>();
        LetraPostDTO dto1 = LetraPostDTO.builder().letra('c').posicaoX(1).posicaoY(1).build();
        LetraPostDTO dto2 = LetraPostDTO.builder().letra('s').posicaoX(1).posicaoY(2).build();
        letrasParaAdicionar.addAll(java.util.Arrays.asList(dto1, dto2));
        
        List<Letra> letraMapeadas = letrasParaAdicionar.stream()
                                    .map(dto -> Letra.builder()
                                                    .letra(dto.getLetra())
                                                    .posicao(new Posicao(dto.getPosicaoX(), dto.getPosicaoY()))
                                                    .build()
                                    ).collect(Collectors.toList());
        BDDMockito.when(payloadMapper.toLetras(letrasParaAdicionar)).thenReturn(letraMapeadas);

        controller.adicionarLetras(letrasParaAdicionar, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).adicionarLetras(letraMapeadas, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindAllDoServiceComSucesso() {
        controller.findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).findAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarFindByIdDoServiceComSucesso() {
        controller.findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).findById(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarAtualizarDoServiceComSucesso() {
        LetraPutDTO dto = LetraPutDTO.builder().letra('s').build();
        controller.atualizar(dto, ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).atualizar(dto.getLetra(), ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteDoServiceComSucesso() {
        controller.delete(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).delete(ID_LETRA, ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    @Test
    void deveChamarDeleteAllDoServiceComSucesso() {
        controller.deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);

        Mockito.verify(service).deleteAll(ID_TABULEIRO, ID_CACA_PALAVRAS);
    }

    private Letra criarLetraValida() {
        return Letra.builder().id(ID_LETRA).letra('s').build();
    }

}
