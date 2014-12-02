package org.oregonask.utils;

import spark.ResponseTransformer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class JsonTransformer implements ResponseTransformer {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public JsonTransformer() {
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		mapper.registerModule(new Hibernate4Module());
	}
	
	@Override
	public String render(Object model) {
		
		String result = "";
		
		try {
			result = mapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
