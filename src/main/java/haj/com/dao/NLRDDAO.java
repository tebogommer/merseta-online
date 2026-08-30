package haj.com.dao;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.dataextract.bean.NLRDFile21Bean;
import haj.com.dataextract.bean.NLRDFile21BeanProviderVersionTwo;
import haj.com.dataextract.bean.NLRDFile22Bean;
import haj.com.dataextract.bean.NLRDFile23Bean;
import haj.com.dataextract.bean.NLRDFile24Bean;
import haj.com.dataextract.bean.NLRDFile24BeanProviderAccreditationVersionTwo;
import haj.com.dataextract.bean.NLRDFile25Bean;
import haj.com.dataextract.bean.NLRDFile25BeanPersonInformationVersionTwo;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.dataextract.bean.NLRDFile26BeanPersonDesignationVersionTwo;
import haj.com.dataextract.bean.NLRDFile27Bean;
import haj.com.dataextract.bean.NLRDFile27BeanNQFDesignationRegistrationVersionTwo;
import haj.com.dataextract.bean.NLRDFile28Bean;
import haj.com.dataextract.bean.NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile29Bean;
import haj.com.dataextract.bean.NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo;
import haj.com.dataextract.bean.NLRDFile30Bean;
import haj.com.dataextract.bean.NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo;
import haj.com.entity.NLRDFile21;
import haj.com.entity.NLRDFile24;
import haj.com.entity.NLRDFile25;
import haj.com.entity.NLRDFile26;
import haj.com.entity.NLRDFile27;
import haj.com.entity.NLRDFile28;
import haj.com.entity.NLRDFile29;
import haj.com.entity.NLRDFile30;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class NLRDDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}
	
	/**
	 * NRLD FILES
	 */
	public List<NLRDFile21Bean> extractNLRDFile21Bean() throws Exception {
		String sql =  " select CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode, "
						+ " e.nlrd_code as etqaId, "
						+ " '' as stdIndustryClassCode, "
						+ " c.trading_name as providerName, "
						+ " pt.nlrd_code as providerTypeId, "
						+ " ap.address_line_1 as providerAddress1, "
						+ " ap.address_line_2 as providerAddress2, "
						+ " ap.address_line_3 as providerAddress3, "
						+ " ap.postcode as providerPostalCode, "
						+ " c.tel_number as providerPhoneNumber, "
						+ " c.fax_number as providerFaxNumber, "
						+ " c.company_registration_number as providerSarsNumber, "
						+ " '' as providerContactName, "
						+ " '' as providerContactEmailAddress, "
						+ " '' as providerContactPhoneNumber, "
						+ " '' as providerContactCellNumber, "
						+ " tpa.`accreditation_number` as providerAccreditationNum, "
						+ " tpa.start_date as providerAccreditStartDate, "
						+ " tpa.expiriy_date as providerAccreditEndDate, "
						+ " rcmg.decision_number  as etqaDecisionNumber, "
						+ " '' as providerClassId, "
						+ " '' as structureStatusId, "
						+ " p.stats_prov_id	as provinceCode, "
						+ " 'ZA'			as countryCode,  "
						+ " ap.latitude_degrees	as latitudeDegree,  "
						+ " ap.latitude_minutes as latitudeMinutes, "
						+ " ap.latitude_seconds as latitudeSeconds,  "
						+ " ap.longitude_degrees as longitudeDegree,  "
						+ " ap.longitude_minutes as longitudeMinutes,  "
						+ " ap.longitude_seconds as longitudeSeconds, "
						+ " a.address_line_1 as providerPhysicalAddress1,  "
						+ " a.address_line_2 as providerPhysicalAddress2,  "
						+ " a.address_line_3 as providerPhysicalAddressTown, "
						+ " a.postcode as providerPhysAddressPostcode,  "
						+ " ''			as providerWebAddress,  "
						+ " CURDATE()			as dateStamp "
						+ " from training_provider_application tpa "
						+ " inner join company c on c.id = tpa.company_id  "
						+ " inner join etqa e on tpa.`etqa_id` = e.id "
						+ " inner join provider_type pt on pt.id = tpa.provider_type "
						+ " inner join address a on a.id = c.residential_address_id "
						+ " inner join address ap on ap.id = c.postal_address_id "
						+ " inner join municipality m on m.id = ap.municipality_id "
						+ " inner join province p on p.id = m.provinces_idprovinces "
						+ " inner join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id = tpa.review_committee_meeting_id ";
		return (List<NLRDFile21Bean>) super.nativeSelectSqlList(sql, NLRDFile21Bean.class);
	}
	
	public List<NLRDFile21BeanProviderVersionTwo> extractNLRDFile21BeanVersionTwo() throws Exception {
		String sql =  "Select  " + 
				"    providerCode  " + 
				"    , etqaId  " + 
				"    , stdIndustryClassCode  " + 
				"    , providerName  " + 
				"    , providerTypeId  " + 
				"    , providerAddress1  " + 
				"    , providerAddress2  " + 
				"    , providerAddress3       " + 
				"    , providerPostalCode  " + 
				"    , providerPhoneNumber  " + 
				"    , providerFaxNumber      " + 
				"    , providerSarsNumber  " + 
				"    , providerContactName  " + 
				"    , providerContactEmailAddress  " + 
				"    , providerContactPhoneNumber       " + 
				"    , providerContactCellNumber       " + 
				"    , providerAccreditationNum     " + 
				"    , providerAccreditStartDate  " + 
				"    , providerAccreditEndDate    " + 
				"    , etqaDecisionNumber  " + 
				"    , providerClassId     " + 
				"    , structureStatusId  " + 
				"    , provinceCode    " + 
				"    , countryCode        " + 
				"    , latitudeDegree  " + 
				"    , latitudeMinutes  " + 
				"    , latitudeSeconds     " + 
				"    , longitudeDegree     " + 
				"    , longitudeMinutes     " + 
				"    , longitudeSeconds     " + 
				"    , providerPhysicalAddress1        " + 
				"    , providerPhysicalAddress2   " + 
				"    , providerPhysicalAddressTown       " + 
				"    , providerPhysAddressPostcode        " + 
				"    , providerWebAddress  " + 
				"    , dateStamp    " + 
				"From (  " + 
				"    select  " + 
				"         providerCode  " + 
				"        , etqaId  " + 
				"        , stdIndustryClassCode  " + 
				"        , providerName  " + 
				"        , providerTypeId  " + 
				"        , providerAddress1  " + 
				"        , providerAddress2  " + 
				"        , providerAddress3       " + 
				"        , providerPostalCode  " + 
				"        , providerPhoneNumber  " + 
				"        , providerFaxNumber      " + 
				"        , providerSarsNumber  " + 
				"        , providerContactName  " + 
				"        , providerContactEmailAddress  " + 
				"        , providerContactPhoneNumber       " + 
				"        , providerContactCellNumber       " + 
				"        , providerAccreditationNum     " + 
				"        , providerAccreditStartDate  " + 
				"        , providerAccreditEndDate    " + 
				"        , etqaDecisionNumber  " + 
				"        , providerClassId     " + 
				"        , structureStatusId  " + 
				"        , provinceCode    " + 
				"        , countryCode        " + 
				"        , latitudeDegree  " + 
				"        , latitudeMinutes  " + 
				"        , latitudeSeconds     " + 
				"        , longitudeDegree     " + 
				"        , longitudeMinutes     " + 
				"        , longitudeSeconds     " + 
				"        , providerPhysicalAddress1        " + 
				"        , providerPhysicalAddress2        " + 
				"        , providerPhysicalAddressTown       " + 
				"        , providerPhysAddressPostcode        " + 
				"        , providerWebAddress  " + 
				"        , sDLNo  " + 
				"        , dateStamp    " + 
				"    FROM (  " + 
				"    select  " + 
				"        tpa.id as provider_id,  " + 
				"        SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode,      " + 
				"        case     " + 
				"            when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"            when e.code is not null and e.code  <> '' then SUBSTRING(e.code , 1, 10)     " + 
				"            ELSE SUBSTRING('599', 1, 10)      " + 
				"        end as etqaId,     " + 
				"        SUBSTRING(sc.code, 1, 10) as stdIndustryClassCode,       " + 
				"        SUBSTRING(c.company_name, 1, 70) as providerName,       " + 
				"        case     " + 
				"            when pt.nlrd_code is not null and pt.nlrd_code <> '' then SUBSTRING(pt.nlrd_code, 1, 10)     " + 
				"            ELSE SUBSTRING('500', 1, 10)     " + 
				"            end as providerTypeId,      " + 
				"        SUBSTRING(ap.address_line_1, 1, 50) as providerAddress1,       " + 
				"        SUBSTRING(ap.address_line_2, 1, 50) as providerAddress2,       " + 
				"        SUBSTRING(ap.address_line_3, 1, 50) as providerAddress3,       " + 
				"        SUBSTRING(ap.postcode, 1, 4) as providerPostalCode,       " + 
				"        SUBSTRING(c.tel_number, 1, 10) as providerPhoneNumber,       " + 
				"        SUBSTRING(c.fax_number, 1, 20) as providerFaxNumber,       " + 
				"        SUBSTRING(c.company_registration_number, 1, 20) as providerSarsNumber,       " + 
				"        SUBSTRING(' ', 1, 50) as providerContactName,       " + 
				"        SUBSTRING(' ', 1, 50) as providerContactEmailAddress,       " + 
				"        SUBSTRING(' ', 1, 20) as providerContactPhoneNumber,       " + 
				"        SUBSTRING(' ', 1, 20) as providerContactCellNumber,       " + 
				"        case     " + 
				"            when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)     " + 
				"            when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)     " + 
				"            else ' '     " + 
				"            end as providerAccreditationNum,     " + 
				"        tpa.start_date as providerAccreditStartDate,     " + 
				"        tpa.expiriy_date as providerAccreditEndDate,     " + 
				"        SUBSTRING(f.etqeDecisionNumber, 1, 20) as etqaDecisionNumber,       " + 
				"        SUBSTRING(pc.setmis_code, 1, 10) as providerClassId,       " + 
				"        case     " + 
				"            when tpa.approval_status = 34 then SUBSTRING('512', 1, 20)     " + 
				"            when tpa.accreditation_application_type in (0,2,3) and tpa.approval_status = 0 then SUBSTRING('510', 1, 10)     " + 
				"            when tpa.accreditation_application_type in (4,5,6) and tpa.approval_status = 0 then SUBSTRING('501', 1, 10)     " + 
				"            when tpa.accreditation_application_type in (1) and tpa.approval_status = 0 then SUBSTRING('511', 1, 10)     " + 
				"            ELSE SUBSTRING('515', 1, 10)     " + 
				"            end as structureStatusId,         " + 
				"        SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode,       " + 
				"        SUBSTRING('ZA', 1, 4) as countryCode,        " + 
				"        SUBSTRING(FORMAT(a.latitude_degrees, 0), 1, 3) as latitudeDegree,      " + 
				"        RIGHT(CONCAT('0', FORMAT(a.latitude_minutes,0)), 2) as latitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.latitude_seconds, 3)),6) as latitudeSeconds,     " + 
				"        SUBSTRING(FORMAT(a.longitude_degrees, 0), 1, 2) as longitudeDegree,     " + 
				"        RIGHT(CONCAT('0', FORMAT(a.longitude_minutes,0)), 2) as longitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.longitude_seconds, 3)),6) as longitudeSeconds,     " + 
				"        SUBSTRING(a.address_line_1, 1, 50) as providerPhysicalAddress1,        " + 
				"        SUBSTRING(a.address_line_2, 1, 50) as providerPhysicalAddress2,        " + 
				"        SUBSTRING(t.description, 1, 50) as providerPhysicalAddressTown,       " + 
				"        SUBSTRING(a.postcode, 1, 4) as providerPhysAddressPostcode,        " + 
				"        SUBSTRING(c.company_website, 1, 50) as providerWebAddress,      " + 
				"        SUBSTRING(c.levy_number, 1, 10) as sDLNo,   " + 
				"        lastCompanyUpdate.linux_timestamp as dateStamp   " + 
				"    from   " + 
				"        training_provider_application tpa       " + 
				"    inner join company c on c.id = tpa.company_id       " + 
				"    left join sic_code sc on sc.id = c.sic_code_id       " + 
				"    left join etqa e on tpa.etqa_id = e.id     " + 
				"    left join etqa eCompany on c.etqa_id = eCompany.id       " + 
				"    left join provider_type pt on pt.id = tpa.provider_type       " + 
				"    inner join address a on a.id = c.residential_address_id   " + 
				"    inner join towns t on t.id = a.town_id      " + 
				"    inner join address ap on ap.id = c.postal_address_id       " + 
				"    inner join municipality m on m.id = ap.municipality_id       " + 
				"    inner join province p on p.id = m.provinces_idprovinces       " + 
				"    left join provider_status ps on ps.id = tpa.provider_status_id      " + 
				"    left join provider_class pc on pc.id = tpa.provider_class      " + 
				"    left join (select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id      " + 
				"    inner join (  " + 
				"        select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"        from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"        inner join REVINFO rv on rv.REV = ch.REV  " + 
				"    ) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"    where      " + 
				"        tpa.approval_status in (0,34)     " + 
				"        and tpa.accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        and tpa.duplicate_application = false  " + 
				"        and tpa.company_id in (     " + 
				"            select   " + 
				"                companyId   " + 
				"            from (     " + 
				"                select      " + 
				"                    cl.company_id as companyId     " + 
				"                from      " + 
				"                    company_learners cl     " + 
				"                where     " + 
				"                    cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"                    and cl.intervention_type_id is not null      " + 
				"                    and cl.company_id is not null      " + 
				"                group by cl.company_id     " + 
				"                          " + 
				"                UNION ALL     " + 
				"                      " + 
				"                select     " + 
				"                    cl.company_id as companyId     " + 
				"                from      " + 
				"                    company_learners cl     " + 
				"                where     " + 
				"                    cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"                    and cl.intervention_type_id is not null      " + 
				"                    and cl.company_id is not null     " + 
				"                    and cl.test_centre_training_provider_application_id is not null     " + 
				"                group by cl.company_id         " + 
				"            ) companyIdSearch     " + 
				"            group by companyId     " + 
				"        )  " + 
				"    ) base     " + 
				"    inner join (     " + 
				"        select      " + 
				"            max(tpa.id) as provider_id     " + 
				"            , tpa.company_id     " + 
				"        from      " + 
				"            training_provider_application tpa     " + 
				"        where      " + 
				"            tpa.company_id is not null     " + 
				"            and tpa.approval_status in (0,34)     " + 
				"            and tpa.accreditation_application_type in (0,1,3,4,5,6)     " + 
				"            and tpa.duplicate_application = false     " + 
				"        group by tpa.company_id     " + 
				"  " + 
				"    ) filt on filt.provider_id = base.provider_id  " + 
				"      " + 
				"UNION ALL   " + 
				"  " + 
				"    select  " + 
				"        SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode,      " + 
				"        case     " + 
				"            when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"            ELSE SUBSTRING('599', 1, 10)      " + 
				"        end as etqaId,    " + 
				"        SUBSTRING(sc.code, 1, 10) as stdIndustryClassCode,    " + 
				"        case   " + 
				"            when c.levy_number in ('L230711980', 'L170710323', 'L080770524', 'L780726594', 'L600718649', 'L810714826') then SUBSTRING(c.trading_name, 1, 70)  " + 
				"            Else SUBSTRING(c.company_name, 1, 70)  " + 
				"        end as providerName,   " + 
				"        SUBSTRING('4', 1, 10) as providerTypeId,           " + 
				"        SUBSTRING(ap.address_line_1, 1, 50) as providerAddress1,       " + 
				"        SUBSTRING(ap.address_line_2, 1, 50) as providerAddress2,       " + 
				"        SUBSTRING(ap.address_line_3, 1, 50) as providerAddress3,       " + 
				"        SUBSTRING(ap.postcode, 1, 4) as providerPostalCode,       " + 
				"        SUBSTRING(c.tel_number, 1, 10) as providerPhoneNumber,       " + 
				"        SUBSTRING(c.fax_number, 1, 20) as providerFaxNumber,       " + 
				"        SUBSTRING(c.company_registration_number, 1, 20) as providerSarsNumber,       " + 
				"        SUBSTRING(' ', 1, 50) as providerContactName,       " + 
				"        SUBSTRING(' ', 1, 50) as providerContactEmailAddress,       " + 
				"        SUBSTRING(' ', 1, 20) as providerContactPhoneNumber,       " + 
				"        SUBSTRING(' ', 1, 20) as providerContactCellNumber,   " + 
				"        SUBSTRING(' ', 1, 20) as providerAccreditationNum,        " + 
				"        c.approval_date as providerAccreditStartDate,   " + 
				"        (SELECT STR_TO_DATE('01,6,2022','%d,%m,%Y')) as providerAccreditEndDate,  " + 
				"        SUBSTRING(' ', 1, 20) as etqaDecisionNumber,     " + 
				"        SUBSTRING('5', 1, 10) as providerClassId,     " + 
				"        SUBSTRING('501', 1, 10) as structureStatusId,         " + 
				"        SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode,       " + 
				"        SUBSTRING('ZA', 1, 4) as countryCode,        " + 
				"        SUBSTRING(FORMAT(a.latitude_degrees, 0), 1, 3) as latitudeDegree,      " + 
				"        RIGHT(CONCAT('0', FORMAT(a.latitude_minutes,0)), 2) as latitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.latitude_seconds, 3)),6) as latitudeSeconds,     " + 
				"        SUBSTRING(FORMAT(a.longitude_degrees, 0), 1, 2) as longitudeDegree,     " + 
				"        RIGHT(CONCAT('0', FORMAT(a.longitude_minutes,0)), 2) as longitudeMinutes,     " + 
				"        RIGHT(CONCAT('00', FORMAT(a.longitude_seconds, 3)),6) as longitudeSeconds,     " + 
				"        SUBSTRING(a.address_line_1, 1, 50) as providerPhysicalAddress1,        " + 
				"        SUBSTRING(a.address_line_2, 1, 50) as providerPhysicalAddress2,   " + 
				"        SUBSTRING(t.description, 1, 50) as providerPhysicalAddressTown,            " + 
				"        SUBSTRING(a.postcode, 1, 4) as providerPhysAddressPostcode,        " + 
				"        SUBSTRING(c.company_website, 1, 50) as providerWebAddress,      " + 
				"        SUBSTRING(c.levy_number, 1, 10) as sDLNo,   " + 
				"        lastCompanyUpdate.linux_timestamp as dateStamp    " + 
				"    from      " + 
				"        company c  " + 
				"    left join sic_code sc on sc.id = c.sic_code_id       " + 
				"    left join etqa eCompany on c.etqa_id = eCompany.id        " + 
				"    inner join address a on a.id = c.residential_address_id  " + 
				"    inner join towns t on t.id = a.town_id        " + 
				"    inner join address ap on ap.id = c.postal_address_id       " + 
				"    inner join municipality m on m.id = ap.municipality_id       " + 
				"    inner join province p on p.id = m.provinces_idprovinces  " + 
				"    inner join (  " + 
				"        select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"        from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"        inner join REVINFO rv on rv.REV = ch.REV  " + 
				"    ) lastCompanyUpdate on lastCompanyUpdate.id = c.id   " + 
				"    where   " + 
				"        c.id in (     " + 
				"            select      " + 
				"                company_id      " + 
				"            from   " + 
				"                company_learners      " + 
				"            where company_id = employer_id     " + 
				"            and learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"            and intervention_type_id is not null      " + 
				"            and company_id is not null     " + 
				"            and company_id not in (     " + 
				"                select      " + 
				"                    company_id      " + 
				"                from      " + 
				"                    training_provider_application      " + 
				"                where         " + 
				"                    company_id is not null     " + 
				"                and approval_status in ( 0, 34 )     " + 
				"                and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"                and duplicate_application = false     " + 
				"                group by company_id     " + 
				"            )	     " + 
				"        group by company_id     " + 
				"        )  " + 
				") mainSQL   " + 
				"where etqaId = '599'";
		return (List<NLRDFile21BeanProviderVersionTwo>) super.nativeSelectSqlList(sql, NLRDFile21BeanProviderVersionTwo.class);
	}
	
	public List<NLRDFile21BeanProviderVersionTwo> extractNLRDFile21BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile21BeanProviderVersionTwo>) super.nativeSelectSqlList(sql, NLRDFile21BeanProviderVersionTwo.class);
	}
	
	public int extractNLRDFile21Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_21 ( provider_code, "
                + " etqa_id, "
                + " std_industry_class_code, "
                + " provider_name, "
                + " provider_type_id, "
                + " provider_address_1, "
                + " provider_address_2, "
                + " provider_address_3, "
                + " provider_postal_code, "
                + " provider_phone_number, "
                + " provider_fax_number, "
                + " provider_sars_number, "
                + " provider_contact_name, "
                + " provider_contact_email_address, "
                + " provider_contact_phone_number, "
                + " provider_contact_cell_number, "
                + " provider_accreditation_num, "
                + " provider_accredit_start_date, "
                + " provider_accredit_end_date, "
                + " etqa_decision_number, "
                + " provider_etqa_id, "
                + " structure_status_id, "
                + " province_code, "
                + " country_code, "
                + " latitude_degree, "
                + " latitude_minutes, "
                + " latitude_seconds, "
                + " longitude_degree, "
                + " longitude_minutes, "
                + " longitude_seconds, "
                + " provider_physical_address_1, "
                + " provider_physical_address_2, "
                + " provider_physical_address_town, "
                + " provider_phys_address_post_code, "
                + " provider_web_address, "
                + " date_stamp ) "
                + " select CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode, "
				+ " e.nlrd_code as etqaId, "
				+ " '' as stdIndustryClassCode, "
				+ " c.trading_name as providerName, "
				+ " pt.nlrd_code as providerTypeId, "
				+ " ap.address_line_1 as providerAddress1, "
				+ " ap.address_line_2 as providerAddress2, "
				+ " ap.address_line_3 as providerAddress3, "
				+ " ap.postcode as providerPostalCode, "
				+ " c.tel_number as providerPhoneNumber, "
				+ " c.fax_number as providerFaxNumber, "
				+ " c.company_registration_number as providerSarsNumber, "
				+ " '' as providerContactName, "
				+ " '' as providerContactEmailAddress, "
				+ " '' as providerContactPhoneNumber, "
				+ " '' as providerContactCellNumber, "
				+ " tpa.`accreditation_number` as providerAccreditationNum, "
				+ " tpa.start_date as providerAccreditStartDate, "
				+ " tpa.expiriy_date as providerAccreditEndDate, "
				+ " rcmg.decision_number  as etqaDecisionNumber, "
				+ " '' as providerClassId, "
				+ " '' as structureStatusId, "
				+ " p.stats_prov_id	as provinceCode, "
				+ " 'ZA'			as countryCode,  "
				+ " ap.latitude_degrees	as latitudeDegree,  "
				+ " ap.latitude_minutes as latitudeMinutes, "
				+ " ap.latitude_seconds as latitudeSeconds,  "
				+ " ap.longitude_degrees as longitudeDegree,  "
				+ " ap.longitude_minutes as longitudeMinutes,  "
				+ " ap.longitude_seconds as longitudeSeconds, "
				+ " a.address_line_1 as providerPhysicalAddress1,  "
				+ " a.address_line_2 as providerPhysicalAddress2,  "
				+ " a.address_line_3 as providerPhysicalAddressTown, "
				+ " a.postcode as providerPhysAddressPostcode,  "
				+ " ''			as providerWebAddress,  "
				+ " CURDATE()			as dateStamp "
				+ " from training_provider_application tpa "
				+ " inner join company c on c.id = tpa.company_id  "
				+ " inner join etqa e on tpa.`etqa_id` = e.id "
				+ " inner join provider_type pt on pt.id = tpa.provider_type "
				+ " inner join address a on a.id = c.residential_address_id "
				+ " inner join address ap on ap.id = c.postal_address_id "
				+ " inner join municipality m on m.id = ap.municipality_id "
				+ " inner join province p on p.id = m.provinces_idprovinces "
				+ " inner join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id = tpa.review_committee_meeting_id ";
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile21> allNLRDFile21() throws Exception {
        return (List<NLRDFile21>)super.getList("select o from NLRDFile21 o");
    }
	
	public List<NLRDFile22Bean> extractNLRDFile22Bean() throws Exception {
		String sql = " select sq.qualificationid as qualificationCode, "
				+" sq.qualificationitle as qualificationName, "
				+" nl.nlrdCode as nQFLevelId, "
				+" s.nlrd_code as subdomainId, "
				+" ab.nlrd_code as abetBandId, "
				+" sq.qualregistrationstartdate as qualRegistrationStartDate, "
				+" sq.qualregistrationenddate as qualRegistrationEndDate, "
				+" sq.providercode	as providerCode, "
				+" sq.provideretqaid as providerEtqaId, "
				+" CURDATE()			   as dateStamp "
				+" from saqa_qualification sq "
				+" inner join nqf_levels nl on nl.id = sq.nqf_level_id "
				+" inner join abet_band ab on ab.description = sq.abetbanddescription "
				+" inner join subdomain s on s.description = sq.subfielddescription ";
		
		
		return (List<NLRDFile22Bean>)  super.nativeSelectSqlList(sql, NLRDFile22Bean.class);
	}
	
	public List<NLRDFile23Bean> extractNLRDFile23Bean() throws Exception {
		String sql = " select '' as courseCode,"
				+" '' as courseName,"
				+" nl.nlrdCode as nQFLevelId,"
				+" s.nlrd_code as subdomainId,"
				+" ab.nlrd_code as abetBandId,"
				+" '' as courseRegistrationStartDate,"
				+" '' as courseRegistrationEndDate,"
				+" sq.providercode	as providerCode,"
				+" sq.provideretqaid as providerEtqaId,"
				+" CURDATE()			   as dateStamp"
				+" from saqa_qualification sq"
				+" inner join nqf_levels nl on nl.id = sq.nqf_level_id"
				+" inner join abet_band ab on ab.description = sq.abetbanddescription"
				+" inner join subdomain s on s.description = sq.subfielddescription";
		
		
		return (List<NLRDFile23Bean>)  super.nativeSelectSqlList(sql, NLRDFile23Bean.class);
	}

	
	public List<NLRDFile24Bean> extractNLRDFile24Bean() throws Exception {
		String sql = " select "
				+" '' as learnershipId, "
				+" cq.`qualification_id` as qualificationId, "
				+" cu.`unit_standard_id` as unitStandardId, "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode,"
				+" e.nlrd_code as providerEtqaId, "
				+" tpa.`accreditation_number` as providerAccreditationNum, "
				+" '' as providerAccreditAssessorInd,"
				+" tpa.start_date as providerAccredStartDate, "
				+" tpa.expiriy_date as providerAccredEndDate, "
				+" rcmg.decision_number as etqaDecisionNumber,"
				+" tpa.accreditation_status as providerAccredStatusCode, "
				+" CURDATE() as dateStamp"
				+" from training_provider_application tpa"
				+" inner join company c on c.id = tpa.company_id "
				+" inner join etqa e on tpa.`etqa_id` = e.id"
				+" left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id = tpa.review_committee_meeting_id"
				+" left join company_qualifications cq on cq.company_id = tpa.company_id"
				+" left join company_unit_standard cu on cu.company_id = tpa.company_id";
		
		
		return (List<NLRDFile24Bean>)  super.nativeSelectSqlList(sql, NLRDFile24Bean.class);
	}
	
	public List<NLRDFile24BeanProviderAccreditationVersionTwo> extractNLRDFile24BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"distinct  " + 
				"	SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , case     " + 
				"        when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)     " + 
				"        when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)     " + 
				"        else ''     " + 
				"        end as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, tpa.start_date as providerAccredStartDate  " + 
				"	, tpa.expiriy_date as providerAccredEndDate  " + 
				"	, SUBSTRING(f.etqeDecisionNumber, 1, 20) as etqaDecisionNumber    " + 
				"	, case  " + 
				"		when tpa.approval_status = 0 then SUBSTRING('A', 1, 10)  " + 
				"		Else SUBSTRING('I', 1, 10)  " + 
				"		end as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id  " + 
				"left join (select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id      " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null   " + 
				"	and cl.qualification_id is not null  " + 
				"	and cl.skills_set_id is null  " + 
				"	and cl.skills_program_id is null  " + 
				"	and cl.learnership_id is null  " + 
				"	and cl.company_id in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)  " + 
				"	  " + 
				"UNION ALL   " + 
				"	  " + 
				"select   " + 
				"distinct  " + 
				"	SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case     " + 
				"		when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"		ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , '' as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, c.approval_date as providerAccredStartDate  " + 
				"	, (SELECT STR_TO_DATE('01,6,2022','%d,%m,%Y')) as providerAccredEndDate  " + 
				"	, '' as etqaDecisionNumber    " + 
				"	, SUBSTRING('A', 1, 10) as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null   " + 
				"	and cl.qualification_id is not null  " + 
				"	and cl.skills_set_id is null  " + 
				"	and cl.skills_program_id is null  " + 
				"	and cl.learnership_id is null  " + 
				"	and cl.company_id not in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)  " + 
				"UNION ALL  " + 
				"  " + 
				"select   " + 
				"distinct  " + 
				"	SUBSTRING(lship.setmis_code, 1, 10) as learnershipId   " + 
				"	, SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , case     " + 
				"        when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)     " + 
				"        when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)     " + 
				"        else ''     " + 
				"        end as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, tpa.start_date as providerAccredStartDate  " + 
				"	, tpa.expiriy_date as providerAccredEndDate  " + 
				"	, SUBSTRING(f.etqeDecisionNumber, 1, 20) as etqaDecisionNumber    " + 
				"	, case  " + 
				"		when tpa.approval_status = 0 then SUBSTRING('A', 1, 10)  " + 
				"		Else SUBSTRING('I', 1, 10)  " + 
				"		end as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join learnership lship on lship.id = cl.learnership_id     " + 
				"left join saqa_qualification sq on sq.id = lship.qualification_id    " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id  " + 
				"left join (select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id      " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.learnership_id is not null     " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"	and cl.intervention_type_id is not null      " + 
				"	and cl.company_id is not null  " + 
				"	and lship.setmis_code <> '8'  " + 
				"	and cl.company_id in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)  " + 
				"	  " + 
				"UNION ALL  " + 
				"  " + 
				"select   " + 
				"distinct  " + 
				"	SUBSTRING(lship.setmis_code, 1, 10) as learnershipId   " + 
				"	, SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case     " + 
				"		when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"		ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , '' as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, c.approval_date as providerAccredStartDate  " + 
				"	, (SELECT STR_TO_DATE('01,6,2022','%d,%m,%Y')) as providerAccredEndDate  " + 
				"	, '' as etqaDecisionNumber    " + 
				"	, SUBSTRING('A', 1, 10) as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join learnership lship on lship.id = cl.learnership_id     " + 
				"left join saqa_qualification sq on sq.id = lship.qualification_id    " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.learnership_id is not null     " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"	and cl.intervention_type_id is not null      " + 
				"	and cl.company_id is not null  " + 
				"	and lship.setmis_code <> '8'  " + 
				"	and cl.company_id not in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)  " + 
				"  " + 
				"UNION ALL   " + 
				"  " + 
				"select   " + 
				"distinct  " + 
				"	SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING('', 1, 10) as qualificationId  " + 
				"	, SUBSTRING(su.unitstandardid, 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , case     " + 
				"        when tpa.certificate_number is not null and tpa.certificate_number <> '' then SUBSTRING(tpa.certificate_number, 1, 20)     " + 
				"        when tpa.accreditation_number is not null and tpa.accreditation_number <> '' then SUBSTRING(tpa.accreditation_number, 1, 20)     " + 
				"        else ''     " + 
				"        end as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, tpa.start_date as providerAccredStartDate  " + 
				"	, tpa.expiriy_date as providerAccredEndDate  " + 
				"	, SUBSTRING(f.etqeDecisionNumber, 1, 20) as etqaDecisionNumber    " + 
				"	, case  " + 
				"		when tpa.approval_status = 0 then SUBSTRING('A', 1, 10)  " + 
				"		Else SUBSTRING('I', 1, 10)  " + 
				"		end as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join saqa_unitstandard su on su.id = cl.unit_standard_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id  " + 
				"left join (select MAX(rcmg.decision_number) as etqeDecisionNumber, rcmg.review_committee_meeting_id from Review_committee_meeting_agenda rcmg group by rcmg.review_committee_meeting_id) f on f.review_committee_meeting_id = tpa.review_committee_meeting_id      " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.unit_standard_id is not null  " + 
				"    and cl.qualification_id is null  " + 
				"    and cl.learnership_id is null  " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null  " + 
				"	and cl.company_id in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)  " + 
				"	  " + 
				"UNION ALL   " + 
				"	  " + 
				"select   " + 
				"distinct  " + 
				"	SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING('', 1, 10) as qualificationId  " + 
				"	, SUBSTRING(su.unitstandardid, 1, 10) as unitStandardId  " + 
				"	, SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"	, case     " + 
				"		when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)     " + 
				"		ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , '' as providerAccreditationNum  " + 
				"	, SUBSTRING('', 1, 1) as providerAccreditAssessorInd  " + 
				"	, c.approval_date as providerAccredStartDate  " + 
				"	, (SELECT STR_TO_DATE('01,6,2022','%d,%m,%Y')) as providerAccredEndDate  " + 
				"	, '' as etqaDecisionNumber    " + 
				"	, SUBSTRING('A', 1, 10) as providerAccredStatusCode  " + 
				"	, lastCompanyUpdate.linux_timestamp as dateStamp  " + 
				"from company_learners cl  " + 
				"left join saqa_unitstandard su on su.id = cl.unit_standard_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"inner join (  " + 
				"	select ch.id, FROM_UNIXTIME(SUBSTRING(rv.REVTSTMP,1,CHAR_LENGTH(rv.REVTSTMP) - 3)) as linux_timestamp  " + 
				"	from (select id, MAX(REV) as REV from company_hist group by id) ch  " + 
				"	inner join REVINFO rv on rv.REV = ch.REV  " + 
				"	) lastCompanyUpdate on lastCompanyUpdate.id = c.id  " + 
				"where   " + 
				"	cl.unit_standard_id is not null  " + 
				"    and cl.qualification_id is null  " + 
				"    and cl.learnership_id is null  " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null  " + 
				"	and cl.company_id not in (    " + 
				"		select      " + 
				"			company_id      " + 
				"		from      " + 
				"			training_provider_application      " + 
				"		where         " + 
				"			company_id is not null     " + 
				"        	and approval_status in ( 0, 34 )     " + 
				"        	and accreditation_application_type in (0,1,3,4,5,6)     " + 
				"        	and duplicate_application = false     " + 
				"    	group by company_id     " + 
				"	)";
		return (List<NLRDFile24BeanProviderAccreditationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile24BeanProviderAccreditationVersionTwo.class);
	}
	
	public List<NLRDFile24BeanProviderAccreditationVersionTwo> extractNLRDFile24BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile24BeanProviderAccreditationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile24BeanProviderAccreditationVersionTwo.class);
	}
	
	public int extractNLRDFile24Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_24 ( learnership_id, "
                + " qualification_id, "
                + " unit_standard_id, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " provider_accreditation_num, "
                + " provider_accredit_assessor_ind, "
                + " provider_accred_start_date, "
                + " provider_accred_end_date, "
                + " etqa_decision_number, "
                + " provider_accred_status_code, "
                + " date_stamp ) "
                +" select "
				+" '' as learnershipId, "
				+" cq.`qualification_id` as qualificationId, "
				+" cu.`unit_standard_id` as unitStandardId, "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode,"
				+" e.nlrd_code as providerEtqaId, "
				+" tpa.`accreditation_number` as providerAccreditationNum, "
				+" '' as providerAccreditAssessorInd,"
				+" tpa.start_date as providerAccredStartDate, "
				+" tpa.expiriy_date as providerAccredEndDate, "
				+" rcmg.decision_number as etqaDecisionNumber,"
				+" tpa.accreditation_status as providerAccredStatusCode, "
				+" CURDATE() as dateStamp"
				+" from training_provider_application tpa"
				+" inner join company c on c.id = tpa.company_id "
				+" inner join etqa e on tpa.`etqa_id` = e.id"
				+" left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id = tpa.review_committee_meeting_id"
				+" left join company_qualifications cq on cq.company_id = tpa.company_id"
				+" left join company_unit_standard cu on cu.company_id = tpa.company_id";
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile24> allNLRDFile24() throws Exception {
        return (List<NLRDFile24>)super.getList("select o from NLRDFile24 o");
    }
	
	public List<NLRDFile25Bean> extractNLRDFile25Bean() throws Exception {
		String sql = " select  "
				+" u.rsa_id_number as nationalId,  "
				+" u.passport_number as personAlternateId,  "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType,  "
				+" e.nlrd_code as equityCode, "
				+" n.nlrd_code as nationalityCode,  "
				+" l.nlrd_code as homeLanguageCode,  "
				+" g.nlrd_code as genderCode,  "
				+" IF(n.nlrd_code = 'SA','SA','U') as citizenResidentStatusCode, "
				+" et.nlrd_code as socioeconomicStatusCode,  "
				+" ds.nlrd_code as disabilityStatusCode,  "
				+" u.last_name as personLastName,  "
				+" u.first_name as personFirstName, "
				+" u.middle_name as personMiddleName,  "
				+" t.nlrd_code as personTitle,  "
				+" u.date_of_birth as personBirthDate,  "
				+" a.address_line_1 as personHomeAddress1, "
				+" a.address_line_2 as personHomeAddress2,  "
				+" a.address_line_3 as personHomeAddress3,  "
				+" ap.address_line_1 as personPostalAddress1, "
				+" ap.address_line_2 as personPostalAddress2,  "
				+" ap.address_line_3 as personPostalAddress3,  "
				+" a.postcode as personHomeAddrPostalCode, "
				+" ap.postcode as personPostalAddrPostCode,  "
				+" u.tel_number as personPhoneNumber,  "
				+" u.cell_number as personCellPhoneNumber, "
				+" u.fax_number as personFaxNumber,  "
				+" u.email as personEmailAddress,  "
				+" p.stats_prov_id as provinceCode,  "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode,		 "
				+" eqt.nlrd_code as providerEtqaId,  "
				+" '' as personPreviousLastname,  "
				+" '' as personPreviousAlternateId, "
				+" '' as personPreviousAlternativeIdType,  "
				+" '' as personPreviousProviderCode, "
				+" '' as personPreviousProviderEtqaId, "
				+" '6' as seeingRatingId,  "
				+" '6' as hearingRatingId, "
				+" '6' as communicatingRatingId,  "
				+" '6' as walkingRatingId,  "
				+" '6' as rememberingRatingId,  "
				+" '6' as selfcareRatingId, "
				+" CURDATE() as dateStamp "
				+" from users u "
				+" left join equity e on e.id = u.equity_id "
				+" left join nationality n on u.nationality_id = n.id "
				+" left join language l on u.home_language = l.id "
				+" left join gender g on u.gender_id = g.id "
				+" left join disability_status ds on ds.id = u.disabilityStatus "
				+" left join title t on u.title_id = t.id "
				+" left join address a on a.id = u.residential_address_id "
				+" left join address ap on ap.id = u.postal_address_id "
				+" left join municipality m on m.id = a.municipality_id "
				+" left join province p on p.id = m.provinces_idprovinces "
				+" left join employment_type et on et.employment_status = u.employment_status "
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id "
				+" left join company c on c.id = amc.company_id "
				+" left join etqa eqt on c.`etqa_id` = eqt.id " ;		
		return (List<NLRDFile25Bean>)  super.nativeSelectSqlList(sql, NLRDFile25Bean.class);
	}
	
	public List<NLRDFile25BeanPersonInformationVersionTwo> extractNLRDFile25BeanVersionTwo() throws Exception {
		String sql = "select  " + 
				"    case   " + 
				"        when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"        ELSE ''  " + 
				"    end as nationalId  " + 
				"    , case   " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"        ELSE ''  " + 
				"    end as personAlternateId  " + 
				"    , case   " + 
				"        when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE ''  " + 
				"    end as alternativeIdType  " + 
				"    , SUBSTRING(e.nlrd_code, 1, 10) as equityCode  " + 
				"    , SUBSTRING(n.nlrd_code, 1, 3) as nationalityCode  " + 
				"    , case  " + 
				"        when homeLang.nlrd_code is not null and homeLang.setmis_code <> '' then SUBSTRING(homeLang.nlrd_code, 1, 10)  " + 
				"        Else SUBSTRING('Eng', 1, 10)  " + 
				"        end as homeLanguageCode  " + 
				"    , SUBSTRING(g.nlrd_code,1,1) as genderCode  " + 
				"    , IF(n.setmis_code = 'SA', SUBSTRING('SA', 1, 10),SUBSTRING('U', 1, 10)) as citizenResidentStatusCode  " + 
				"    , case  " + 
				"        when cl.employment_status = '0' then SUBSTRING('01', 1, 10)  " + 
				"        when cl.employment_status = '1' then SUBSTRING('02', 1, 10)  " + 
				"        when cl.employment_status = '2' then SUBSTRING('02', 1, 10)  " + 
				"        Else SUBSTRING('U', 1, 10)  " + 
				"        end as socioeconomicStatusCode  " + 
				"    , case  " + 
				"        when counterUserDisability.counter < '1' then SUBSTRING('07', 1, 10)  " + 
				"        when counterUserDisability.counter = '1' and userDisabilityCode.qctoCode is not null and userDisabilityCode.qctoCode <> '' then SUBSTRING(userDisabilityCode.qctoCode, 1, 10)  " + 
				"        else SUBSTRING('09', 1, 10)  " + 
				"        end as disabilityStatusCode  " + 
				"    , SUBSTRING(u.last_name, 1, 45) as personLastName  " + 
				"    , SUBSTRING(u.first_name, 1, 26) as personFirstName  " + 
				"    , SUBSTRING(u.middle_name, 1, 50) as personMiddleName  " + 
				"    , SUBSTRING(t.description, 1, 10) as personTitle  " + 
				"    , u.date_of_birth as personBirthDate  " + 
				"      " + 
				"    , SUBSTRING(ap.address_line_1, 1, 50) as personHomeAddress1  " + 
				"    , SUBSTRING(ap.address_line_2, 1, 50) as personHomeAddress2  " + 
				"    , SUBSTRING(ap.address_line_3, 1, 50) as personHomeAddress3  " + 
				"    , SUBSTRING(a.address_line_1, 1, 50) as personPostalAddress1  " + 
				"    , SUBSTRING(a.address_line_2, 1, 50) as personPostalAddress2  " + 
				"    , SUBSTRING(a.address_line_3, 1, 50) as personPostalAddress3  " + 
				"      " + 
				"    , SUBSTRING(ap.postcode, 1, 4) as personHomeAddrPostalCode  " + 
				"    , SUBSTRING(a.postcode, 1, 4) as personPostalAddrPostCode  " + 
				"      " + 
				"    , SUBSTRING(u.tel_number, 1, 20) as personPhoneNumber  " + 
				"    , SUBSTRING(u.cell_number, 1, 20) as personCellPhoneNumber  " + 
				"    , SUBSTRING(u.fax_number, 1, 20) as personFaxNumber  " + 
				"    , SUBSTRING(u.email, 1, 50) as personEmailAddress  " + 
				"      " + 
				"    , SUBSTRING(p.stats_prov_id, 1, 2) as provinceCode  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode    " + 
				"      " + 
				"    , case  " + 
				"        when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
				"        when et.code is not null and et.code  <> '' then SUBSTRING(et.code, 1, 10)  " + 
				"        ELSE SUBSTRING('599', 1, 10)  " + 
				"        end as providerEtqaId  " + 
				"          " + 
				"    , SUBSTRING('', 1, 45) as personPreviousLastname  " + 
				"    , SUBSTRING('', 1, 20) as personPreviousAlternateId  " + 
				"    , SUBSTRING('', 1, 3) as personPreviousAlternativeIdType  " + 
				"    , SUBSTRING('', 1, 20) as personPreviousProviderCode  " + 
				"    , SUBSTRING('', 1, 10) as personPreviousProviderEtqaId  " + 
				"  " + 
				"    , SUBSTRING('', 1, 2) as seeingRatingId  " + 
				"    , SUBSTRING('', 1, 2) as hearingRatingId  " + 
				"    , SUBSTRING('', 1, 2) as communicatingRatingId  " + 
				"    , SUBSTRING('', 1, 2) as walkingRatingId  " + 
				"    , SUBSTRING('', 1, 2) as rememberingRatingId  " + 
				"    , SUBSTRING('', 1, 2) as selfcareRatingId  " + 
				"    , lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"    company_learners cl  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join equity e on e.id = u.equity_id  " + 
				"left join nationality n on u.nationality_id = n.id  " + 
				"left join language l on u.home_language = l.id  " + 
				"left join gender g on u.gender_id = g.id  " + 
				"left join address a on a.id = u.postal_address_id  " + 
				"left join address ap on ap.id = u.residential_address_id  " + 
				"left join title t on u.title_id = t.id  " + 
				"left join municipality m on m.id = a.municipality_id  " + 
				"left join province p on p.id = m.provinces_idprovinces  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join etqa et on tpa.etqa_id = et.id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id  " + 
				"left join etqa on etqa.id = tpa.etqa_id  " + 
				"left join users_language ul on ul.id = (select MIN(id) from users_language where user_id = u.id and home_language = true)  " + 
				"left join language homeLang on homeLang.id = ul.language_id  " + 
				"inner join (  " + 
				"    select   " + 
				"        clh.id as id  " + 
				"        , FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"    from   " + 
				"        company_learners_hist clh   " + 
				"    inner join REVINFO rv on rv.REV = clh.REV  " + 
				"    group by   " + 
				"        clh.id  " + 
				"    ) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
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
				"    ) counterUserDisability on counterUserDisability.userId = u.id  " + 
				"left join (	  " + 
				"    select   " + 
				"        ud.user_id as userId  " + 
				"        , min(ds.qcto_code) as qctoCode  " + 
				"    from   " + 
				"        users_disability ud  " + 
				"    left join disability_status ds on ds.id  = ud.disabilityStatus  " + 
				"    where   " + 
				"        ud.disabilityStatus is not null  " + 
				"    group by   " + 
				"        ud.user_id  " + 
				"    ) userDisabilityCode on userDisabilityCode.userId = u.id	  " + 
				"where  " + 
				"    u.id not in (  " + 
				"        select   " + 
				"            amp.users_id  " + 
				"        from   " + 
				"            assessor_moderator_application amp  " + 
				"        where  " + 
				"            amp.certificate_number is not null  " + 
				"            and amp.application_type in (0,4)  " + 
				"            and amp.status in (0,34)  " + 
				"        group by   " + 
				"            amp.users_id  " + 
				"    )  " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"    and cl.intervention_type_id is not null   " + 
				"    and cl.company_id is not null  " + 
				"    and u.id in (  " + 
				"      " + 
				"        select   " + 
				"            userId  " + 
				"        from (  " + 
				"          " + 
				"            select   " + 
				"                cl.user_id as userId  " + 
				"            from   " + 
				"                company_learners cl  " + 
				"            where   " + 
				"                cl.skills_set_id is null  " + 
				"                and cl.skills_program_id is null  " + 
				"                and cl.learnership_id is null  " + 
				"                and cl.qualification_id is not null  " + 
				"                and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"                and cl.intervention_type_id is not null   " + 
				"                and cl.company_id is not null   " + 
				"                  " + 
				"            UNION ALL   " + 
				"              " + 
				"            select   " + 
				"                cl.user_id as userId  " + 
				"            from   " + 
				"                company_learners cl  " + 
				"            left join learnership lship on lship.id = cl.learnership_id   " + 
				"            where      " + 
				"                cl.learnership_id is not null     " + 
				"                and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"                and cl.intervention_type_id is not null      " + 
				"                and cl.company_id is not null  " + 
				"                and lship.setmis_code <> '8'  " + 
				"                  " + 
				"            UNION ALL   " + 
				"              " + 
				"            select   " + 
				"                cl.user_id as userId  " + 
				"            from   " + 
				"                company_learners cl  " + 
				"            where   " + 
				"                cl.unit_standard_id is not null  " + 
				"                and cl.qualification_id is null  " + 
				"                and cl.learnership_id is null  " + 
				"                and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"                and cl.intervention_type_id is not null   " + 
				"                and cl.company_id is not null  " + 
				"          " + 
				"          " + 
				"        ) distinctUserId  " + 
				"        group by userId  " + 
				"    )" ;		
		return (List<NLRDFile25BeanPersonInformationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile25BeanPersonInformationVersionTwo.class);
	}
	
	public List<NLRDFile25BeanPersonInformationVersionTwo> extractNLRDFile25BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile25BeanPersonInformationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile25BeanPersonInformationVersionTwo.class);
	}
	
	public int extractNLRDFile25Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_25 ( national_id, "
                + " person_alternate_id, "
                + " alternative_id_type, "
                + " equity_code, "
                + " nationality_code, "
                + " home_language_code, "
                + " gender_code, "
                + " citizen_resident_status_code, "
                + " socioeconomic_status_code, "
                + " disability_status_code, "
                + " person_last_name, "
                + " person_first_name, "
                + " person_middle_name, "
                + " person_title, "
                + " person_birth_date, "
                + " person_home_address_1, "
                + " person_home_address_2, "
                + " person_home_address_3, "
                + " person_postal_address_1, "
                + " person_postal_address_2, "
                + " person_postal_address_3, "
                + " person_home_addr_postal_code, "
                + " person_postal_addr_post_code, "
                + " person_phone_number, "
                + " person_cell_phone_number, "
                + " person_fax_number, "
                + " person_email_address, "
                + " province_code, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " person_previous_last_name, "
                + " person_previous_alternate_id, "
                + " person_previous_alternative_id_type, "
                + " person_previous_provider_code, "
                + " person_previous_provider_etqa_id	, "
                + " seeing_rating_id, "
                + " hearing_rating_id, "
                + " communicating_rating_id, "
                + " walking_rating_id, "
                + " remembering_rating_id, "
                + " selfcare_rating_id, "
                + " date_stamp ) "
                +  " select  "
				+" u.rsa_id_number as nationalId,  "
				+" u.passport_number as personAlternateId,  "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType,  "
				+" e.nlrd_code as equityCode, "
				+" n.nlrd_code as nationalityCode,  "
				+" l.nlrd_code as homeLanguageCode,  "
				+" g.nlrd_code as genderCode,  "
				+" IF(n.nlrd_code = 'SA','SA','U') as citizenResidentStatusCode, "
				+" et.nlrd_code as socioeconomicStatusCode,  "
				+" ds.nlrd_code as disabilityStatusCode,  "
				+" u.last_name as personLastName,  "
				+" u.first_name as personFirstName, "
				+" u.middle_name as personMiddleName,  "
				+" t.nlrd_code as personTitle,  "
				+" u.date_of_birth as personBirthDate,  "
				+" a.address_line_1 as personHomeAddress1, "
				+" a.address_line_2 as personHomeAddress2,  "
				+" a.address_line_3 as personHomeAddress3,  "
				+" ap.address_line_1 as personPostalAddress1, "
				+" ap.address_line_2 as personPostalAddress2,  "
				+" ap.address_line_3 as personPostalAddress3,  "
				+" a.postcode as personHomeAddrPostalCode, "
				+" ap.postcode as personPostalAddrPostCode,  "
				+" u.tel_number as personPhoneNumber,  "
				+" u.cell_number as personCellPhoneNumber, "
				+" u.fax_number as personFaxNumber,  "
				+" u.email as personEmailAddress,  "
				+" p.stats_prov_id as provinceCode,  "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode,		 "
				+" eqt.nlrd_code as providerEtqaId,  "
				+" '' as personPreviousLastname,  "
				+" '' as personPreviousAlternateId, "
				+" '' as personPreviousAlternativeIdType,  "
				+" '' as personPreviousProviderCode, "
				+" '' as personPreviousProviderEtqaId, "
				+" '6' as seeingRatingId,  "
				+" '6' as hearingRatingId, "
				+" '6' as communicatingRatingId,  "
				+" '6' as walkingRatingId,  "
				+" '6' as rememberingRatingId,  "
				+" '6' as selfcareRatingId, "
				+" CURDATE() as dateStamp "
				+" from users u "
				+" left join equity e on e.id = u.equity_id "
				+" left join nationality n on u.nationality_id = n.id "
				+" left join language l on u.home_language = l.id "
				+" left join gender g on u.gender_id = g.id "
				+" left join disability_status ds on ds.id = u.disabilityStatus "
				+" left join title t on u.title_id = t.id "
				+" left join address a on a.id = u.residential_address_id "
				+" left join address ap on ap.id = u.postal_address_id "
				+" left join municipality m on m.id = a.municipality_id "
				+" left join province p on p.id = m.provinces_idprovinces "
				+" left join employment_type et on et.employment_status = u.employment_status "
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id "
				+" left join company c on c.id = amc.company_id "
				+" left join etqa eqt on c.`etqa_id` = eqt.id " ;
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile25> allNLRDFile25() throws Exception {
        return (List<NLRDFile25>)super.getList("select o from NLRDFile25 o");
    }
	
	public List<NLRDFile26Bean> extractNLRDFile26Bean() throws Exception {
		String sql = "select u.rsa_id_number as nationalId,"
				+ " u.passport_number as personAlternateId, "
				+ " IF(u.passport_number is not NULL,533,527) as alternativeIdType,"
				+ " '1' as designationId,"
				+ " amp.certificate_number as designationRegistrationNumber,"
				+ " eqt.nlrd_code as designationETQAId,"
				+ " amp.start_date as designationStartDate,"
				+ " amp.end_date as designationEndDate,"
				+ " IF(amp.status = 0, '510' ,"
				+ " (IF(amp.status = 1, '515' ,"
				+ " (IF(amp.status = 2, '506' ,"
				+ " (IF(amp.status = 3, '506' ,"
				+ " (IF(amp.status = 4, '506' ,"
				+ " (IF(amp.status = 5, '501' , '501'"
				+ " ))  ))  ))  ))  ))  ) as structureStatusId, "
				+ " rcmg.decision_number as etqaDecisionNumber, "
				+ " CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode, "
				+ " eqt.nlrd_code as providerETQAID, "
				+ " CURDATE() as dateStamp"
				+ " from users u"
				+ " inner join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+ " inner join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+ " inner join company c on c.id = amc.company_id"
				+ " left join etqa eqt on c.`etqa_id` = eqt.id"
				+ " left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id"
				+ " left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id"
				+ " left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id";

		return (List<NLRDFile26Bean>) super.nativeSelectSqlList(sql, NLRDFile26Bean.class);
	}
	
	public List<NLRDFile26BeanPersonDesignationVersionTwo> extractNLRDFile26BeanVersionTwo() throws Exception {
		String sql = "select   " + 
				"	nationalId  " + 
				"	, personAlternateId  " + 
				"	, alternativeIdType  " + 
				"	, designationId  " + 
				"	, designationRegistrationNumber  " + 
				"	, designationETQAId  " + 
				"	, designationStartDate  " + 
				"	, designationEndDate  " + 
				"	, structureStatusId  " + 
				"	, etqaDecisionNumber  " + 
				"	, providerCode  " + 
				"	, providerETQAID  " + 
				"	, dateStamp  " + 
				"from (  " + 
				"select  " + 
				"	case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING(' ' , 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING(' ' , 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE ''  " + 
				"        end as alternativeIdType  " + 
				"    , case   " + 
				"    	when amp.application_type is not null and amp.application_type in (0,4) then SUBSTRING('1', 1, 5)  " + 
				"    	when amp.application_type is not null and amp.application_type in (1,6) then SUBSTRING('0', 1, 5)  " + 
				"    	ELSE ''  " + 
				"    	end as designationId  " + 
				"	, SUBSTRING(amp.certificate_number, 1, 20) as designationRegistrationNumber  " + 
				"	, case  " + 
				"    	when eqtApp.code is not null and eqtApp.code  <> '' then SUBSTRING(eqtApp.code , 1, 10)  " + 
				"     	ELSE '599'  " + 
				"    	end as designationETQAId  " + 
				"	, amp.start_date as designationStartDate  " + 
				"	, amp.end_date as designationEndDate  " + 
				"	, case   " + 
				"		when amp.status in (34) then SUBSTRING('503', 1, 10)  " + 
				"		when amp.application_type is not null and amp.application_type in (0,1) and amp.status in (0) then SUBSTRING('501', 1, 10)  " + 
				"		when amp.application_type is not null and amp.application_type in (4,6) and amp.status in (0) then SUBSTRING('505', 1, 10)		  " + 
				"		Else SUBSTRING('506', 1, 10)  " + 
				"		end as structureStatusId  " + 
				"	, Case  " + 
				"		when rcmg.decision_number is null or rcmg.decision_number = '' then SUBSTRING(' ', 1, 20)  " + 
				"		Else SUBSTRING(CONCAT(REPLACE(REPLACE(rcmg.decision_number, 'ETQA', '') , '/', ''), amp.id), 1, 20)  " + 
				"	 	end as etqaDecisionNumber  " + 
				"	, SUBSTRING(' ', 1, 20) as providerCode    " + 
				"    , SUBSTRING(' ' , 1, 10) as providerETQAID  " + 
				"	, CURDATE() as dateStamp  " + 
				"from   " + 
				"	assessor_moderator_application amp  " + 
				"inner join users u on amp.users_id = u.id  " + 
				"left join company_users cu on cu.user_id = u.id and assessor_mod_type is not NULL  " + 
				"left join company c on cu.company_id = c.id  " + 
				"left join training_provider_application tpa on c.id = tpa.company_id  " + 
				"left join etqa eqt on c.etqa_id = eqt.id  " + 
				"left join etqa eqtApp on amp.etqa_id = eqtApp.id  " + 
				"left join Review_committee_meeting_agenda rcmg on rcmg.id  = amp.review_committee_meeting_agenda_id  " + 
				"left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id  " + 
				"left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id  " + 
				"where   " + 
				"	amp.certificate_number is not null  " + 
				"	and amp.application_type in (0,4)  " + 
				"	and amp.status in (0,34)  " + 
				"	and u.rsa_id_number = '9003055928084'  " + 
				") base  " + 
				"group by   " + 
				"	nationalId  " + 
				"	, personAlternateId  " + 
				"	, alternativeIdType  " + 
				"	, designationId  " + 
				"	, designationRegistrationNumber  " + 
				"	, designationETQAId  " + 
				"	, designationStartDate  " + 
				"	, designationEndDate  " + 
				"	, structureStatusId  " + 
				"	, etqaDecisionNumber  " + 
				"	, providerCode  " + 
				"	, providerETQAID  " + 
				"	, dateStamp";
		return (List<NLRDFile26BeanPersonDesignationVersionTwo>) super.nativeSelectSqlList(sql, NLRDFile26BeanPersonDesignationVersionTwo.class);
	}
	
	public List<NLRDFile26BeanPersonDesignationVersionTwo> extractNLRDFile26BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile26BeanPersonDesignationVersionTwo>) super.nativeSelectSqlList(sql, NLRDFile26BeanPersonDesignationVersionTwo.class);
	}
	
	public int extractNLRDFile26Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_26 ( national_id, "
                + " person_alternate_id, "
                + " alternative_id_type, "
                + " designation_id, "
                + " designation_registration_number, "
                + " designation_etqa_id, "
                + " designation_start_date, "
                + " designation_end_date, "
                + " structure_status_id, "
                + " etqa_decision_number, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " date_stamp ) "
                + "select u.rsa_id_number as nationalId,"
				+ " u.passport_number as personAlternateId, "
				+ " IF(u.passport_number is not NULL,533,527) as alternativeIdType,"
				+ " '1' as designationId,"
				+ " amp.certificate_number as designationRegistrationNumber,"
				+ " eqt.nlrd_code as designationETQAId,"
				+ " amp.start_date as designationStartDate,"
				+ " amp.end_date as designationEndDate,"
				+ " IF(amp.status = 0, '510' ,"
				+ " (IF(amp.status = 1, '515' ,"
				+ " (IF(amp.status = 2, '506' ,"
				+ " (IF(amp.status = 3, '506' ,"
				+ " (IF(amp.status = 4, '506' ,"
				+ " (IF(amp.status = 5, '501' , '501'"
				+ " ))  ))  ))  ))  ))  ) as structureStatusId, "
				+ " rcmg.decision_number as etqaDecisionNumber, "
				+ " CONCAT('MerSETA-' , LPAD(c.id, 8, '0')) as providerCode, "
				+ " eqt.nlrd_code as providerETQAID, "
				+ " CURDATE() as dateStamp"
				+ " from users u"
				+ " inner join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+ " inner join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+ " inner join company c on c.id = amc.company_id"
				+ " left join etqa eqt on c.`etqa_id` = eqt.id"
				+ " left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id"
				+ " left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id"
				+ " left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id";
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile26> allNLRDFile26() throws Exception {
        return (List<NLRDFile26>)super.getList("select o from NLRDFile26 o");
    }
	
	public List<NLRDFile27Bean> extractNLRDFile27Bean() throws Exception {
		String sql = " select "
				+" '' as learnershipId, "
				+" sq.qualificationid as qualificationId, "
				+" su.unitstandardid as unitStandardId, "
				+" '1' as designationId,"
				+" amp.certificate_number as designationRegistrationNumber, "
				+" eqt.nlrd_code as designationETQAId, "
				+" amp.start_date as nQFDesignationStartDate,"
				+" amp.end_date as nQFDesignationEndDate, "
				+" rcmg.decision_number  as etqaDecisionNumber, "
				+" 'A' as nQFDesigStatusCode,  "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id"
				+" left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id"
				+" left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id"
				+" inner join saqa_qualification sq on sq.id = uq.qualification_id"
				+" inner join saqa_unitstandard su on su.id = uus.unit_standard_id";
		return (List<NLRDFile27Bean>)  super.nativeSelectSqlList(sql, NLRDFile27Bean.class);
	}
	
	public List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> extractNLRDFile27BeanVersionTwo() throws Exception {
		String sql = "select 	  " + 
				"	SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, case   " + 
				"    	when amp.application_type is not null and amp.application_type in (0,4) then SUBSTRING('1', 1, 5)  " + 
				"    	when amp.application_type is not null and amp.application_type in (1,6) then SUBSTRING('0', 1, 5)  " + 
				"    	ELSE ''  " + 
				"    	end as designationId  " + 
				"    , SUBSTRING(amp.certificate_number, 1, 20) as designationRegistrationNumber  " + 
				"    , case  " + 
				"    	when eqtApp.code is not null and eqtApp.code  <> '' then SUBSTRING(eqtApp.code , 1, 10)  " + 
				"     	ELSE '599'  " + 
				"    	end as designationETQAId  " + 
				"	, amp.start_date as nQFDesignationStartDate  " + 
				"	, amp.end_date as nQFDesignationEndDate  " + 
				"	, case  " + 
				"		when rcmg.decision_number is null or rcmg.decision_number = '' then ''  " + 
				"		Else SUBSTRING(CONCAT(REPLACE(REPLACE(rcmg.decision_number, 'ETQA', '') , '/', ''), amp.id), 1, 20)  " + 
				"	 	end as etqaDecisionNumber  " + 
				"	, case   " + 
				"		when amp.status in (34) then SUBSTRING('I', 1, 10)  " + 
				"		when amp.status in (0) then SUBSTRING('A', 1, 10)  " + 
				"	 	end as nQFDesigStatusCode  " + 
				"	, CURDATE() as dateStamp   " + 
				"from   " + 
				"	user_qualifications uq  " + 
				"left join assessor_moderator_application amp on amp.id = uq.for_assessor_moderator_application_id  " + 
				"inner join users u on amp.users_id = u.id  " + 
				"left join saqa_qualification sq on sq.id = uq.qualification_id   " + 
				"left join etqa eqtApp on amp.etqa_id = eqtApp.id  " + 
				"left join Review_committee_meeting_agenda rcmg on rcmg.id  = amp.review_committee_meeting_agenda_id  " + 
				"where  " + 
				"	uq.accept = true  " + 
				"	and amp.certificate_number is not null  " + 
				"	and amp.application_type in (0,4)  " + 
				"	and amp.status in (0,34)  " + 
				"	and u.rsa_id_number = '9003055928084'  " + 
				"	  " + 
				"UNION ALL   " + 
				"  " + 
				"select 	  " + 
				"	SUBSTRING('', 1, 10) as qualificationId  " + 
				"	, SUBSTRING('', 1, 10) as learnershipId  " + 
				"	, SUBSTRING(su.unitstandardid, 1, 10) as unitStandardId  " + 
				"	, case   " + 
				"    	when amp.application_type is not null and amp.application_type in (0,4) then SUBSTRING('1', 1, 5)  " + 
				"    	when amp.application_type is not null and amp.application_type in (1,6) then SUBSTRING('0', 1, 5)  " + 
				"    	ELSE ''  " + 
				"    	end as designationId  " + 
				"    , SUBSTRING(amp.certificate_number, 1, 20) as designationRegistrationNumber  " + 
				"    , case  " + 
				"    	when eqtApp.code is not null and eqtApp.code  <> '' then SUBSTRING(eqtApp.code , 1, 10)  " + 
				"     	ELSE '599'  " + 
				"    	end as designationETQAId  " + 
				"	, amp.start_date as nQFDesignationStartDate  " + 
				"	, amp.end_date as nQFDesignationEndDate  " + 
				"	, case  " + 
				"		when rcmg.decision_number is null or rcmg.decision_number = '' then ''  " + 
				"		Else SUBSTRING(CONCAT(REPLACE(REPLACE(rcmg.decision_number, 'ETQA', '') , '/', ''), amp.id), 1, 20)  " + 
				"	 	end as etqaDecisionNumber  " + 
				"	, case   " + 
				"		when amp.status in (34) then SUBSTRING('I', 1, 10)  " + 
				"		when amp.status in (0) then SUBSTRING('A', 1, 10)  " + 
				"	 	end as nQFDesigStatusCode  " + 
				"	, CURDATE() as dateStamp   " + 
				"from   " + 
				"	user_unit_standard uus  " + 
				"left join assessor_moderator_application amp on amp.id = uus.for_assessor_moderator_application_id  " + 
				"inner join users u on amp.users_id = u.id  " + 
				"left join saqa_unitstandard su on su.id = uus.unit_standard_id  " + 
				"left join etqa eqtApp on amp.etqa_id = eqtApp.id  " + 
				"left join Review_committee_meeting_agenda rcmg on rcmg.id  = amp.review_committee_meeting_agenda_id  " + 
				"where  " + 
				"	uus.accept = true  " + 
				"	and amp.certificate_number is not null  " + 
				"	and amp.application_type in (0,4)  " + 
				"	and amp.status in (0,34)  " + 
				"	and u.rsa_id_number = '9003055928084'  " + 
				"	  " + 
				"UNION ALL  " + 
				"  " + 
				"select 	  " + 
				"	SUBSTRING(sq.qualificationid_string, 1, 10) as qualificationId  " + 
				"	, SUBSTRING(l.setmis_code, 1, 10) as learnershipId  " + 
				"	, SUBSTRING('', 1, 10) as unitStandardId  " + 
				"	, case   " + 
				"    	when amp.application_type is not null and amp.application_type in (0,4) then SUBSTRING('1', 1, 5)  " + 
				"    	when amp.application_type is not null and amp.application_type in (1,6) then SUBSTRING('0', 1, 5)  " + 
				"    	ELSE ''  " + 
				"    	end as designationId  " + 
				"    , SUBSTRING(amp.certificate_number, 1, 20) as designationRegistrationNumber  " + 
				"    , case  " + 
				"    	when eqtApp.code is not null and eqtApp.code  <> '' then SUBSTRING(eqtApp.code , 1, 10)  " + 
				"     	ELSE '599'  " + 
				"    	end as designationETQAId  " + 
				"	, amp.start_date as nQFDesignationStartDate  " + 
				"	, amp.end_date as nQFDesignationEndDate  " + 
				"	, case  " + 
				"		when rcmg.decision_number is null or rcmg.decision_number = '' then ''  " + 
				"		Else SUBSTRING(CONCAT(REPLACE(REPLACE(rcmg.decision_number, 'ETQA', '') , '/', ''), amp.id), 1, 20)  " + 
				"	 	end as etqaDecisionNumber  " + 
				"	, case   " + 
				"		when amp.status in (34) then SUBSTRING('I', 1, 10)  " + 
				"		when amp.status in (0) then SUBSTRING('A', 1, 10)  " + 
				"	 	end as nQFDesigStatusCode  " + 
				"	, CURDATE() as dateStamp   " + 
				"from   " + 
				"	user_qualifications uq  " + 
				"left join assessor_moderator_application amp on amp.id = uq.for_assessor_moderator_application_id  " + 
				"inner join users u on amp.users_id = u.id  " + 
				"inner join learnership l on l.qualification_id = uq.qualification_id and l.setmis_code <> '8'  " + 
				"left join saqa_qualification sq on sq.id = uq.qualification_id   " + 
				"left join etqa eqtApp on amp.etqa_id = eqtApp.id  " + 
				"left join Review_committee_meeting_agenda rcmg on rcmg.id  = amp.review_committee_meeting_agenda_id  " + 
				"where  " + 
				"	uq.accept = true  " + 
				"	and amp.certificate_number is not null  " + 
				"	and amp.application_type in (0,4)  " + 
				"	and amp.status in (0,34)  " + 
				"	and u.rsa_id_number = '9003055928084'";
		return (List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile27BeanNQFDesignationRegistrationVersionTwo.class);
	}
	
	public List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo> extractNLRDFile27BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile27BeanNQFDesignationRegistrationVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile27BeanNQFDesignationRegistrationVersionTwo.class);
	}
	
	public int extractNLRDFile27Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_27 ( "
        		+" learnership_id, "
        		+" qualification_id, "
        		+" unit_standard_id, "
        		+" designation_id, "
        		+" designation_registration_number, "
        		+" designation_etqa_id, "
        		+" nqf_designation_start_date, "
        		+" nqf_designation_end_date, "
        		+" etqa_decision_number, "
        		+" nqf_desig_status_code, "
        		+" date_stamp ) "
        		+ " select "
				+" '' as learnershipId, "
				+" sq.qualificationid as qualificationId, "
				+" su.unitstandardid as unitStandardId, "
				+" '1' as designationId,"
				+" amp.certificate_number as designationRegistrationNumber, "
				+" eqt.nlrd_code as designationETQAId, "
				+" amp.start_date as nQFDesignationStartDate,"
				+" amp.end_date as nQFDesignationEndDate, "
				+" rcmg.decision_number  as etqaDecisionNumber, "
				+" 'A' as nQFDesigStatusCode,  "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id"
				+" left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id"
				+" left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id"
				+" inner join saqa_qualification sq on sq.id = uq.qualification_id"
				+" inner join saqa_unitstandard su on su.id = uus.unit_standard_id";
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile27> allNLRDFile27() throws Exception {
        return (List<NLRDFile27>)super.getList("select o from NLRDFile27 o");
    }
	
	public int extractNLRDFile27Validation() throws Exception {
		String sql =  " INSERT INTO nlrd_file_27 ( learnership_id, "
                + " qualification_id, "
                + " unit_standard_id, "
                + " designation_id, "
                + " designation_registration_number, "
                + " designation_etqa_id, "
                + " nqf_designation_start_date, "
                + " nqf_designation_end_date, "
                + " etqa_decision_number, "
                + " nqf_desig_status_code, "
                + " date_stamp ) "
                + " select "
				+" '' as learnershipId, "
				+" sq.qualificationid as qualificationId, "
				+" su.unitstandardid as unitStandardId, "
				+" '1' as designationId,"
				+" amp.certificate_number as designationRegistrationNumber, "
				+" eqt.nlrd_code as designationETQAId, "
				+" amp.start_date as nQFDesignationStartDate,"
				+" amp.end_date as nQFDesignationEndDate, "
				+" rcmg.decision_number  as etqaDecisionNumber, "
				+" 'A' as nQFDesigStatusCode,  "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id"
				+" left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id"
				+" left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id"
				+" inner join saqa_qualification sq on sq.id = uq.qualification_id"
				+" inner join saqa_unitstandard su on su.id = uus.unit_standard_id";
		
		return (int) super.nativeSQLInsert(sql);
	}
	
	public List<NLRDFile28Bean> extractNLRDFile28Bean() throws Exception {
		String sql = " select "
				+" u.rsa_id_number as nationalId, "
				+" u.passport_number as personAlternateId, "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType, "
				+" cl.learnership_code as learnershipId,"
				+" IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId, "
				+" amp.certificate_number  as assessorRegistrationNumber, "
				+" cl.completion_date as learnerAchievementDate,"
				+" cl.commencement_date as learnerEnrolledDate, "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+" eqt.nlrd_code  as providerEtqaId, "
				+" eqt.nlrd_code as assessorEtqaId,"
				+" cl.certificate_date as certificationDate, "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join company_learners cl on cl.user_id = u.id"
				+" left join intervention_type it on it.id = cl.intervention_type_id"
				+" where it.intervention_type_enum = 1";
		return (List<NLRDFile28Bean>)  super.nativeSelectSqlList(sql, NLRDFile28Bean.class);
	}
	
	public List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> extractNLRDFile28BeanVersionTwo() throws Exception {
		String sql = "select      " + 
				"	case      " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)     " + 
				"       ELSE SUBSTRING('', 1, 15)     " + 
				"       end as nationalId     " + 
				"	, case      " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)     " + 
				"       ELSE SUBSTRING('', 1, 20)     " + 
				"       end as personAlternateId     " + 
				"	, case      " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)     " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)     " + 
				"        ELSE SUBSTRING('', 1, 3)     " + 
				"        end as alternativeIdType     " + 
				"    , SUBSTRING(lship.setmis_code, 1, 10) as learnershipId      " + 
				"    , case      " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)     " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)     " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)     " + 
				"    	Else SUBSTRING('', 1, 3)     " + 
				"    	end as learnerAchievementStatusId	     " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)     " + 
				"    	Else ''     " + 
				"    	end as assessorRegistrationNumber  " + 
				"    , case  " + 
				"    	when cl.learner_status in (6,10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as learnerAchievementDate   " + 
				"    , case      " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date     " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date     " + 
				"		Else null     " + 
				"		end as learnerEnrolledDate     " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode     " + 
				"    , case     " + 
				"    	when eCompany.code is not null and eCompany.code  <> '' then SUBSTRING(eCompany.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)     " + 
				"     	ELSE SUBSTRING('599', 1, 10)     " + 
				"    	end as providerEtqaId     " + 
				"    , case      " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)     " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)     " + 
				"    	Else SUBSTRING('', 1, 10)     " + 
				"    	end as assessorEtqaId  " + 
				"    , case  " + 
				"    	when cl.learner_status in (10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as certificationDate  " + 
				"    , lastUpdateEntry.linux_timestamp as dateStamp     " + 
				"from      " + 
				"    company_learners cl      " + 
				"inner join (     " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp     " + 
				"	from company_learners_hist clh      " + 
				"	inner join REVINFO rv on rv.REV = clh.REV     " + 
				"	group by clh.id     " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id     " + 
				"left join users u on u.id = cl.user_id     " + 
				"left join learnership lship on lship.id = cl.learnership_id     " + 
				"left join saqa_qualification sq on sq.id = lship.qualification_id      " + 
				"left join intervention_type it on it.id = cl.intervention_type_id      " + 
				"left join company c on c.id = cl.company_id   " + 
				"left join etqa eCompany on c.etqa_id = eCompany.id    " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id     " + 
				"left join etqa on etqa.id = tpa.etqa_id      " + 
				"left join company cEmp on cEmp.id = cl.employer_id      " + 
				"left join funding f on f.id = cl.funding     " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id      " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id      " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id      " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id     " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum      " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id     " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id     " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id     " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id     " + 
				"where      " + 
				"    cl.learnership_id is not null     " + 
				"    and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)     " + 
				"	and cl.intervention_type_id is not null      " + 
				"	and cl.company_id is not null  " + 
				"	and lship.setmis_code <> '8'";
		return (List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo.class);
	}
	
	public List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo> extractNLRDFile28BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile28BeanLearnershipEnrolmentAchievementVersionTwo.class);
	}
	
	public int extractNLRDFile28Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_28 ( national_id, "
                + " person_alternate_id, "
                + " alternative_id_type, "
                + " learnership_id, "
                + " learner_achievement_status_id, "
                + " assessor_registration_number, "
                + " learner_achievement_date, "
                + " learner_enrolled_date, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " assessor_etqa_id, "
                + " certification_date, "
                + " date_stamp ) "
                + " select "
				+" u.rsa_id_number as nationalId, "
				+" u.passport_number as personAlternateId, "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType, "
				+" cl.learnership_code as learnershipId,"
				+" IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId, "
				+" amp.certificate_number  as assessorRegistrationNumber, "
				+" cl.completion_date as learnerAchievementDate,"
				+" cl.commencement_date as learnerEnrolledDate, "
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+" eqt.nlrd_code  as providerEtqaId, "
				+" eqt.nlrd_code as assessorEtqaId,"
				+" cl.certificate_date as certificationDate, "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join company_learners cl on cl.user_id = u.id"
				+" left join intervention_type it on it.id = cl.intervention_type_id"
				+" where it.intervention_type_enum = 1";

        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile28> allNLRDFile28() throws Exception {
        return (List<NLRDFile28>)super.getList("select o from NLRDFile28 o");
    }
	
	public List<NLRDFile29Bean> extractNLRDFile29Bean() throws Exception {
		String sql = " select "
				+" u.rsa_id_number as nationalId, "
				+" u.passport_number as personAlternateId, "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType, "
				+" sq.qualificationid as qualificationId,"
				+" IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId,"
				+" amp.certificate_number  as assessorRegistrationNumber, "
				+" '7' as learnerAchievementTypeId,"
				+" cl.completion_date as learnerAchievementDate,"
				+" cl.commencement_date as learnerEnrolledDate, "
				+" '1' as honoursClassification, "
				+" it.nlrd_code as partof,"
				+" cl.learnership_code as learnershipId,"
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+" eqt.nlrd_code  as providerEtqaId, "
				+" eqt.nlrd_code as assessorEtqaId,"
				+" cl.certificate_date as certificationDate, "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join company_learners cl on cl.user_id = u.id"
				+" left join intervention_type it on it.id = cl.intervention_type_id"
				+" inner join saqa_qualification sq on cl.qualification_id = sq.id";
		
		
		return (List<NLRDFile29Bean>)  super.nativeSelectSqlList(sql, NLRDFile29Bean.class);
	}
	
	public List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> extractNLRDFile29BeanVersionTwo() throws Exception {
		String sql = "select	  " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , case  " + 
				"    	when l.id is not null then SUBSTRING(lsq.qualificationid_string, 1, 10)  " + 
				"    	Else SUBSTRING(sq.qualificationid_string, 1, 10)  " + 
				"    	End as qualificationId    " + 
				"	, case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as learnerAchievementStatusId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"	, '7' as learnerAchievementTypeId  " + 
				"	, case  " + 
				"    	when cl.learner_status in (6,10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as learnerAchievementDate  " + 
				"	, case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as learnerEnrolledDate  " + 
				"	, SUBSTRING('', 1, 3) as honoursClassification  " + 
				"	, case   " + 
				"		when it.part_of_id_string = '2' then SUBSTRING('1', 1, 2)  " + 
				"		Else SUBSTRING(it.part_of_id_string, 1, 2)  " + 
				"	 	end as partof   " + 
				"	, case  " + 
				"    	when l.id is not null then SUBSTRING(l.setmis_code, 1, 10)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	End as learnershipId  " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorEtqaId  " + 
				"    , case  " + 
				"    	when cl.learner_status in (10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as certificationDate	  " + 
				"	, lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"    company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id   " + 
				"left join learnership l on l.id = cl.learnership_id and l.setmis_code <> '8'  " + 
				"left join saqa_qualification lsq on lsq.id = l.qualification_id  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join training_provider_application tpa on tpa.id = cl.training_provider_application_id  " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"	cl.skills_set_id is null  " + 
				"	and cl.skills_program_id is null  " + 
				"	and cl.learnership_id is null  " + 
				"	and cl.qualification_id is not null  " + 
				"   and cl.learner_status in (1,2,4,5,6,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null";
		return (List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo.class);
	}
	
	public List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo> extractNLRDFile29BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo.class);
	}
	
	public int extractNLRDFile29Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_29 ( "
        		+" national_id "
        		+" ,person_alternate_id "
        		+" ,alternative_id_type "
        		+" ,qualification_id "
        		+" ,learner_achievement_status_id "
        		+" ,assessor_registration_number "
        		+" ,learner_achievement_type_id "
        		+" ,learner_achievement_date "
        		+" ,learner_enrolled_date "
        		+" ,honours_classification "
        		+" ,part_of "
        		+" ,learnership_id "
        		+" ,provider_code "
        		+" ,provider_etqa_id "
        		+" ,assessor_etqa_id "
        		+" ,certification_date "
        		+" ,date_stamp ) "
                +" select "
				+" u.rsa_id_number as nationalId, "
				+" u.passport_number as personAlternateId, "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType, "
				+" sq.qualificationid as qualificationId,"
				+" IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId,"
				+" amp.certificate_number  as assessorRegistrationNumber, "
				+" '7' as learnerAchievementTypeId,"
				+" cl.completion_date as learnerAchievementDate,"
				+" cl.commencement_date as learnerEnrolledDate, "
				+" '1' as honoursClassification, "
				+" it.nlrd_code as partof,"
				+" cl.learnership_code as learnershipId,"
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+" eqt.nlrd_code  as providerEtqaId, "
				+" eqt.nlrd_code as assessorEtqaId,"
				+" cl.certificate_date as certificationDate, "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join company_learners cl on cl.user_id = u.id"
				+" left join intervention_type it on it.id = cl.intervention_type_id"
				+" inner join saqa_qualification sq on cl.qualification_id = sq.id";
		
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile29> allNLRDFile29() throws Exception {
        return (List<NLRDFile29>)super.getList("select o from NLRDFile29 o");
    }
	
	public int extractNLRDFile29Validation() throws Exception {
		String sql =  " INSERT INTO nlrd_file_29 ( national_id, "
                + " person_alternate_id, "
                + " alternative_id_type, "
                + " qualification_id, "
                + " learner_achievement_status_id, "
                + " assessor_registration_number, "
                + " learner_achievement_type_id, "
                + " learner_achievement_date, "
                + " learner_enrolled_date, "
                + " honours_classification, "
                + " part_of, "
                + " learnership_id, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " assessor_etqa_id, "
                + " certification_date, "
                + " date_stamp ) "
                + " select "
				+" u.rsa_id_number as nationalId, "
				+" u.passport_number as personAlternateId, "
				+" IF(u.passport_number is not NULL,533,527) as alternativeIdType, "
				+" sq.qualificationid as qualificationId,"
				+" IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId,"
				+" amp.certificate_number  as assessorRegistrationNumber, "
				+" '7' as learnerAchievementTypeId,"
				+" cl.completion_date as learnerAchievementDate,"
				+" cl.commencement_date as learnerEnrolledDate, "
				+" '1' as honoursClassification, "
				+" it.nlrd_code as partof,"
				+" cl.learnership_code as learnershipId,"
				+" CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+" eqt.nlrd_code  as providerEtqaId, "
				+" eqt.nlrd_code as assessorEtqaId,"
				+" cl.certificate_date as certificationDate, "
				+" CURDATE() as dateStamp"
				+" from users u"
				+" left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+" left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+" left join company c on c.id = amc.company_id"
				+" left join etqa eqt on c.`etqa_id` = eqt.id"
				+" left join company_learners cl on cl.user_id = u.id"
				+" left join intervention_type it on it.id = cl.intervention_type_id"
				+" inner join saqa_qualification sq on cl.qualification_id = sq.id";
		
		return (int) super.nativeSQLInsert(sql);
	}
	
	public List<NLRDFile30Bean> extractNLRDFile30Bean() throws Exception {
		String sql = "select "
				+ "  u.rsa_id_number as nationalId, "
				+ "  u.passport_number as personAlternateId, "
				+ "  IF(u.passport_number is not NULL,533,527) as alternativeIdType,  "
				+ "  su.unitstandardid as unitStandardId,"
				+ "  IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId,"
				+ "  amp.certificate_number  as assessorRegistrationNumber,  "
				+ "  '7' as learnerAchievementTypeId,"
				+ "  cl.completion_date as learnerAchievementDate,"
				+ "  cl.commencement_date as learnerEnrolledDate, "
				+ "  '1' as honoursClassification,"
				+ "  it.nlrd_code as partof,"
				+ "  sq.qualificationid as qualificationId,"
				+ "  cl.learnership_code as learnershipId,"
				+ "  CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+ "  eqt.nlrd_code  as providerEtqaId, "
				+ "  eqt.nlrd_code as assessorEtqaId,"
				+ "  cl.certificate_date as certificationDate, "
				+ "  CURDATE() as dateStamp"
				+ "  from users u"
				+ "  left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+ "  left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+ "  left join company c on c.id = amc.company_id"
				+ "  left join etqa eqt on c.`etqa_id` = eqt.id"
				+ "  inner join company_learners cl on cl.user_id = u.id"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id"
				+ "  inner join saqa_unitstandard su on cl.unit_standard_id = su.id"
				+ "  left join saqa_qualification sq on cl.qualification_id = sq.id";
		return (List<NLRDFile30Bean>)  super.nativeSelectSqlList(sql, NLRDFile30Bean.class);
	}
	
	public List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> extractNLRDFile30BeanVersionTwo() throws Exception {
		String sql = "select  " + 
				"    case   " + 
				"       when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING(u.rsa_id_number, 1, 15)  " + 
				"       ELSE SUBSTRING('', 1, 15)  " + 
				"       end as nationalId  " + 
				"	, case   " + 
				"       when u.passport_number is not null and u.passport_number <> '' then SUBSTRING(u.passport_number, 1, 20)  " + 
				"       ELSE SUBSTRING('', 1, 20)  " + 
				"       end as personAlternateId  " + 
				"	, case   " + 
				"    	when u.rsa_id_number is not null and u.rsa_id_number <> '' then SUBSTRING('533', 1, 3)  " + 
				"        when u.passport_number is not null and u.passport_number <> '' then SUBSTRING('527', 1, 3)  " + 
				"        ELSE SUBSTRING('', 1, 3)  " + 
				"        end as alternativeIdType  " + 
				"    , SUBSTRING(su.unitstandardid, 1, 10) as unitStandardId   " + 
				"    , case   " + 
				"    	when cl.learner_status in (6) then SUBSTRING('2', 1, 3)  " + 
				"    	when cl.learner_status in (10) then SUBSTRING('15', 1, 3)  " + 
				"    	when cl.learner_status in (1, 2, 4, 5, 7, 8, 9, 11, 12, 13) then SUBSTRING('3', 1, 3)  " + 
				"    	Else SUBSTRING('', 1, 3)  " + 
				"    	end as learnerAchievementStatusId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) then SUBSTRING(ampAss.certificate_number, 1, 20)  " + 
				"    	Else ''  " + 
				"    	end as assessorRegistrationNumber  " + 
				"    , SUBSTRING('7', 1, 3) as learnerAchievementTypeId  " + 
				"    , case  " + 
				"    	when cl.learner_status in (6,10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as learnerAchievementDate  " + 
				"	, case   " + 
				"    	when legacy_id is null and (legacy_target_class is null or legacy_target_class = '') then cl.registered_date  " + 
				"    	when legacy_id is not null and legacy_target_class is not null and cl.learner_status then cl.commencement_date  " + 
				"		Else null  " + 
				"		end as learnerEnrolledDate  " + 
				"    , SUBSTRING('', 1, 3) as honoursClassification   " + 
				"    , '1' as partof  " + 
				"    , SUBSTRING('', 1, 10) as qualificationId   " + 
				"    , SUBSTRING('', 1, 10) as learnershipId   " + 
				"    , SUBSTRING(CONCAT('NSDMS-TP-' , c.id), 1, 20) as providerCode  " + 
				"    , case  " + 
				"    	when etqaComp.code is not null and etqaComp.code  <> '' then SUBSTRING(etqaComp.code , 1, 10)  " + 
				"    	when etqa.code is not null and etqa.code  <> '' then SUBSTRING(etqa.code , 1, 10)  " + 
				"     	ELSE SUBSTRING('599', 1, 10)  " + 
				"    	end as providerEtqaId  " + 
				"    , case   " + 
				"    	when cl.learner_status in (6,10) and eqtaAss.code is not null and eqtaAss.code  <> '' then SUBSTRING(eqtaAss.code, 1, 10)  " + 
				"    	when cl.learner_status in (6,10) and (eqtaAss.code is null or eqtaAss.code  <> '') then SUBSTRING('599', 1, 20)  " + 
				"    	Else SUBSTRING('', 1, 10)  " + 
				"    	end as assessorEtqaId  " + 
				"    , case  " + 
				"    	when cl.learner_status in (10) then cl.certificate_date  " + 
				"    	Else null  " + 
				"    	end as certificationDate  " + 
				"    , lastUpdateEntry.linux_timestamp as dateStamp  " + 
				"from   " + 
				"    company_learners cl   " + 
				"inner join (  " + 
				"	select clh.id as id, FROM_UNIXTIME(SUBSTRING(max(rv.REVTSTMP),1,CHAR_LENGTH(max(rv.REVTSTMP)) - 3)) as linux_timestamp  " + 
				"	from company_learners_hist clh   " + 
				"	inner join REVINFO rv on rv.REV = clh.REV  " + 
				"	group by clh.id  " + 
				"	) lastUpdateEntry on lastUpdateEntry.id = cl.id  " + 
				"left join users u on u.id = cl.user_id  " + 
				"left join saqa_qualification sq on sq.id = cl.qualification_id and sq.qualificationtypeid <> '554'  " + 
				"left join intervention_type it on it.id = cl.intervention_type_id   " + 
				"left join company c on c.id = cl.company_id  " + 
				"left join etqa etqaComp on etqaComp.id = c.etqa_id   " + 
				"left join training_provider_application tpa on tpa.company_id = c.id   " + 
				"left join etqa on etqa.id = tpa.etqa_id   " + 
				"left join company cEmp on cEmp.id = cl.employer_id  " + 
				"left join funding f on f.id = cl.funding  " + 
				"left join ofo_codes ofo on ofo.id = cl.ofo_codes_id   " + 
				"left join ofo_codes ofoParent on ofoParent.id = ofo.ofo_codes_id   " + 
				"left join ofo_codes ofoU on ofoU.id = u.ofo_codes_id   " + 
				"left join ofo_codes ofoParentU on ofoParentU.id = ofoU.ofo_codes_id   " + 
				"left join urbal_rural ur on ur.id = u.urban_rural_enum   " + 
				"left join saqa_unitstandard su on su.id = cl.unit_standard_id  " + 
				"left join assessor_moderator_application ampAss on ampAss.id = cl.assessor_application_id  " + 
				"left join etqa eqtaAss on ampAss.etqa_id = eqtaAss.id  " + 
				"left join assessor_moderator_application ampMod on ampMod.id = cl.moderator_application_id  " + 
				"left join etqa eqtaMod on ampMod.etqa_id = eqtaMod.id  " + 
				"where   " + 
				"    cl.unit_standard_id is not null  " + 
				"    and cl.qualification_id is null  " + 
				"    and cl.learnership_id is null  " + 
				"	and cl.learner_status in (1,2,4,5,7,8,10,11,12,13)  " + 
				"	and cl.intervention_type_id is not null   " + 
				"	and cl.company_id is not null";
		return (List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo.class);
	}
	
	public List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo> extractNLRDFile30BeanVersionTwoSqlPassed(String sql) throws Exception {
		return (List<NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo>)  super.nativeSelectSqlList(sql, NLRDFile30BeanUnitStandardEnrolmentAchievementVersionTwo.class);
	}
	
	public int extractNLRDFile30Insert() throws Exception {
        String sql = " INSERT INTO nlrd_file_30 ( national_id, "
                + " person_alternate_id, "
                + " alternative_id_type, "
                + " unit_standard_id, "
                + " learner_achievement_status_id, "
                + " assessor_registration_number, "
                + " learner_achievement_type_id, "
                + " learner_achievement_date, "
                + " learner_enrolled_date, "
                + " honours_classification, "
                + " part_of, "
                + " qualification_id, "
                + " learnership_id, "
                + " provider_code, "
                + " provider_etqa_id, "
                + " assessor_etqa_id, "
                + " certification_date, "
                + " date_stamp ) "
                + "select "
				+ "  u.rsa_id_number as nationalId, "
				+ "  u.passport_number as personAlternateId, "
				+ "  IF(u.passport_number is not NULL,533,527) as alternativeIdType,  "
				+ "  su.unitstandardid as unitStandardId,"
				+ "  IF(cl.completion_date is NULL,3,2) as learnerAchievementStatusId,"
				+ "  amp.certificate_number  as assessorRegistrationNumber,  "
				+ "  '7' as learnerAchievementTypeId,"
				+ "  cl.completion_date as learnerAchievementDate,"
				+ "  cl.commencement_date as learnerEnrolledDate, "
				+ "  '1' as honoursClassification,"
				+ "  it.nlrd_code as partof,"
				+ "  sq.qualificationid as qualificationId,"
				+ "  cl.learnership_code as learnershipId,"
				+ "  CONCAT('MerSETA-' , LPAD(c.id, 8, '0'))  as providerCode, "
				+ "  eqt.nlrd_code  as providerEtqaId, "
				+ "  eqt.nlrd_code as assessorEtqaId,"
				+ "  cl.certificate_date as certificationDate, "
				+ "  CURDATE() as dateStamp"
				+ "  from users u"
				+ "  left join assessor_moderator_company amc on amc.assessor_moderator_id = u.id"
				+ "  left join assessor_moderator_application amp on amp.id = amc.for_assessor_moderator_application_id"
				+ "  left join company c on c.id = amc.company_id"
				+ "  left join etqa eqt on c.`etqa_id` = eqt.id"
				+ "  inner join company_learners cl on cl.user_id = u.id"
				+ "  inner join intervention_type it on it.id = cl.intervention_type_id"
				+ "  inner join saqa_unitstandard su on cl.unit_standard_id = su.id"
				+ "  left join saqa_qualification sq on cl.qualification_id = sq.id";
        return (int) super.nativeSQLInsert(sql);
    }
    @SuppressWarnings("unchecked")
    public List<NLRDFile30> allNLRDFile30() throws Exception {
        return (List<NLRDFile30>)super.getList("select o from NLRDFile30 o");
    }
	
	public Integer combinationFieldCheck(String entityName,String learnershipId, Integer qualificationId, Integer unitStandardId, String designationId, String designationRegistrationNumber, String designationETQAId) throws Exception {
		String hql = "select count(o) from "+entityName+" o "
				+ " where o.learnershipId = :learnershipId and "
				+ " where o.qualificationId = :qualificationId and "
				+ " where o.unitStandardId = :unitStandardId and "
				+ " where o.designationId = :designationId and "
				+ " where o.designationRegistrationNumber = :designationRegistrationNumber ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put(":entityName", entityName);
		parameters.put(":learnershipId", learnershipId);
		parameters.put(":qualificationId", qualificationId);
		parameters.put(":unitStandardId", unitStandardId);
		parameters.put(":designationId", designationId);
		parameters.put(":designationRegistrationNumber", designationRegistrationNumber);
		parameters.put(":designationETQAId", designationETQAId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer combinationFieldCheck(String entityName,String nationalId, String personAlternateId, BigInteger alternativeIdType, Integer qualificationId) throws Exception {
		String hql = "select count(o) from "+entityName+" o "
				+ " where o.nationalId = :nationalId and "
				+ " where o.personAlternateId = :personAlternateId and "
				+ " where o.alternativeIdType = :alternativeIdType and "
				+ " where o.qualificationId = :qualificationId ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put(":entityName", entityName);
		parameters.put(":nationalId", nationalId);
		parameters.put(":personAlternateId", personAlternateId);
		parameters.put(":alternativeIdType", alternativeIdType);
		parameters.put(":qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
}
