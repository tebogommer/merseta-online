
package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

/**
 * The Class QmrScriptFourBean.
 */
public class QmrScriptFourBean {

	/*
	 * Generic scripts for QMR Reporting Entity uses: 
	 * haj.com.entity.QmrTVETData
	 * haj.com.entity.QmrUniversitryStudentData
	 */
	private String reportingPeriod;
	private BigInteger companyLearnerFlatID;
	private String namesOfTheLearner;
	private String surnameOfTheLearner;
	private String idNumberOfTheLearner;
	private String ofoCode;
	private String nqfLevel;
	private String qualificationAsPerOfoCodeDesctiptionOfTheQualification;
	
	/*
	 * This field is only recorder for TVET gradudate placement entered, i.e.
	 * not TVET graduate palcement completed or any of the university graduate
	 * placement
	 */
	private String doesTheProgrammeAddressSspNeedsSipSkillsNeeds;
	
	/* TVET college or name of university */
	private String nameOfInstetution;
	private String nameOfTheEmployer;
	private String employerRegistartionSdlNumber;
	private String employerContactDetails;
	private Date startDate;
	private String durationOfPlacementReflectsStartAndEndDates;
	private Date completionDate;
	private String learnerProvince;
	private String learnerLocalDistrictMunciplaity;
	private String specifyLearnerResidentialArea;
	private String isTheLearnerResidentialAreaUrbanRural;
	private String isTheProgrammeSetaIndustryFunded;
	private Double amountSpentPerLearner;
	private String race;
	private String gender;
	private BigInteger age;
	private String disability;
	private String youth;
	private String nonRsaCitizen;

	// getters and setters
	public String getReportingPeriod() {
		return reportingPeriod;
	}

	// getters and setters
	public void setReportingPeriod(String reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}

	public String getNamesOfTheLearner() {
		return namesOfTheLearner;
	}

	public void setNamesOfTheLearner(String namesOfTheLearner) {
		this.namesOfTheLearner = namesOfTheLearner;
	}

	public String getSurnameOfTheLearner() {
		return surnameOfTheLearner;
	}

	public void setSurnameOfTheLearner(String surnameOfTheLearner) {
		this.surnameOfTheLearner = surnameOfTheLearner;
	}

	public String getIdNumberOfTheLearner() {
		return idNumberOfTheLearner;
	}

	public void setIdNumberOfTheLearner(String idNumberOfTheLearner) {
		this.idNumberOfTheLearner = idNumberOfTheLearner;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getNqfLevel() {
		return nqfLevel;
	}

	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	public String getQualificationAsPerOfoCodeDesctiptionOfTheQualification() {
		return qualificationAsPerOfoCodeDesctiptionOfTheQualification;
	}

	public void setQualificationAsPerOfoCodeDesctiptionOfTheQualification(
			String qualificationAsPerOfoCodeDesctiptionOfTheQualification) {
		this.qualificationAsPerOfoCodeDesctiptionOfTheQualification = qualificationAsPerOfoCodeDesctiptionOfTheQualification;
	}

	public String getDoesTheProgrammeAddressSspNeedsSipSkillsNeeds() {
		return doesTheProgrammeAddressSspNeedsSipSkillsNeeds;
	}

	public void setDoesTheProgrammeAddressSspNeedsSipSkillsNeeds(String doesTheProgrammeAddressSspNeedsSipSkillsNeeds) {
		this.doesTheProgrammeAddressSspNeedsSipSkillsNeeds = doesTheProgrammeAddressSspNeedsSipSkillsNeeds;
	}

	public String getNameOfInstetution() {
		return nameOfInstetution;
	}

	public void setNameOfInstetution(String nameOfInstetution) {
		this.nameOfInstetution = nameOfInstetution;
	}

	public String getNameOfTheEmployer() {
		return nameOfTheEmployer;
	}

	public void setNameOfTheEmployer(String nameOfTheEmployer) {
		this.nameOfTheEmployer = nameOfTheEmployer;
	}

	public String getEmployerRegistartionSdlNumber() {
		return employerRegistartionSdlNumber;
	}

	public void setEmployerRegistartionSdlNumber(String employerRegistartionSdlNumber) {
		this.employerRegistartionSdlNumber = employerRegistartionSdlNumber;
	}

	public String getEmployerContactDetails() {
		return employerContactDetails;
	}

	public void setEmployerContactDetails(String employerContactDetails) {
		this.employerContactDetails = employerContactDetails;
	}

	public String getDurationOfPlacementReflectsStartAndEndDates() {
		return durationOfPlacementReflectsStartAndEndDates;
	}

	public void setDurationOfPlacementReflectsStartAndEndDates(String durationOfPlacementReflectsStartAndEndDates) {
		this.durationOfPlacementReflectsStartAndEndDates = durationOfPlacementReflectsStartAndEndDates;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getLearnerProvince() {
		return learnerProvince;
	}

	public void setLearnerProvince(String learnerProvince) {
		this.learnerProvince = learnerProvince;
	}

	public String getLearnerLocalDistrictMunciplaity() {
		return learnerLocalDistrictMunciplaity;
	}

	public void setLearnerLocalDistrictMunciplaity(String learnerLocalDistrictMunciplaity) {
		this.learnerLocalDistrictMunciplaity = learnerLocalDistrictMunciplaity;
	}

	public String getSpecifyLearnerResidentialArea() {
		return specifyLearnerResidentialArea;
	}

	public void setSpecifyLearnerResidentialArea(String specifyLearnerResidentialArea) {
		this.specifyLearnerResidentialArea = specifyLearnerResidentialArea;
	}

	public String getIsTheLearnerResidentialAreaUrbanRural() {
		return isTheLearnerResidentialAreaUrbanRural;
	}

	public void setIsTheLearnerResidentialAreaUrbanRural(String isTheLearnerResidentialAreaUrbanRural) {
		this.isTheLearnerResidentialAreaUrbanRural = isTheLearnerResidentialAreaUrbanRural;
	}

	public String getIsTheProgrammeSetaIndustryFunded() {
		return isTheProgrammeSetaIndustryFunded;
	}

	public void setIsTheProgrammeSetaIndustryFunded(String isTheProgrammeSetaIndustryFunded) {
		this.isTheProgrammeSetaIndustryFunded = isTheProgrammeSetaIndustryFunded;
	}

	public Double getAmountSpentPerLearner() {
		return amountSpentPerLearner;
	}

	public void setAmountSpentPerLearner(Double amountSpentPerLearner) {
		this.amountSpentPerLearner = amountSpentPerLearner;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getYouth() {
		return youth;
	}

	public void setYouth(String youth) {
		this.youth = youth;
	}

	public String getNonRsaCitizen() {
		return nonRsaCitizen;
	}

	public void setNonRsaCitizen(String nonRsaCitizen) {
		this.nonRsaCitizen = nonRsaCitizen;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigInteger getAge() {
		return age;
	}

	public void setAge(BigInteger age) {
		this.age = age;
	}

	public BigInteger getCompanyLearnerFlatID() {
		return companyLearnerFlatID;
	}

	public void setCompanyLearnerFlatID(BigInteger companyLearnerFlatID) {
		this.companyLearnerFlatID = companyLearnerFlatID;
	}

}