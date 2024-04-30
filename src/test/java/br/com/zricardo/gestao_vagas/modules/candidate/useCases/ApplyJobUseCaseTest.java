package br.com.zricardo.gestao_vagas.modules.candidate.useCases;

import br.com.zricardo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.zricardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.zricardo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.zricardo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.zricardo.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.zricardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.zricardo.gestao_vagas.modules.company.repository.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void should_be_abble_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyjob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        //quando o método findById for chamado, ele tem que retornar um CandidateEntit
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        //quando o método execute for chamado, ele tem que retornar um objeto applyJobCreated com um id definido pelo UUID.randomUUID()
        when(applyJobRepository.save(applyjob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        //confere se o execute retornou um objeto que contain um "id"
        assertThat(result).hasFieldOrProperty("id");
        //confere se o id não está nulo
        assertNotNull(result.getId());

    }
}
