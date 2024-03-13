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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.blopup.openmrs.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.openmrs.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.module.blopup.openmrs.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.openmrs.module.api.impl.BlopupfileuploadmoduleServiceImpl;
import org.openmrs.module.blopup.openmrs.module.api.models.LegalConsentRequest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * This is a unit test, which verifies logic in BlopupfileuploadmoduleService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BlopupfileuploadmoduleService.class, Context.class })
public class BlopupfileuploadmoduleServiceTest {
	
	public static final String VALID_PATIENT_IDENTIFIER = "8FGPT";
	
	@Mock
	private BlopupfileuploadmoduleDao dao;
	
	@Mock
	private FileStorageService fileStorageService;
	
	@Mock
	private PatientService patientService;
	
	@InjectMocks
	BlopupfileuploadmoduleServiceImpl blopupfileuploadmoduleService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSaveFile() {
		LegalConsentRequest legalConsentRequest = new LegalConsentRequest();
		legalConsentRequest.setPatientIdentifier(VALID_PATIENT_IDENTIFIER);
		legalConsentRequest.setFileByteString("File");
		
		blopupfileuploadmoduleService.setFileStorageService(fileStorageService);
		
		PowerMockito.mockStatic(Context.class);
		PowerMockito.when(Context.getPatientService()).thenReturn(patientService);
		
		ArrayList<Patient> patients = validPatientFixture();
		
		when(patientService.getPatients(VALID_PATIENT_IDENTIFIER)).thenReturn(patients);
		when(dao.getLegalConsentByFilePath(VALID_PATIENT_IDENTIFIER + ".mp3")).thenReturn(new LegalConsent());
		
		String response = blopupfileuploadmoduleService.saveLegalConsentRecording(legalConsentRequest);
		
		assert (response.equals(VALID_PATIENT_IDENTIFIER + ".mp3"));
	}
	
	@Test(expected = StorageException.class)
	public void shouldNotSaveFileIfPatientDoesNotExist() {
		LegalConsentRequest legalConsentRequest = new LegalConsentRequest();
		legalConsentRequest.setPatientIdentifier("INVALID");
		legalConsentRequest.setFileByteString("File");
		
		String fileName = "INVALID" + ".mp3";
		
		PowerMockito.mockStatic(Context.class);
		PowerMockito.when(Context.getPatientService()).thenReturn(patientService);
		
		when(patientService.getPatients(any())).thenReturn(null);
		when(dao.getLegalConsentByFilePath(fileName)).thenReturn(new LegalConsent());
		
		blopupfileuploadmoduleService.saveLegalConsentRecording(legalConsentRequest);
	}
	
	private ArrayList<Patient> validPatientFixture() {
		Patient patient = new Patient();
		PatientIdentifier patientIdentifier = new PatientIdentifier();
		patientIdentifier.setIdentifier(VALID_PATIENT_IDENTIFIER);
		Set<PatientIdentifier> patientIdentifiers = new HashSet<>(Collections.singletonList(patientIdentifier));
		patient.setIdentifiers(patientIdentifiers);
		ArrayList<Patient> patients = new ArrayList<>();
		patients.add(patient);
		return patients;
	}
}
