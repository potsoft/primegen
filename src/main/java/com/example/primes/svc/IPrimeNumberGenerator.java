package com.example.primes.svc;

import java.util.BitSet;

/**
 * Defines an algorithmic mechanism for generating prime numbers.
 *
 * @since 1.0
 */
public interface IPrimeNumberGenerator {
  /**
   * Generate prime integers from zero to specified limit.
   *
   * @param aLimit
   *   maximum integer for which to generate prime numbers.
   * @return
   *   set of bits corresponding to prime integers (1=prime,0=not).
   */
  public BitSet primes(final int aLimit);
}
