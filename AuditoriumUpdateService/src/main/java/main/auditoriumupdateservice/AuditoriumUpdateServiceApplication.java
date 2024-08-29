package main.auditoriumupdateservice;

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
