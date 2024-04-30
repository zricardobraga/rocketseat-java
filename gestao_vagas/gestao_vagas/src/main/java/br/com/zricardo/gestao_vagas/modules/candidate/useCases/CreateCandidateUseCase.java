package br.com.zricardo.gestao_vagas.modules.candidate.useCases;

import br.com.zricardo.gestao_vagas.exceptions.UserFoundException;
import br.com.zricardo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute (CandidateEntity candidate){
        this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail()).ifPresent(
                (user) -> {
                    //só é possível passar Exceções não checadas
                    throw new UserFoundException();
                }
        );

        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);

        return this.candidateRepository.save(candidate);
    }

}
