package org.openmrs.module.blopup.fileupload.module.api.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.ProviderAttribute;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomProviderAttribute extends ProviderAttribute {
	
	String display;
	
	public String getDisplay() {
		return display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}
	
	public String getChatId() {
		int size = display.split(" ").length;
		return display.split(" ")[size - 1];
	}
	
	public String getAttributeTypeName() {
		int size = display.split(" ").length;
		return display.split(":")[0];
	}
}
