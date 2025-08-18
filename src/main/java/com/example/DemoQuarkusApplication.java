package com.example;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.License;

@QuarkusMain
@OpenAPIDefinition(
        info = @Info(
                title = "My Quarkus API",
                version = "1.0.0",
                description = "API for my Quarkus application",
                contact = @Contact(name = "Your Name", email = "your.email@example.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class DemoQuarkusApplication {
    public static void main(String... args) {
        Quarkus.run(args); // Khởi chạy Quarkus
    }
}
