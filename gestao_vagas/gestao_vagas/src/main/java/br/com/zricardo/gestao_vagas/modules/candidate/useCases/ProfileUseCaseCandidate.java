package br.com.zricardo.gestao_vagas.modules.candidate.useCases;

import br.com.zricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.zricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileUseCaseCandidate {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate){
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .createdAt(candidate.getCreatedAt())
                .build();


        return candidateDTO;
    }

}
