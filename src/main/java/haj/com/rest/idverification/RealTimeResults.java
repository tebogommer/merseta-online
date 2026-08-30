package haj.com.rest.idverification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealTimeResults {

	@SerializedName("traceId")
	@Expose
	private Integer traceId;
	@SerializedName("surName")
	@Expose
	private String  surName;
	@SerializedName("gender")
	@Expose
	private String  gender;
	@SerializedName("dob")
	@Expose
	private String  dob;
	@SerializedName("citizenship")
	@Expose
	private String  citizenship;
	@SerializedName("firstNames")
	@Expose
	private String  firstNames;
	@SerializedName("deceasedStatus")
	@Expose
	private String  deceasedStatus;
	@SerializedName("idNumber")
	@Expose
	private String  idNumber;
	@SerializedName("deceasedDate")
	@Expose
	private String  deceasedDate;
	@SerializedName("age")
	@Expose
	private Integer age;

	public Integer getTraceId() {
		return traceId;
	}

	public void setTraceId(Integer traceId) {
		this.traceId = traceId;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getDeceasedStatus() {
		return deceasedStatus;
	}

	public void setDeceasedStatus(String deceasedStatus) {
		this.deceasedStatus = deceasedStatus;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getDeceasedDate() {
		return deceasedDate;
	}

	public void setDeceasedDate(String deceasedDate) {
		this.deceasedDate = deceasedDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
