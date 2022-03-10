package com.example.primes;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Validates and stores an upper limit for prime number generation.
 * 
 * @since 1.0
 */
public class PrimeGenerationLimit {
  /** The prime number generation limit cannot be greater than the maximum integer value. */
  private static final BigInteger MAXIMUM_GENERATION_LIMIT = BigInteger.valueOf(Integer.MAX_VALUE);
  
  /** The prime number generation limit, if valid. */
  public final Optional<Integer> limit;
  /** Error found while parsing prime number generation limit from string value, if any. */
  public final  Optional<String> parseError;
  
  /**
   * Constructor that initializes an instance of this class.
   * 
   * @param aLimit
   *   prime number generation limit, may be NULL.
   * @param anError
   *   error found while parsing limit from string, may be NULL. 
   */
  private PrimeGenerationLimit(final BigInteger aLimit, final String anError) {
    this.limit = null == aLimit ? Optional.ofNullable(null) : Optional.of(aLimit.intValue());
    this.parseError = null == anError ? Optional.ofNullable(null) : Optional.of(anError);
  }
  
  /**
   * Parse prime number generation limit provided as a string.
   * 
   * @param aMaximum
   *   limit as a string.
   * @return
   *   prime number generation limit as integer (if valid), and error
   *   message (if not valid).
   */
  static PrimeGenerationLimit parseLimit(final String aMaximum) {
    String errorMessage = null;
    BigInteger maximumToGenerate = null;

    // The prime number generation limit must be a valid integer string
    // whose value is not negative or zero, and is not too big.
    try {
      maximumToGenerate = (null == aMaximum || aMaximum.isEmpty() || aMaximum.isBlank()) ? 
        null : new BigInteger(aMaximum);
      if (null == maximumToGenerate)
        errorMessage = "generationLimit cannot be blank";
      else if (maximumToGenerate.compareTo(BigInteger.ONE) < 0)
        errorMessage = "generationLimit cannot be zero or less than zero";
      else if (maximumToGenerate.compareTo(MAXIMUM_GENERATION_LIMIT) > 0)
        errorMessage = "generationLimit cannot exceed " + MAXIMUM_GENERATION_LIMIT.intValue();
    } 
    catch (NumberFormatException ex) {
      errorMessage = "generationLimit must be a valid integer"; 
    }
    return new PrimeGenerationLimit(maximumToGenerate, errorMessage);
  }
}
