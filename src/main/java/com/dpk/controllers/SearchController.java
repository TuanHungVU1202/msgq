package com.dpk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dpk.common.Utils;
import com.dpk.models.Claim;
import com.dpk.models.ClaimDetails;
import com.dpk.models.ClaimList;
import com.dpk.services.SearchService;

@Controller
@RequestMapping(value = "/claim")
public class SearchController {
	@Autowired
	SearchService searchService;

	/*
	 * Get all claim list
	 */
	@GetMapping(value = "/lists", headers = "Accept=application/json")
	public ResponseEntity<String> getAllLists() {
		String returnList = searchService.getAllList();
		return new ResponseEntity<String>(returnList,HttpStatus.OK);
	}
	
	/*
	 * Get all claim Details
	 */
	@GetMapping(value = "/details", headers = "Accept=application/json")
	public ResponseEntity<String> getAllDetails() {
		String returnDetails = searchService.getAllDetails();
		return new ResponseEntity<String>(returnDetails,HttpStatus.OK);
	}

	/*
	 * Get claim list by ID
	 */
	@GetMapping(value = "/list/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> getUserClaimList(@PathVariable String id) {
		String returnString = searchService.getClaimList(id);
		return new ResponseEntity<String>(returnString,HttpStatus.OK);
	}
	
	/*
	 * Get claim details by ID
	 */
	@GetMapping(value = "/details/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> getUserClaimDetails(@PathVariable String id) {
		String returnString = searchService.getClaimDetails(id);
		return new ResponseEntity<String>(returnString,HttpStatus.OK);
	}

	// Creating claim request
//	@PostMapping(value = "/add/{id}", headers = "Accept=application/json")
//	public ResponseEntity<String> addClaimDetails(@PathVariable String id, @RequestBody Claim claimRequest) {
//		String claimJson = searchService.createClaim(claimRequest, id);
//		return new ResponseEntity<String>(claimJson, HttpStatus.CREATED);
//	}

	/*
	 * Search by ClaimId or proposerName
	 * Passed argument: 
	 * {
	 * 	"search": ""
	 * }
	 */
	@PostMapping(value = "/search", headers = "Accept=application/json")
	public ResponseEntity<String> searchClaimList(@RequestBody String dataSearch) {
		String returnList="";
		try {
			JSONObject json = Utils.parseToJsonObject(dataSearch);
			returnList = searchService.searchClaimList(json.get("search").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>(returnList, HttpStatus.FOUND);
	}
	
	/*
	 * Date sort
	 * Passed argument: 
	 * {
	 * 	"order": "asc" (or desc)
	 * }
	 */
	@PostMapping(value = "/sort-date", headers = "Accept=application/json")
	public ResponseEntity<String> sortDate(@RequestBody String order) {
		String returnList="";
		try {
			JSONObject json = Utils.parseToJsonObject(order);
			returnList = searchService.sortDate(json.get("order").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>(returnList, HttpStatus.FOUND);
	}
}
