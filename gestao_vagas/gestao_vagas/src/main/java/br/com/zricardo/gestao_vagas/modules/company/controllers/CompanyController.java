package br.com.zricardo.gestao_vagas.modules.company.controllers;

import br.com.zricardo.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import br.com.zricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company) {
        //o Object no responseEntity faz com que seja possível devolver, nesse contexto, tanto uma mensagem de erro quanto um objeto do tipo companhia
        try {
            var result = this.createCompanyUseCase.execute(company);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
