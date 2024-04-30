package br.com.zricardo.gestao_vagas.modules.candidate.useCases;

import br.com.zricardo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.zricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.zricardo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.zricardo.gestao_vagas.modules.company.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob){
        //valida se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException());

        //valida se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        //inscreve o candidado na vaga
        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        applyJob = applyJobRepository.save(applyJob);

        return applyJob;

    }



}
