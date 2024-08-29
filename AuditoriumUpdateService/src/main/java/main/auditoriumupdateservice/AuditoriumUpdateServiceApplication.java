package main.auditoriumupdateservice;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuditoriumUpdateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditoriumUpdateServiceApplication.class, args);
    }

}
