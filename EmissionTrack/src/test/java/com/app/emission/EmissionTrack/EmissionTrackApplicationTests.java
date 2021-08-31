package com.app.emission.EmissionTrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.emission.EmissionTrack.Entity.Emission;
import com.app.emission.EmissionTrack.Repository.EmissionRepo;
import com.app.emission.EmissionTrack.service.EmissionService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class EmissionTrackApplicationTests {

	@Autowired
	EmissionRepo emissionRepo;
	
	@Autowired
	EmissionService emissionService;

	UUID uuid = UUID.randomUUID();
	LocalDateTime dateTime1 = LocalDateTime.now().plus(Duration.of(1, ChronoUnit.MINUTES));
	LocalDateTime dateTime2 = LocalDateTime.now().plus(Duration.of(2, ChronoUnit.MINUTES));
	LocalDateTime dateTime3 = LocalDateTime.now().plus(Duration.of(3, ChronoUnit.MINUTES));
	LocalDateTime dateTime4 = LocalDateTime.now().plus(Duration.of(4, ChronoUnit.MINUTES));

	@Test

	@Order(1)
	public void testToCollectSesnorMeasurements() {

		List<Emission> emission = new ArrayList<Emission>();
		Emission e1 = new Emission();
		e1.setId(1);
		e1.setProductId("Sensor");
		e1.setCo2(1900);
		e1.setRecordedAt(dateTime1);
		e1.setUuid(uuid);

		Emission e2 = new Emission();
		e2.setId(2);
		e2.setProductId("Sensor");
		e2.setCo2(2100);
		e2.setRecordedAt(dateTime2);
		e2.setUuid(uuid);

		Emission e3 = new Emission();
		e3.setId(3);
		e3.setProductId("Sensor");
		e3.setCo2(2700);
		e3.setRecordedAt(dateTime3);
		e3.setUuid(uuid);

		Emission e4 = new Emission();
		e4.setId(4);
		e4.setProductId("Sensor");
		e4.setCo2(2500);
		e4.setRecordedAt(dateTime4);
		e4.setUuid(uuid);

		emission.add(e1); emission.add(e2); emission.add(e3); emission.add(e4);
		emissionService.addEmissionsInfo(uuid, emission);
		emissionRepo.saveAll(emission);

		assertNotNull(emissionRepo.count());
		assertEquals("OKAY", emission.get(0).getStatus());
		assertEquals("WARN", emission.get(1).getStatus());
		assertEquals("WARN", emission.get(2).getStatus());
		assertEquals("ALERT", emission.get(3).getStatus());

		Emission e = emissionRepo.findTopByUuidOrderByRecordedAtDesc(uuid);
		assertEquals("ALERT", e.getStatus() );

		int max = emissionRepo.findMaxEmission(uuid); 
		int avg = emissionRepo.findAvgEmission(uuid);

		assertEquals(2700, max ); assertEquals(2300, avg ); 

		List<Emission> ee2 =
				emissionRepo.findByUuidAndStatusEqualsOrderByRecordedAtAsc(uuid, "ALERT");
		assertNotNull(ee2.size());

	}

}
