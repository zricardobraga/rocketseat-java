package br.com.zricardo.gestao_vagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name= "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Daniel de Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    @Schema(example = "daniel_souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo [e-mail] deve conter um e-mail válido")
    @Schema(example = "daniel@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Length(min = 5, max = 100, message = "A senha deve conter entre (5) e (100) caracteres")
    @Schema(minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(example = "Desenvolvedor Java Jr", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
