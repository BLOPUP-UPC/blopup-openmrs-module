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

import org.apache.commons.io.FileUtils;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.openmrs.module.blopup.fileupload.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.fileupload.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Transactional
public class BlopupfileuploadmoduleServiceImpl extends BaseOpenmrsService implements BlopupfileuploadmoduleService {
	
	BlopupfileuploadmoduleDao dao;

	PatientService patientService;

	@Override
	public String store(LegalConsentRequest legalConsentRequest) {
		
		String fileName = null;
		try {
			
			if (legalConsentRequest.getFileByteString() != null) {
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] decodedBytes = decoder.decodeBuffer(legalConsentRequest.getFileByteString());
				File recordingDir = new File("../../../opt/app/legalConsentStore");
				if (!recordingDir.exists()) {
					FileUtils.forceMkdir(recordingDir);
				}
				patientService = Context.getPatientService();
				Patient patient = null;
				List<Patient> list = patientService.getPatients(legalConsentRequest.getPatientIdentifier());
				if (list != null && !list.isEmpty())
					patient = list.get(0);
				
				if (patient != null) {
					fileName = patient.getPatientIdentifier().getIdentifier() + ".mp3";
					
					FileOutputStream fos = new FileOutputStream(recordingDir + "/" + fileName);
					fos.write(decodedBytes);
					fos.close();
					
					//#region --save to database--
					LegalConsent exist = dao.getLegalConsentByFilePath(fileName);
					if (exist != null) {
						exist.setFilePath(fileName);
						exist.setPatient(patient);
					} else {
						exist = new LegalConsent();
						exist.setFilePath(fileName);
						exist.setPatient(patient);
					}
					
					dao.saveLegalConsent(exist);
					//#endregion
				}
				
			}
			return fileName;
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
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@Override
	public LegalConsent getLegalConsentByUuid(String uuid) throws APIException {
		return dao.getLegalConsentByUuid(uuid);
	}
	
	@Override
	public LegalConsent saveLegalConsent(@NotNull LegalConsent legalConsent) throws APIException {
		return dao.saveLegalConsent(legalConsent);
	}
}
