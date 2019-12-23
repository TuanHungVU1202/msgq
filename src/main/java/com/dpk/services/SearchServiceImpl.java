package com.dpk.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dpk.common.Utils;
import com.dpk.mapper.Mapper;
import com.dpk.models.ClaimDetails;
import com.dpk.models.ClaimList;
import com.google.gson.JsonParser;

/*
 * Convert to json:
 * 		JSONObject json = Utils.parseToJsonObject(string);
 * 
 * Passing variable to String: %s: string, %d: decimal
 * 		String queryRawUrl = urlClaimDetails + "/%s";
		String parsedUrl = String.format(queryRawUrl, id); 
 */
@Service
public class SearchServiceImpl implements SearchService {
	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private RestTemplate restClient;

//	@Value("${elasticsearch.base.url}")
//	private String elasticsearchBaseUrl;
//
//	@Value("${elasticsearch.index.uri}")
//	private String elasticsearchIndexUri;
//
//	@Value("${elasticsearch.type.uri.details}")
//	private String elasticsearchTypeDetails;
//	
//	@Value("${elasticsearch.type.uri.list}")
//	private String elasticsearchTypeList;

	@Value("${elasticsearch.check.mapping}")
	private String urlCheckMapping;

	@Value("${elasticsearch.set.mapping}")
	private String urlSetMapping;

	@Value("${elasticsearch.search.list}")
	private String urlSearchList;

	@Value("${elasticsearch.search.details}")
	private String urlSearchDetails;

	@Value("${elasticsearch.claim.details}")
	private String urlClaimDetails;

	@Value("${elasticsearch.claim.list}")
	private String urlClaimList;

//	String URL_SET_MAPPING = "http://localhost:9600/claim";

//	String URL_SEARCH = "http://localhost:9600/claim/details/_search";

	String idToCreate;

//	public SearchServiceImpl() {
//		// TODO Auto-generated constructor stub
//		URL_CHECK_STATUS_LIST = "http://" + elasticsearchBaseUrl + "/" + elasticsearchIndexUri + "/"
//				+ elasticsearchTypeList;
//	}

