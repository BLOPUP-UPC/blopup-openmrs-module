package org.openmrs.module.blopup.openmrs.module.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.module.blopup.openmrs.module.api.ContactDoctorService;
import org.openmrs.module.blopup.openmrs.module.api.models.ContactDoctorRequest;
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
		String providerUuid = "123456789";
		String message = "Hello Doctor";
		
		when(contactDoctorService.sendMessageToDoctor(any(ContactDoctorRequest.class))).thenReturn(true);
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor")
		            .content("{\"providerUuid\":\"" + providerUuid + "\",\"message\":\"" + message + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isCreated());
		
		verify(contactDoctorService).sendMessageToDoctor(any(ContactDoctorRequest.class));
	}
	
	@Test
	public void shouldRespondBadRequestIfAParameterIsMissing() throws Exception {
		String providerUuid = "123456789";
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor").content("{\"providerUuid\":\"" + providerUuid + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isBadRequest());
		
		verify(contactDoctorService, times(0)).sendMessageToDoctor(any(ContactDoctorRequest.class));
	}
	
	@Test
	public void shouldRespondServerErrorWhenSendingMessageFails() throws Exception {
		String providerUuid = "123456789";
		String message = "Hello Doctor";
		
		when(contactDoctorService.sendMessageToDoctor(any(ContactDoctorRequest.class))).thenReturn(false);
		
		mockMvc.perform(
		    post("/rest/v1/contactDoctor")
		            .content("{\"providerUuid\":\"" + providerUuid + "\",\"message\":\"" + message + "\"}")
		            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(
		    status().isInternalServerError());
		
		verify(contactDoctorService, times(1)).sendMessageToDoctor(any(ContactDoctorRequest.class));
	}
	
}
