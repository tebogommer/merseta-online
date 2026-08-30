package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;

// TODO: Auto-generated Javadoc
/**
 * The Class QmrScriptOneBean.
 */
public class QmrScriptOneBean {

	/*
	 * Generic scripts for QMR Reporting Entity uses:
	 * haj.com.entity.QmrLearnershipData 
	 * haj.com.entity.QmrInternshipData
	 * haj.com.entity.QmrSkillsProgrammeData
	 * haj.com.entity.QmrCandidacyProgrammeData 
	 * haj.com.entity.QmrRPLData
	 * haj.com.entity.QmrLecturerDevelopmentData
	 * haj.com.entity.QmrIndividualUnitStandardData
	 */
	private String reportingPeriod;
	private BigInteger companyLearnerFlatID;
	private String namesOfTheLearner;
	private String surnameOfTheLearner;
	private String idNumberOfTheLearner;
	private String typeOfLearningProgramme;
	private Date dateTheLearnerEnteredTheLearningProgramme;
	private Date actualStartDateOfTheLearningProgramme;
	private Date dateTheLearnerCompletedTheLearningProgramme;
	private Date dateRecivedCertificateLearningProgramme;
	private String ofoCode;
	private String nqfLevel;
	private String qualificationAsPerOfoCodeDesctiptionOfTheQualification;
	private String nameOfTheEmployer;
	private String employerRegistartionSdlNumber;
	private String employerContactDetails;
	private String nameOfTheTraingProvider;
	private String trainingProviderAccrediciationNumber;
	private String trainingProviderContactDetails;
	private String isTrainingProviderPrivatePublic;
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
	private Integer ageOfLearner;
	
	
	
	public Integer getAgeOfLearner() {
		return ageOfLearner;
	}

	public void setAgeOfLearner(Integer ageOfLearner) {
		this.ageOfLearner = ageOfLearner;
	}

	// getters and setters
	public String getReportingPeriod() {
		return reportingPeriod;
	}

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

	public String getTypeOfLearningProgramme() {
		return typeOfLearningProgramme;
	}

	public void setTypeOfLearningProgramme(String typeOfLearningProgramme) {
		this.typeOfLearningProgramme = typeOfLearningProgramme;
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

	public String getNameOfTheTraingProvider() {
		return nameOfTheTraingProvider;
	}

	public void setNameOfTheTraingProvider(String nameOfTheTraingProvider) {
		this.nameOfTheTraingProvider = nameOfTheTraingProvider;
	}

	public String getTrainingProviderAccrediciationNumber() {
		return trainingProviderAccrediciationNumber;
	}

	public void setTrainingProviderAccrediciationNumber(String trainingProviderAccrediciationNumber) {
		this.trainingProviderAccrediciationNumber = trainingProviderAccrediciationNumber;
	}

	public String getTrainingProviderContactDetails() {
		return trainingProviderContactDetails;
	}

	public void setTrainingProviderContactDetails(String trainingProviderContactDetails) {
		this.trainingProviderContactDetails = trainingProviderContactDetails;
	}

	public String getIsTrainingProviderPrivatePublic() {
		return isTrainingProviderPrivatePublic;
	}

	public void setIsTrainingProviderPrivatePublic(String isTrainingProviderPrivatePublic) {
		this.isTrainingProviderPrivatePublic = isTrainingProviderPrivatePublic;
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

	public Date getDateTheLearnerEnteredTheLearningProgramme() {
		return dateTheLearnerEnteredTheLearningProgramme;
	}

	public void setDateTheLearnerEnteredTheLearningProgramme(Date dateTheLearnerEnteredTheLearningProgramme) {
		this.dateTheLearnerEnteredTheLearningProgramme = dateTheLearnerEnteredTheLearningProgramme;
	}

	public Date getActualStartDateOfTheLearningProgramme() {
		return actualStartDateOfTheLearningProgramme;
	}

	public void setActualStartDateOfTheLearningProgramme(Date actualStartDateOfTheLearningProgramme) {
		this.actualStartDateOfTheLearningProgramme = actualStartDateOfTheLearningProgramme;
	}

	public Date getDateTheLearnerCompletedTheLearningProgramme() {
		return dateTheLearnerCompletedTheLearningProgramme;
	}

	public void setDateTheLearnerCompletedTheLearningProgramme(Date dateTheLearnerCompletedTheLearningProgramme) {
		this.dateTheLearnerCompletedTheLearningProgramme = dateTheLearnerCompletedTheLearningProgramme;
	}

	public Date getDateRecivedCertificateLearningProgramme() {
		return dateRecivedCertificateLearningProgramme;
	}

	public void setDateRecivedCertificateLearningProgramme(Date dateRecivedCertificateLearningProgramme) {
		this.dateRecivedCertificateLearningProgramme = dateRecivedCertificateLearningProgramme;
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