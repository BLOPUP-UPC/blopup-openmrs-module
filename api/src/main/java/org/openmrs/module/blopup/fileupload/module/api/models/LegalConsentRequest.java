package org.openmrs.module.blopup.fileupload.module.api.models;

import org.hibernate.validator.constraints.NotBlank;

public class LegalConsentRequest {
	
	@NotBlank(message = "Patient Uuid can not be empty")
	private String patientIdentifier;
	
	@NotBlank(message = "FileByteString Uuid can not be empty")
	private String fileByteString;
	
	public String getPatientIdentifier() {
		return patientIdentifier;
	}
	
	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}
	
	public String getFileByteString() {
		return fileByteString;
	}
	
	public void setFileByteString(String fileByteString) {
		this.fileByteString = fileByteString;
	}
	
}
