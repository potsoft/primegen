package com.example.primes.svc;

import java.util.BitSet;
import org.springframework.stereotype.Service;

/**
 * Prime number generation using simple implementation of Sieve of Eratosthenes algorithm.
 *
 * @since 1.0
 */
@Service("simpleSieve")
public class SimpleSievePrimeGeneratorImpl implements IPrimeNumberGenerator {
  /** Generate bit set of primes using simple Sieve of Eratosthenes algorithm. */
  @Override public BitSet primes(final int aLimit) {
    // Create a bit set where each position in the bit set corresponds to 
    // and integer in our range, such that one means the corresponding integer
    // is prime and zero means not a prime number. Then initialize all
    // positions to one which assumes all numbers prime to start, except we
    // start at 2 since neither zero nor one are considered prime numbers.
    
    int maximumToGenerate = Math.abs(aLimit);
    BitSet isPrimeFlags = new BitSet(maximumToGenerate);
    isPrimeFlags.set(2, maximumToGenerate);

    // Check if each number from 2 to the maximum is prime. However, we don't 
    // need to check beyond the square root of the maximum: a number will only 
    // have a factor greater than it's square root if that factor has a partner 
    // which is less than the square root.

    int maximumSqrt = (int) (Math.sqrt(maximumToGenerate) + 0.5);
    for (int primeCandidate=2;primeCandidate <= maximumSqrt;primeCandidate++) {
      if (isPrimeFlags.get(primeCandidate)) {
        // We know the candidate number is prime, which means all multiples of 
        // the number (composite numbers) aren't prime (for example, 5 is prime, 
        // but composites 10, 15, 20, 25 and so on are not). A common optimization 
        // here is to start marking composite numbers at square of the known prime.

        int nextCompositeOfPrime = primeCandidate * primeCandidate;
        while (nextCompositeOfPrime < maximumToGenerate) {
          // Mark the next composite number as not being prime by setting flag to zero.
          isPrimeFlags.clear(nextCompositeOfPrime);
          nextCompositeOfPrime += primeCandidate;
        }
      }
    }
    return isPrimeFlags;
  }
}
