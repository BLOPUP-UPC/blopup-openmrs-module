package org.openmrs.module.blopup.fileupload.module.api.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class LegalConsentRequest {
	
	@NotBlank(message = "Patient Uuid can not be empty")
	private String patientUuid;
	
	@NotBlank(message = "FileByteString Uuid can not be empty")
	private String fileByteString;
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public String getFileByteString() {
		return fileByteString;
	}
	
	public void setFileByteString(String fileByteString) {
		this.fileByteString = fileByteString;
	}
	
}
