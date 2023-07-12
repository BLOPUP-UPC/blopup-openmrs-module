/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module.web.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.blopup.fileupload.module.api.BlopupFileUploadModuleService;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Duration;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/upload")
public class FileUploadController extends BaseRestController {
	
	/**
	 * Logger for this class and subclasses
	 */
	protected final Log log = LogFactory.getLog(getClass());
	
	private final BlopupFileUploadModuleService storageService;
	
	private final Bucket bucket;
	
	@Autowired
	public FileUploadController(BlopupFileUploadModuleService storageService) {
		this.storageService = storageService;
		
		Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
		bucket = Bucket.builder().addLimit(limit).build();
	}

	public FileUploadController(BlopupFileUploadModuleService storageService, Bucket bucket) {
		this.storageService = storageService;
		this.bucket = bucket;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity handleFileUpload(@RequestBody LegalConsentRequest legalConsentRequest) {
		if (bucket.tryConsume(1)) {
			if(legalConsentRequest == null){
				return new ResponseEntity("Body cannot be null!", HttpStatus.BAD_REQUEST);
			}
			if(legalConsentRequest.getFileByteString() == null || legalConsentRequest.getFileByteString().isEmpty()){
				return new ResponseEntity("File cannot be empty or null!", HttpStatus.BAD_REQUEST);
			}
			if(legalConsentRequest.getPatientIdentifier() == null || legalConsentRequest.getPatientIdentifier().isEmpty()){
				return new ResponseEntity("Patient identifier cannot be empty or null!", HttpStatus.BAD_REQUEST);
			}
			String fileName = storageService.saveLegalConsentRecording(legalConsentRequest);

			return new ResponseEntity("You have successfully uploaded " + fileName + "!", HttpStatus.OK);
		}
		return new ResponseEntity("Too many request!", HttpStatus.TOO_MANY_REQUESTS);
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageException(StorageException exc) {
		log.error(exc.getMessage(), exc);
		if(exc.getMessage().contains("Patient")){
			return new ResponseEntity(exc.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
