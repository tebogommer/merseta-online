package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyPerson;

public class LegacyInternshipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyInternship
 	 * @author TechFinium 
 	 * @see    LegacyInternship
 	 * @return a list of {@link haj.com.entity.LegacyInternship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyInternship> allLegacyInternship() throws Exception {
		return (List<LegacyInternship>)super.getList("select o from LegacyInternship o");
	}

	/**
	 * Get all LegacyInternship between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyInternship
 	 * @return a list of {@link haj.com.entity.LegacyInternship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyInternship> allLegacyInternship(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyInternship o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyInternship>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyInternship
 	 * @return a {@link haj.com.entity.LegacyInternship}
 	 * @throws Exception global exception
 	 */
	public LegacyInternship findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyInternship o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyInternship)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyInternship by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyInternship
  	 * @return a list of {@link haj.com.entity.LegacyInternship}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyInternship> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyInternship o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyInternship>)super.getList(hql, parameters);
	}
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyInternship o")).intValue();
	}
	
	public Integer countAllLegacyInternshipNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyInternship o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyInternship> allLegacyInternshipNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyInternship o where o.processed = false";
		return (List<LegacyInternship>) super.getList(hql,  numberOfEntries);
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.partnership_sdl as providerEntityId ,  " + 
				"	lsp.partner_legal_name as providerName ,  " + 
				"	lsp.partner_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.id_2 as passportNumber ,  " + 
				
				"	lsp.application_date as effectiveDate ,  " + 
				"	lsp.start_date as startDate ,  " + 
				"	lsp.end_date as endDate ,  " + 
				"	lsp.status as status ,  " + 
				"	lsp.saqa_id as code ,  " + 
				"	lsp.skill_priority as title  " + 
				
				"	from " + 
				"	legacy_internship lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.partnership_sdl as providerEntityId ,  " + 
				"	lsp.partner_legal_name as providerName ,  " + 
				"	lsp.partner_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.id_2 as passportNumber ,  " + 
				
				"	lsp.application_date as effectiveDate ,  " + 
				"	lsp.start_date as startDate ,  " + 
				"	lsp.end_date as endDate ,  " + 
				"	lsp.status as status ,  " + 
				"	lsp.saqa_id as code ,  " + 
				"	lsp.skill_priority as title  " + 
				
				"	from " + 
				"	legacy_internship lsp "  +
				"   where lsp.employer_sdl = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}

