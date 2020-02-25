package br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen.model.util","br.ufpe.cin.spgroup.dslcotasgen.dslcotasgen"})
public class DslCotasGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DslCotasGenApplication.class, args);
	}

}
