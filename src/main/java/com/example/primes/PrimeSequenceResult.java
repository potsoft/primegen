package com.example.primes;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result returned by prime number generation API that contains a message and, if
 * primes were generated, a sequence of prime numbers.
 * 
 * @since 1.0
 */
public class PrimeSequenceResult {
  /** Message about success or failure of prime number generation. */
  private String message;
  /** Sequence of prime numbers, empty list if none generated. */
  private List<Integer> primes;
  
  /** Zero argument constructor amenable to JSON serialization. */
  public PrimeSequenceResult() { }

  /**
   * Constructor that initializes an instance of this class.
   * 
   * @param aMessage
   *   message about success or failure of prime number generation.
   * @param somePrimes
   *   sequence of prime numbers, may be empty list.
   */  
  public PrimeSequenceResult(final String aMessage, final List<Integer> somePrimes) {
    this.message = aMessage;
    this.primes = somePrimes;
  }

  /**
   * Get message about success or failure of prime number generation.
   * 
   * @return
   *   message about success or failure, always populated by API.
   */
  @JsonProperty("message")
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String aMessage) {
    this.message = aMessage;
  }

  /**
   * Get sequence of generated prime numbers.
   * 
   * @return
   *   prime numbers, empty list if none generated. 
   */
  @JsonProperty("generatedPrimes")
  public List<Integer> getPrimes() {
    return this.primes;
  }

  public void setPrimes(final List<Integer> somePrimes) {
    this.primes = somePrimes;
  }
}
