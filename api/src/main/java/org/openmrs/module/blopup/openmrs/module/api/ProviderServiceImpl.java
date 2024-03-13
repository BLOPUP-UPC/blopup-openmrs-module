package org.openmrs.module.blopup.openmrs.module.api;

import org.openmrs.Provider;
import org.openmrs.ProviderAttribute;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl extends BaseOpenmrsService {
	
	public String getProviderChatId(String providerUuid) {
        Provider provider = Context.getProviderService().getProviderByUuid(providerUuid);
        return provider.getAttributes()
                .stream()
                .filter(attribute -> attribute.getAttributeType().getProviderAttributeTypeId().equals(1))
                .findFirst()
                .orElseGet(ProviderAttribute::new)
                .getValue().toString();
    }
}
