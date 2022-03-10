package com.example.primes;

import java.util.BitSet;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.primes.svc.IPrimeNumberGenerator;

/**
 * Controller that implements simple REST API for prime number generation. 
 *
 * @since 1.0
 */
@RestController
public class PrimeGeneratorController {
  
  /** The actual generation of prime numbers is delegated to this service. */
  @Autowired
  @Qualifier("simpleSieve")
  private IPrimeNumberGenerator primeGenerator;
  
  /**
   * Generate prime numbers from zero to specified limit.
   * 
   * @param aMaximum
   *   no primes past this limit are generated.
   * @return
   *   a success or failure message, and if successful, a sequence of primes.
   */
  @GetMapping(path = "/primes", produces = "application/json")
  public ResponseEntity<PrimeSequenceResult> generatePrimes(
      @RequestParam(value = "generationLimit") final String aMaximum) {
    HttpStatus httpStatus = null;
    PrimeSequenceResult result = null;
    PrimeGenerationLimit maximumToGenerate = PrimeGenerationLimit.parseLimit(aMaximum);

    // If we couldn't parse and validate the prime number generation limit
    // passed to the API, return HTTP status and error message indicating such.

    if (maximumToGenerate.parseError.isPresent()) {
      result = new PrimeSequenceResult(maximumToGenerate.parseError.get(), Collections.emptyList());
      httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }
    else {
      // Delegate to prime number generation service, which returns a bit set for
      // compactness, where each prime from zero to the limit has it's bit set.
      // Convert the set bits to their indices (which are the prime integers) as
      // integer stream, then collect into integer list we'll pass back to caller.

      assert maximumToGenerate.limit.isPresent();
      BitSet isPrimeFlags = this.primeGenerator.primes(maximumToGenerate.limit.get());
      result = new PrimeSequenceResult("primes generated!", 
        isPrimeFlags.stream().boxed().collect(Collectors.toList()));
      httpStatus = HttpStatus.OK;
    }

    assert httpStatus != null && result != null;
    return ResponseEntity.status(httpStatus).body(result);
  }
}
