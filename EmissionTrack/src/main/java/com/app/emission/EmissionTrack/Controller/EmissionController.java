package com.app.emission.EmissionTrack.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.emission.EmissionTrack.Entity.Emission;
import com.app.emission.EmissionTrack.Error.ResourceNotFoundException;
import com.app.emission.EmissionTrack.service.EmissionService;

@RestController
@RequestMapping("/api")
public class EmissionController {

	@Autowired
	EmissionService emissionService;

	/*
	 * // Collect sensor measurement
	 * 
	 * @PostMapping("/v1/sensors/measurements") public List<Emission>
	 * addEmissionsInfo(@RequestBody List<Emission> emission) { try { return
	 * emissionService.addEmissionsInfo(emission); } catch (Exception e) { throw new
	 * ResourceNotFoundException( "Please provide valid emission values"); } }
	 */
	
	// Collect sensor measurement
	@PostMapping("/v1/sensors/{uuid}/measurements") 
	public List<Emission> addEmissionsInfo(@PathVariable UUID uuid, @RequestBody List<Emission> emission) 
	{ try 
	{ return emissionService.addEmissionsInfo(uuid,emission); } 
	catch (Exception e) 
	{ throw new	ResourceNotFoundException( "Please provide valid emission values"); } }

	// Sensor Status
	@GetMapping("/v1/sensors/{uuid}") 
	public ResponseEntity<String> getEmissionStatus(@PathVariable UUID uuid) 
	{ try 
	{ return emissionService.getEmissionStatus(uuid); 
	} 
	catch (Exception exc) 
	{ throw
		new ResourceNotFoundException( uuid + " Not found"); } }

	// Sensor Metrics 
	@GetMapping("/v1/sensors/{uuid}/metrics") 
	public ResponseEntity<String> getEmissionStats(@PathVariable UUID uuid) 
	{ try 
	{ return emissionService.getEmissionMetrics(uuid); } 
	catch (Exception exc) 
	{ throw	new ResourceNotFoundException( uuid + " Not found"); } }

	// Listing Alerts
	@GetMapping("/v1/sensors/{uuid}/alerts") 
	public ResponseEntity<List<Emission>> getEmissionAlerts(@PathVariable UUID uuid) 
	{ try 
	{ return emissionService.getEmissionAlerts(uuid); 
	} 
	catch (Exception exc) 
	{ throw
		new ResourceNotFoundException( uuid + " Not found"); } }


}
