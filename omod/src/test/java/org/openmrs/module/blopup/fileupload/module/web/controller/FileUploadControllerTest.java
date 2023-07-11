package org.openmrs.module.blopup.fileupload.module.web.controller;

import io.github.bucket4j.Bucket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.module.blopup.fileupload.module.api.exceptions.StorageException;
import org.openmrs.module.blopup.fileupload.module.api.impl.BlopupfileuploadmoduleServiceImpl;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class FileUploadControllerTest {

    private LegalConsentRequest legalConsentRequest;

    private FileUploadController fileUploadController;

    @Mock
    BlopupfileuploadmoduleServiceImpl basicModuleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        legalConsentRequest = new LegalConsentRequest();
        legalConsentRequest.setPatientIdentifier("10027D");
        legalConsentRequest
                .setFileByteString("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAYAAAD0eNT6AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAOxAAADsQBlSsOGwAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAACAASURBVHic7N13nFTVwT7w59zpszOzlWVBelWxRXwTjUYXLAkRC+LaECyJxhJN4hujSUzExBg1+kteTaKiRIUFNKvSFBMUWLAQE1FTMHZ63d5mdto9vz+Wpe4uW+bec++d5/sXuztz76Mf2PPMveeeAxARERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERWZdQHYDsT55SFoDL44db5MAtvYe8QHM1I5pOijfn1ymIR2Qr8pzpOdu+aMlPJ4S3OZEI5Pj80YNfs2tTqnET3PWXoCKtIiM5AwsAHUBOusWHeOMgIHkE0q6hAAZByP4ACiFRAIHCtj+LAgjpA5DTw1MkATQDiEKiBkLUQMoqANXQUAMdOyDkFujYBM2zRVQ+U5/Z/0Ii89Qfe0V+Yyz+5VRSPw5pjE6n04PSOorSaeSnkzKS0vWcdFL3AsKVSulClxB6Wnb7+C6XkC6XkJomUpomEpoLMU3TWjQNjZpb7HC5xAYhtE+FSP+7uTH5wYnNr1YZ+J9LNsMCkIVkWZkL1b5RkDgKEqMhMAbAaABjAAxQHO9gzZD4AgKfQOITQHwEV/pj6N5PWA7ICuRZZbk7NqQntib009JpeUwyKUekknpxvFUPxeO6JmX3B3SjaRqkz6u1eHzaLrdb+8Llwj8F8Fbd7tTKk/Fqo+p8ZC4WAIeT510fRFPriYB+AoQ4DlKeAOAYAAHV2fpObgTE+xDyA+ja+xD6+6Jy3lbVqci56r9cNrypWj8/nkiXJuPp4+KtcmA0mvKne/Cp3aq8Xi3h9Wk7vF7tX243VrWkc54/sfq57apzkXFYABxGTrxiKKQ4FdBOhpSnADgBgFt1LhPtAPA2IN+EJt9GQ+h9sW5WUnUosh9ZWuququp3VktT+tLWWOq0aDQ9NBZNeSz0gd5wPq8W9/q1TR6P9g40LFhf5V/OeQfOwQJgc7L06hKI5ETo2pkQ+kRA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");

        fileUploadController = new FileUploadController(basicModuleService);
    }

    @Test
    public void shouldSaveUploadedFile() {

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenReturn("blopup.mp3");

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        assert (Objects.equals(response.getBody(), "You have successfully uploaded blopup.mp3!"));
    }

    @Test
    public void shouldRespondWith400IfFileIsEmpty() {
        legalConsentRequest.setFileByteString("");

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenThrow(new StorageException(""));

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);
        assert (response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assert (Objects.equals(response.getBody(), "File cannot be empty or null!"));

    }

    @Test
    public void shouldRespondWith400IfFileIsNull() {
        legalConsentRequest.setFileByteString(null);

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenThrow(new StorageException(""));

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);
        assert (response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assert (Objects.equals(response.getBody(), "File cannot be empty or null!"));
    }

    @Test
    public void shouldRespondWith400IfPatientIsEmpty() {
        legalConsentRequest.setPatientIdentifier("");

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenThrow(new StorageException(""));

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);
        assert (response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assert (Objects.equals(response.getBody(), "Patient identifier cannot be empty or null!"));
    }

    @Test
    public void shouldRespondWith400IfPatientIsNull() {
        legalConsentRequest.setPatientIdentifier(null);

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenThrow(new StorageException(""));

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);
        assert (response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assert (Objects.equals(response.getBody(), "Patient identifier cannot be empty or null!"));
    }

    @Test
    public void shouldRespondWith500IfErrorWritingFile(){

    }

    @Test
    public void shouldRespondWith400IfEmptyBody(){

    }

    @Test
    public void shouldRespondWith404IfPatientDoesNotExist(){

    }

    @Test
    public void shouldRespondWith429IfTooManyRequests() {
        Bucket bucket = Mockito.mock(Bucket.class);
        Mockito.when(bucket.tryConsume(1)).thenReturn(false);

        fileUploadController = new FileUploadController(basicModuleService, bucket);

        Mockito.when(basicModuleService.store(legalConsentRequest)).thenThrow(new StorageException(""));

        ResponseEntity response = fileUploadController.handleFileUpload(legalConsentRequest);

        assert (response.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS));
        assert (Objects.equals(response.getBody(), "Too many request!"));
    }
}
