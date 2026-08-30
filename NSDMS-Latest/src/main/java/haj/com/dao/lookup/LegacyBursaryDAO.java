package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LegacyBursaryDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyBursary
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyBursary> allLegacyBursary() throws Exception {
		return (List<LegacyBursary>) super.getList("select o from LegacyBursary o");
	}

	/**
	 * Get all LegacyBursary between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see LegacyBursary
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyBursary> allLegacyBursary(Long from, int noRows) throws Exception {
		String hql = "select o from LegacyBursary o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyBursary>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see LegacyBursary
	 * @return a {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             global exception
	 */
	public LegacyBursary findByKey(Long id) throws Exception {
		String hql = "select o from LegacyBursary o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyBursary) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyBursary by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see LegacyBursary
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyBursary> findByName(String description) throws Exception {
		String hql = "select o from LegacyBursary o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyBursary>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyBursary> findBySdlNumber(String employerSdl) throws Exception {
		String hql = "select o from LegacyBursary o where o.employerSdl = :employerSdl";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("employerSdl", employerSdl);
		return (List<LegacyBursary>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyBursary o")).intValue();
	}

	public Integer countAllLegacyBursaryNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyBursary o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyBursary> allLegacyBursaryNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyBursary o where o.processed = false";
		return (List<LegacyBursary>) super.getList(hql, numberOfEntries);
	}
	
	/* Reporting Start */
	
	/*
	 * Count Reporting:
	 * 1 - processed by boolean: value 
	 * 2 - Valid RSA ID Numbers by boolean: value
	 * 3 - On Home Affairs Files by boolean: value
	 * 4 - Linked To SAQA Qualification
	 * 5 - Not Linked To SAQA Qualification
	 * 6 - Linked To Site
	 * 7 - Not Linked To Site
	 */
	public Integer countReporting(int reportNumber, boolean value) throws Exception{
		String hql = "select o from LegacyBursary ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		switch (reportNumber) {
		case 1:
			// Processed Count
			hql += " where o.processed = :value";
			parameters.put("value", value);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 2:
			// Valid RSA ID Number
			hql += " where o.validIdNumber = :value and o.processed = :processedValue ";
			parameters.put("value", value);
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 3:
			// On Home Affairs Files
			hql += " where o.appearsOnHomeAffairsData = :value and o.processed = :processedValue ";
			parameters.put("value", value);
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 4:
			// Linked To SAQA Qualification
			hql += " where o.qualification is not null and o.processed = :processedValue ";
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 5:
			// Not Linked To SAQA Qualification
			hql += " where o.qualification is null and o.processed = :processedValue ";
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 6:
			// Linked To Site
			hql += " where o.legacyOrganisationSites is not null and o.processed = :processedValue ";
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		case 7:
			// Linked To Site
			hql += " where o.legacyOrganisationSites is null and o.processed = :processedValue ";
			parameters.put("processedValue", true);
			return ((Long) super.getUniqueResult(hql,parameters)).intValue();
		default:
			return 0;
		}
	}

	public List<LegacyDataReportBean> populateReport() throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.oprganisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.partnership_sdl as providerEntityId ,  " + 
				"	lsp.partner_legal_name as providerName ,  " + 
				"	lsp.partner_trade_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.id2 as passportNumber ,  " + 
				
				"	lsp.application_date as effectiveDate ,  " + 
				"	lsp.start_date as startDate ,  " + 
				"	lsp.end_date as endDate ,  " + 
				"	lsp.description as status ,  " + 
				"	lsp.saqa_id as code ,  " + 
				"	lsp.skill_priority as title  " + 
				
				"	from " + 
				"	legacy_bursary lsp " ;
		return (List<LegacyDataReportBean>) super.nativeSelectSqlList(sql, LegacyDataReportBean.class);
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		String sql = "select    " + 
				"	lsp.employer_sdl as entityId,   " + 
				"	lsp.oprganisation_name_legal as companyName ,  " + 
				"	lsp.organisation_name_trade as tradingName ,  " + 
				
				"	lsp.partnership_sdl as providerEntityId ,  " + 
				"	lsp.partner_legal_name as providerName ,  " + 
				"	lsp.partner_trade_name as providerTradingName ,  " + 
				
				"	lsp.first_name as firstName ,  " + 
				"	lsp.middle_names as middleNames ,  " + 
				"	lsp.surname as lastName ,  " + 
				"	lsp.id_no as rsaIdNumber ,  " + 
				"	lsp.id2 as passportNumber ,  " + 
				
				"	lsp.application_date as effectiveDate ,  " + 
				"	lsp.start_date as startDate ,  " + 
				"	lsp.end_date as endDate ,  " + 
				"	lsp.description as status ,  " + 
				"	lsp.saqa_id as code ,  " + 
				"	lsp.skill_priority as title  " + 
				
				"	from " + 
				"	legacy_bursary lsp " +
				"   where lsp.employer_sdl = :sdlNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sdlNo", sdlNo);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}
