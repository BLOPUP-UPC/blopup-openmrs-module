package org.openmrs.module.blopup.fileupload.module.web.controller;

import org.openmrs.module.blopup.fileupload.module.ContactDoctorService;
import org.openmrs.module.blopup.fileupload.module.api.models.ContactDoctorRequest;
import org.openmrs.module.blopup.fileupload.module.api.models.TelegramMessage;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/contactDoctor")
public class ContactDoctorController extends BaseRestController {
	
	@Autowired
	private ContactDoctorService contactDoctorService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> contactDoctor(@RequestBody ContactDoctorRequest contactDoctorRequest) {

        if (contactDoctorRequest.getProviderUuid() == null || contactDoctorRequest.getMessage() == null)
            return new ResponseEntity<>("Missing parameter: chatId or message", HttpStatus.BAD_REQUEST);

        TelegramMessage telegramMessage = contactDoctorService.createTelegramMessage(contactDoctorRequest);

        Boolean response = contactDoctorService.sendMessageToDoctor(telegramMessage);

        if (!response) {
            return new ResponseEntity<>("Error sending message to doctor", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
