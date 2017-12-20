package ringbackstage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages="ringbackstage")
@ServletComponentScan // 扫描使用注解方式的servlet
public class BackstageApplication extends SpringBootServletInitializer {

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BackstageApplication.class);
	}

	public static void main(String[] args) {
        SpringApplication.run(BackstageApplication.class, args);
    }
}