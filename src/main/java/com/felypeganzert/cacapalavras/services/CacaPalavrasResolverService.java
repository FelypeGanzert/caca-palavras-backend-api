package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasResolverService {

    void resolver(CacaPalavras cacaPalavras);
    
}
