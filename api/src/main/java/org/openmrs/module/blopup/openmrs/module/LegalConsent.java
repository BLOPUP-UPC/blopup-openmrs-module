/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.openmrs.module;

import org.hibernate.validator.constraints.NotEmpty;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Patient;

import javax.persistence.*;
import java.util.UUID;

/**
 * Please note that a corresponding table schema must be created in liquibase.xml.
 */
//Uncomment 2 lines below if you want to make the Item class persistable, see also BlopupfileuploadmoduleDaoTest and liquibase.xml
@Entity(name = "blopup_legal_consent")
@Table(name = "blopup_legal_consent")
public class LegalConsent {
	
	@Id
	@GeneratedValue
	@Column(name = "blopup_legal_consent_id")
	private Integer id;
	
	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	private String uuid = UUID.randomUUID().toString();
	
	@OneToOne
	@JoinColumn(name = "patient")
	private Patient patient;
	
	@Basic
	@Column(name = "file_path", length = 500)
	private String filePath;
	
	public LegalConsent() {
	}
	
	public LegalConsent(Patient patient, String filePath) {
		this.patient = patient;
		this.filePath = filePath;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
