package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyLearnership;

public class LegacyLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyLearnership
 	 * @author TechFinium 
 	 * @see    LegacyLearnership
 	 * @return a list of {@link haj.com.entity.LegacyLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnership() throws Exception {
		return (List<LegacyLearnership>)super.getList("select o from LegacyLearnership o");
	}

	/**
	 * Get all LegacyLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyLearnership
 	 * @return a list of {@link haj.com.entity.LegacyLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyLearnership
 	 * @return a {@link haj.com.entity.LegacyLearnership}
 	 * @throws Exception global exception
 	 */
	public LegacyLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyLearnership
  	 * @return a list of {@link haj.com.entity.LegacyLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> findByEmployerSdl(String employerSdl) throws Exception {
	 	String hql = "select o from LegacyLearnership o where o.employerSdl = :employerSdl " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("employerSdl", employerSdl);
		return (List<LegacyLearnership>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyLearnership o")).intValue();
	}
	
	public Integer countAllLegacyLearnershipNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyLearnership o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}
	
	public Integer countAllLegacyLearnershipNoQualification() throws Exception {
		String hql = "select count(o) from LegacyLearnership o where o.learnership is null";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnershipNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyLearnership o where o.processed = false";
		return (List<LegacyLearnership>) super.getList(hql, numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnershipNotLearnership() throws Exception {
		String hql = "select o from LegacyLearnership o where o.learnership is null";
		return (List<LegacyLearnership>)super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnershipNoQualification(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyLearnership o where o.learnership is null";
		return (List<LegacyLearnership>) super.getList(hql, numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> findByProviderSDL(String providerSdl) throws Exception{
		String hql = "select o from LegacyLearnership o where o.providerSdl = :providerSdl";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("providerSdl", "" + providerSdl.trim());
		return (List<LegacyLearnership>) super.getList(hql, parameters);		
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> findByLearnershipCode(String learnershipCode) throws Exception{
		String hql = "select o from LegacyLearnership o where o.learnershipCode = :learnershipCode";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learnershipCode", "" + learnershipCode.trim());
		return (List<LegacyLearnership>) super.getList(hql, parameters);		
	}
	
	public int countLearnershipByCode(String learnershipCode)  throws Exception {
		String hql = "select count(o) from LegacyLearnership o where o.learnershipCode = :learnershipCode";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learnershipCode", "" + learnershipCode.trim());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
						
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.dol_organisation_name_legal as providerName ,  " + 
				"	lsp.dol_organisation_name_legal as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.registration_date as effectiveDate ,  " + 
				"	lsp.agreement_start_date as startDate ,  " + 
				"	lsp.agreement_end_date as endDate ,  " + 
				"	lsp.agreement_status_desc as status ,  " + 
				"	lsp.learnership_code as code ,  " + 
				"	lsp.learnership_title as title  " + 
				
				"	from " + 
				"	legacy_learnership lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
						
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.dol_organisation_name_legal as providerName ,  " + 
				"	lsp.dol_organisation_name_legal as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.registration_date as effectiveDate ,  " + 
				"	lsp.agreement_start_date as startDate ,  " + 
				"	lsp.agreement_end_date as endDate ,  " + 
				"	lsp.agreement_status_desc as status ,  " + 
				"	lsp.learnership_code as code ,  " + 
				"	lsp.learnership_title as title  " + 
				
				"	from " + 
				"	legacy_learnership lsp "  +
				"   where lsp.employer_sdl = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
	
	public List<LegacyDataReportBean> populateReportAccreditationNumber(String accreditationNumber) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
						
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.dol_organisation_name_legal as providerName ,  " + 
				"	lsp.dol_organisation_name_legal as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.registration_date as effectiveDate ,  " + 
				"	lsp.agreement_start_date as startDate ,  " + 
				"	lsp.agreement_end_date as endDate ,  " + 
				"	lsp.agreement_status_desc as status ,  " + 
				"	lsp.learnership_code as code ,  " + 
				"	lsp.learnership_title as title  " + 
				
				"	from " + 
				"	legacy_learnership lsp "  +
				"   where lsp.accreditation_number = :accreditationNumber ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accreditationNumber", accreditationNumber);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}

}

