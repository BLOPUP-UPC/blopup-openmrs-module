package org.openmrs.module.blopup.openmrs.module.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.Provider;
import org.openmrs.ProviderAttribute;
import org.openmrs.ProviderAttributeType;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProviderServiceImpl.class, Context.class })
public class ProviderServiceImplTest {
	
	@InjectMocks
	private ProviderServiceImpl providerService;
	
	@Mock
	private ProviderService providerServiceMock;
	
	@Before
	public void setUp() {
		initMocks(this);
	}
	
	@Test
	public void shouldReturnProviderChatId() {
		String providerUuid = "providerUuid";
		String providerChatId = "providerChatId";
		
		Provider provider = new Provider();
		ProviderAttribute providerAttribute = new ProviderAttribute();
		ProviderAttributeType attributeType = new ProviderAttributeType();
		
		attributeType.setProviderAttributeTypeId(1);
		providerAttribute.setValue(providerChatId);
		providerAttribute.setAttributeType(attributeType);
		provider.setUuid(providerUuid);
		provider.setAttributes(new HashSet<ProviderAttribute>() {
			
			{
				add(providerAttribute);
			}
		});
		
		PowerMockito.mockStatic(Context.class);
		PowerMockito.when(Context.getProviderService()).thenReturn(providerServiceMock);
		when(providerServiceMock.getProviderByUuid(providerUuid)).thenReturn(provider);
		
		Object response = providerService.getProviderChatId(providerUuid);
		assertEquals(providerChatId, response);
	}
}
