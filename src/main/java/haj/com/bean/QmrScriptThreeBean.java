
package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

/**
 * The Class QmrScriptThreeBean.
 */
public class QmrScriptThreeBean {

	/*
	 * Generic scripts for QMR Reporting Entity uses:
	 * haj.com.entity.QmrArtisanData
	 * 
	 * Add to main script: 
	 * age
	 * disability
	 * nonRsaCitizen
	 */
	private String reportingPeriod;
	private BigInteger companyLearnerFlatID;
	private String ofoCode;
	private String artisanTradeDiscription;
	private String doesTheProgrammeAdressSspNeedsSipSkills;
	private String learnerName;
	private String learnerOtherNames;
	private String learnerSurname;
	private String learnerPermenantlyEmployed;
	private String learnerIdNumber;
	private String learnerAlternateIdPassportNumber;
	private Date dateOfBirthNonSaCitizens;
	private String saNonSaCitizens;
	private String race;
	private String gender;
	private String pwd;
	private String learnerMinicipality;
	private String setaIdlelaOrPublicFetAcronym;
	private String leadSkillsDevelopmentProviderKnowladgeComponents;
	private String accreditationNumberKnowlageComponent;
	private String leadSkillsDevelopmentProviderPracticalComponents;
	private String accreditationNumberPracticalComponent;
	private String leadEmployer;
	private String leadEmployerApprovalNumber;
	private Date dateLearnerAgreementRegistration;
	private Date dateLearnerAgreementCancelation;
	private String reasonForCancelation;
	private Date dateLearnerAgreementCompletion;
	private String tradeTest12And3;
	private Date dateTradeTest12And3;
	private String reasonForNotYetCompetent;
	private String tradeTestCenter;
	private String tradeTestCenterAccreditationNumber;
	private String tradeTestAssessorName;
	private String tradeTestAssessorSurname;
	private String tradeTestAssessorIdNumber;
	private String tradeTestModeratorName;
	private String tradeTestModeratorSurname;
	private String tradeTestModeratorIdNumber;
	private Date dateLearnerDelacredCompetent;
	private Date dateLearnerCertification;
	private String learnerCertificationNumber;
	
	private BigInteger age;
	private String disability;
	private String nonRsaCitizen;

	// getters and setters
	public String getReportingPeriod() {
		return reportingPeriod;
	}

