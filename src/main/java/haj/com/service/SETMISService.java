package haj.com.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.dataextract.bean.SETMISFile200BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile400BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile503Bean;
import haj.com.dataextract.bean.SETMISFile503BeanVersionTwo;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.dataextract.bean.SETMISFile506BeanSandra;
import haj.com.entity.enums.DhetFileNumberEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISService extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();
	
	private DhetReportingService dhetReportingService = new DhetReportingService();

	public List<SETMISFile100Bean> extractSETMISFile100Bean() throws Exception {
		return dao.extractSETMISFile100Bean();
	}

	public List<SETMISFile200Bean> extractSETMISFile200Bean() throws Exception {
		return dao.extractSETMISFile200Bean();
	}
	
	public List<SETMISFile200BeanVersionTwo> extractSETMISFile200BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile200BeanVersionTwo();
	}
	
	public List<SETMISFile200BeanVersionTwo> extractSETMISFile200BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile200BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile200));
	}

	public int extractSetmisFile200Validation() throws Exception {
		return dao.extractSetmisFile200Validation();
	}

	public List<SETMISFile304Bean> extractSETMISFile304Bean() throws Exception {
		return dao.extractSETMISFile304Bean();
	}
	
	public List<SETMISFile304Bean> extractSETMISFile304BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile304BeanVersionTwo();
	}
	
	public List<SETMISFile304Bean> extractSETMISFile304BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile304BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile304));
	}

	public List<SETMISFile400Bean> extractSETMISFile400Bean() throws Exception {
		return dao.extractSETMISFile400Bean();
	}
	
	public List<SETMISFile400BeanVersionTwo> extractSETMISFile400BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile400BeanVersionTwo();
	}
	
	public List<SETMISFile400BeanVersionTwo> extractSETMISFile400BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile400BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile400));
	}
	
	
	public void extractSETMISFile400BeanScrollableResult(String fileName) throws Exception {
		String sql = " select u.rsa_id_number as nationalId "
				+ " , u.passport_number as personAlternateId "
				+ " , IF(u.rsa_id_number is not NULL,'533','527')  as alternativeIdType "
				+ " , e.`setmis_code` as equityCode "
				+ " , n.`setmis_code` as nationalityCode "
				+ " , l.`setmis_code` as homeLanguageCode "
				+ " , g.`setmis_code` as genderCode "
				+ " , IF(n.nlrd_code = 'SA','SA','U') as citizenResidentStatusCode "
				+ " , '' as filler01 "
				+ " , '' as filler02 "
				+ " , u.last_name as personLastName "
				+ " , u.first_name as personFirstName "
				+ " , u.middle_name as personMiddleName "
				+ " , t.description as personTitle "
				+ " , u.date_of_birth as personBirthDate "
				+ " , ap.address_line_1 as personHomeAddress1 "
				+ " , ap.address_line_2 as personHomeAddress2 "
				+ " , ap.address_line_3 as personHomeAddress3 "
				+ " , a.address_line_1 as personPostalAddress1 "
				+ " , a.address_line_2 as personPostalAddress2 "
				+ " , a.address_line_3 as personPostalAddress3 "
				+ " , ap.postcode as personHomeAddrPostalCode "
				+ " , a.postcode as personPostalAddrPostalCode "
				+ " , u.tel_number as personPhoneNumber "
				+ " , u.cell_number as personCellPhoneNumber "
				+ " , u.fax_number as personFaxNumber "
				+ " , u.email as personEmailAddress "
				+ " , p.stats_prov_id as provinceCode "
				+ " , CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode "
				+ " , etqa.setmis_code as providerETQEId "
				+ " , '' as personPreviousLastname "
				+ " , '' as personPreviousAlternateId "
				+ " , '' as personPreviousAlternativeIdType "
				+ " , '' as personPreviousProviderCode "
				+ " , '' as personPreviousProviderETQEId "
				+ " , '6' as seeingRatingId "
				+ " , '6' as hearingRatingId "
				+ " , '6' as walkingRatingId "
				+ " , '6' as rememberingRatingId "
				+ " , '6' as communicationRatingId "
				+ " , '6' as selfcareRatingId "
				+ " ,  psemis.`new_natemis` as lastSchoolEMISNo "
				+ " , f.lastSchoolYear as lastSchoolYear"
				+ " , ssaac.`setmis_code` as sTATSSAAreaCode "
				+ " , '1' as pOPIActStatusID "
				+ " , u.`create_date` as pOPIActStatusDate "
				+ " , CURDATE() as dateStamp "
				+ " from company_learners cl "
				+ " inner join ( "
				+ " select MAX(cl.`last_school_year`) as lastSchoolYear, cl.user_id from company_learners cl group by cl.user_id "
				+ " ) f on f.user_id = cl.user_id and f.lastSchoolYear = cl.`last_school_year` "
				+ " left join company_qualifications cq on cq.company_id = cl.company_id "
				+ " left join company_unit_standard cus on cus.company_id = cl.company_id "
				+ " left join statssa_area_code ssaac on ssaac.id = cl.`stats_saarea_code_id` "
				+ " left join previous_schools psemis on cl.`previous_schools` = psemis.id "
				+ " inner join users u on u.id = cl.user_id "
				+ " left join equity e on e.id = u.equity_id "
				+ " left join nationality n on u.nationality_id = n.id "
				+ " left join language l on u.home_language = l.id "
				+ " left join gender g on u.gender_id = g.id "
				+ " left join address a on a.id = u.postal_address_id "
				+ " left join address ap on ap.id = u.residential_address_id "
				+ " left join disability_status ds on ds.id = u.disabilityStatus "
				+ " left join title t on u.title_id = t.id "
				+ " left join municipality m on m.id = a.municipality_id "
				+ " left join province p on p.id = m.provinces_idprovinces "
				+ " left join training_provider_application tpa on  tpa.users_id = u.id "
	            + " left join company c on c.id = tpa.company_id "
				+ " left join etqa on etqa.id = tpa.etqa_id "
				+ " where cl.learner_status not in (3,14)"
				+ " group by "
				+ "   nationalId "
				+ " , personAlternateId "
				+ " , alternativeIdType "
				+ " , equityCode "
				+ " , nationalityCode "
				+ " , homeLanguageCode "
				+ " , genderCode "
				+ " , citizenResidentStatusCode "
				+ " , personLastName "
				+ " , personFirstName "
				+ " , personMiddleName "
				+ " , personTitle "
				+ " , personBirthDate "
				+ " , personHomeAddress1 "
				+ " , personHomeAddress2 "
				+ " , personHomeAddress3 "
				+ " , personPostalAddress1 "
				+ " , personPostalAddress2 "
				+ " , personPostalAddress3 "
				+ " , personHomeAddrPostalCode "
				+ " , personPostalAddrPostalCode "
				+ " , personPhoneNumber "
				+ " , personCellPhoneNumber "
				+ " , personFaxNumber "
				+ " , personEmailAddress "
				+ " , provinceCode "
				+ " , providerCode "
				+ " , providerETQEId "
				+ " , personPreviousLastname "
				+ " , personPreviousAlternateId "
				+ " , personPreviousAlternativeIdType "
				+ " , personPreviousProviderCode "
				+ " , personPreviousProviderETQEId "
				+ " , seeingRatingId "
				+ " , hearingRatingId "
				+ " , walkingRatingId "
				+ " , rememberingRatingId "
				+ " , communicationRatingId "
				+ " , selfcareRatingId "
				+ " , lastSchoolEMISNo "
				+ " , lastSchoolYear "
				+ " , sTATSSAAreaCode "
				+ " , pOPIActStatusID "
				+ " , pOPIActStatusDate ";		
		
		commonResultSetCode(sql, SETMISFile400Bean.class, fileName);
	}

	public List<SETMISFile401Bean> extractSETMISFile401Bean() throws Exception {
		return dao.extractSETMISFile401Bean();
	}
	
	public List<SETMISFile401Bean> extractSETMISFile401BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile401BeanVersionTwo();
	}
	
	public List<SETMISFile401Bean> extractSETMISFile401BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile401BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile401));
	}
	
	
	public void extractSETMISFile401BeanScrollableResult(String fileName) throws Exception {
		String sql = " select u.rsa_id_number as nationalId,  "
				+ " u.passport_number as personAlternateId,  "
				+ " IF(u.rsa_id_number is not NULL,'533','527') as alternativeIdType, "
				+ " '1' as designationId,  "
				+ " amp.certificate_number as designationRegistrationNumber,  "
				+ " eqt.setmis_code as designationETQEId, "
				+ " amp.start_date as designationStartDate, "
				+ " amp.end_date  as designationEndDate, "
				+ " IF(amp.status = 0, '510' , (IF(amp.status = 1, '515' , (IF(amp.status = 2, '506' , (IF(amp.status = 3, '506' ,(IF(amp.status = 4, '506' , (IF(amp.status = 5, '501' , '501' )) )) )))) )) )  as designationStructureStatusId, "
				+ " rcmg.decision_number  as eTQEDecisionNumber,  "
				+ " CONCAT('MERSETA-' , tpa.`accreditation_number`) as providerCode, "
				+ " eqt.setmis_code as providerETQEId,  "
				+ " '' as filler01,  "
				+ " '' as filler02, "
				+ " '' as filler03,  "
				+ " CURDATE() as dateStamp "
				+ " from assessor_moderator_application amp "
				+ " inner join users u on amp.users_id = u.id "
				+ " left join company_users cu on cu.user_id = u.id and assessor_mod_type is not NULL "
				+ " left join company c on cu.company_id = c.id "
				+ " left join training_provider_application tpa on c.id = tpa.company_id "
				+ " left join etqa eqt on c.`etqa_id` = eqt.id "
				+ " left join Review_committee_meeting_agenda rcmg on rcmg.review_committee_meeting_id  = amp.review_committee_meeting_agenda_id "
				+ " left join user_unit_standard uus on uus.for_assessor_moderator_application_id = amp.id "
				+ " left join user_qualifications uq on uq.for_assessor_moderator_application_id = amp.id "
				+ " where amp.certificate_number = 1 "
				+ " group by nationalId, personAlternateId, alternativeIdType, designationId, designationRegistrationNumber, designationETQEId, designationStartDate, designationEndDate, designationStructureStatusId, eTQEDecisionNumber, providerCode, providerETQEId "; 

		  commonResultSetCode(sql, SETMISFile401Bean.class, fileName);
}
	
	
	public <T>  void commonResultSetCode (String sql, Class<T> entityType, String fileName) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

		Session session = dao.getDataProvider().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			NativeQuery nq = session.createNativeQuery(sql);
			nq.setResultTransformer(Transformers.aliasToBean(entityType));
			ScrollableResults scrollableResults = nq.setCacheable(false).setFetchSize(500).setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
			int count = 0;
			while (scrollableResults.next()) {
				if (++count > 0 && count % 500 == 0) {
					System.out.println("Fetched " + count + " entities");
				}
				String csv = CSVUtil.writeFixedLength(scrollableResults.get()[0]);
				writer.write(csv);
			}
			writer.close();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			try {
				writer.close();
			} catch (IOException e1) {
				logger.fatal(e1);
			}
			throw e;
		} finally {
			session.close();
			try {
				writer.close();
			} catch (IOException e1) {
				logger.fatal(e1);
			}
		}
	}
	

	public List<SETMISFile500Bean> extractSETMISFile500Bean() throws Exception {
		return dao.extractSETMISFile500Bean();
	}
	
	public List<SETMISFile500Bean> extractSETMISFile500BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile500BeanVersionTwo();
	}
	
	public List<SETMISFile500Bean> extractSETMISFile500BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile500BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile500));
	}

	public List<SETMISFile501Bean> extractSETMISFile501Bean() throws Exception {
		return dao.extractSETMISFile501Bean();
	}
	
	public List<SETMISFile501Bean> extractSETMISFile501BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile501BeanVersionTwo();
	}
	
	public List<SETMISFile501Bean> extractSETMISFile501BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile501BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile501));
	}

	public List<SETMISFile502Bean> extractSETMISFile502Bean() throws Exception {
		return dao.extractSETMISFile502Bean();
	}
	
	public List<SETMISFile502Bean> extractSETMISFile502BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile502BeanVersionTwo();
	}
	
	public List<SETMISFile502Bean> extractSETMISFile502BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile502BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile502));
	}

	public List<SETMISFile503Bean> extractSETMISFile503Bean() throws Exception {
		return dao.extractSETMISFile503Bean();
	}
	
	public List<SETMISFile503BeanVersionTwo> extractSETMISFile503BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile503BeanVersionTwo();
	}
	
	public List<SETMISFile503BeanVersionTwo> extractSETMISFile503BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile503BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile503));
	}

	public List<SETMISFile505Bean> extractSETMISFile505Bean() throws Exception {
		return dao.extractSETMISFile505Bean();
	}
	
	public List<SETMISFile505Bean> extractSETMISFile505BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile505BeanVersionTwo();
	}
	
	public List<SETMISFile505Bean> extractSETMISFile505BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile505BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile505));
	}

	public List<SETMISFile506Bean> extractSETMISFile506Bean() throws Exception {
		return dao.extractSETMISFile506Bean();
	}
	
	public List<SETMISFile506Bean> extractSETMISFile506BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile506BeanVersionTwo();
	}
	
	public List<SETMISFile506Bean> extractSETMISFile506BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile506BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile506));
	}

	public List<SETMISFile506BeanSandra> extractSETMISFile506BeanSandra() throws Exception {
		return dao.extractSETMISFile506BeanSandra();
	}
}
