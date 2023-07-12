package org.openmrs.module.blopup.fileupload.module;

import org.junit.Test;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;

import java.io.File;
import java.io.IOException;

public class FileStorageServiceTest {
	
	@Test
	public void shouldSaveRecordingFile() throws IOException {
		
		LegalConsentRequest legalConsentRequest = new LegalConsentRequest();
		legalConsentRequest.setPatientIdentifier("4DTPQ");
		legalConsentRequest.setFileByteString("ByteString");
		
		FileStorageService fileStorageService = new FileStorageService();
		
		fileStorageService.saveRecordingFile(legalConsentRequest);
		
		File expectedFile = new File("../../../opt/app/legalConsentStore/4DTPQ.mp3");
		
		assert (expectedFile.exists());
		assert (expectedFile.length() > 0);
	}
}