	public void setReportingPeriod(String reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getDoesTheProgrammeAdressSspNeedsSipSkills() {
		return doesTheProgrammeAdressSspNeedsSipSkills;
	}

	public void setDoesTheProgrammeAdressSspNeedsSipSkills(String doesTheProgrammeAdressSspNeedsSipSkills) {
		this.doesTheProgrammeAdressSspNeedsSipSkills = doesTheProgrammeAdressSspNeedsSipSkills;
	}

	public String getLearnerName() {
		return learnerName;
	}

	public void setLearnerName(String learnerName) {
		this.learnerName = learnerName;
	}

	public String getLearnerOtherNames() {
		return learnerOtherNames;
	}

	public void setLearnerOtherNames(String learnerOtherNames) {
		this.learnerOtherNames = learnerOtherNames;
	}

	public String getLearnerSurname() {
		return learnerSurname;
	}

	public void setLearnerSurname(String learnerSurname) {
		this.learnerSurname = learnerSurname;
	}
	
	public String getLearnerIdNumber() {
		return learnerIdNumber;
	}

	public void setLearnerIdNumber(String learnerIdNumber) {
		this.learnerIdNumber = learnerIdNumber;
	}

	public String getLearnerAlternateIdPassportNumber() {
		return learnerAlternateIdPassportNumber;
	}

	public void setLearnerAlternateIdPassportNumber(String learnerAlternateIdPassportNumber) {
		this.learnerAlternateIdPassportNumber = learnerAlternateIdPassportNumber;
	}

	public Date getDateOfBirthNonSaCitizens() {
		return dateOfBirthNonSaCitizens;
	}

	public void setDateOfBirthNonSaCitizens(Date dateOfBirthNonSaCitizens) {
		this.dateOfBirthNonSaCitizens = dateOfBirthNonSaCitizens;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLearnerMinicipality() {
		return learnerMinicipality;
	}

	public void setLearnerMinicipality(String learnerMinicipality) {
		this.learnerMinicipality = learnerMinicipality;
	}

	public String getSetaIdlelaOrPublicFetAcronym() {
		return setaIdlelaOrPublicFetAcronym;
	}

	public void setSetaIdlelaOrPublicFetAcronym(String setaIdlelaOrPublicFetAcronym) {
		this.setaIdlelaOrPublicFetAcronym = setaIdlelaOrPublicFetAcronym;
	}

	public String getLeadSkillsDevelopmentProviderKnowladgeComponents() {
		return leadSkillsDevelopmentProviderKnowladgeComponents;
	}

	public void setLeadSkillsDevelopmentProviderKnowladgeComponents(
			String leadSkillsDevelopmentProviderKnowladgeComponents) {
		this.leadSkillsDevelopmentProviderKnowladgeComponents = leadSkillsDevelopmentProviderKnowladgeComponents;
	}

	public String getLeadEmployer() {
		return leadEmployer;
	}

	public void setLeadEmployer(String leadEmployer) {
		this.leadEmployer = leadEmployer;
	}

	public String getLeadEmployerApprovalNumber() {
		return leadEmployerApprovalNumber;
	}

	public void setLeadEmployerApprovalNumber(String leadEmployerApprovalNumber) {
		this.leadEmployerApprovalNumber = leadEmployerApprovalNumber;
	}

	public Date getDateLearnerAgreementRegistration() {
		return dateLearnerAgreementRegistration;
	}

	public void setDateLearnerAgreementRegistration(Date dateLearnerAgreementRegistration) {
		this.dateLearnerAgreementRegistration = dateLearnerAgreementRegistration;
	}

	public Date getDateLearnerAgreementCancelation() {
		return dateLearnerAgreementCancelation;
	}

	public void setDateLearnerAgreementCancelation(Date dateLearnerAgreementCancelation) {
		this.dateLearnerAgreementCancelation = dateLearnerAgreementCancelation;
	}

	public String getReasonForCancelation() {
		return reasonForCancelation;
	}

	public void setReasonForCancelation(String reasonForCancelation) {
		this.reasonForCancelation = reasonForCancelation;
	}

	public Date getDateLearnerAgreementCompletion() {
		return dateLearnerAgreementCompletion;
	}

	public void setDateLearnerAgreementCompletion(Date dateLearnerAgreementCompletion) {
		this.dateLearnerAgreementCompletion = dateLearnerAgreementCompletion;
	}

	public Date getDateTradeTest12And3() {
		return dateTradeTest12And3;
	}

	public void setDateTradeTest12And3(Date dateTradeTest12And3) {
		this.dateTradeTest12And3 = dateTradeTest12And3;
	}
	
	public String getTradeTestCenter() {
		return tradeTestCenter;
	}

	public void setTradeTestCenter(String tradeTestCenter) {
		this.tradeTestCenter = tradeTestCenter;
	}

	public String getTradeTestCenterAccreditationNumber() {
		return tradeTestCenterAccreditationNumber;
	}

	public void setTradeTestCenterAccreditationNumber(String tradeTestCenterAccreditationNumber) {
		this.tradeTestCenterAccreditationNumber = tradeTestCenterAccreditationNumber;
	}

	public String getTradeTestAssessorName() {
		return tradeTestAssessorName;
	}

	public void setTradeTestAssessorName(String tradeTestAssessorName) {
		this.tradeTestAssessorName = tradeTestAssessorName;
	}

	public String getTradeTestAssessorSurname() {
		return tradeTestAssessorSurname;
	}

	public void setTradeTestAssessorSurname(String tradeTestAssessorSurname) {
		this.tradeTestAssessorSurname = tradeTestAssessorSurname;
	}

	public String getTradeTestAssessorIdNumber() {
		return tradeTestAssessorIdNumber;
	}

	public void setTradeTestAssessorIdNumber(String tradeTestAssessorIdNumber) {
		this.tradeTestAssessorIdNumber = tradeTestAssessorIdNumber;
	}

	public String getTradeTestModeratorName() {
		return tradeTestModeratorName;
	}

	public void setTradeTestModeratorName(String tradeTestModeratorName) {
		this.tradeTestModeratorName = tradeTestModeratorName;
	}

	public String getTradeTestModeratorSurname() {
		return tradeTestModeratorSurname;
	}

	public void setTradeTestModeratorSurname(String tradeTestModeratorSurname) {
		this.tradeTestModeratorSurname = tradeTestModeratorSurname;
	}

	public String getTradeTestModeratorIdNumber() {
		return tradeTestModeratorIdNumber;
	}

	public void setTradeTestModeratorIdNumber(String tradeTestModeratorIdNumber) {
		this.tradeTestModeratorIdNumber = tradeTestModeratorIdNumber;
	}

	public Date getDateLearnerDelacredCompetent() {
		return dateLearnerDelacredCompetent;
	}

	public void setDateLearnerDelacredCompetent(Date dateLearnerDelacredCompetent) {
		this.dateLearnerDelacredCompetent = dateLearnerDelacredCompetent;
	}

	public Date getDateLearnerCertification() {
		return dateLearnerCertification;
	}

	public void setDateLearnerCertification(Date dateLearnerCertification) {
		this.dateLearnerCertification = dateLearnerCertification;
	}

	public String getLearnerCertificationNumber() {
		return learnerCertificationNumber;
	}

	public void setLearnerCertificationNumber(String learnerCertificationNumber) {
		this.learnerCertificationNumber = learnerCertificationNumber;
	}

	public String getSaNonSaCitizens() {
		return saNonSaCitizens;
	}

	public void setSaNonSaCitizens(String saNonSaCitizens) {
		this.saNonSaCitizens = saNonSaCitizens;
	}

	public String getAccreditationNumberKnowlageComponent() {
		return accreditationNumberKnowlageComponent;
	}

	public void setAccreditationNumberKnowlageComponent(String accreditationNumberKnowlageComponent) {
		this.accreditationNumberKnowlageComponent = accreditationNumberKnowlageComponent;
	}

	public String getAccreditationNumberPracticalComponent() {
		return accreditationNumberPracticalComponent;
	}

	public void setAccreditationNumberPracticalComponent(String accreditationNumberPracticalComponent) {
		this.accreditationNumberPracticalComponent = accreditationNumberPracticalComponent;
	}

	public String getLeadSkillsDevelopmentProviderPracticalComponents() {
		return leadSkillsDevelopmentProviderPracticalComponents;
	}

	public void setLeadSkillsDevelopmentProviderPracticalComponents(
			String leadSkillsDevelopmentProviderPracticalComponents) {
		this.leadSkillsDevelopmentProviderPracticalComponents = leadSkillsDevelopmentProviderPracticalComponents;
	}

	public BigInteger getCompanyLearnerFlatID() {
		return companyLearnerFlatID;
	}

	public void setCompanyLearnerFlatID(BigInteger companyLearnerFlatID) {
		this.companyLearnerFlatID = companyLearnerFlatID;
	}

	public String getLearnerPermenantlyEmployed() {
		return learnerPermenantlyEmployed;
	}

	public void setLearnerPermenantlyEmployed(String learnerPermenantlyEmployed) {
		this.learnerPermenantlyEmployed = learnerPermenantlyEmployed;
	}

	public void setTradeTest12And3(String tradeTest12And3) {
		this.tradeTest12And3 = tradeTest12And3;
	}

	public String getTradeTest12And3() {
		return tradeTest12And3;
	}

	public String getReasonForNotYetCompetent() {
		return reasonForNotYetCompetent;
	}

	public void setReasonForNotYetCompetent(String reasonForNotYetCompetent) {
		this.reasonForNotYetCompetent = reasonForNotYetCompetent;
	}

	public String getArtisanTradeDiscription() {
		return artisanTradeDiscription;
	}

	public void setArtisanTradeDiscription(String artisanTradeDiscription) {
		this.artisanTradeDiscription = artisanTradeDiscription;
	}

	public BigInteger getAge() {
		return age;
	}

	public void setAge(BigInteger age) {
		this.age = age;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getNonRsaCitizen() {
		return nonRsaCitizen;
	}

	public void setNonRsaCitizen(String nonRsaCitizen) {
		this.nonRsaCitizen = nonRsaCitizen;
	}

	// getters and setters

}