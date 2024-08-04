package com.project.wallet.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.wallet.exception.ForbiddenException;
import com.project.wallet.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    public void validateToken(String token) {
        if(token == null || token.isBlank()){
            throw new InvalidTokenException("Token não informado");
        }
        if(!token.contains("Bearer")){
            throw new InvalidTokenException("Token inválido");
        }
        verifyRole(token);
    }

    private static void verifyRole(String token) {
        String actualToken = token.substring(7);
        DecodedJWT decode = JWT.decode(actualToken);

        if(!decode.getClaim("role").asString().equals("USER")){
            throw new ForbiddenException("Acesso nao autorizado");
        }
    }
}
