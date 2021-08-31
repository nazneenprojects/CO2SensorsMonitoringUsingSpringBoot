package com.app.emission.EmissionTrack.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.emission.EmissionTrack.Entity.Emission;
import com.app.emission.EmissionTrack.Repository.EmissionRepo;



@Service
public class EmissionService {

	@Autowired
	private EmissionRepo emissionRepo;
	boolean alert=false;

	public List<Emission> addEmissionsInfo(UUID uuid, List<Emission> emission) {	

		// The below business logic is implemented to compute the status based on CO2 levels.
		// Currently, by considering time constraints, it is implemented in basic JAVA but it can be improved 
		// using latest JAVA concepts like streamAPI. 

		for(Emission e: emission)
		{
			e.setUuid(uuid);
		}
		
		for(int i =0; i< emission.size(); i++)
	{	
		if(alert==true)
		{		
			if(emission.get(i).getCo2() < 2000 && emission.get(i-1).getCo2() <2000 && emission.get(i-2).getCo2() <2000 )
			{
				emission.get(i).setStatus("OKAY");
				alert=false;
			}else 
			{				
				emission.get(i).setStatus("ALERT");
					alert=true;
			}
		}
					
		if(alert==false && emission.get(i).getCo2() > 2000 )
			{
				if((i-1)<2)
				{
					emission.get(i).setStatus("WARN");
					alert=false;					
				}
				else
				{
					if(emission.get(i-1).getCo2() > 2000 && emission.get(i-2).getCo2() > 2000){
						emission.get(i).setStatus("ALERT");
						alert=true;
					}
					else{
						
						emission.get(i).setStatus("WARN");
						alert=false;
					}					
				}					
		}
		
		
		if(alert==false && emission.get(i).getCo2() < 2000)
		{
			emission.get(i).setStatus("OKAY");
			alert=false;
			
		}
	}
		return emissionRepo.saveAll(emission);

	} 

	public ResponseEntity<String> getEmissionMetrics(UUID uuid) {		  
		int max =  emissionRepo.findMaxEmission(uuid);
		int avg = emissionRepo.findAvgEmission(uuid);
		String output = "MaxLast30Days: "+ max +" AND " + " AvgLast30Days: "+ avg;		
	        return new ResponseEntity<>(output, HttpStatus.OK);
	}

	public ResponseEntity<String> getEmissionStatus(UUID uuid) {
		
		Emission emission = emissionRepo.findTopByUuidOrderByRecordedAtDesc(uuid);
		return  new ResponseEntity<>(emission.getStatus(), HttpStatus.OK);
	}

	public ResponseEntity<List<Emission>> getEmissionAlerts(UUID uuid) {
				
		String status = "ALERT";		
		List<Emission> emission = emissionRepo.findByUuidAndStatusEqualsOrderByRecordedAtAsc(uuid, status);
		
		return  new ResponseEntity<>(emission, HttpStatus.OK);
	
	}
	
}
