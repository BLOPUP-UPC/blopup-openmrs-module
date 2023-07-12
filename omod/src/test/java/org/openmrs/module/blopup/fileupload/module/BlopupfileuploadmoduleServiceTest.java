/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.blopup.fileupload.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.fileupload.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.impl.BlopupfileuploadmoduleServiceImpl;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.mockito.Matchers.any;

/**
 * This is a unit test, which verifies logic in BlopupfileuploadmoduleService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BlopupfileuploadmoduleService.class, Context.class})
public class BlopupfileuploadmoduleServiceTest {

	@Mock
	private BlopupfileuploadmoduleDao dao;

	@Mock
	private PatientService patientService;

	@InjectMocks
	BlopupfileuploadmoduleServiceImpl blopupfileuploadmoduleService;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSaveFile(){
		LegalConsentRequest legalConsentRequest = new LegalConsentRequest();
		legalConsentRequest.setPatientIdentifier("8FGPT");
		legalConsentRequest.setFileByteString("File");

		String fileName = "8FGPT" + ".mp3";

		PowerMockito.mockStatic(Context.class);
		PowerMockito.when(Context.getPatientService()).thenReturn(patientService);

		Patient patient = new Patient();
		PatientIdentifier patientIdentifier = new PatientIdentifier();
		patientIdentifier.setIdentifier("8FGPT");
		Set<PatientIdentifier> patientIdentifiers = new HashSet<>(Collections.singletonList(patientIdentifier));
		patient.setIdentifiers(patientIdentifiers);
		ArrayList<Patient> patients = new ArrayList<>();
		patients.add(patient);

		Mockito.when(patientService.getPatients("8FGPT")).thenReturn(patients);
		Mockito.when(dao.getLegalConsentByFilePath(fileName)).thenReturn(new LegalConsent());

		String response = blopupfileuploadmoduleService.store(legalConsentRequest);

		assert(response.equals(fileName));
	}

	@Test (expected = StorageException.class)
	public void shouldNotSaveFileIfPatientDoesNotExist(){
		LegalConsentRequest legalConsentRequest = new LegalConsentRequest();
		legalConsentRequest.setPatientIdentifier("8FGPT");
		legalConsentRequest.setFileByteString("File");

		String fileName = "8FGPT" + ".mp3";

		PowerMockito.mockStatic(Context.class);
		PowerMockito.when(Context.getPatientService()).thenReturn(patientService);

		Mockito.when(patientService.getPatients(any())).thenReturn(null);
		Mockito.when(dao.getLegalConsentByFilePath(fileName)).thenReturn(new LegalConsent());

		blopupfileuploadmoduleService.store(legalConsentRequest);
	}
}
