package org.openmrs.module.blopup.openmrs.module.api.models;

public class ContactDoctorRequest {
	
	String providerUuid;
	
	String message;
	
	public ContactDoctorRequest() {
		
	}
	
	public ContactDoctorRequest(String providerUuid, String message) {
		this.providerUuid = providerUuid;
		this.message = message;
	}
	
	public String getProviderUuid() {
		return providerUuid;
	}
	
	public String getMessage() {
		return message;
	}
}
