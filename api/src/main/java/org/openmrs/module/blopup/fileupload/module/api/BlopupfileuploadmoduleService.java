/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.blopup.fileupload.module.BlopupfileuploadmoduleConfig;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.openmrs.module.blopup.fileupload.module.api.models.LegalConsentRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface BlopupfileuploadmoduleService extends OpenmrsService {
	
	String store(LegalConsentRequest legalConsentRequest);
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	LegalConsent getLegalConsentByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param legalConsent
	 * @return
	 * @throws APIException
	 */
	@Authorized(BlopupfileuploadmoduleConfig.MODULE_PRIVILEGE)
	@Transactional
	LegalConsent saveLegalConsent(LegalConsent legalConsent) throws APIException;
}
