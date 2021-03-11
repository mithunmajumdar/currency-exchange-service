package com.javatpoint.microservices.currencyexchangeskafkaproducer;


import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeKafkaMessageProducerController {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	//********Default call***********//
	
	@GetMapping("/creditmessageproducer") //where {from} and {to} represents the column   
	//return a bean back  
	public void callCurrencyExchangeKafkaMessageProducer() throws ExecutionException, InterruptedException  
	{  
		//Creating properties
        String bootstrapServers="127.0.0.1:9092";
        String topic,value,key;
        topic="currency-exchange-message";
        value="Currency Exchange";
        key="id_"+ 01;

        CurrencyExchangeKafkaMessageProducer currencyExchangeKafkaMessageProducer = new CurrencyExchangeKafkaMessageProducer();
		currencyExchangeKafkaMessageProducer.callCurrencyExchangeKafkaMessageProducer(bootstrapServers,topic, value, key);
		logger.info("{}", currencyExchangeKafkaMessageProducer);
	}
	
}
