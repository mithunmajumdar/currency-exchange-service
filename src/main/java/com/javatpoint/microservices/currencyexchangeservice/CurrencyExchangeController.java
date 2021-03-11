package com.javatpoint.microservices.currencyexchangeservice;  
//import java.math.BigDecimal;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.microservices.currencyexchangeskafkaproducer.CurrencyExchangeKafkaMessageProducer;


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
		
		System.out.println("CurrencyExchangeController KafkaMessage Producer...Message sending started.....");
		//Creating properties
        String bootstrapServers="127.0.0.1:9092"; //zookeeper server port
        String topic,value,key;
        topic="currency-exchange-message";
        value="CurrencyExchangeMessage :: " + exchangeValue.getPort()+"-"+exchangeValue.getFrom()+"-"+exchangeValue.getTo()+"-"+exchangeValue.getId()+"-"+exchangeValue.getConversionMultiple() ;
        System.out.println("CurrencyExchangeController KafkaMessage Producer...Message value :: " + value);
        key="currency-exchange-message-id_"+ 01;
		CurrencyExchangeKafkaMessageProducer currencyExchangeKafkaMessageProducer = new CurrencyExchangeKafkaMessageProducer();
		try {
			currencyExchangeKafkaMessageProducer.callCurrencyExchangeKafkaMessageProducer(bootstrapServers,topic, value, key);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("{}", currencyExchangeKafkaMessageProducer);
		System.out.println("CurrencyExchangeController KafkaMessage Producer...Message sending ended.....");
		
		return exchangeValue;  
	}  
}  