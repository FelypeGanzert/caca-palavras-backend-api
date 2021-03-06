package com.felypeganzert.cacapalavras.mapper.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoLetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoPalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;

import org.springframework.stereotype.Component;

@Component
public class CacaPalavrasMapperImpl implements CacaPalavrasMapper{

    @Override
    public CacaPalavrasDTO toCacaPalavrasDTO(CacaPalavras c){
        if(c == null){
            return null;
        }
        return CacaPalavrasDTO.builder()
                .id(c.getId())
                .dataCriacao(c.getDataCriacao())
                .criador(c.getCriador())
                .titulo(c.getTitulo())
                .tabuleiro(toTabuleiroDTO(c.getTabuleiro()))
                .palavras(toPalavrasDTO(c.getPalavras()))
                .build();
    }

    @Override
    public TabuleiroDTO toTabuleiroDTO(Tabuleiro t){
        if(t == null){
            return null;
        }
        return TabuleiroDTO.builder()
                .id(t.getId())
                .largura(t.getLargura())
                .altura(t.getAltura())
                .letras(toLetrasDTO(t.getLetras()))
                .build();
    }

    @Override
    public LetraDTO toLetraDTO(Letra l){
        if(l == null){
            return null;
        }
        return LetraDTO.builder()
                .id(l.getId())
                .letra(l.getLetra())
                .posicaoX(l.getPosicao().getX())
                .posicaoY(l.getPosicao().getY())
                .build();
    }

    @Override
    public List<LetraDTO> toLetrasDTO(List<Letra> letras){
        if(letras == null){
            new ArrayList<LetraDTO>();
        }
        return letras.stream().map(l -> toLetraDTO(l)).collect(Collectors.toList());
    }

    @Override
    public PalavraDTO toPalavraDTO(Palavra p){
        if(p == null){
            return null;
        }
        return PalavraDTO.builder()
                .id(p.getId())
                .palavra(p.getPalavra())
                .localizacoes(toLocalizacoesPalavraDTO(p.getLocalizacoes()))
                .build();
    }

    @Override
    public List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras){
        if(palavras == null){
            new ArrayList<PalavraDTO>();
        }
        return palavras.stream().map(p -> toPalavraDTO(p)).collect(Collectors.toList());
    }

    private LocalizacaoPalavraDTO toLocalizacaoPalavraDTO(LocalizacaoPalavra lp){
        if(lp == null){
            return null;
        }
        return LocalizacaoPalavraDTO.builder()
                .id(lp.getId())
                .localizacoesLetras(toLocalizacoesLetraDTO(lp.getLocalizacoesLetras()))
                .build();
    }

    private Set<LocalizacaoPalavraDTO> toLocalizacoesPalavraDTO(Set<LocalizacaoPalavra> localizacoesPalavra){
        if(localizacoesPalavra == null){
            return new HashSet<LocalizacaoPalavraDTO>();
        }
        return localizacoesPalavra.stream().map(l -> toLocalizacaoPalavraDTO(l)).collect(Collectors.toSet());
    }

    private LocalizacaoLetraDTO toLocalizacaoLetraDTO(LocalizacaoLetra ll){
        if(ll == null){
            return null;
        }
        return LocalizacaoLetraDTO.builder()
                .id(ll.getId())
                .ordem(ll.getOrdem())
                .letraId(ll.getLetra().getId())
                .build();
    }

    private List<LocalizacaoLetraDTO> toLocalizacoesLetraDTO(List<LocalizacaoLetra> localizacoesLetra){
        if(localizacoesLetra == null){
            new ArrayList<LocalizacaoLetraDTO>();
        }
        return localizacoesLetra.stream().map(l -> toLocalizacaoLetraDTO(l)).collect(Collectors.toList());
    }
    
}
