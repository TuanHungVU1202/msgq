package com.dpk.models;

public class Claim {
	private String policyNumber;
	private String policyholder;
	private String insuredObject;
	private String startDate;
	private String endDate;
	private String productType;
	private Long insuredSum;
	private String premium;
	private String dateOfPayment;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyholder() {
		return policyholder;
	}

	public void setPolicyholder(String policyholder) {
		this.policyholder = policyholder;
	}

	public String getInsuredObject() {
		return insuredObject;
	}

	public void setInsuredObject(String insuredObject) {
		this.insuredObject = insuredObject;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getInsuredSum() {
		return insuredSum;
	}

	public void setInsuredSum(Long insuredSum) {
		this.insuredSum = insuredSum;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
}
