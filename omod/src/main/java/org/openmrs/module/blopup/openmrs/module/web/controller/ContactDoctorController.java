package org.openmrs.module.blopup.openmrs.module.web.controller;

import org.openmrs.module.blopup.openmrs.module.api.ContactDoctorService;
import org.openmrs.module.blopup.openmrs.module.api.models.ContactDoctorRequest;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/contactDoctor")
public class ContactDoctorController extends BaseRestController {
	
	@Autowired
	private ContactDoctorService contactDoctorService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> contactDoctor(@RequestBody ContactDoctorRequest contactDoctorRequest) {

        if (contactDoctorRequest.getProviderUuid() == null || contactDoctorRequest.getMessage() == null)
            return new ResponseEntity<>("Missing parameter: providerUuid or message", HttpStatus.BAD_REQUEST);


        Boolean response = contactDoctorService.sendMessageToDoctor(contactDoctorRequest);

        if (!response) {
            return new ResponseEntity<>("Error sending message to doctor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Logger.getLogger("ContactDoctorController").info("Message sent to doctor with provider uuid: " + contactDoctorRequest.getProviderUuid() + " and message: " + contactDoctorRequest.getMessage());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
