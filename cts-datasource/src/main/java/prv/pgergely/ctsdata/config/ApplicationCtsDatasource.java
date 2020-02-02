package prv.pgergely.ctsdata.config;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.domain.TransitFeedZipFile;
import prv.pgergely.ctsdata.CtsDsComponents;
import prv.pgergely.ctsdata.utility.Schema;

@Profile("dev")
@SpringBootApplication
@ComponentScan(basePackageClasses= {CommonComponents.class, CtsDsComponents.class})
public class ApplicationCtsDatasource extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	application.addCommandLineProperties(true);
    	application.properties("spring.datasource-primary.schema="+getSchema().getSchemaName());

    	return application.sources(ApplicationCtsDatasource.class);
    }
    
    private String getSchemaNameFromJNDI() {
		try {
			Context ctx = new InitialContext();
			String schema = (String) ctx.lookup("java:/comp/env/global/schema");
			return schema;
		} catch (NamingException e) {
			return "test_gtfs";
		}
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCtsDatasource.class, args);
	}
	
	@Bean
	public Queue<TransitFeedZipFile> getInternalStore(){
		return new LinkedBlockingQueue<TransitFeedZipFile>();
	}
	
	@Bean
	public Schema getSchema() {
		Schema sc = new Schema();
		sc.setSchemaName(getSchemaNameFromJNDI());
		return sc;
	}
}
