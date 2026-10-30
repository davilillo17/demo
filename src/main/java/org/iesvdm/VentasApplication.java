package org.iesvdm;

import org.iesvdm.dao.ComercialDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class VentasApplication implements CommandLineRunner {

    private final ComercialDAO comercialDAO;

    VentasApplication(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(VentasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("* Prueba de arranque ComercialDAO *");
        log.info("***********************************");

        
        comercialDAO.getAll().forEach(c -> log.info("Comercial: {}", c));
    }
}