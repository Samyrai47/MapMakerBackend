package mipt.app.mapmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MapmakerApplication {
  public static void main(String[] args) {
    SpringApplication.run(MapmakerApplication.class, args);
  }
}
