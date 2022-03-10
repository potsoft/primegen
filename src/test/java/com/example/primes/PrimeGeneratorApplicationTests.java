package com.example.primes;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Integration test for REST API exposed by example prime number generation application. 
 *
 * @since 1.0
 */
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = PrimeGeneratorApplication.class)
@AutoConfigureMockMvc
class PrimeGeneratorApplicationTests {
  
  @Autowired private MockMvc mvc;

  /**
   * Test that {@code generationLimit=10} query parameter to {@code primes} API generates 
   * the correct prime numbers in range 0 to 10. The test succeeds if the API returns {@code HttpStatus.OK}
   * and an appropriate success message in JSON {@code PrimeSequenceResult}.
   */
  @Test public void testPrimesZeroToTen() throws Exception {
    MvcResult mvcResult = this.mvc.perform(get("/primes?generationLimit=10").contentType(MediaType.APPLICATION_JSON)).
      andExpect(status().isOk()).
      andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
      andExpect(jsonPath("$.message", is("primes generated!"))).
      andDo(print()).
      andReturn();

    ObjectMapper objectMapper = JSONObjectMapperFactory.getInstance().getObjectMapper();
    String responseAsString = mvcResult.getResponse().getContentAsString();
    PrimeSequenceResult primeSeqResult = objectMapper.readValue(responseAsString, PrimeSequenceResult.class);
    assertTrue(Arrays.equals(new int[] { 2,3,5,7 }, primeSeqResult.getPrimes().stream().mapToInt(Integer::intValue).toArray()));
  }

  /**
   * Test that {@code generationLimit=10000} query parameter to {@code primes} API generates 
   * the correct prime numbers in range 0 to 10,000. The test succeeds if the API returns 
   * {@code HttpStatus.OK} and an appropriate success message in JSON {@code PrimeSequenceResult}.
   */
  @Test public void testPrimesZeroToTenThousand() throws Exception {
    MvcResult mvcResult = this.mvc.perform(get("/primes?generationLimit=10000").contentType(MediaType.APPLICATION_JSON)).
      andExpect(status().isOk()).
      andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
      andExpect(jsonPath("$.message", is("primes generated!"))).
      andDo(print()).
      andReturn();

    ObjectMapper objectMapper = JSONObjectMapperFactory.getInstance().getObjectMapper();
    String responseAsString = mvcResult.getResponse().getContentAsString();
    PrimeSequenceResult primeSeqResult = objectMapper.readValue(responseAsString, PrimeSequenceResult.class);
    assertTrue(primeSeqResult.getPrimes().size() > 0 && primeSeqResult.getPrimes().size() < 5000);
    validatePrimes(primeSeqResult.getPrimes());
  }

  /**
   * Test that a non-numeric {@code generationLimit} query parameter to {@code primes}
   * API is treated as an error. The test succeeds if the API returns {@code HttpStatus.UNPROCESSABLE_ENTITY}
   * and an appropriate error message in JSON {@code PrimeSequenceResult}.
   */
  @Test public void testNonNumericLimit() throws Exception {
    this.mvc.perform(get("/primes?generationLimit=r2d2").contentType(MediaType.APPLICATION_JSON)).
      andExpect(status().isUnprocessableEntity()).
      andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
      andExpect(jsonPath("$.message", is("generationLimit must be a valid integer"))).
      andDo(print());
  }

  /**
   * Test that an excessively large or small {@code generationLimit} query parameter 
   * to {@code primes} API is treated as an error. The test succeeds if the API 
   * returns {@code HttpStatus.UNPROCESSABLE_ENTITY} and an appropriate error message 
   * in JSON {@code PrimeSequenceResult}.
   */
  @Test public void testLimitTooBigOrSmall() throws Exception {
    long tooBig = Integer.MAX_VALUE + 1L;
    this.mvc.perform(get("/primes?generationLimit=" + String.valueOf(tooBig)).contentType(MediaType.APPLICATION_JSON)).
      andExpect(status().isUnprocessableEntity()).
      andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
      andExpect(jsonPath("$.message", containsString("generationLimit cannot exceed"))).
      andDo(print());

    long tooSmall = 0;
    this.mvc.perform(get("/primes?generationLimit=" + String.valueOf(tooSmall)).contentType(MediaType.APPLICATION_JSON)).
      andExpect(status().isUnprocessableEntity()).
      andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).
      andExpect(jsonPath("$.message", containsString("generationLimit cannot be zero or less"))).
      andDo(print());
  }
  
  /** Validate the specified integers are prime numbers. */
  private void validatePrimes(final List<Integer> someIntegers) {
    for (Integer i : someIntegers) {
      assertTrue(BigInteger.valueOf(i.longValue()).isProbablePrime(1));
    }
  }
}
