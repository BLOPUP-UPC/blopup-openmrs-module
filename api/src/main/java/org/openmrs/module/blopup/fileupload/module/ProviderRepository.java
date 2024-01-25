package org.openmrs.module.blopup.fileupload.module;

import org.openmrs.module.blopup.fileupload.module.api.models.CustomProvider;
import org.openmrs.module.blopup.fileupload.module.api.models.CustomProviderAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Repository
@PropertySource("classpath:application.properties")
public class ProviderRepository {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	Environment env;
	
	public String getProviderChatId(String providerUuid) {
        String baseUrl = "http://localhost:8080";

        CustomProvider provider = restTemplate.exchange(
                        String.format("%s/openmrs/ws/rest/v1/provider/%s", baseUrl, providerUuid),
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        CustomProvider.class)
                .getBody();

        String providerChatId = provider.getAttributes()
                .stream()
                .filter(attribute -> Objects.equals(attribute.getAttributeTypeName(), "Telegram Chat ID"))
                .findFirst()
                .orElseGet(CustomProviderAttribute::new)
                .getChatId();

        if (providerChatId == null) {
            throw new RuntimeException("Provider chat id not found");
        }
        return providerChatId;
    }
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
