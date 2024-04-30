package br.com.zricardo.gestao_vagas.modules.company.useCases;

import br.com.zricardo.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.zricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.zricardo.gestao_vagas.modules.company.repository.JobRepository;
import br.com.zricardo.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute (JobEntity job){
        companyRepository.findById(job.getCompanyId()).orElseThrow(
                () -> {
                    throw new CompanyNotFoundException();
                }
        );
        return this.jobRepository.save(job);
    }

}
