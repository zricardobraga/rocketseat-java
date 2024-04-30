package br.com.zricardo.gestao_vagas.modules.candidate.entities;

import br.com.zricardo.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name= "apply_jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //esse objeto só será usado a nível de busca e de consulta, não será utilizado para fazer as inserções
    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private CandidateEntity candidateEntity;

    //esse objeto só será usado a nível de busca e de consulta, não será utilizado para fazer as inserções
    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity jobEntity;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
