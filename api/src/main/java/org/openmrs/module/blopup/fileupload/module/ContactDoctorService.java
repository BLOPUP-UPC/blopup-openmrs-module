package org.openmrs.module.blopup.fileupload.module;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.blopup.fileupload.module.api.models.TelegramMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@PropertySource("classpath:telegram.properties")
public class ContactDoctorService extends BaseOpenmrsService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private Environment environment;
	
	public Boolean sendMessageToDoctor(TelegramMessage telegramMessage) {

        String token = environment.getProperty("telegram.bot.token");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            String body = "{\"chat_id\":\"" + telegramMessage.getChatId() + "\",\"text\":\"" + telegramMessage.getMessage() + "\"}";

            restTemplate.exchange("https://api.telegram.org/bot" + token + "/sendMessage",
                    HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
            return true;

        } catch (Exception e) {
            Logger.getLogger("ContactDoctorService").warning("Error sending message to doctor: " + e.getMessage());
            Logger.getLogger("ContactDoctorService").warning(token);
            return false;
        }
    }
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
