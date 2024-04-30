package br.com.zricardo.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name= "job")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Schema(example = "Vaga para pessoa desenvolvedora júnior")
    private String description;

    @NotBlank(message = "Esse campo é obrigatório")
    @Schema(example = "Júnior")
    private String level;

    @Schema(example = "GYMPass, Plano de saúde e etc")
    private String benefits;

    //o insertable e o updatable explicam pro ORM que para manipulação dos dados serão usados apenas o atributo companyId
    @ManyToOne()
    @JoinColumn(name= "company_id", insertable = false, updatable = false)
    private CompanyEntity company;

    @Column(name= "company_id", nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
