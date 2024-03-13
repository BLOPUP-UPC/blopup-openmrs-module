package org.openmrs.module.blopup.openmrs.module.api;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.blopup.openmrs.module.api.models.ContactDoctorRequest;
import org.openmrs.module.blopup.openmrs.module.api.models.TelegramMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@PropertySource("classpath:application.properties")
public class ContactDoctorService extends BaseOpenmrsService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ProviderServiceImpl providerService;
	
	public Boolean sendMessageToDoctor(ContactDoctorRequest request) {
        String chatId = providerService.getProviderChatId(request.getProviderUuid());
        TelegramMessage telegramMessage = new TelegramMessage(chatId, request.getMessage());
        String token = environment.getProperty("telegram.bot.token");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            String body = "{\"chat_id\":\"" + telegramMessage.getChatId() + "\",\"text\":\"" + telegramMessage.getMessage() + "\"}";

            ResponseEntity<String> response = restTemplate.exchange("https://api.telegram.org/bot" + token + "/sendMessage",
                    HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Logger.getLogger("ContactDoctorService").info("Message sent to doctor");
                return true;
            } else {
                Logger.getLogger("ContactDoctorService").warning("Error sending message to doctor: " + response.getStatusCode());
                return false;
            }

        } catch (Exception e) {
            Logger.getLogger("ContactDoctorService").warning("Error sending message to doctor: " + e.getMessage());
            return false;
        }
    }
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
