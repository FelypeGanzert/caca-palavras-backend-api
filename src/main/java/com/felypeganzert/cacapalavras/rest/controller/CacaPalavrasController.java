package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/caca-palavras")
@RequiredArgsConstructor
@Api(value = "API REST Caça Palavras")
@CrossOrigin(origins = "*")
public class CacaPalavrasController {

    private final CacaPalavrasService service;
    private final CacaPalavrasPayloadMapper payloadMapper;;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um Caça Palavras com informações básicas")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Caça Palavras criado")
    })
    public CacaPalavrasDTO criarComBasico(@Valid @RequestBody CacaPalavrasPostDTO postDTO) {
        CacaPalavrasDTO dto = payloadMapper.toCacaPalavrasDTO(postDTO);
        return service.criarComBasico(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna todos os Caça Palavras com suas informações básicas")
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas() {
        return service.findAllComInformacoesBasicas();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna um Caça Palavras único")
    public CacaPalavrasDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta um Caça Palavras")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Caça Palavras deletado")
    })
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PostMapping("/{id}/solucionar")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Soluciona um Caça Palavras")
    public CacaPalavrasDTO solucionar(@PathVariable Integer id) {
        return service.resolverCacaPalavras(id);
    }

}
