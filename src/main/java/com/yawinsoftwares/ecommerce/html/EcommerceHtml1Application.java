package com.yawinsoftwares.ecommerce.html;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.yawinsoftwares.ecommerce.html.api.Project;
import com.yawinsoftwares.ecommerce.html.impl.DefaultProject;

@SpringBootApplication
public class EcommerceHtml1Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EcommerceHtml1Application.class, args);
		Project project = context.getBean(DefaultProject.class);
		project.createProjectFolder();
		//Page page = context.getBean(StaticPage.class);
		//System.out.println(page.getHTMLString());
	}
}
