package br.com.zricardo.gestao_vagas.modules.company.repository;

import br.com.zricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyRepository> findByUsernameOrEmail(String username, String email);

    //encontra a company pelo username
    Optional<CompanyEntity> findByUsername(String username);
}
