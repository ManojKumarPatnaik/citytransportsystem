package prv.pgergely.ctscountry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.ctscountry.services.DatabaseInit;

@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, ApplicationCountryComponents.class})
public class ApplicationCtsCountry extends SpringBootServletInitializer {
	
	@Autowired
	private DatabaseInit initDb;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(ApplicationCtsCountry.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsCountry.class, args);
	}
	
	@PostConstruct
	public void initDatabase() {
		initDb.init();
	}
}
