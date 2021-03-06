package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TabuleiroServiceImpl implements TabuleiroService {
    
    private final TabuleiroRepository repository;
    private final CacaPalavrasService serviceCacaPalavras;
    private final LocalizacaoPalavraService serviceLocalizacaoPalavra;
    private final CacaPalavrasMapper mapper;

    @Override
    @Transactional
    public TabuleiroDTO criarComBasico(TabuleiroDTO dto, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);

        if(cacaPalavras.getTabuleiro() != null){
            throw new RegraNegocioException("Já existe um tabuleiro criado para esse Caça Palavras");
        }

        Tabuleiro tabuleiro = new Tabuleiro(dto.getLargura(), dto.getAltura());
        tabuleiro.setCacaPalavras(cacaPalavras);

        tabuleiro = repository.save(tabuleiro);
        return mapper.toTabuleiroDTO(tabuleiro);
    }

    @Override
    public TabuleiroDTO findById(Integer id, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findByIdEntity(id, idCacaPalavras);
        return mapper.toTabuleiroDTO(tabuleiro);
    }

    @Override
    public Tabuleiro findByIdEntity(Integer id, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        Tabuleiro tabuleiro =  repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(TABULEIRO, ID, id));

        if(tabuleiro.getCacaPalavras().getId() != cacaPalavras.getId()){
            throw new RecursoNaoPertenceAException(TABULEIRO, CACA_PALAVRAS);
        }

        return tabuleiro;
    }

    @Override
    public void delete(Integer id, Integer idCacaPalavras){
        Tabuleiro tabuleiro = findByIdEntity(id, idCacaPalavras);
        serviceLocalizacaoPalavra.deleteAllAssociadasAoTabuleiro(tabuleiro.getId());
        repository.delete(tabuleiro);
    }

    private CacaPalavras findCacaPalavrasById(Integer idCacaPalavras) {
        return serviceCacaPalavras.findByIdEntity(idCacaPalavras);
    }

}
