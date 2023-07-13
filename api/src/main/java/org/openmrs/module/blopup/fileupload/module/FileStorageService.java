package org.openmrs.module.blopup.fileupload.module;

import org.apache.commons.io.FileUtils;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component("blopup.fileupload.module.FileStorageService")
public class FileStorageService {
	
	File RECORDING_DIRECTORY = new File("../../../opt/app/legalConsentStore");
	
	public void saveRecordingFile(LegalConsentRequest legalConsentRequest) throws IOException {
		byte[] byteArray = convertToByteArray(legalConsentRequest.getFileByteString());
		createRecordingDirectory();
		saveFile(legalConsentRequest.getPatientIdentifier() + ".mp3", byteArray);
	}
	
	private byte[] convertToByteArray(String fileString) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(fileString);
	}
	
	private void createRecordingDirectory() throws IOException {
		if (!RECORDING_DIRECTORY.exists()) {
			FileUtils.forceMkdir(RECORDING_DIRECTORY);
		}
	}
	
	private void saveFile(String fileName, byte[] decodedBytes) throws IOException {
		FileOutputStream fos = new FileOutputStream(RECORDING_DIRECTORY + "/" + fileName);
		fos.write(decodedBytes);
		fos.close();
	}
}
