package br.com.zricardo.gestao_vagas.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .info(new Info().title("Gestão de Vagas")
//                        .description("API responsável pela gestão de vagas")
//                        .version("1")
//                        .schemaRequirement("jwt_auth", createSecurityScheme())
//
//    }
//     todo: verificar como resolver o erro do SecurityScheme
//    private SecurityScheme createSecurityScheme(){
//        return new SecurityScheme().name("jwt_auth").type(SecuritySchemeType.HTTP).scheme("bearer").bearerFormat("JWT");
//    }
//}
