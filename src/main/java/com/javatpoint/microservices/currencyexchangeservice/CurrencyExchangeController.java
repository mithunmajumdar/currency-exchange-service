package com.javatpoint.microservices.currencyexchangeservice;  
//import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication  
@RestController   
public class CurrencyExchangeController   
{  
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	Environment environment;
	@Autowired  
	private ExchangeValueRepository repository;  
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")       //where {from} and {to} are path variable  
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to)  //from map to USD and to map to INR  
	{     
		//return new  ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		
		//taking the exchange value  
		//ExchangeValue exchangeValue= new ExchangeValue (1000L, from, to, BigDecimal.valueOf(65));  
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);  
		//picking port from the environment  
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));  
		logger.info("{}", exchangeValue);
		return exchangeValue;  
	}  
}  