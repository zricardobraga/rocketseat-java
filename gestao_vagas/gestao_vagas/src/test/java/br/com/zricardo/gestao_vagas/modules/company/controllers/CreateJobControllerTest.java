package br.com.zricardo.gestao_vagas.modules.company.controllers;

import br.com.zricardo.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.zricardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.zricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.zricardo.gestao_vagas.modules.company.repository.CompanyRepository;
import br.com.zricardo.gestao_vagas.modules.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    //define um contexto para uma web application
    @Autowired
    private WebApplicationContext context;

    //injeção de dependência do company repository para salvar a entidade de company no H2
    @Autowired
    private CompanyRepository companyRepository;

    //cria uma estrutura para poder rodar os testes e executa antes (sempre)
    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_abble_to_create_a_new_job() throws Exception {

        //builda uma entidade de company para que possamos salvar no h2
        var company = CompanyEntity.builder()
                .name("COMPANY_NAME")
                .username("COMPANY_USERNAME")
                .email("email@company.com.br")
                .password("1234567890")
                .website("www.company.com")
                .description("COMPANY_DESCRIPTION").build();

        //o save and flush salva a informação imediatamente, sem esperar que toda a requisição sega feita
        var inDatabaseCompanySaved = companyRepository.saveAndFlush(company);

        //builda um createJobDTO
        var createdJobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        //o conteúdo da request vai ser um JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        //define o conteúdo, que no caso é um objeto de createJobDTO convertido em JSON
                        .content(TestUtils.objectToJSON(createdJobDTO))
                        //preenche o header da requisição com as informações obrigatórias, que no caso são a Authorization, o ID do Job e a secret
                        .header("Authorization", TestUtils.generateToken(inDatabaseCompanySaved.getId(), "JAVAGAS_@123#")))
                        //define qual é o resultado esperado, que no caso é um 200
                        .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
        //builda um createJobDTO
        var createdJobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                //o conteúdo da request vai ser um JSON
                .contentType(MediaType.APPLICATION_JSON)
                //define o conteúdo, que no caso é um objeto de createJobDTO convertido em JSON
                .content(TestUtils.objectToJSON(createdJobDTO))
                //preenche o header da requisição com as informações obrigatórias, que no caso são a Authorization, o ID do Job e a secret
                .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
