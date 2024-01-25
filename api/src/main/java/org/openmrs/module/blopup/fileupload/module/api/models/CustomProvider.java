package org.openmrs.module.blopup.fileupload.module.api.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.BaseCustomizableMetadata;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomProvider extends BaseCustomizableMetadata<CustomProviderAttribute> {
	
	public CustomPerson person;
	
	private Integer providerId;
	
	private Set<CustomProviderAttribute> attributes;
	
	@Override
	public Integer getId() {
		return this.getProviderId();
	}
	
	@Override
	public void setId(Integer id) {
		this.setProviderId(id);
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	
	public Integer getProviderId() {
		return this.providerId;
	}
	
	public CustomPerson getPerson() {
		return person;
	}
	
	public void setPerson(CustomPerson person) {
		this.person = person;
	}
	
	@Override
	public Set<CustomProviderAttribute> getAttributes() {
		return this.attributes;
	}
	
	public void setAttributes(Set<CustomProviderAttribute> attributes) {
		this.attributes = attributes;
	}
}
