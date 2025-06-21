package utez.edu.mx.foodster.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Este es el servicio foodster");

        Contact contact = new Contact();
        contact.setEmail("redalphasiete@gmail.com");
        contact.setName("Critian Rodriguez Rodriguez");

        Info info = new Info()
                .title("Foodster API")
                .version("1.0")
                .contact(contact)
                .description("API para tarea integradora")
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("JWT",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .servers(List.of(devServer));
    }
}