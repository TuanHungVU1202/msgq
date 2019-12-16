package com.dpk.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;

public class Mapper {
	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

	public byte[] objectToByte(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(object);
			return json.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> T byteToObject(byte[] bytes, Class<T> cls) throws IOException {
		try {
			return new ObjectMapper().readValue(new String(bytes), cls);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static ResponseClaimObjectExt getfieldsResclaim(String input) {
//		ResponseClaimObjectExt  resClaimExt = new ResponseClaimObjectExt();
//		ResponseClaimObject resClaim = new ResponseClaimObject();
//		try {
//			com.google.gson.JsonObject convertedObject = new Gson().fromJson(input, com.google.gson.JsonObject.class)
//					.getAsJsonObject(DATA);
//			JsonElement element = convertedObject.get(EXTERNAL_REFERENCE);
//			if (element != null) {
//				String externalReference = convertedObject.get(EXTERNAL_REFERENCE).getAsString();
//				resClaim.setReferenceNumber(Long.parseLong(externalReference));
//			}
//			/*com.google.gson.JsonObject metaDataObject = convertedObject.getAsJsonObject(META_DATA);
//			if (metaDataObject != null) {
//			element = metaDataObject.get(AGENT_ID);*/
//			element = getJsonElement(convertedObject,META_DATA,AGENT_ID);
//			if (element != null) {
//				resClaim.setAgentid(element.getAsString());
//			}
//			element = getJsonElement(convertedObject,CUSTOMER,POLICY_HOLDER);
//			resClaimExt.setResponseClaim(resClaim);
//			if (element != null) {
//				resClaimExt.setPolicyHolder(element.getAsString());
//			}
//			//}
//			//com.google.gson.JsonObject customerObject = convertedObject.getAsJsonObject(CUSTOMER);
//			// 
//		} catch (Exception e) {
//			//e.printStackTrace();
//			logger.error(e.getMessage());
//			resClaimExt = null;
//		}
//		return resClaimExt;
//	}

	private static JsonElement getJsonElement(com.google.gson.JsonObject convertedObject, String parent, String child) {
		com.google.gson.JsonObject metaDataObject = convertedObject.getAsJsonObject(parent);
		// com.google.gson.JsonObject metaDataObject =
		// convertedObject.getAsJsonObject(META_DATA);
		if (metaDataObject != null) {
			return metaDataObject.get(child);
		} else {
			return null;
		}
	}

//	public static <T> List<String> getListField(List<T> filterlist, String methodName) {
//		List<String> result = filterlist.stream().map(obj -> {
//			try {
//				Method method = obj.getClass().getMethod(methodName);
//				return method.invoke(obj).toString();
//			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
//					| NoSuchMethodException | SecurityException e) {
//				e.printStackTrace();
//				return null;
//			}
//		}).collect(Collectors.toList());
//		return result;
//	}

//	public static List<ArrayClaimIdListResultInner> convertfromClaimToResponse(List<Claim> lstClaims) {
//		List<ArrayClaimIdListResultInner> result = lstClaims.stream().map(obj -> {
//			ArrayClaimIdListResultInner responseClaim = new ArrayClaimIdListResultInner();
//			responseClaim.setClaimId(obj.getId().toString());
//			responseClaim.setStatus(obj.getStatus());
//			responseClaim.setRemarks(obj.getRemarks());
//			ArrayDocumentRequired arrDocument = new ArrayDocumentRequired();
//			arrDocument.addAll(getListField(obj.getDocumentRequired(), "getIdDocument"));
//			responseClaim.setDocumentRequired(arrDocument);
//			return responseClaim;
//		}).collect(Collectors.toList());
//		return result;
//	}
}
