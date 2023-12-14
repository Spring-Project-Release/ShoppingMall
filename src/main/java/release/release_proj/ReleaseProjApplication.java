package release.release_proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //orderDate 자동생성 위함
@SpringBootApplication
public class ReleaseProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReleaseProjApplication.class, args);
	}

}
