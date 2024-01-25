package org.openmrs.module.blopup.fileupload.module;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class ProviderRepositoryTest {
	
	@Test
	public void shouldGetChatIdByProviderUuid() throws IOException {
		ProviderRepository providerRepository = new ProviderRepository();
		URL url = getClass().getResource("/providerJson.json");
		File jsonResponseFile = new File(url.getPath());
		
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(providerRepository.getRestTemplate());
		mockServer.expect(requestTo("http://localhost:8080/openmrs/ws/rest/v1/provider/123456789")).andRespond(
		    withSuccess(FileUtils.readFileToString(jsonResponseFile), MediaType.APPLICATION_JSON));
		
		String chatId = providerRepository.getProviderChatId("123456789");
		
		assertEquals("6592323167", chatId);
	}
}
