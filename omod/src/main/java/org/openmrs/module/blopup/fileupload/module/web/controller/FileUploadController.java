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
import org.openmrs.module.blopup.fileupload.module.api.BlopupfileuploadmoduleService;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageFileNotFoundException;
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
	
	private final BlopupfileuploadmoduleService storageService;
	
	private final Bucket bucket;
	
	@Autowired
	public FileUploadController(BlopupfileuploadmoduleService storageService) {
		this.storageService = storageService;
		
		Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
		bucket = Bucket.builder().addLimit(limit).build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity handleFileUpload(@RequestBody LegalConsentRequest legalConsentRequest) {
		if (bucket.tryConsume(1)) {
			String fileName = storageService.store(legalConsentRequest);
			
			return new ResponseEntity("You have successfully uploaded " + fileName + "!", HttpStatus.OK);
		}
		return new ResponseEntity("Too many request!", HttpStatus.TOO_MANY_REQUESTS);
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		log.error(exc.getMessage(), exc);
		return new ResponseEntity(exc.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageException(StorageException exc) {
		log.error(exc.getMessage(), exc);
		return new ResponseEntity(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
