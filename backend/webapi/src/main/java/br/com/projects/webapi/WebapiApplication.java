package br.com.projects.webapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(value = {"br.com.projects"})
@EnableJpaRepositories("br.com.projects.persistence.*")
@EntityScan("br.com.projects.persistence.*")
@OpenAPIDefinition(info = @Info(title = "Socia API", version = "1"))
public class WebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class, args);
    }

}
