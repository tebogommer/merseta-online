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
import haj.com.entity.lookup.LegacyUnitStandard;

public class LegacyUnitStandardDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyUnitStandard() throws Exception {
		return (List<LegacyUnitStandard>)super.getList("select o from LegacyUnitStandard o");
	}

	/**
	 * Get all LegacyUnitStandard between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyUnitStandard
 	 * @return a {@link haj.com.entity.LegacyUnitStandard}
 	 * @throws Exception global exception
 	 */
	public LegacyUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyUnitStandard by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyUnitStandard
  	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyUnitStandard>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyUnitStandard o")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> findByIdNumberAndCode(String idNumber, String unitStandardCode) {
		String hql = "select o from LegacyUnitStandard o where o.idNo = :idNumber and o.unitStdCode = :unitStandardCode";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNumber", idNumber);
	    parameters.put("unitStandardCode", unitStandardCode);
		return (List<LegacyUnitStandard>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> findByProviderSDL(String providerSDL) throws Exception {
		String hql = "select o from LegacyUnitStandard o where o.providerSDL = :providerSDL";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("providerSDL", "" + providerSDL.trim());
		return (List<LegacyUnitStandard>) super.getList(hql, parameters);
	}

	public Integer countAllLegacyLearnershipNoQualification() throws Exception{
		String hql = "select count(o) from LegacyUnitStandard o where o.unitStandard is null";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyLearnershipNoQualification(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyUnitStandard o where o.unitStandard is null";
		return (List<LegacyUnitStandard>) super.getList(hql, numberOfEntries);
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.provider_legal_name as providerName ,  " + 
				"	lsp.provider_trade_name as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.dt_effective_date as effectiveDate ,  " + 
				"	lsp.dt_start_date as startDate ,  " + 
				"	lsp.dt_end_date as endDate ,  " + 
				"	lsp.learner_us_status_desc as status ,  " + 
				"	lsp.unit_std_code as code ,  " + 
				"	lsp.unit_std_desc as title  " + 
				
				"	from " + 
				"	legacy_unit_standard lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.provider_legal_name as providerName ,  " + 
				"	lsp.provider_trade_name as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.dt_effective_date as effectiveDate ,  " + 
				"	lsp.dt_start_date as startDate ,  " + 
				"	lsp.dt_end_date as endDate ,  " + 
				"	lsp.learner_us_status_desc as status ,  " + 
				"	lsp.unit_std_code as code ,  " + 
				"	lsp.unit_std_desc as title  " + 
				
				"	from " + 
				"	legacy_unit_standard lsp "  +
				"   where lsp.employer_sdl = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
	
	public List<LegacyDataReportBean> populateReportAccreditaionNo(String accreditationNumber) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.provider_sdl as providerEntityId ,  " + 
				"	lsp.provider_legal_name as providerName ,  " + 
				"	lsp.provider_trade_name as providerTradingName ,  " + 
				"	lsp.accreditation_number as accreditationNumber ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.dt_effective_date as effectiveDate ,  " + 
				"	lsp.dt_start_date as startDate ,  " + 
				"	lsp.dt_end_date as endDate ,  " + 
				"	lsp.learner_us_status_desc as status ,  " + 
				"	lsp.unit_std_code as code ,  " + 
				"	lsp.unit_std_desc as title  " + 
				
				"	from " + 
				"	legacy_unit_standard lsp "  +
				"   where lsp.accreditation_number = :accreditationNumber ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accreditationNumber", accreditationNumber);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}