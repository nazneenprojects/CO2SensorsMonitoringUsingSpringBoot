package com.app.emission.EmissionTrack.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.emission.EmissionTrack.Entity.Emission;


@Repository
public interface EmissionRepo extends JpaRepository<Emission, Serializable>  {


	@Query("SELECT AVG(e.co2) FROM Emission e WHERE e.uuid = ?1")
	public double emissionAvgMetrics(UUID uuid);

	Emission findTopByUuidOrderByRecordedAtDesc(UUID uuuid);

	List<Emission> findTopByUuidAndStatusEqualsOrderByRecordedAtDesc(UUID uuid, String status);

	List<Emission> findByUuidAndStatusEqualsOrderByRecordedAtAsc(UUID uuid, String status);

	@Query("SELECT MAX(e.co2) FROM Emission e WHERE e.uuid = ?1 AND e.recordedAt > DATEADD('DAY',-30, CURRENT_DATE)")
	int findMaxEmission(UUID uuid);

	@Query("SELECT  AVG(e.co2) FROM Emission e WHERE e.uuid = ?1 AND e.recordedAt > DATEADD('DAY',-30, CURRENT_DATE)")
	int findAvgEmission(UUID uuid);


}
