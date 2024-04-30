package br.com.zricardo.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    private UUID id;
    @Schema(example = "maria")
    private String username;
    @Schema(example = "Maria de Souza")
    private String name;
    @Schema(example = "maria@gmail.com")
    private String email;
    @Schema(example = "Desenvolvedora Java")
    private String description;
    private LocalDateTime createdAt;

}
