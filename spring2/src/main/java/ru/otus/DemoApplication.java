package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.DemoInteraction;

@PropertySource("classpath:/application.properties")
@Configuration
@ComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DemoApplication.class);

        DemoInteraction interaction = context.getBean(DemoInteraction.class);
        interaction.interaction();
    }
}
