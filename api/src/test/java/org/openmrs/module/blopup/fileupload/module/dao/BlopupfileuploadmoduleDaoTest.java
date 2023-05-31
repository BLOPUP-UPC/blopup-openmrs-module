/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.openmrs.module.blopup.fileupload.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class BlopupfileuploadmoduleDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	BlopupfileuploadmoduleDao dao;
	
	@Autowired
	PatientService patientService;
	
	@Test
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//Given
		LegalConsent legalConsent = new LegalConsent();
		legalConsent.setFilePath("path_to_legal_consent");
		legalConsent.setPatient(patientService.getPatientByUuid("43e633de-4986-42a0-a7f5-673f5dcba078"));
		
		//When
		dao.saveLegalConsent(legalConsent);
		
		//Let's clean up the cache to be sure getLegalConsentByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		
		//Then
		LegalConsent savedLegalConsent = dao.getLegalConsentByUuid(legalConsent.getUuid());
		
		assertThat(savedLegalConsent, hasProperty("uuid", is(legalConsent.getUuid())));
		assertThat(savedLegalConsent, hasProperty("patient", is(legalConsent.getPatient())));
		assertThat(savedLegalConsent, hasProperty("filePath", is(legalConsent.getFilePath())));
	}
}
