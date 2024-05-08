package org.zerogravitysolutions.digitalschool.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(apInfo());
    }

    public Info apInfo() {
        return new Info()
            .title("Email Service API")
            .description("API documentation for email service.")
            .version("1.0.0")
            .license(apiLicense())
            .contact(apiContact());
    }

    public License apiLicense() {
        return new License()
            .name("MIT Licence")
            .url("https://opensource.org/licenses/mit-license.php");
    }
    
    private Contact apiContact() {
        return new Contact()
            .name("Qëndrim Pllana")
            .email("trainings@zerogravitysolutions.org")
            .url("https://zerogravitysolutions.org");
    }
}
