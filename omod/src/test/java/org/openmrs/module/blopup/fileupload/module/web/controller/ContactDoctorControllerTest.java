package org.openmrs.module.blopup.fileupload.module.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.module.blopup.fileupload.module.ContactDoctorService;
import org.openmrs.module.blopup.fileupload.module.api.models.TelegramMessage;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactDoctorControllerTest {
	
	@Mock
	private ContactDoctorService contactDoctorService;
	
	@InjectMocks
	private ContactDoctorController contactDoctorController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(contactDoctorController).build();
	}
	
	@Test
	public void shouldRespond200WhenDoctorHasBeenContacted() throws Exception {
		String chatId = "123456789";
		String message = "Hello Doctor";
		
		when(contactDoctorService.sendMessageToDoctor(any(TelegramMessage.class))).thenReturn(true);
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor").content("{\"chatId\":\"" + chatId + "\",\"message\":\"" + message + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isCreated());
		
		verify(contactDoctorService).sendMessageToDoctor(any(TelegramMessage.class));
	}
	
	@Test
	public void shouldRespondBadRequestIfAParameterIsMissing() throws Exception {
		String chatId = "123456789";
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor").content("{\"chatId\":\"" + chatId + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isBadRequest());
		
		verify(contactDoctorService, times(0)).sendMessageToDoctor(any(TelegramMessage.class));
	}
	
	@Test
	public void shouldRespondServerErrorWhenSendingMessageFails() throws Exception {
		String chatId = "123456789";
		String message = "Hello Doctor";
		
		when(contactDoctorService.sendMessageToDoctor(any(TelegramMessage.class))).thenReturn(false);
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor").content("{\"chatId\":\"" + chatId + "\",\"message\":\"" + message + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isInternalServerError());
		
		verify(contactDoctorService, times(1)).sendMessageToDoctor(any(TelegramMessage.class));
	}
	
}
