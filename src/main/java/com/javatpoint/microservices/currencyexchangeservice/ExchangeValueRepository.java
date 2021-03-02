package com.javatpoint.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>  
{  
//creating query method  
ExchangeValue findByFromAndTo(String from, String to);  
}  