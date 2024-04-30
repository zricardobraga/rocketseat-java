package br.com.zricardo.gestao_vagas.modules.candidate.controllers;

import br.com.zricardo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.zricardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.zricardo.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.zricardo.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.zricardo.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.zricardo.gestao_vagas.modules.candidate.useCases.ProfileUseCaseCandidate;
import br.com.zricardo.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidado")
public class CandidateController {


    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileUseCaseCandidate profileUseCaseCandidate;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = " Cadastro de candidato", description = "Essa rota é responsável por cadastrar o candidato no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        //o Object no responseEntity faz com que seja possível devolver, nesse contexto, tanto uma mensagem de erro quanto um objeto do tipo candidato
        try {
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa rota é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileUseCaseCandidate.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    //swagger
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa rota é responsável por listar todas as vagas disponíveis por filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema (
                            schema = @Schema(implementation = JobEntity.class)
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    //swagger
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }


    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa rota é responsável por possibilitar a candidatura a uma vaga")
    public ResponseEntity<Object> applyJob (HttpServletRequest request, @RequestBody UUID idJob){
        //pega o id do candidado da requesição
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
