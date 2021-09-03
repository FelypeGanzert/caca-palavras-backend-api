package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import javax.transaction.Transactional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/caca-palavras")
@RequiredArgsConstructor
public class CacaPalavrasController {

    private final CacaPalavrasService service;
    private final CacaPalavrasMaper cacaPalavrasMapper;
    private final CacaPalavrasRepository repository;
    private final PalavraRepository palavraRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer criarComBasico(@RequestBody CacaPalavrasPostDTO dto) {
        CacaPalavras cacaPalavras = service.criarComBasico(dto);
        return cacaPalavras.getId();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas(){
        return service.findAllComInformacoesBasicas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CacaPalavrasDTO findById(@PathVariable Integer id){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        return cacaPalavrasMapper.toCacaPalavrasDTO(cacaPalavras);
    }

    
    @GetMapping("/{id}/solucionar")
    @ResponseStatus(HttpStatus.OK)
    public CacaPalavrasDTO solucionarById(@PathVariable Integer id){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        service.encontrarPalavrasNoTabuleiro(cacaPalavras);
        repository.save(cacaPalavras);
        
        return cacaPalavrasMapper.toCacaPalavrasDTO(cacaPalavras);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        repository.delete(cacaPalavras);
    }

    @PostMapping("/{id}/tabuleiro")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer criarTabuleiroComBasico(@PathVariable Integer id, @RequestBody TabuleiroPostDTO dto){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        
        Tabuleiro tabuleiro = service.criarTabuleiroComBasico(cacaPalavras, dto);
        return tabuleiro.getId();
    }

    @PostMapping("/{id}/palavras")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PalavraDTO> adicionarPalavras(@PathVariable Integer id, @RequestBody List<String> palavras){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        
        List<Palavra> palavrasAdicionadas = service.adicionarPalavras(cacaPalavras, palavras);
        return cacaPalavrasMapper.toPalavrasDTO(palavrasAdicionadas);
    }

    @DeleteMapping("/{id}/palavras/{idPalavra}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePalavra(@PathVariable Integer id, @PathVariable Integer idPalavra){
        CacaPalavras cacaPalavras =  service.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        Palavra palavra = cacaPalavras.getPalavras().stream()
                                .filter(p -> p.getId() == idPalavra)
                                .findFirst()
                                .orElseThrow(() ->
                                        new ResponseStatusException (HttpStatus.NOT_FOUND,"Palavra não encontrada"));

        palavraRepository.delete(palavra);
    }
    
}
