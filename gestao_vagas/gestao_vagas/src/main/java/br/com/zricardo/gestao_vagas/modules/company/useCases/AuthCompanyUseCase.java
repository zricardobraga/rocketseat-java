package br.com.zricardo.gestao_vagas.modules.company.useCases;

import br.com.zricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.zricardo.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.zricardo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    //esse atributo tá vindo do application properties
    @Value("${security.token.secret}")
    private String secretKey;

    //esse importe é necessário para usarmos os métodos que estão no repository de company
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        //verifica, pelo username, se a company existe dentro do banco de dados e salva na variável
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Company not found");
        });

        //verifica se as senham batem
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            //se não for igual, retorna a exceção abaixo
            throw new AuthenticationException();
        }

        //se as senhas forem iguais, o código baixo gera o token com validade de duas horas
        //tipo de algoritmo que iremos usar pra criar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("javagas").withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;
    }
}
