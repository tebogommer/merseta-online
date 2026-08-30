package haj.com.dao;

import java.util.Date;
import java.util.List;

import haj.com.bean.CounterBean;
import haj.com.bean.QmrScriptFiveBean;
import haj.com.bean.QmrScriptFourBean;
import haj.com.bean.QmrScriptOneBean;
import haj.com.bean.QmrScriptThreeBean;
import haj.com.bean.QmrScriptTwoBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QmrReportingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}
	
	public List<QmrScriptOneBean> qmrScriptOneGeneration(String quarterRef, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select   " + 
				"	'"+quarterRef+"' as reportingPeriod  " + 
				"   , cl.id as companyLearnerFlatID  " +
				"	, SUBSTRING(CONCAT_WS(' ', UPPER(learner.first_name), UPPER(learner.middle_name)), 1, 95) as namesOfTheLearner  " + 
				"	, SUBSTRING(UPPER(learner.last_name), 1, 45) as surnameOfTheLearner  " + 
				"	, case    " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then SUBSTRING(learner.rsa_id_number, 1, 15)   " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then SUBSTRING(learner.passport_number, 1, 20)   " + 
				"		ELSE SUBSTRING('', 1, 15)    " + 
				"		end as idNumberOfTheLearner  " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then 'Qualification'  " + 
				"		when it.qualification_type_selection = '1' then 'Skills Program'  " + 
				"		when it.qualification_type_selection = '2' then 'Skills Set'  " + 
				"		when it.qualification_type_selection = '3' then 'Non-Credit Bearing Intervation Title'  " + 
				"		when it.qualification_type_selection = '4' then 'Unit Standard'  " + 
				"		when it.qualification_type_selection = '5' then 'Learnership'  " + 
				"		Else 'Unable to locate type'  " + 
				"		end as typeOfLearningProgramme  " + 
				"	, cl.approval_date as dateTheLearnerEnteredTheLearningProgramme  " + 
				"	, cl.commencement_date as actualStartDateOfTheLearningProgramme  " + 
				"	, cl.completion_date as dateTheLearnerCompletedTheLearningProgramme  " + 
				"	, cl.certificate_date as dateRecivedCertificateLearningProgramme  " + 
				"	, case    " + 
				"		when ofoParent.id is not null then ofoParent.ofo_code   " + 
				"		when ofo.id is not null then ofo.ofo_code   " + 
				"		when ofoParentlearner.id is not null then ofoParentlearner.ofo_code   " + 
				"		when ofolearner.id is not null then ofolearner.ofo_code   " + 
				"		Else SUBSTRING('', 1, 2)   " + 
				"		end as ofoCode   " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then nqfMainQual.description  " + 
				"		when it.qualification_type_selection = '1' then spNqf.description  " + 
				"		when it.qualification_type_selection = '2' then ssNqf.description  " + 
				"		when it.qualification_type_selection = '3' then nqf.description  " + 
				"		when it.qualification_type_selection = '4' then nqf.description  " + 
				"		when it.qualification_type_selection = '5' then lshipNqf.description  " + 
				"		Else nqf.description  " + 
				"		end as nqfLevel  " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle  " + 
				"		when it.qualification_type_selection = '1' then sp.description  " + 
				"		when it.qualification_type_selection = '2' then ss.description  " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " +
				"		when it.qualification_type_selection = '4' then us.unitstdtitle  " + 
				"		when it.qualification_type_selection = '5' then lship.description  " + 
				"		Else 'Unable to locate information'  " + 
				"		end as qualificationAsPerOfoCodeDesctiptionOfTheQualification  " + 
				"	, employer.company_name as nameOfTheEmployer  " + 
				"	, case   " + 
				"		when employer.levy_number is not null then employer.levy_number  " + 
				"		when employer.company_registration_number is not null then employer.company_registration_number  " + 
				"		else ''  " + 
				"		end as employerRegistartionSdlNumber   " + 
				"	, CONCAT('Phone: ', employer.tel_number , ' Fax: ' , employer.fax_number , ' Email:',  employer.email) as employerContactDetails  " + 
				"	, provider.company_name as nameOfTheTraingProvider  " + 
				"	, case        " + 
				"		when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)        " + 
				"		when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)        " + 
				"		else ''        " + 
				"		end as trainingProviderAccrediciationNumber  " + 
				"	, CONCAT('Phone: ', provider.tel_number , ' Fax: ' , provider.fax_number , ' Email:', provider.email) as trainingProviderContactDetails  " + 
				"	, case   " + 
				"		when providerOT.public_private = '0' then 'Public'  " + 
				"		when providerOT.public_private = '1' then 'Private'  " + 
				"		Else ''  " + 
				"		end as isTrainingProviderPrivatePublic  " + 
				"	, learnerResidentialProvince.province_desc as learnerProvince  " + 
				"	, learnerResidentialMunicipality.municipality_desc as learnerLocalDistrictMunciplaity  " + 
				"	, learnerResidentialAreaCode.description as specifyLearnerResidentialArea  " + 
				"	, case  " + 
				"		when learner.urban_rural_enum = '0' then 'Urban'  " + 
				"		when learner.urban_rural_enum = '1' then 'Rural'  " + 
				"		else 'Unknown'  " + 
				"		end as isTheLearnerResidentialAreaUrbanRural  " + 
				"	, case  " + 
				"		when learnerFunding.seta_industry_funded = '0' then 'SETA FUNDED'  " + 
				"		when learnerFunding.seta_industry_funded = '1' then 'INDUSTRY FUNDED'  " + 
				"		end as isTheProgrammeSetaIndustryFunded   " + 
				"	, case   " + 
				"		when learnerFunding.seta_industry_funded = '0' and it.basic_grant_amount is not null then it.basic_grant_amount " + 
				"		else NULL " + 
				"		end as amountSpentPerLearner  " + 
				"	, learnerEquity.description as race  " + 
				"	, learnerGender.gender_name as gender  " + 
				"	, YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age  " + 
				"	, case  " + 
				"   	 when counterUserDisability.counter is null then 'No'  " +
				"        when counterUserDisability.counter < '1' then 'No'  " + 
				"        when counterUserDisability.counter = '1' then 'Yes'  " + 
				"        else 'Yes'  " + 
				"        end as disability  " + 
				"	, case   " + 
				"		when (YEAR(CURDATE()) - YEAR(learner.date_of_birth)) > "+HAJConstants.QMR_AGE_YOUTH+" then 'No'  " + 
				"		else 'Yes'  " + 
				"		end as youth  " + 
				"	, case   " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'  " + 
				"		else 'Non-Rsa Citizen'  " + 
				"		end as nonRsaCitizen  " + 
				"		  " + 
				"from company_learners cl   " + 
				"  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id  " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id  " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id  " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id    " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id    " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterUserDisability on counterUserDisability.userId = learner.id  " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id  " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id  " + 
				"  " + 
				"left join skills_set ss on cl.skills_set_id = ss.id  " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id  " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id  " + 
				"  " + 
				"left join skills_program sp on cl.skills_program_id = sp.id  " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id  " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id  " + 
				"  " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id  " + 
				"  " + 
				"left join learnership lship on cl.learnership_id = lship.id  " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id  " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id  " + 
				"  " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id  " + 
				"  " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id  " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id   " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id  " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id    " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id   " + 
				"  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id  " + 
				"  " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id  " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id  " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id  " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id   " + 
				"  " + 
				"left join address learnerPostalAddress on learner.postal_address_id = learnerPostalAddress.id  " + 
				"left join statssa_area_code learnerPostalAreaCode on learnerPostalAddress.stats_saarea_code_id = learnerPostalAreaCode.id  " + 
				"left join municipality learnerPostalMunicipality on learnerPostalAddress.municipality_id = learnerPostalMunicipality.id  " + 
				"left join province learnerPostalProvince on learnerPostalMunicipality.provinces_idprovinces = learnerPostalProvince.id   " + 
				"  " + 
				"left join address employerResidentialAddress on employer.residential_address_id = employerResidentialAddress.id  " + 
				"left join statssa_area_code employerResidentialAreaCode on employerResidentialAddress.stats_saarea_code_id = employerResidentialAreaCode.id  " + 
				"left join municipality employerResidentialMunicipality on employerResidentialAddress.municipality_id = employerResidentialMunicipality.id  " + 
				"left join province employerResidentialProvince on employerResidentialMunicipality.provinces_idprovinces = employerResidentialProvince.id   " + 
				"  " + 
				"left join address employerPostalAddress on employer.postal_address_id = employerPostalAddress.id  " + 
				"left join statssa_area_code employerPostalAreaCode on employerPostalAddress.stats_saarea_code_id = employerPostalAreaCode.id  " + 
				"left join municipality employerPostalMunicipality on employerPostalAddress.municipality_id = employerPostalMunicipality.id  " + 
				"left join province employerPostalProvince on employerPostalMunicipality.provinces_idprovinces = employerPostalProvince.id   " + 
				"  " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id  " + 
				"  " + 
				"left join address providerResidentialAddress on provider.residential_address_id = providerResidentialAddress.id  " + 
				"left join statssa_area_code providerResidentialAreaCode on providerResidentialAddress.stats_saarea_code_id = providerResidentialAreaCode.id  " + 
				"left join municipality providerResidentialMunicipality on providerResidentialAddress.municipality_id = providerResidentialMunicipality.id  " + 
				"left join province providerResidentialProvince on providerResidentialMunicipality.provinces_idprovinces = providerResidentialProvince.id   " + 
				"  " + 
				"left join address providerPostalAddress on provider.postal_address_id = providerPostalAddress.id  " + 
				"left join statssa_area_code providerPostalAreaCode on providerPostalAddress.stats_saarea_code_id = providerPostalAreaCode.id  " + 
				"left join municipality providerPostalMunicipality on providerPostalAddress.municipality_id = providerPostalMunicipality.id  " + 
				"left join province providerPostalProvince on providerPostalMunicipality.provinces_idprovinces = providerPostalProvince.id  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " +
				"  " + 
				"where   "; 
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		System.out.println("sql:"+sql);
		return (List<QmrScriptOneBean>) super.nativeSelectSqlList(sql, QmrScriptOneBean.class);
	}
	
	public List<QmrScriptTwoBean> qmrScriptTwoGeneration(String quarterRef, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select     " + 
				"	'"+quarterRef+"' as reportingPeriod    " + 
				"   , cl.id as companyLearnerFlatID    " + 
				"   , case " + 
				"		when cl.new_bursary = true then 'New' " + 
				"		when cl.new_bursary = false then 'Continuing' " +
				"		ELSE 'New' " +
				"		end as newContinuingBursary " +
				"	, SUBSTRING(CONCAT_WS(' ', UPPER(learner.first_name), UPPER(learner.middle_name)), 1, 95) as namesOfTheLearner    " + 
				"	, SUBSTRING(UPPER(learner.last_name), 1, 45) as surnameOfTheLearner    " + 
				"	, case      " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then SUBSTRING(learner.rsa_id_number, 1, 15)     " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then SUBSTRING(learner.passport_number, 1, 20)     " + 
				"		ELSE SUBSTRING('', 1, 15)      " + 
				"		end as idNumberOfTheLearner    " + 
				"	, case    " + 
				"		when it.qualification_type_selection = '0' then 'Qualification'    " + 
				"		when it.qualification_type_selection = '1' then 'Skills Program'    " + 
				"		when it.qualification_type_selection = '2' then 'Skills Set'    " + 
				"		when it.qualification_type_selection = '3' then 'Non-Credit Bearing Intervation Title'    " + 
				"		when it.qualification_type_selection = '4' then 'Unit Standard'    " + 
				"		when it.qualification_type_selection = '5' then 'Learnership'    " + 
				"		Else 'Unable to locate type'    " + 
				"		end as typeOfLearningProgramme    " + 
				"	, cl.approval_date as dateTheLearnerEnteredTheLearningProgramme    " + 
				"	, cl.commencement_date as actualStartDateOfTheLearningProgramme    " + 
				"	, cl.completion_date as dateTheLearnerCompletedTheLearningProgramme    " + 
				"	, cl.certificate_date as dateRecivedCertificateLearningProgramme    " + 
				"	, case    " + 
				"		when it.qualification_type_selection = '0' then nqfMainQual.description    " + 
				"		when it.qualification_type_selection = '1' then spNqf.description    " + 
				"		when it.qualification_type_selection = '2' then ssNqf.description    " + 
				"		when it.qualification_type_selection = '3' then nqf.description    " + 
				"		when it.qualification_type_selection = '4' then nqf.description    " + 
				"		when it.qualification_type_selection = '5' then lshipNqf.description    " + 
				"		Else nqf.description    " + 
				"		end as nqfLevel    " + 
				"	, case    " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle    " + 
				"		when it.qualification_type_selection = '1' then sp.description    " + 
				"		when it.qualification_type_selection = '2' then ss.description    " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " +
				"		when it.qualification_type_selection = '4' then us.unitstdtitle    " + 
				"		when it.qualification_type_selection = '5' then lship.description    " + 
				"		Else 'Unable to locate information'    " + 
				"		end as descriptionOfTheQualification    " + 
				"	, employer.company_name as nameOfTheEmployer    " + 
				"	, case     " + 
				"		when employer.levy_number is not null then employer.levy_number    " + 
				"		when employer.company_registration_number is not null then employer.company_registration_number    " + 
				"		else ''    " + 
				"		end as employerRegistartionSdlNumber     " + 
				"	, CONCAT('Phone: ', employer.tel_number , ' Fax: ' , employer.fax_number , ' Email:',  employer.email) as employerContactDetails    " + 
				"	, provider.company_name as nameOfTheTraingProvider    " + 
				"	, case          " + 
				"		when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)          " + 
				"		when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)          " + 
				"		else ''          " + 
				"		end as trainingProviderAccrediciationNumber    " + 
				"	, CONCAT('Phone: ', provider.tel_number , ' Fax: ' , provider.fax_number , ' Email:', provider.email) as trainingProviderContactDetails    " + 
				"	, case     " + 
				"		when providerOT.public_private = '0' then 'Public'    " + 
				"		when providerOT.public_private = '1' then 'Private'    " + 
				"		Else ''    " + 
				"		end as isTrainingProviderPrivatePublic    " + 
				"	, learnerResidentialProvince.province_desc as learnerProvince    " + 
				"	, learnerResidentialMunicipality.municipality_desc as learnerLocalDistrictMunciplaity    " + 
				"	, learnerResidentialAreaCode.description as specifyLearnerResidentialArea    " + 
				"	, case    " + 
				"		when learner.urban_rural_enum = '0' then 'Urban'    " + 
				"		when learner.urban_rural_enum = '1' then 'Rural'    " + 
				"		else 'Unknown'    " + 
				"		end as isTheLearnerResidentialAreaUrbanRural    " + 
				"	, case    " + 
				"		when learnerFunding.seta_industry_funded = '0' then 'SETA FUNDED'    " + 
				"		when learnerFunding.seta_industry_funded = '1' then 'INDUSTRY FUNDED'    " + 
				"		end as isTheProgrammeSetaIndustryFunded     " + 
				"	, case    " + 
				"		when learnerFunding.seta_industry_funded = '0' and it.basic_grant_amount is not null then it.basic_grant_amount  " + 
				"		else NULL  " + 
				"		end as amountSpentPerLearner    " + 
				"	, learnerEquity.description as race    " + 
				"	, learnerGender.gender_name as gender    " + 
				"	, YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age    " + 
				"	, case    " + 
				"   	 when counterUserDisability.counter is null then 'No'  " +
				"        when counterUserDisability.counter < '1' then 'No'    " + 
				"        when counterUserDisability.counter = '1' then 'Yes'    " + 
				"        else 'Yes'    " + 
				"        end as disability    " + 
				"	, case     " + 
				"		when (YEAR(CURDATE()) - YEAR(learner.date_of_birth)) > "+ HAJConstants.QMR_AGE_YOUTH + " then 'No'    " + 
				"		else 'Yes'    " + 
				"		end as youth    " + 
				"	, case     " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'    " + 
				"		else 'Non-Rsa Citizen'    " + 
				"		end as nonRsaCitizen    " + 
				"		    " + 
				"from " + 
				"	company_learners cl " + 
				"inner join users learner on cl.user_id = learner.id    " + 
				"inner join company employer on cl.employer_id = employer.id    " + 
				"inner join company provider on cl.company_id = provider.id    " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id    " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id    " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id    " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id    " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id      " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id      " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id    " + 
				"left join (    " + 
				"    select     " + 
				"        ud.user_id as userId    " + 
				"        , count(ud.id) as counter     " + 
				"        from     " + 
				"            users_disability ud     " + 
				"        where     " + 
				"            ud.disabilityStatus is not null    " + 
				"        group by     " + 
				"            ud.user_id    " + 
				"    ) counterUserDisability on counterUserDisability.userId = learner.id    " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id    " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id    " + 
				"left join skills_set ss on cl.skills_set_id = ss.id    " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id    " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id    " + 
				"left join skills_program sp on cl.skills_program_id = sp.id    " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id    " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id    " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id    " + 
				"left join learnership lship on cl.learnership_id = lship.id    " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id    " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id    " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id    " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id    " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id     " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id    " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id      " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id     " + 
				"left join (    " + 
				"    select     " + 
				"        ud.user_id as userId    " + 
				"        , count(ud.id) as counter     " + 
				"        from     " + 
				"            users_disability ud     " + 
				"        where     " + 
				"            ud.disabilityStatus is not null    " + 
				"        group by     " + 
				"            ud.user_id    " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id    " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id    " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id    " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id    " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id     " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id    " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " ;
		sql += "where   " ;
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql += " and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		System.out.println("Query for QMR Data:"+sql);
		
		return (List<QmrScriptTwoBean>) super.nativeSelectSqlList(sql, QmrScriptTwoBean.class);
	}
	// '"+quarterRef+"'
	public List<QmrScriptThreeBean> qmrScriptThreeGeneration(String quarterRef, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select      " + 
				"	'"+quarterRef+"' as reportingPeriod     " + 
				"    , cl.id as companyLearnerFlatID   " + 
				"    , case       " + 
				"		when ofoParent.id is not null then ofoParent.ofo_code      " + 
				"		when ofo.id is not null then ofo.ofo_code      " + 
				"		when ofoParentlearner.id is not null then ofoParentlearner.ofo_code      " + 
				"		when ofolearner.id is not null then ofolearner.ofo_code      " + 
				"		Else SUBSTRING('', 1, 2)      " + 
				"		end as ofoCode    " + 
				"   , case    " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle    " + 
				"		when it.qualification_type_selection = '1' then sp.description    " + 
				"		when it.qualification_type_selection = '2' then ss.description    " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " + 
				"		when it.qualification_type_selection = '4' then us.unitstdtitle    " + 
				"		when it.qualification_type_selection = '5' then lship.description    " + 
				"		Else 'Unable to locate information'    " + 
				"		end as artisanTradeDiscription  " + 
				"	, case " + 
				"		when alloc.ofo_codes_id is not null then 'Yes' " + 
				"	    else 'no' " + 
				"	    end as doesTheProgrammeAdressSspNeedsSipSkills   " + 
				"	, UPPER(learner.first_name) as learnerName   " + 
				"	, UPPER(learner.middle_name) as learnerOtherNames   " + 
				"	, UPPER(learner.last_name) as learnerSurname   " + 
				"	, case " + 
				"       when cl.employment_status in (0) then 'YES' " + 
				"       Else 'NO'  " + 
				"       end as learnerPermenantlyEmployed   " + 
				"	, case   " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then learner.rsa_id_number   " + 
				"		Else 'N/A'   " + 
				"		end as learnerIdNumber   " + 
				"	, case   " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then learner.passport_number   " + 
				"		Else 'N/A'   " + 
				"		end as learnerAlternateIdPassportNumber   " + 
				"    , case      " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then learner.date_of_birth     " + 
				"		else learner.date_of_birth    " + 
				"		end as dateOfBirthNonSaCitizens    " + 
				"    , case      " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'     " + 
				"		else 'Non-Rsa Citizen'     " + 
				"		end as saNonSaCitizens     " + 
				"    , learnerEquity.description as race     " + 
				"	 , learnerGender.gender_name as gender   " + 
				"    , case     " + 
				"		 when counterUserDisability.counter is null then 'No'    " + 
				"        when counterUserDisability.counter < '1' then 'No'     " + 
				"        when counterUserDisability.counter = '1' then 'Yes'     " + 
				"        else 'Yes'     " + 
				"        end as pwd   " + 
				"    , learnerResidentialMunicipality.municipality_desc as learnerMinicipality   " + 
				"    , 'SETA' as setaIdlelaOrPublicFetAcronym     " + 
				"    , '' as leadSkillsDevelopmentProviderKnowladgeComponents   " + 
				"    , '' as accreditationNumberKnowlageComponent   " + 
				"    , employer.company_name as leadSkillsDevelopmentProviderPracticalComponents   " + 
				"    , employer.company_site_number as accreditationNumberPracticalComponent   " + 
				"    , employer.company_name as leadEmployer   " + 
				"	, employer.company_site_number as leadEmployerApprovalNumber   " + 
				"	, cl.commencement_date as dateLearnerAgreementRegistration   " + 
				"	, cl.date_learner_terminated as dateLearnerAgreementCancelation   " + 
				"	, '' as reasonForCancelation   " + 
				"	, cl.completion_date as dateLearnerAgreementCompletion   " + 
				"	, '' as tradeTest12And3   " + 
				"	, cl.last_passed_test_date as dateTradeTest12And3   " + 
				"	, '' as reasonForNotYetCompetent   " + 
				"	, ttc.company_name as tradeTestCenter   " + 
				"	, case           " + 
				"		when tpaTTC.certificate_number is not null and tpaTTC.certificate_number <> '' then SUBSTRING(tpaTTC.certificate_number, 1, 20)           " + 
				"		when tpaTTC.accreditation_number is not null and tpaTTC.accreditation_number <> '' then SUBSTRING(tpaTTC.accreditation_number, 1, 20)           " + 
				"		else ''           " + 
				"		end as tradeTestCenterAccreditationNumber   " + 
				"	, UPPER(userAss.first_name) as tradeTestAssessorName   " + 
				"	, UPPER(userAss.last_name) as tradeTestAssessorSurname   " + 
				"	, case   " + 
				"		when userAss.rsa_id_number is not null and userAss.rsa_id_number <> '' then userAss.rsa_id_number   " + 
				"		when userAss.passport_number is not null and userAss.passport_number <> '' then userAss.passport_number   " + 
				"		Else ''   " + 
				"		end as tradeTestAssessorIdNumber   " + 
				"	, UPPER(userMod.first_name) as tradeTestModeratorName   " + 
				"	, UPPER(userMod.last_name) as tradeTestModeratorSurname   " + 
				"	, case   " + 
				"		when userMod.rsa_id_number is not null and userMod.rsa_id_number <> '' then userMod.rsa_id_number   " + 
				"		when userMod.passport_number is not null and userMod.passport_number <> '' then userMod.passport_number   " + 
				"		Else ''   " + 
				"		end as tradeTestModeratorIdNumber   " + 
				"	, cl.date_qualification_obtained as dateLearnerDelacredCompetent   " + 
				"	, cl.certificate_date as dateLearnerCertification   " + 
				"	, cl.certificate_number as learnerCertificationNumber		     "   + 
				"   , YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age    " + 
				"	, case    " + 
				"   	 when counterUserDisability.counter is null then 'No'  " +
				"        when counterUserDisability.counter < '1' then 'No'    " + 
				"        when counterUserDisability.counter = '1' then 'Yes'    " + 
				"        else 'Yes'    " + 
				"        end as disability   " + 
				"	, case     " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'    " + 
				"		else 'Non-Rsa Citizen'    " + 
				"		end as nonRsaCitizen  " + 
				"from    " + 
				"	company_learners cl " + 
				"inner join users learner on cl.user_id = learner.id     " + 
				"inner join company employer on cl.employer_id = employer.id     " + 
				"inner join company provider on cl.company_id = provider.id     " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id     " + 
				"left join training_provider_application tpaTTC on cl.test_centre_training_provider_application_id = tpaTTC.id     " + 
				"left join company ttc on tpaTTC.company_id = ttc.id     " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id     " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id     " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id     " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id       " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id       " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id     " + 
				"left join (     " + 
				"    select      " + 
				"        ud.user_id as userId     " + 
				"        , count(ud.id) as counter      " + 
				"        from      " + 
				"            users_disability ud      " + 
				"        where      " + 
				"            ud.disabilityStatus is not null     " + 
				"        group by      " + 
				"            ud.user_id     " + 
				"    ) counterUserDisability on counterUserDisability.userId = learner.id     " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id     " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id     " + 
				"     " + 
				"left join skills_set ss on cl.skills_set_id = ss.id     " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id     " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id     " + 
				"     " + 
				"left join skills_program sp on cl.skills_program_id = sp.id     " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id     " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id     " + 
				"     " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id     " + 
				"     " + 
				"left join learnership lship on cl.learnership_id = lship.id     " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id     " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id     " + 
				"     " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id     " + 
				"     " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id     " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id      " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id     " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id       " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id      " + 
				"     " + 
				"left join (     " + 
				"    select      " + 
				"        ud.user_id as userId     " + 
				"        , count(ud.id) as counter      " + 
				"        from      " + 
				"            users_disability ud      " + 
				"        where      " + 
				"            ud.disabilityStatus is not null     " + 
				"        group by      " + 
				"            ud.user_id     " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id     " + 
				"     " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id     " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id     " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id     " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id      " + 
				"     " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id     " + 
				"   " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id    " + 
				"left join users userAss on ampAss.users_id = userAss.id     " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id    " + 
				"left join users userMod on ampMod.users_id = userMod.id     " + 
				"left join allocation_list as alloc on alloc.ofo_codes_id = coalesce(ofoParent.id, ofo.id, ofoParentlearner.id, ofolearner.id, 0)    " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " ;
		sql += " where   ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=		"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<QmrScriptThreeBean>) super.nativeSelectSqlList(sql, QmrScriptThreeBean.class);
	}
	
	public List<QmrScriptFourBean> qmrScriptFourGeneration(String quarterRef, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select     " + 
				"	'"+quarterRef+"' as reportingPeriod    " + 
				"   , cl.id as companyLearnerFlatID    " + 
				"	, SUBSTRING(CONCAT_WS(' ', UPPER(learner.first_name), UPPER(learner.middle_name)), 1, 95) as namesOfTheLearner    " + 
				"	, SUBSTRING(UPPER(learner.last_name), 1, 45) as surnameOfTheLearner    " + 
				"	, case      " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then SUBSTRING(learner.rsa_id_number, 1, 15)     " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then SUBSTRING(learner.passport_number, 1, 20)     " + 
				"		ELSE SUBSTRING('', 1, 15)      " + 
				"		end as idNumberOfTheLearner    " + 
				"	, case      " + 
				"		when ofoParent.id is not null then ofoParent.ofo_code     " + 
				"		when ofo.id is not null then ofo.ofo_code     " + 
				"		when ofoParentlearner.id is not null then ofoParentlearner.ofo_code     " + 
				"		when ofolearner.id is not null then ofolearner.ofo_code     " + 
				"		Else SUBSTRING('', 1, 2)     " + 
				"		end as ofoCode  " + 
				"	, case    " + 
				"		when it.qualification_type_selection = '0' then nqfMainQual.description    " + 
				"		when it.qualification_type_selection = '1' then spNqf.description    " + 
				"		when it.qualification_type_selection = '2' then ssNqf.description    " + 
				"		when it.qualification_type_selection = '3' then nqf.description    " + 
				"		when it.qualification_type_selection = '4' then nqf.description    " + 
				"		when it.qualification_type_selection = '5' then lshipNqf.description    " + 
				"		Else nqf.description    " + 
				"		end as nqfLevel  " + 
				"	, case    " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle    " + 
				"		when it.qualification_type_selection = '1' then sp.description    " + 
				"		when it.qualification_type_selection = '2' then ss.description    " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " +
				"		when it.qualification_type_selection = '4' then us.unitstdtitle    " + 
				"		when it.qualification_type_selection = '5' then lship.description    " + 
				"		Else 'Unable to locate information'    " + 
				"		end as qualificationAsPerOfoCodeDesctiptionOfTheQualification    " + 
				"	, case   " + 
				"		when alloc.ofo_codes_id is not null then 'Yes'  " + 
				"	    else 'No'   " + 
				"	    end as doesTheProgrammeAddressSspNeedsSipSkillsNeeds  " + 
				"	, provider.company_name as nameOfInstetution  " + 
				"	, employer.company_name as nameOfTheEmployer   " + 
				"	, case     " + 
				"		when employer.levy_number is not null then employer.levy_number    " + 
				"		when employer.company_registration_number is not null then employer.company_registration_number    " + 
				"		else ''    " + 
				"		end as employerRegistartionSdlNumber   " + 
				"	, CONCAT('Phone: ', employer.tel_number , ' Fax: ' , employer.fax_number , ' Email:',  employer.email) as employerContactDetails  " + 
				"	, cl.commencement_date as startDate  " + 
				"	, '' as durationOfPlacementReflectsStartAndEndDates  " + 
				"	, cl.completion_date as completionDate  " + 
				"	, learnerResidentialProvince.province_desc as learnerProvince   " + 
				"	, learnerResidentialMunicipality.municipality_desc as learnerLocalDistrictMunciplaity     " + 
				"	, learnerResidentialAreaCode.description as specifyLearnerResidentialArea   " + 
				"	, case    " + 
				"		when learner.urban_rural_enum = '0' then 'Urban'    " + 
				"		when learner.urban_rural_enum = '1' then 'Rural'    " + 
				"		else 'Unknown'    " + 
				"		end as isTheLearnerResidentialAreaUrbanRural    " + 
				"	, case    " + 
				"		when learnerFunding.seta_industry_funded = '0' then 'SETA FUNDED'    " + 
				"		when learnerFunding.seta_industry_funded = '1' then 'INDUSTRY FUNDED'    " + 
				"		end as isTheProgrammeSetaIndustryFunded     " + 
				"	, case     " + 
				"		when learnerFunding.seta_industry_funded = '0' and it.basic_grant_amount is not null then it.basic_grant_amount   " + 
				"		else NULL   " + 
				"		end as amountSpentPerLearner    " + 
				"	, learnerEquity.description as race    " + 
				"	, learnerGender.gender_name as gender    " + 
				"	, YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age    " + 
				"	, case    " + 
				"   	 when counterUserDisability.counter is null then 'No'  " +
				"        when counterUserDisability.counter < '1' then 'No'    " + 
				"        when counterUserDisability.counter = '1' then 'Yes'    " + 
				"        else 'Yes'    " + 
				"        end as disability    " + 
				"	, case     " + 
				"		when (YEAR(CURDATE()) - YEAR(learner.date_of_birth)) > " + HAJConstants.QMR_AGE_YOUTH + " then 'No'    " + 
				"		else 'Yes'    " + 
				"		end as youth    " + 
				"	, case     " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'    " + 
				"		else 'Non-Rsa Citizen'    " + 
				"		end as nonRsaCitizen  " + 
				"from   " + 
				"	company_learners cl     " + 
				"inner join users learner on cl.user_id = learner.id    " + 
				"inner join company employer on cl.employer_id = employer.id    " + 
				"inner join company provider on cl.company_id = provider.id    " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id    " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id    " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id    " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id    " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id      " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id      " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id    " + 
				"left join (    " + 
				"    select     " + 
				"        ud.user_id as userId    " + 
				"        , count(ud.id) as counter     " + 
				"        from     " + 
				"            users_disability ud     " + 
				"        where     " + 
				"            ud.disabilityStatus is not null    " + 
				"        group by     " + 
				"            ud.user_id    " + 
				"    ) counterUserDisability on counterUserDisability.userId = learner.id    " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id    " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id    " + 
				"left join skills_set ss on cl.skills_set_id = ss.id    " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id    " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id    " + 
				"left join skills_program sp on cl.skills_program_id = sp.id    " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id    " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id    " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id    " + 
				"left join learnership lship on cl.learnership_id = lship.id    " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id    " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id   " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id    " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id    " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id     " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id    " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id      " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id     " + 
				"left join (    " + 
				"    select     " + 
				"        ud.user_id as userId    " + 
				"        , count(ud.id) as counter     " + 
				"        from     " + 
				"            users_disability ud     " + 
				"        where     " + 
				"            ud.disabilityStatus is not null    " + 
				"        group by     " + 
				"            ud.user_id    " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id    " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id    " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id    " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id    " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id     " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id    " + 
				"left join allocation_list as alloc on alloc.ofo_codes_id = coalesce(ofoParent.id, ofo.id, ofoParentlearner.id, ofolearner.id, 0)   " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " ;
		sql += "where   ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<QmrScriptFourBean>) super.nativeSelectSqlList(sql, QmrScriptFourBean.class);
	}
	
	public List<QmrScriptFiveBean> qmrScriptFiveGeneration(String quarterRef, QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select      " + 
				"	'"+quarterRef+"' as reportingPeriod     " + 
				"    , cl.id as companyLearnerFlatID     " + 
				"	, SUBSTRING(CONCAT_WS(' ', UPPER(learner.first_name), UPPER(learner.middle_name)), 1, 95) as namesOfTheLearner     " + 
				"	, SUBSTRING(UPPER(learner.last_name), 1, 45) as surnameOfTheLearner     " + 
				"	, case       " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then SUBSTRING(learner.rsa_id_number, 1, 15)      " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then SUBSTRING(learner.passport_number, 1, 20)      " + 
				"		ELSE SUBSTRING('', 1, 15)       " + 
				"		end as idNumberOfTheLearner     " + 
				"	, '' as aetProgrammeLevel   " + 
				"	, case     " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle     " + 
				"		when it.qualification_type_selection = '1' then sp.description     " + 
				"		when it.qualification_type_selection = '2' then ss.description     " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " +
				"		when it.qualification_type_selection = '4' then us.unitstdtitle     " + 
				"		when it.qualification_type_selection = '5' then lship.description     " + 
				"		Else 'Unable to locate information'     " + 
				"		end as courseDiscription   " + 
				"	, cl.approval_date as dateEnteredTheProgramme   " + 
				"	, cl.completion_date as dateTheLearnerCompletedTheLearningProgramme 	   " + 
				"	, cl.certificate_date as dateTheLearnerIssuedWithCertificate 	 	   " + 
				"	, employer.company_name as nameOfTheEmployer    " + 
				"	, case      " + 
				"		when employer.levy_number is not null then employer.levy_number     " + 
				"		when employer.company_registration_number is not null then employer.company_registration_number     " + 
				"		else ''     " + 
				"		end as employerRegistartionSdlNumber      " + 
				"	, CONCAT('Phone: ', employer.tel_number , ' Fax: ' , employer.fax_number , ' Email:',  employer.email) as employerContactDetails   " + 
				"	, provider.company_name as nameOfTheTraingProvider     " + 
				"	, case           " + 
				"		when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)           " + 
				"		when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)           " + 
				"		else ''           " + 
				"		end as trainingProviderAccrediciationNumber     " + 
				"	, CONCAT('Phone: ', provider.tel_number , ' Fax: ' , provider.fax_number , ' Email:', provider.email) as trainingProviderContactDetails     " + 
				"	, case      " + 
				"		when providerOT.public_private = '0' then 'Public'     " + 
				"		when providerOT.public_private = '1' then 'Private'     " + 
				"		Else ''     " + 
				"		end as isTrainingProviderPrivatePublic     " + 
				"	, learnerResidentialProvince.province_desc as learnerProvince     " + 
				"	, learnerResidentialMunicipality.municipality_desc as learnerLocalDistrictMunciplaity     " + 
				"	, learnerResidentialAreaCode.description as specifyLearnerResidentialArea     " + 
				"	, case     " + 
				"		when learner.urban_rural_enum = '0' then 'Urban'     " + 
				"		when learner.urban_rural_enum = '1' then 'Rural'     " + 
				"		else 'Unknown'     " + 
				"		end as isTheLearnerResidentialAreaUrbanRural    " + 
				"	, case     " + 
				"		when learnerFunding.seta_industry_funded = '0' then 'SETA FUNDED'     " + 
				"		when learnerFunding.seta_industry_funded = '1' then 'INDUSTRY FUNDED'     " + 
				"		end as isTheProgrammeSetaIndustryFunded      " + 
				"	, case     " + 
				"		when learnerFunding.seta_industry_funded = '0' and it.basic_grant_amount is not null then it.basic_grant_amount   " + 
				"		else NULL   " + 
				"		end as amountSpentPerLearner     " + 
				"	, learnerEquity.description as race     " + 
				"	, learnerGender.gender_name as gender     " + 
				"	, YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age     " + 
				"	, case     " + 
				"   	 when counterUserDisability.counter is null then 'No'  " +
				"        when counterUserDisability.counter < '1' then 'No'     " + 
				"        when counterUserDisability.counter = '1' then 'Yes'     " + 
				"        else 'Yes'     " + 
				"        end as disability     " + 
				"	, case      " + 
				"		when (YEAR(CURDATE()) - YEAR(learner.date_of_birth)) > " + HAJConstants.QMR_AGE_YOUTH + " then 'No'     " + 
				"		else 'Yes'     " + 
				"		end as youth     " + 
				"	, case      " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'     " + 
				"		else 'Non-Rsa Citizen'     " + 
				"		end as nonRsaCitizen    " + 
				"from    " + 
				"	company_learners cl      " + 
				"inner join users learner on cl.user_id = learner.id     " + 
				"inner join company employer on cl.employer_id = employer.id     " + 
				"inner join company provider on cl.company_id = provider.id     " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id     " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id     " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id     " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id     " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id       " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id       " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id     " + 
				"left join (     " + 
				"    select      " + 
				"        ud.user_id as userId     " + 
				"        , count(ud.id) as counter      " + 
				"        from      " + 
				"            users_disability ud      " + 
				"        where      " + 
				"            ud.disabilityStatus is not null     " + 
				"        group by      " + 
				"            ud.user_id     " + 
				"    ) counterUserDisability on counterUserDisability.userId = learner.id     " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id     " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id     " + 
				"     " + 
				"left join skills_set ss on cl.skills_set_id = ss.id     " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id     " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id     " + 
				"     " + 
				"left join skills_program sp on cl.skills_program_id = sp.id     " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id     " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id     " + 
				"     " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id     " + 
				"     " + 
				"left join learnership lship on cl.learnership_id = lship.id     " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id     " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id     " + 
				"     " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id     " + 
				"     " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id     " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id      " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id     " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id       " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id      " + 
				"     " + 
				"left join (     " + 
				"    select      " + 
				"        ud.user_id as userId     " + 
				"        , count(ud.id) as counter      " + 
				"        from      " + 
				"            users_disability ud      " + 
				"        where      " + 
				"            ud.disabilityStatus is not null     " + 
				"        group by      " + 
				"            ud.user_id     " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id     " + 
				"     " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id     " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id     " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id     " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id      " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id  " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " ;
		sql += "where   ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<QmrScriptFiveBean>) super.nativeSelectSqlList(sql, QmrScriptFiveBean.class);
	}
	
	/*
	 * Count of equity and gender for QMR Reporting
	 */
	public List<CounterBean> qmrScriptGenerEquityCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select   " + 
				"	count(cl.id) as counter  " + 
				"from   " + 
				"	company_learners cl   " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				
				"	and learnerEquity.qmr_equity in ("+populateQmrEquityEnumResult(qmrEquityEnum)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		
		System.out.println("qmrScriptGenerEquityCount:"+sql);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of users who age is equal or below Youth indicator for QMR Reporting
	 */
	public List<CounterBean> qmrScriptYouthCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount) throws Exception {
		String sql = "select " + 
				"	count(age) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		(YEAR(CURDATE()) - YEAR(learner.date_of_birth)) as age  " + 
				"	from   " + 
				"		company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=		"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " ;
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	age <= " + ageCount.toString();
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of users who age is equal or below Youth indicator for QMR Reporting (With Gender and Equity)
	 */
	public List<CounterBean> qmrScriptYouthCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select " + 
				"	count(age) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		(YEAR(CURDATE()) - YEAR(learner.date_of_birth)) as age  " + 
				"	from   " + 
				"		company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  "; 
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerEquity.qmr_equity in ("+populateQmrEquityEnumResult(qmrEquityEnum)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+") ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	age <= " + ageCount.toString();
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of users who age is equal or below Youth indicator for QMR Reporting (With Gender)
	 */
	public List<CounterBean> qmrScriptYouthCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Integer ageCount, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select " + 
				"	count(age) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		(YEAR(CURDATE()) - YEAR(learner.date_of_birth)) as age  " + 
				"	from   " + 
				"		company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+") " ;
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	age <= " + ageCount.toString();
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count Disability
	 */
	public List<CounterBean> qmrScriptCountDisability(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select " + 
				"	count(amount) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		counterLearnerDisability.counter as amount  " + 
				"	from   " + 
				"		company_learners cl " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id  " + 
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+") ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	amount <> 0 ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count Disability with gender and equity
	 */
	public List<CounterBean> qmrScriptCountDisabilityWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select " + 
				"	count(amount) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		counterLearnerDisability.counter as amount  " + 
				"	from   " + 
				"		company_learners cl " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id  " + 
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerEquity.qmr_equity in ("+populateQmrEquityEnumResult(qmrEquityEnum)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+") ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "  and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+") ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	amount <> 0 ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count Disability with gender
	 */
	public List<CounterBean> qmrScriptCountDisabilityWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select " + 
				"	count(amount) as counter " + 
				"FROM (  " + 
				"	select   " + 
				"		counterLearnerDisability.counter as amount  " + 
				"	from   " + 
				"		company_learners cl " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id  " + 
				"where  "; 
		
		
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql += 	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "  and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql += " ) as youthCount " + 
			"where "
			+ "	amount <> 0 ";
		
		System.out.println("QMR Report Query :::"+sql);
		
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of RSA Citizens
	 */
	public List<CounterBean> qmrScriptRsaCitizenCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "  select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				"from ( " + 
				"	select   " + 
				"	case    " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"		else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen  " + 
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"where  " + 
				"	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  " + 
				"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql +=  "  and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase " + 
			" where nonRsaCitizen = 'Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of Non Non-RSA Citizens
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCount(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = " select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				" from ( " + 
				"   select   " + 
				"	case    " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"		else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen  " + 
				" from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id   " +
				"where  " ;
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += " and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  " ;
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase  " + 
			" where nonRsaCitizen = 'Non-Rsa Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of RSA Citizens with Gender And Equity
	 */
	public List<CounterBean> qmrScriptRsaCitizenCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				"from ( " + 
				"   select  " + 
				"		case    " + 
				"			when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"			else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen   " + 
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  "; 
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		} 
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerEquity.qmr_equity in ("+populateQmrEquityEnumResult(qmrEquityEnum)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+") ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql +="	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase  " + 
				" where nonRsaCitizen = 'Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of Non RSA Citizens with Gender And Equity
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCountWithGenderAndEquity(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrEquityEnum> qmrEquityEnum, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				"from ( " + 
				"   select  " + 
				"		case    " + 
				"			when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"			else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen   " + 
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " +
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		}
		sql +=  "	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerEquity.qmr_equity in ("+populateQmrEquityEnumResult(qmrEquityEnum)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase  " + 
				" where nonRsaCitizen = 'Non-Rsa Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of RSA Citizens with Gender
	 */
	public List<CounterBean> qmrScriptRsaCitizenCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				"from ( " + 
				"   select  " + 
				"		case    " + 
				"			when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"			else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen   " +
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " +
				"where  "; 
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		}	 
		sql += "	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
			   "	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+") ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  ";
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase  " + 
				" where nonRsaCitizen = 'Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	/*
	 * Count of Non RSA Citizens with Gender
	 */
	public List<CounterBean> qmrScriptNonRsaCitizenCountWithGender(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList, 
			List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, List<QmrGenderEnum> qmrGenderEnum) throws Exception {
		String sql = "select   " + 
				"	count(nonRsaCitizen) as counter  " + 
				"from ( " + 
				"   select  " + 
				"		case    " + 
				"			when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'   " + 
				"			else 'Non-Rsa Citizen'   " + 
				"		end as nonRsaCitizen   " +
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " + 
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
//		    sql += "	(learnerInterventionType.qmr_tvet_report = true or tfq.appear_in_qmr_report = true)  ";
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		}
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  " + 
				"	and learnerGender.qmr_gender in ("+populateQmrGenderEnumResult(qmrGenderEnum)+")";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  " ;
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		sql +=" ) as learnerbase  " + 
				" where nonRsaCitizen = 'Non-Rsa Citizen' ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	public List<CounterBean> qmrScriptCountTotalLearners(QmrEnteredCompletedEnum qmrEnteredCompletedEnum, Date fromDate, Date toDate, List<QmrTypeSelectionEnum> qmrTypeSelectionEnumList,  List<EmploymentStatusEnum> employmentStatusEnum, List<LearnerStatusEnum> learnerStatusEnumList, Boolean newBurseryLearnerEntered) throws Exception {
		String sql = "select   " + 
				"	count(id) as counter  " + 
				"from ( " + 
				"   select  " + 
				"		cl.id as id   " + 
				"from   " + 
				"	company_learners cl  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"inner join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"inner join equity learnerEquity on learner.equity_id = learnerEquity.id and learnerEquity.qmr_equity is not null  " + 
				"inner join gender learnerGender on learner.gender_id = learnerGender.id and learnerGender.qmr_gender is not null  " + 
				"left join tvet_fet_qualification tfq on tfq.id = cl.tvet_fet_qualification_id " + 
				"where  ";
		if (checkIfTvetInList(qmrTypeSelectionEnumList, QmrTypeSelectionEnum.TVET_STUDENT)) {
			sql += "	((learnerInterventionType.qmr_tvet_report = true and tfq.appear_in_qmr_report = true) or (learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")))   ";
		} else {
		    sql += "	learnerInterventionType.qmr_type_selection in ("+populateQmrTypeSelectionEnumListResult(qmrTypeSelectionEnumList)+")  ";
		}
		sql +=	"	and cl.learner_status in ("+populateLearnerStatusEnumResult(learnerStatusEnumList)+")  ";
		if (employmentStatusEnum != null && !employmentStatusEnum.isEmpty()) {
			sql += "	and cl.employment_status in ("+populateEmploymentStatusEnumResult(employmentStatusEnum)+")  " ;
		}
		if (qmrEnteredCompletedEnum != null && qmrEnteredCompletedEnum == QmrEnteredCompletedEnum.Completed) {
			sql += " and cl.date_learner_completed Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and cl.date_learner_registered Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		
		if (newBurseryLearnerEntered != null) {
			if (newBurseryLearnerEntered) {
				sql += " and ( cl.new_bursary is null or cl.new_bursary = true ) ";
			} else {
				sql += " and cl.new_bursary = false  ";
			}
		}
		
		sql +=" ) as learnerbase  ";
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class);
	}
	
	
	/*
	 * Check if report is for TVET Reporting
	 */
	public boolean checkIfTvetInList(List<QmrTypeSelectionEnum> entryList, QmrTypeSelectionEnum tvetQmrTypeSelection) throws Exception{
		boolean inList = false;
		for (QmrTypeSelectionEnum entry : entryList) {
			if (entry == tvetQmrTypeSelection) {
				return true;
			}
		}
		return inList;
	}
	
	/*
	 * Populates List<QmrTypeSelectionEnum> into ordinal values for Native SQL
	 * Used across all scripts
	 */
	public String populateQmrTypeSelectionEnumListResult(List<QmrTypeSelectionEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (QmrTypeSelectionEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	/*
	 * Populates List<EmploymentStatusEnum> into ordinal values for Native SQL
	 * Used across all scripts 
	 */
	public String populateEmploymentStatusEnumResult(List<EmploymentStatusEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (EmploymentStatusEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	/*
	 * Populates List<LearnerStatusEnum> into ordinal values for Native SQL
	 * Used across all scripts
	 */
	public String populateLearnerStatusEnumResult(List<LearnerStatusEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (LearnerStatusEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	/*
	 * Populates List<QmrEquityEnum> into ordinal values for Native SQL
	 * Used across all scripts
	 */
	public String populateQmrEquityEnumResult(List<QmrEquityEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (QmrEquityEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	/*
	 * Populates List<QmrGenderEnum> into ordinal values for Native SQL
	 * Used across all scripts
	 */
	public String populateQmrGenderEnumResult(List<QmrGenderEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (QmrGenderEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	
	/*
	 * 	TEST METHOD FOR SCRIPT ONE
	 *  script one all data
	 */
	public List<QmrScriptOneBean> qmrScriptOneBeanExtractTestMethod() throws Exception {
		String sql = "select   " + 
				"	'' as reportingPeriod  " + 
				"	, cl.id as companyLearnerFlatID " + 
				"	, SUBSTRING(CONCAT_WS(' ', learner.first_name, learner.middle_name), 1, 95) as namesOfTheLearner  " + 
				"	, SUBSTRING(learner.last_name, 1, 45) as surnameOfTheLearner  " + 
				"	, case    " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' then SUBSTRING(learner.rsa_id_number, 1, 15)   " + 
				"		when learner.passport_number is not null and learner.passport_number <> '' then SUBSTRING(learner.passport_number, 1, 20)   " + 
				"		ELSE SUBSTRING('', 1, 15)    " + 
				"		end as idNumberOfTheLearner  " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then 'Qualification'  " + 
				"		when it.qualification_type_selection = '1' then 'Skills Program'  " + 
				"		when it.qualification_type_selection = '2' then 'Skills Set'  " + 
				"		when it.qualification_type_selection = '3' then 'Non-Credit Bearing Intervation Title'  " + 
				"		when it.qualification_type_selection = '4' then 'Unit Standard'  " + 
				"		when it.qualification_type_selection = '5' then 'Learnership'  " + 
				"		Else 'Unable to locate type'  " + 
				"		end as typeOfLearningProgramme  " + 
				"	, cl.approval_date as dateTheLearnerEnteredTheLearningProgramme  " + 
				"	, cl.commencement_date as actualStartDateOfTheLearningProgramme  " + 
				"	, cl.completion_date as dateTheLearnerCompletedTheLearningProgramme  " + 
				"	, cl.certificate_date as dateRecivedCertificateLearningProgramme  " + 
				"	, case    " + 
				"		when ofoParent.id is not null then SUBSTRING(ofoParent.ofo_code, 6, 21)   " + 
				"		when ofo.id is not null then SUBSTRING(ofo.ofo_code, 6, 21)   " + 
				"		when ofoParentlearner.id is not null then SUBSTRING(ofoParentlearner.ofo_code, 6, 21)   " + 
				"		when ofolearner.id is not null then SUBSTRING(ofolearner.ofo_code, 6, 21)   " + 
				"		Else SUBSTRING('', 1, 2)   " + 
				"		end as ofoCode   " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then nqfMainQual.description  " + 
				"		when it.qualification_type_selection = '1' then spNqf.description  " + 
				"		when it.qualification_type_selection = '2' then ssNqf.description  " + 
				"		when it.qualification_type_selection = '3' then nqf.description  " + 
				"		when it.qualification_type_selection = '4' then nqf.description  " + 
				"		when it.qualification_type_selection = '5' then lshipNqf.description  " + 
				"		Else nqf.description  " + 
				"		end as nqfLevel  " + 
				"	, case  " + 
				"		when it.qualification_type_selection = '0' then mainQualification.qualificationtitle  " + 
				"		when it.qualification_type_selection = '1' then sp.description  " + 
				"		when it.qualification_type_selection = '2' then ss.description  " + 
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is not null then cl.non_credid_bearing_description  " +
				"		when it.qualification_type_selection = '3' and cl.non_credid_bearing_description is null then ncbtt.description  " +
				"		when it.qualification_type_selection = '4' then us.unitstdtitle  " + 
				"		when it.qualification_type_selection = '5' then lship.description  " + 
				"		Else 'Unable to locate information'  " + 
				"		end as qualificationAsPerOfoCodeDesctiptionOfTheQualification  " + 
				"	, employer.company_name as nameOfTheEmployer  " + 
				"	, case   " + 
				"		when employer.levy_number is not null then employer.levy_number  " + 
				"		when employer.company_registration_number is not null then employer.company_registration_number  " + 
				"		else ''  " + 
				"		end as employerRegistartionSdlNumber   " + 
				"	, CONCAT('Phone: ', employer.tel_number , ' Fax: ' , employer.fax_number , ' Email:',  employer.email) as employerContactDetails  " + 
				"	, provider.company_name as nameOfTheTraingProvider  " + 
				"	, case        " + 
				"		when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)        " + 
				"		when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)        " + 
				"		else ''        " + 
				"		end as trainingProviderAccrediciationNumber  " + 
				"	, CONCAT('Phone: ', provider.tel_number , ' Fax: ' , provider.fax_number , ' Email:', provider.email) as trainingProviderContactDetails  " + 
				"	, case   " + 
				"		when providerOT.public_private = '0' then 'Public'  " + 
				"		when providerOT.public_private = '1' then 'Private'  " + 
				"		Else ''  " + 
				"		end as isTrainingProviderPrivatePublic  " + 
				"	, learnerResidentialProvince.province_desc as learnerProvince  " + 
				"	, learnerResidentialMunicipality.municipality_desc as learnerLocalDistrictMunciplaity  " + 
				"	, learnerResidentialAreaCode.description as specifyLearnerResidentialArea  " + 
				"	, case  " + 
				"		when learner.urban_rural_enum = '0' then 'Urban'  " + 
				"		when learner.urban_rural_enum = '1' then 'Rural'  " + 
				"		else 'Unknown'  " + 
				"		end as isTheLearnerResidentialAreaUrbanRural  " + 
				"	, case  " + 
				"		when learnerFunding.seta_industry_funded = '0' then 'SETA FUNDED'  " + 
				"		when learnerFunding.seta_industry_funded = '1' then 'INDUSTRY FUNDED'  " + 
				"		end as isTheProgrammeSetaIndustryFunded   " + 
				"	, case     " + 
				"		when learnerFunding.seta_industry_funded = '0' and it.basic_grant_amount is not null then it.basic_grant_amount   " + 
				"		else NULL   " + 
				"		end as amountSpentPerLearner  " + 
				"	, learnerEquity.description as race  " + 
				"	, learnerGender.gender_name as gender  " + 
				"	, YEAR(CURDATE()) - YEAR(learner.date_of_birth) as age  " + 
				"	, '' as disability  " + 
				"	, case   " + 
				"		when (YEAR(CURDATE()) - YEAR(learner.date_of_birth)) > 35 then 'No'  " + 
				"		else 'Yes'  " + 
				"		end as youth  " + 
				"	, case   " + 
				"		when learner.rsa_id_number is not null and learner.rsa_id_number <> '' and learner.rsa_citizen_type = 0 then 'Citizen'  " + 
				"		else 'Non-Rsa Citizen'  " + 
				"		end as nonRsaCitizen  " + 
				"		  " + 
				"from company_learners cl   " + 
				"  " + 
				"inner join users learner on cl.user_id = learner.id  " + 
				"inner join company employer on cl.employer_id = employer.id  " + 
				"inner join company provider on cl.company_id = provider.id  " + 
				"left join training_provider_application tpa on cl.training_provider_application_id = tpa.id  " + 
				"inner join intervention_type it on cl.intervention_type_id = it.id  " + 
				"left join funding learnerFunding on cl.dunding_id = learnerFunding.id  " + 
				"left join intervention_type learnerInterventionType on cl.intervention_type_id  = learnerInterventionType.id  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id    " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id    " + 
				"left join nqf_levels nqf on cl.nqf_levels_id = nqf.id  " + 
				"  " + 
				"left join saqa_qualification mainQualification on cl.qualification_id = mainQualification.id  " + 
				"left join nqf_levels nqfMainQual on mainQualification.nqf_level_id = nqfMainQual.id  " + 
				"  " + 
				"left join skills_set ss on cl.skills_set_id = ss.id  " + 
				"left join saqa_qualification ssQual on ss.qualification_id = ssQual.id  " + 
				"left join nqf_levels ssNqf on ssQual.nqf_level_id = ssNqf.id  " + 
				"  " + 
				"left join skills_program sp on cl.skills_program_id = sp.id  " + 
				"left join saqa_qualification spQual on sp.qualification_id = spQual.id  " + 
				"left join nqf_levels spNqf on spQual.nqf_level_id = spNqf.id  " + 
				"  " + 
				"left join saqa_unitstandard us on cl.unit_standard_id = us.id  " + 
				"  " + 
				"left join learnership lship on cl.learnership_id = lship.id  " + 
				"left join saqa_qualification lshipQual on lship.qualification_id = lshipQual.id  " + 
				"left join nqf_levels lshipNqf on lshipQual.nqf_level_id = lshipNqf.id  " + 
				"  " + 
				"left join non_credit_bearing_intervation_title ncbtt on cl.non_credit_bearing_intervation_title_id = ncbtt.id  " + 
				"  " + 
				"left join equity learnerEquity on learner.equity_id = learnerEquity.id  " + 
				"left join gender learnerGender on learner.gender_id = learnerGender.id   " + 
				"left join urbal_rural learnerUrbalRural on learner.urban_rural_enum = learnerUrbalRural.id  " + 
				"left join ofo_codes ofolearner on ofolearner.id = learner.ofo_codes_id    " + 
				"left join ofo_codes ofoParentlearner on ofoParentlearner.id = ofolearner.ofo_codes_id   " + 
				"  " + 
				"left join (  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , count(ud.id) as counter   " + 
				"        from   " + 
				"            users_disability ud   " + 
				"        where   " + 
				"            ud.disabilityStatus is not null  " + 
				"        group by   " + 
				"            ud.user_id  " + 
				"    ) counterLearnerDisability on counterLearnerDisability.userId = learner.id  " + 
				"  " + 
				"left join address learnerResidentialAddress on learner.residential_address_id = learnerResidentialAddress.id  " + 
				"left join statssa_area_code learnerResidentialAreaCode on learnerResidentialAddress.stats_saarea_code_id = learnerResidentialAreaCode.id  " + 
				"left join municipality learnerResidentialMunicipality on learnerResidentialAddress.municipality_id = learnerResidentialMunicipality.id  " + 
				"left join province learnerResidentialProvince on learnerResidentialMunicipality.provinces_idprovinces = learnerResidentialProvince.id   " + 
				"  " + 
				"left join address learnerPostalAddress on learner.postal_address_id = learnerPostalAddress.id  " + 
				"left join statssa_area_code learnerPostalAreaCode on learnerPostalAddress.stats_saarea_code_id = learnerPostalAreaCode.id  " + 
				"left join municipality learnerPostalMunicipality on learnerPostalAddress.municipality_id = learnerPostalMunicipality.id  " + 
				"left join province learnerPostalProvince on learnerPostalMunicipality.provinces_idprovinces = learnerPostalProvince.id   " + 
				"  " + 
				"left join address employerResidentialAddress on employer.residential_address_id = employerResidentialAddress.id  " + 
				"left join statssa_area_code employerResidentialAreaCode on employerResidentialAddress.stats_saarea_code_id = employerResidentialAreaCode.id  " + 
				"left join municipality employerResidentialMunicipality on employerResidentialAddress.municipality_id = employerResidentialMunicipality.id  " + 
				"left join province employerResidentialProvince on employerResidentialMunicipality.provinces_idprovinces = employerResidentialProvince.id   " + 
				"  " + 
				"left join address employerPostalAddress on employer.postal_address_id = employerPostalAddress.id  " + 
				"left join statssa_area_code employerPostalAreaCode on employerPostalAddress.stats_saarea_code_id = employerPostalAreaCode.id  " + 
				"left join municipality employerPostalMunicipality on employerPostalAddress.municipality_id = employerPostalMunicipality.id  " + 
				"left join province employerPostalProvince on employerPostalMunicipality.provinces_idprovinces = employerPostalProvince.id   " + 
				"  " + 
				"left join organisation_type providerOT on provider.organisation_type_id = providerOT.id  " + 
				"  " + 
				"left join address providerResidentialAddress on provider.residential_address_id = providerResidentialAddress.id  " + 
				"left join statssa_area_code providerResidentialAreaCode on providerResidentialAddress.stats_saarea_code_id = providerResidentialAreaCode.id  " + 
				"left join municipality providerResidentialMunicipality on providerResidentialAddress.municipality_id = providerResidentialMunicipality.id  " + 
				"left join province providerResidentialProvince on providerResidentialMunicipality.provinces_idprovinces = providerResidentialProvince.id   " + 
				"  " + 
				"left join address providerPostalAddress on provider.postal_address_id = providerPostalAddress.id  " + 
				"left join statssa_area_code providerPostalAreaCode on providerPostalAddress.stats_saarea_code_id = providerPostalAreaCode.id  " + 
				"left join municipality providerPostalMunicipality on providerPostalAddress.municipality_id = providerPostalMunicipality.id  " + 
				"left join province providerPostalProvince on providerPostalMunicipality.provinces_idprovinces = providerPostalProvince.id";
		return (List<QmrScriptOneBean>) super.nativeSelectSqlList(sql, QmrScriptOneBean.class);
	}
	
	
}