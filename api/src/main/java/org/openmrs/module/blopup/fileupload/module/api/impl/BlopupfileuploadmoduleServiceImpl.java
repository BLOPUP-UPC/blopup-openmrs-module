/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module.api.impl;

import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.blopup.fileupload.module.FileStorageService;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.openmrs.module.blopup.fileupload.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.fileupload.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class BlopupfileuploadmoduleServiceImpl extends BaseOpenmrsService implements BlopupfileuploadmoduleService {
	
	BlopupfileuploadmoduleDao dao;
	
	PatientService patientService;
	
	@Override
	public String saveLegalConsentRecording(LegalConsentRequest legalConsentRequest) {
		String filePath = legalConsentRequest.getPatientIdentifier() + ".mp3";
		
		try {
			patientService = Context.getPatientService();
			List<Patient> patients = patientService.getPatients(legalConsentRequest.getPatientIdentifier());
			
			if (patients == null || patients.isEmpty()) {
				throw new StorageException("Failed to store file. Patient not found");
			}
			
			FileStorageService fileStorageService = new FileStorageService();
			fileStorageService.saveRecordingFile(legalConsentRequest);
			
			dao.saveOrUpdateLegalConsent(new LegalConsent(patients.get(0), filePath));
			
			return filePath;
		}
		catch (Exception e) {
			throw new StorageException("Failed to store file.", e);
		}
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(BlopupfileuploadmoduleDao dao) {
		this.dao = dao;
	}
}
