package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LearnersMentorBean;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeship;

public class LegacyApprenticeshipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyApprenticeship
 	 * @author TechFinium 
 	 * @see    LegacyApprenticeship
 	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeship> allLegacyApprenticeship() throws Exception {
		return (List<LegacyApprenticeship>)super.getList("select o from LegacyApprenticeship o");
	}

	/**
	 * Get all LegacyApprenticeship between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyApprenticeship
 	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeship> allLegacyApprenticeship(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyApprenticeship o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyApprenticeship>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyApprenticeship
 	 * @return a {@link haj.com.entity.LegacyApprenticeship}
 	 * @throws Exception global exception
 	 */
	public LegacyApprenticeship findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyApprenticeship o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyApprenticeship)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyApprenticeship by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyApprenticeship
  	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeship> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyApprenticeship o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyApprenticeship>)super.getList(hql, parameters);
	}
	
	public Integer countByRsaIdNumber(String idNumber) throws Exception {
	 	String hql = "select count(o) from LegacyApprenticeship o where o.idNo = :idNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNumber", idNumber.trim());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyApprenticeship o")).intValue();
	}
	
	public Integer countAllLegacyApprenticeshipNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyApprenticeship o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeship> allLegacyApprenticeshipNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyApprenticeship o where o.processed = false";
		return (List<LegacyApprenticeship>) super.getList(hql, numberOfEntries);
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.sdl_no as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.wae_mp_sdl as providerEntityId ,  " + 
				"	lsp.wa_legal_name as providerName ,  " + 
				"	lsp.wa_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.status_date as effectiveDate ,  " + 
				"	lsp.from_date as startDate ,  " + 
				"	lsp.to_date as endDate ,  " + 
				"	lsp.contract_status as status ,  " + 
				"	lsp.qual_code as code ,  " + 
				"	lsp.trade_description as title  " + 
				
				"	from " + 
				"	legacy_apprenticeship lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.sdl_no as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.wae_mp_sdl as providerEntityId ,  " + 
				"	lsp.wa_legal_name as providerName ,  " + 
				"	lsp.wa_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id as passportNumber ,  " + 
				
				"	lsp.status_date as effectiveDate ,  " + 
				"	lsp.from_date as startDate ,  " + 
				"	lsp.to_date as endDate ,  " + 
				"	lsp.contract_status as status ,  " + 
				"	lsp.qual_code as code ,  " + 
				"	lsp.trade_description as title  " + 
				
				"	from " + 
				"	legacy_apprenticeship lsp " +
				"   where lsp.sdl_no = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}

