package cat.itacademy.barcelonactiva.salgadosalichs.josep.s04.t02.n02;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="S4T2N2E1", version = "2.0.1",
				contact = @Contact(
						name = "Josep Salgado", email = "example@example.com", url = "https://example.cat")
		),
		servers={@Server(url = "http://localhost:8080", description ="Main server"),
				@Server(url = "https://temp:8080", description ="Production server")},
		tags={@Tag(name="ADD", description="All ADDs of Fruita controller."),
				@Tag(name = "DELETE", description = "All DELETEs of Fruita controller."),
				@Tag(name="GET", description="All GETs of Fruita controller."),
				@Tag(name="PUT", description="All PUTs of Fruita controller.")}
)
@SecurityScheme(name="BearerJWT", type= SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
		description = "Bearer token for the project")
public class S04T02N02SalgadoSalichsJosepApplication {

	public static void main(String[] args) {
		SpringApplication.run(S04T02N02SalgadoSalichsJosepApplication.class, args);
	}

}
