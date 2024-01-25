package org.openmrs.module.blopup.fileupload.module.api.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.openmrs.Person;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPerson extends Person {
	
}
