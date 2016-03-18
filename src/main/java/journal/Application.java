package journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author <a href="mailto:carlos.oviedo@gmail.com">Carlos Oviedo</a>
 */
@SpringBootApplication
@ImportResource("spring/data-layer.xml")
public class Application {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}