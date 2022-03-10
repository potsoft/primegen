package com.example.primes.svc;

import java.math.BigInteger;
import java.util.BitSet;
import org.springframework.stereotype.Service;

/**
 * Prime number generation using capabilities in {@code java.math.BigInteger}.
 *
 * @since 1.0
 */
@Service("bigInt")
public class BigIntegerPrimeGeneratorImpl implements IPrimeNumberGenerator {
  /** Generate bit set of primes using {@code java.math.BigInteger}. */
  @Override public BitSet primes(final int aLimit) {
    // Create a bit set where each position in the bit set corresponds to 
    // and integer in our range, such that one means the corresponding integer
    // is prime and zero means not a prime number. Then initialize all
    // positions to zero which assumes all numbers not prime to start.

    int maximumToGenerate = Math.abs(aLimit);
    BitSet isPrimeFlags = new BitSet(maximumToGenerate);
    
    // Delegate to BigInteger to find prime numbers from 2 to the maximum. 

    BigInteger primeCandidate = BigInteger.valueOf(2L);
    while (primeCandidate.intValue() <= maximumToGenerate) {
      if (primeCandidate.isProbablePrime(1))
        isPrimeFlags.set(primeCandidate.intValue());
      primeCandidate = primeCandidate.nextProbablePrime();
    }
    return isPrimeFlags;
  }
}
