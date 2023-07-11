package org.openmrs.module.blopup.fileupload.module;

import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileStorageService {
    private byte[] decodedBytes;

    public void convertToByteArray(String fileString) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        decodedBytes = decoder.decodeBuffer(fileString);
    }

    File recordingDir = new File("../../../opt/app/legalConsentStore");

    public void createRecordingDirectory() throws IOException {
        if(!recordingDir.exists()){
            FileUtils.forceMkdir(recordingDir);
            }
    }

    public void create (String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(recordingDir + "/" + fileName);
        fos.write(decodedBytes);
        fos.close();
    }

}
