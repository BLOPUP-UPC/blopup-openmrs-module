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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.impl.BlopupfileuploadmoduleServiceImpl;
import org.openmrs.module.blopup.fileupload.module.web.controller.FileUploadController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Objects;

/**
 * This is a unit test, which verifies logic in BlopupfileuploadmoduleService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class BlopupfileuploadmoduleServiceTest {

	@Mock
	BlopupfileuploadmoduleServiceImpl basicModuleService;

	@InjectMocks
	FileUploadController fileUploadController;

	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSaveUploadedFile() {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain",
		        "Spring Framework".getBytes());

		ResponseEntity response = fileUploadController.handleFileUpload(multipartFile);
		assert (response.getStatusCode().equals(HttpStatus.OK));
		assert (Objects.equals(response.getBody(), "You have successfully uploaded test.txt!"));

	}

	@Test(expected = StorageException.class)
	public void shouldThrowStorageExceptionIfFileIsEmpty() {

		fileUploadController.handleFileUpload(null);

	}
}
