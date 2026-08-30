package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;

public class LegacySectionTwentyEightTradeTestDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacySectionTwentyEightTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySectionTwentyEightTradeTest> allLegacySectionTwentyEightTradeTest() throws Exception {
		return (List<LegacySectionTwentyEightTradeTest>) super.getList(
				"select o from LegacySectionTwentyEightTradeTest o");
	}

	/**
	 * Get all LegacySectionTwentyEightTradeTest between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacySectionTwentyEightTradeTest
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySectionTwentyEightTradeTest> allLegacySectionTwentyEightTradeTest(Long from, int noRows)
			throws Exception {
		String hql = "select o from LegacySectionTwentyEightTradeTest o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacySectionTwentyEightTradeTest>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacySectionTwentyEightTradeTest
	 * @return a {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception global exception
	 */
	public LegacySectionTwentyEightTradeTest findByKey(Long id) throws Exception {
		String hql = "select o from LegacySectionTwentyEightTradeTest o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacySectionTwentyEightTradeTest) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacySectionTwentyEightTradeTest by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacySectionTwentyEightTradeTest
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySectionTwentyEightTradeTest> findByName(String description) throws Exception {
		String hql = "select o from LegacySectionTwentyEightTradeTest o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacySectionTwentyEightTradeTest>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacySectionTwentyEightTradeTest o")).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacySectionTwentyEightTradeTest> findByIdNo(String idNO) {
		String hql = "select o from LegacySectionTwentyEightTradeTest o where o.idNO = :idNO ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNO", idNO);
		return (List<LegacySectionTwentyEightTradeTest>) super.getList(hql, parameters);
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.sdl_no as entityId,   " + 
				"	lsp.organisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				
				"	lsp.date_application_received as effectiveDate ,  " + 
				"	lsp.test_date_from as startDate ,  " + 
				"	lsp.test_date_to as endDate ,  " + 
				"	lsp.contract_status as status ,  " + 
				"	lsp.saqa_code as code ,  " + 
				"	lsp.trade_description as title  " + 
				
				"	from " + 
				"	legacy_section_twenty_eight_trade_test lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
}
