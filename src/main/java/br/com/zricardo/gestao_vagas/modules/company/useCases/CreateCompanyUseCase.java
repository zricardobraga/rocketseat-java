package br.com.zricardo.gestao_vagas.modules.company.useCases;

import br.com.zricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.zricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.zricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public CompanyEntity execute (CompanyEntity company){
        this.companyRepository.findByUsernameOrEmail(company.getUsername(), company.getEmail()).ifPresent(
                (user) -> {
                    //só é possível passar Exceções não checadas
                    throw new UserFoundException();
                }
        );

        //pega o password e codifica com o encode
        var password = passwordEncoder.encode(company.getPassword());
        //altera o password da entidade criada
        company.setPassword(password);

        return this.companyRepository.save(company);
    }

}
