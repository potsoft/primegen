package com.example.primes;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONObjectMapperFactory {
  private static JSONObjectMapperFactory singleton;
  
  private final ObjectMapper objectMapper;

  // Prevent multiple instances
  private JSONObjectMapperFactory() {
    this.objectMapper = new ObjectMapper();
  }

  public static synchronized JSONObjectMapperFactory getInstance() {
    if (null == singleton) {
      singleton = new JSONObjectMapperFactory();
    }
    return singleton;
  }

  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }
}
