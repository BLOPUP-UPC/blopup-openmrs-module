/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.blopup.fileupload.module;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RateLimitServiceTest {
	
	Refill refill;
	
	Bandwidth limit;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void refill_10_tokens_per_minute() {
		refill = Refill.intervally(10, Duration.ofMinutes(1));
		
		limit = Bandwidth.classic(10, refill);
		
		Bucket bucket = Bucket.builder().addLimit(limit).build();
		
		for (int i = 1; i <= 10; i++) {
			assertTrue(bucket.tryConsume(1));
		}
		
		assertFalse(bucket.tryConsume(1));
	}
	
	@Test
    public void refill_1_token_per_2_seconds() {

        limit = Bandwidth.classic(1, Refill.intervally(1, Duration.ofSeconds(2)));
        Bucket bucket = Bucket.builder()
                .addLimit(limit)
                .build();

        assertTrue(bucket.tryConsume(1));     // first request
        Executors.newScheduledThreadPool(1)   // schedule another request for 2 seconds later
                .schedule(() -> assertTrue(bucket.tryConsume(1)), 2, TimeUnit.SECONDS);
    }
	
	@Test
	public void prevent_exhausting_tokens_in_5_seconds() {
		
		Bucket bucket = Bucket.builder().addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
		        .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(20)))).build();
		
		for (int i = 1; i <= 5; i++) {
			assertTrue(bucket.tryConsume(1));
		}
		assertFalse(bucket.tryConsume(1));
	}
	
}
