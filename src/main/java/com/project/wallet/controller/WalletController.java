package com.project.wallet.controller;

import com.project.wallet.entity.Wallet;
import com.project.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wallet", produces = "application/json", headers = "Accept=*/*")
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/balance/{id}")
    @Operation(summary = "Obtém todos o saldo por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de produtos  feita com sucesso")
    })
    public ResponseEntity<Wallet> getBalance(@RequestHeader("Authorization") String token,
                                             @PathVariable String id){
        return ResponseEntity.ok(walletService.getBalanceById(id));
    }

    @GetMapping("/statement/{cpf}")
    @Operation(summary = "Obtém o extrato por data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de produtos  feita com sucesso")
    })
    public ResponseEntity<Wallet> getStatement(@RequestHeader("Authorization") String token,
                                               @PathVariable String cpf,
                                               @RequestParam String dataInicial,
                                               @RequestParam String dataFinal){
        return ResponseEntity.ok(walletService.getStatement(dataInicial, dataFinal, cpf));
    }
}
