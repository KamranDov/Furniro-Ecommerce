
package az.crocusoft.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(

                ),

                version = "1.0",
                license = @License(
                        name = "Company",
                        url = "https://company.com/"
                ),
                termsOfService = "Terms of service"
        ),

        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        },
        servers = {
                @Server(
                        url = "/", description = "Default Server URL"
                )
        }

)
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
//        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfiguration {

}

