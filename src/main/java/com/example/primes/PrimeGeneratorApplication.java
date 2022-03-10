package com.example.primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for example prime number generation application. 
 *
 * @since 1.0
 */
@SpringBootApplication
public class PrimeGeneratorApplication {
  /** This application accepts no arguments. */
	public static void main(String[] args) {
		SpringApplication.run(PrimeGeneratorApplication.class, args);
	}
}
