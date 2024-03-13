package org.openmrs.module.blopup.openmrs.module.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.module.blopup.openmrs.module.api.models.ContactDoctorRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class ContactDoctorServiceTest {
	
	@InjectMocks
	private ContactDoctorService contactDoctorService;
	
	@Mock
	private Environment environment;
	
	@Mock
	private ProviderServiceImpl providerService;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldSendMessageToTelegram() {
		String chatId = "6592323167";
		String message = "Hello beautiful";
		String body = "{\"chat_id\":\"" + chatId + "\",\"text\":\"" + message + "\"}";
		ContactDoctorRequest contactDoctorRequest = new ContactDoctorRequest("providerUuid", "message");
		
		when(environment.getProperty("telegram.bot.token")).thenReturn("test_token");
		when(providerService.getProviderChatId(contactDoctorRequest.getProviderUuid())).thenReturn("6592323167");
		
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(contactDoctorService.getRestTemplate());
		mockServer.expect(requestTo("https://api.telegram.org/bot" + "test_token" + "/sendMessage"))
		        .andExpect(method(HttpMethod.POST)).andExpect(content().string(body))
		        .andRespond(withStatus(HttpStatus.OK).body("OK"));
		
		Boolean response = contactDoctorService.sendMessageToDoctor(new ContactDoctorRequest("providerUuid", message));
		
		mockServer.verify();
		assert (response);
	}
	
	@Test
	public void shouldReturnFalseWhenMessageToTelegramFails() {
		String chatId = "6592323167";
		String message = "Hello beautiful";
		String body = "{\"chat_id\":\"" + chatId + "\",\"text\":\"" + message + "\"}";
		ContactDoctorRequest contactDoctorRequest = new ContactDoctorRequest("providerUuid", "message");
		
		when(environment.getProperty("telegram.bot.token")).thenReturn("test_token");
		when(providerService.getProviderChatId(contactDoctorRequest.getProviderUuid())).thenReturn("6592323167");
		
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(contactDoctorService.getRestTemplate());
		mockServer.expect(requestTo("https://api.telegram.org/bot" + "test_token" + "/sendMessage"))
		        .andExpect(method(HttpMethod.POST)).andExpect(content().string(body))
		        .andRespond(withStatus(HttpStatus.BAD_REQUEST).body("Bad Request"));
		
		Boolean response = contactDoctorService.sendMessageToDoctor(new ContactDoctorRequest("providerUuid", message));
		
		mockServer.verify();
		assert (!response);
	}
}