	/*
	 * Get All Claim Lists
	 */
	@Override
	public String getAllList() {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<String> responseEntity = restClient.exchange(urlSearchList, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getBody();
	}
	
	/*
	 * Get All Claim Details
	 */
	@Override
	public String getAllDetails() {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<String> responseEntity = restClient.exchange(urlSearchDetails, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getBody();
	}

	// TODO: getClaimList
	@Override
	public String getClaimList(String id) {

		String queryRawUrl = urlClaimList + "/%s";
		String parsedUrl = String.format(queryRawUrl, id);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<String> responseEntity = restClient.exchange(parsedUrl, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getBody();
	}
	
	// TODO: Get claimDetails
	@Override
	public String getClaimDetails(String id) {

		String queryRawUrl = urlClaimDetails + "/%s";
		String parsedUrl = String.format(queryRawUrl, id);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<String> responseEntity = restClient.exchange(parsedUrl, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getBody();
	}

	/*
	 * Set mapping for data
	 * 		claimId: keyword
	 * 		proposerName: keyword
	 * 		createdDate: date
	 * 		lastModified: date
	 */
	@Override
	public void setMapping() throws IOException {
		String mappingClaimList = "{\r\n" + 
				"    \"mappings\": {\r\n" + 
				"        \"list\": {\r\n" + 
				"            \"properties\": {\r\n" + 
				"                \"claimId\": {\r\n" + 
				"                    \"type\": \"keyword\"\r\n" + 
				"                },\r\n" + 
				"                \"proposerName\": {\r\n" + 
				"                    \"type\": \"keyword\"\r\n" + 
				"                },\r\n" + 
				"                \"proposerNameNonAccent\": {\r\n" + 
				"                    \"type\": \"keyword\"\r\n" + 
				"                },				\r\n" + 
				"                \"createdDate\": {\r\n" + 
				"                    \"type\": \"date\"\r\n" + 
				"                },\r\n" + 
				"                \"lastModified\": {\r\n" + 
				"                    \"type\": \"date\"\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    }\r\n" + 
				"}";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		try {
			HttpEntity<String> httpEntity = new HttpEntity<String>(mappingClaimList, httpHeaders);
			ResponseEntity<String> responseEntity = restClient.exchange(urlSetMapping, HttpMethod.PUT, httpEntity,
					String.class);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/*
	 * Check mapping status whether exists
	 */
	@Override
	public HttpStatus getMappingStatus() {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		ResponseEntity<String> responseEntity = restClient.exchange(urlCheckMapping, HttpMethod.GET, httpEntity,
				String.class);

		return responseEntity.getStatusCode();
	}

	// Create claim by claim object receiving from Rabbitmq
//	@Override
//	public String createClaim(Claim claimBody, String id) {
//		this.idToCreate = id;
//		String URL_CREATE = String.format("http://localhost:9600/claim/details/%s", idToCreate);
//
////		RestTemplate restTemplate = new RestTemplate();
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//		String jsonInString = new Gson().toJson(claimBody);
//		HttpEntity<String> httpEntity = new HttpEntity<String>(jsonInString, httpHeaders);
//		ResponseEntity<String> responseEntity = restClient.exchange(URL_CREATE, HttpMethod.PUT, httpEntity,
//				String.class);
//
//		return jsonInString;
//	}

	
	/*
	 * Search by ClaimId or proposerName
	 * Passed argument: 
	 * {
	 * 	"search": ""
	 * }
	 */
	@Override
	public String searchClaimList(String dataSearch) {
//		ClaimList claimList = new ClaimList();

		String bodyToPost = "{\r\n" + 
				" \"query\": {\r\n" + 
				"          \"bool\": {\r\n" + 
				"              \"should\": [\r\n" + 
				"                {\r\n" + 
				"                  \"wildcard\": { \"claimId\": \"*%s*\" }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                  \"wildcard\": { \"proposerName\": \"*%s*\" }\r\n" + 
				"                },\r\n" + 
				"                {\r\n" + 
				"                  \"wildcard\": { \"proposerNameNonAccent\": \"%s\" }\r\n" + 
				"                }\r\n" + 
				"              ],\r\n" + 
				"              \"minimum_should_match\": 1\r\n" + 
				"          }\r\n" + 
				"      }\r\n" + 
				"}";

		String removedAccentData = Utils.removeAccent(dataSearch);
		String parsedData = String.format(bodyToPost, dataSearch, dataSearch, dataSearch);

		JSONObject json = Utils.parseToJsonObject(parsedData);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
		ResponseEntity<String> responseEntity = restClient.exchange(urlSearchList, HttpMethod.POST, httpEntity,
				String.class);

		String returnString = responseEntity.getBody();

		return returnString;
	}

	/*
	 * Date sort
	 * Passed argument: 
	 * {
	 * 	"order": "asc" (or desc)
	 * }
	 */
	@Override
	public String sortDate(String order) {
		String orderString = "{\r\n" + "  \"query\": {\r\n" + "    \"match_all\": {}\r\n" + "  },\r\n"
				+ "  \"sort\": [\r\n" + "    {\r\n" + "      \"createdDate\": {\r\n" + "        \"order\": \"%s\"\r\n"
				+ "      }\r\n" + "    }\r\n" + "  ]\r\n" + "}";

		String parsedData = String.format(orderString, order);
		JSONObject json = Utils.parseToJsonObject(parsedData);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
		ResponseEntity<String> responseEntity = restClient.exchange(urlSearchList, HttpMethod.POST, httpEntity,
				String.class);

		String returnString = responseEntity.getBody();

		return returnString;
	}

	/*
	 * Handle messages recieved from RabbitMq
	 * Then PUT to Elasticsearch (update if already existed)
	 */
	@Override
	public String receiveMessage(Message message) throws IOException {
//		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		Mapper byteToObj = new Mapper();
		JsonParser parser = new JsonParser();

		String dataToSend = new String(message.getBody(), Charset.forName("UTF-8"));
		String jsonRawString = parser.parse(dataToSend).getAsString();
		JSONObject json = Utils.parseToJsonObject(jsonRawString);

		//TODO: put data to proposerNameNonAccent
		ClaimDetails returnClaimDetails = mapToClaimDetails(json);
		ClaimList returnClaimList = mapToClaimList(json);

		try {
			Long getClaim = json.getJSONObject("claim").getLong("id");
			String URL_CLAIM_DETAILS = String.format(urlClaimDetails + "/%d", getClaim);
			String URL_CLAIM_LIST = String.format(urlClaimList + "/%d", getClaim);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<ClaimList> claimListEntity = new HttpEntity<ClaimList>(returnClaimList, httpHeaders);
			HttpEntity<ClaimDetails> claimDetailsEntity = new HttpEntity<ClaimDetails>(returnClaimDetails, httpHeaders);

			ResponseEntity<String> responseClaimDetails = restClient.exchange(URL_CLAIM_DETAILS, HttpMethod.PUT,
					claimDetailsEntity, String.class);
			ResponseEntity<String> responseClaimList = restClient.exchange(URL_CLAIM_LIST, HttpMethod.PUT,
					claimListEntity, String.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//		HttpEntity<ClaimList> claimListEntity = new HttpEntity<ClaimList>(returnClaimList, httpHeaders);
//		HttpEntity<ClaimDetails> claimDetailsEntity = new HttpEntity<ClaimDetails>(returnClaimDetails, httpHeaders);

//		ResponseEntity<String> responseClaimList = restClient.exchange(urlClaimList + "/1", HttpMethod.PUT, claimListEntity,
//				String.class);
//		ResponseEntity<String> responseClaimDetails = restClient.exchange(urlClaimDetails + "/1", HttpMethod.PUT,
//				claimDetailsEntity, String.class);

		return jsonRawString;
	}

	/*
	 * Map to claimDetails model
	 */
	@Override
	public ClaimDetails mapToClaimDetails(JSONObject json) {
		ClaimDetails claimDetails = new ClaimDetails();
		try {
			String getClaimDetails = json.getJSONObject("claim").getString("claimId");
			claimDetails.setClaimId(getClaimDetails);

			getClaimDetails = json.getJSONObject("claim").getString("policyHolder");
			claimDetails.setPolicyholder(getClaimDetails);

			getClaimDetails = json.getJSONObject("claim").getString("policyNumber");
			claimDetails.setPolicyNumber(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("productDetails")
					.getString("policyStartDate");
			claimDetails.setPolicyStartDate(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("productDetails")
					.getString("policyEndDate");
			claimDetails.setPolicyEndDate(getClaimDetails);

			getClaimDetails = json.getJSONObject("claim").getString("createdDate");
			claimDetails.setCreatedDate(getClaimDetails);

			getClaimDetails = json.getJSONObject("claim").getString("lastModified");
			claimDetails.setLastModified(getClaimDetails);

			getClaimDetails = json.getJSONObject("claim").getString("status");
			claimDetails.setStatus(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("claimantDetails")
					.getString("claimantName");
			claimDetails.setClaimantName(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("proposerDetails")
					.getString("proposerName");
			claimDetails.setProposerName(getClaimDetails);
			
			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("proposerDetails")
					.getString("proposerName");
			claimDetails.setProposerNameNonAccent(Utils.removeAccent(getClaimDetails));

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("proposerDetails")
					.getString("dateOfBirth");
			claimDetails.setDateOfBirth(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("treatmentDetails")
					.getString("dateOfVisit");
			claimDetails.setDateOfVisit(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("causesOfClaims")
					.getString("causeOfIncident");
			claimDetails.setCauseOfIncident(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("treatmentDetails")
					.getString("nameofClinics");
			claimDetails.setNameofClinics(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("treatmentDetails")
					.getString("nameofHospital");
			claimDetails.setNameofHospital(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("treatmentDetails")
					.getString("dateofAdmission");
			claimDetails.setDateofAdmission(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("treatmentDetails")
					.getString("dateofDischarge");
			claimDetails.setDateofDischarge(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("totalClaimAmount");
			claimDetails.setTotalClaimAmount(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("bankorCash");
			claimDetails.setBankorCash(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("accountNumber");
			claimDetails.setAccountNumber(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("nameofBank");
			claimDetails.setNameofBank(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("bankBranch");
			claimDetails.setBankBranch(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("paymentDetails")
					.getString("nameofBankAccountsHolder");
			claimDetails.setNameofBankAccountsHolder(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("supporingDocuments")
					.getString("hospitalDischargePaper");
			claimDetails.setHospitalDischargePaper(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("supporingDocuments")
					.getString("hospitalbillrecipt");
			claimDetails.setHospitalbillrecipt(getClaimDetails);

			getClaimDetails = json.getJSONObject("claimRequest").getJSONObject("supporingDocuments")
					.getString("prescription");
			claimDetails.setPrescription(getClaimDetails);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return claimDetails;
	}

	/*
	 * Map to claimList model
	 */
	@Override
	public ClaimList mapToClaimList(JSONObject json) {
		ClaimList claimList = new ClaimList();
		try {
			String getClaimList = json.getJSONObject("claim").getString("claimId");
			claimList.setClaimId(getClaimList);

			getClaimList = json.getJSONObject("claim").getString("claimant");
			claimList.setClaimantName(getClaimList);

			getClaimList = json.getJSONObject("claimRequest").getJSONObject("proposerDetails")
					.getString("proposerName");
			claimList.setProposerName(getClaimList);
			
			getClaimList = json.getJSONObject("claimRequest").getJSONObject("proposerDetails")
					.getString("proposerName");
			claimList.setProposerNameNonAccent(Utils.removeAccent(getClaimList));

			getClaimList = json.getJSONObject("claim").getString("policyNumber");
			claimList.setPolicyNumber(getClaimList);

			getClaimList = json.getJSONObject("claim").getString("createdDate");
			claimList.setCreatedDate(getClaimList);
			String createdDate = Utils.parseToDateString(getClaimList);
			log.info("created Date: " + createdDate);

			getClaimList = json.getJSONObject("claim").getString("lastModified");
			claimList.setLastModified(getClaimList);
			String lastModified = Utils.parseToDateString(getClaimList);
			log.info("lastModified Date: " + lastModified);

			getClaimList = json.getJSONObject("claim").getString("status");
			claimList.setStatus(getClaimList);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return claimList;
	}
}
