package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacySECTTwentyEight;

public class LegacySECTTwentyEightDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacySECTTwentyEight
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEight() throws Exception {
		return (List<LegacySECTTwentyEight>) super.getList("select o from LegacySECTTwentyEight o");
	}

	/**
	 * Get all LegacySECTTwentyEight between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacySECTTwentyEight
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEight(Long from, int noRows) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacySECTTwentyEight>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacySECTTwentyEight
	 * @return a {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception global exception
	 */
	public LegacySECTTwentyEight findByKey(Long id) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacySECTTwentyEight) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacySECTTwentyEight by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacySECTTwentyEight
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> findByName(String description) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacySECTTwentyEight>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacySECTTwentyEight o")).intValue();
	}
	
	public Integer countAllLegacySECTTwentyEightNotProcessed() throws Exception {
		String hql = "select count(o) from LegacySECTTwentyEight o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEightNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o where o.processed = false";
		return (List<LegacySECTTwentyEight>) super.getList(hql, numberOfEntries);
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.sdl_no as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_legal as tradingName ,  " + 
				
				"	lsp.wasdl as providerEntityId ,  " + 
				"	lsp.wa_legal_name as providerName ,  " + 
				"	lsp.wa_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id_no as passportNumber ,  " + 
				
				"	lsp.learner_status_effective_date as effectiveDate ,  " + 
				"	lsp.apprenticeship_start_date as startDate ,  " + 
				"	lsp.apprenticeship_end_date as endDate ,  " + 
				"	lsp.description as status ,  " + 
				"	lsp.apprenticeship_id as code ,  " + 
				"	lsp.trade_description as title  " + 
				
				"	from " + 
				"	legacy_sect_twenty_eight lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.sdl_no as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_legal as tradingName ,  " + 
				
				"	lsp.wasdl as providerEntityId ,  " + 
				"	lsp.wa_legal_name as providerName ,  " + 
				"	lsp.wa_legal_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.alternate_id_no as passportNumber ,  " + 
				
				"	lsp.learner_status_effective_date as effectiveDate ,  " + 
				"	lsp.apprenticeship_start_date as startDate ,  " + 
				"	lsp.apprenticeship_end_date as endDate ,  " + 
				"	lsp.description as status ,  " + 
				"	lsp.apprenticeship_id as code ,  " + 
				"	lsp.trade_description as title  " + 
				
				"	from " + 
				"	legacy_sect_twenty_eight lsp " +
				"   where lsp.sdl_no = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}
