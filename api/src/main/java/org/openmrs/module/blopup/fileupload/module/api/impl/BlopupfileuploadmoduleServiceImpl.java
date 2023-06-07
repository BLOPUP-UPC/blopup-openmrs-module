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
import org.openmrs.api.APIException;
import org.openmrs.api.PatientService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.openmrs.module.blopup.fileupload.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.fileupload.module.api.dao.BlopupfileuploadmoduleDao;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class BlopupfileuploadmoduleServiceImpl extends BaseOpenmrsService implements BlopupfileuploadmoduleService {
	
	BlopupfileuploadmoduleDao dao;
	
	PatientService patientService;
	
	private final Path rootLocation;
	
	public BlopupfileuploadmoduleServiceImpl() {
		this.rootLocation = Paths.get("/legal_consent");
	}
	
	@Override
    public void store(MultipartFile file, String patientUuid) {
        try {

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
				//#region --save to database--
				Patient patient = patientService.getPatientByUuid(patientUuid);
				LegalConsent legalConsent = new LegalConsent();
				legalConsent.setFilePath(destinationFile.toFile().getPath());
				legalConsent.setPatient(patient);
				//#endregion
            }
        } catch (IOException e) {
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