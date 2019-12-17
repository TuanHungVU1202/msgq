package com.dpk.models;

public class Claim {
	private String policyNumber;
	private String policyHolder;
	private String insuredObject;
	private String startDate;
	private String endDate;
	private String productType;
	private Long insuredSum;
	private String premium;
	private String dateOfPayment;

	public Claim() {
		super();
	}

	public Claim(String policyNumber, String policyHolder, String insuredObject, String startDate, String endDate,
			String productType, Long insuredSum, String premium, String dateOfPayment) {
		super();
		this.policyNumber = policyNumber;
		this.policyHolder = policyHolder;
		this.insuredObject = insuredObject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.productType = productType;
		this.insuredSum = insuredSum;
		this.premium = premium;
		this.dateOfPayment = dateOfPayment;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
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
