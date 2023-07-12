/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module.api.dao;

import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.blopup.fileupload.module.LegalConsent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("blopup.fileupload.module.BlopupfileuploadmoduleDao")
public class BlopupfileuploadmoduleDao {

    @Autowired
    DbSessionFactory sessionFactory;

    private DbSession getSession() {
        return sessionFactory.getCurrentSession();
    }

    public LegalConsent getLegalConsentByFilePath(String filePath) {
        return (LegalConsent) getSession().createCriteria(LegalConsent.class).add(Restrictions.eq("filePath", filePath))
                .uniqueResult();
    }

    public LegalConsent saveOrUpdateLegalConsent(LegalConsent legalConsent) {
        LegalConsent savedLegalConsent = getLegalConsentByFilePath(legalConsent.getFilePath());
        if (savedLegalConsent == null) {
            savedLegalConsent = new LegalConsent();
        }
        savedLegalConsent.setFilePath(legalConsent.getFilePath());
        savedLegalConsent.setPatient(legalConsent.getPatient());

        getSession().saveOrUpdate(savedLegalConsent);
        return savedLegalConsent;
    }
}
