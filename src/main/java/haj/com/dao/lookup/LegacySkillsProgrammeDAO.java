package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.bean.LegacyDataReportBean;
import haj.com.bean.LegacyDataReportBean;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacySkillsProgramme;

public class LegacySkillsProgrammeDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacySkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacySkillsProgramme() throws Exception {
		return (List<LegacySkillsProgramme>) super.getList("select o from LegacySkillsProgramme o");
	}

	/**
	 * Get all LegacySkillsProgramme between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacySkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacySkillsProgramme(Long from, int noRows) throws Exception {
		String hql = "select o from LegacySkillsProgramme o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacySkillsProgramme>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacySkillsProgramme
	 * @return a {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception global exception
	 */
	public LegacySkillsProgramme findByKey(Long id) throws Exception {
		String hql = "select o from LegacySkillsProgramme o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacySkillsProgramme) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacySkillsProgramme by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacySkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> findByName(String description) throws Exception {
		String hql = "select o from LegacySkillsProgramme o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacySkillsProgramme>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> findByProviderSDL(String providerSDL) throws Exception {
		String hql = "select o from LegacySkillsProgramme o where o.providerSDL = :providerSDL";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("providerSDL", "" + providerSDL.trim());
		return (List<LegacySkillsProgramme>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacySkillsProgramme o")).intValue();
	}
	
	public Integer countAllLegacySkillsProgrammeNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacySkillsProgramme o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacySkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacySkillsProgramme o where o.processed = false";
		return (List<LegacySkillsProgramme>) super.getList(hql,  numberOfEntries);
	}

	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacyLearnershipNoQualification(int numberOfEntries) {
		String hql = "select o from LegacySkillsProgramme o where o.skillsProgram is null";
		return (List<LegacySkillsProgramme>) super.getList(hql,  numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacyLearnershipNoQualification() {
		String hql = "select o from LegacySkillsProgramme o where o.skillsProgram is null and o.skillsSet is null";
		return (List<LegacySkillsProgramme>) super.getList(hql);
	}

	public Integer countAllLegacyLearnershipNoQualification() {
		String hql = "select count(o) from LegacySkillsProgramme o where o.skillsProgram is null" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
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
				"	lsp.learner_lp_status_desc as status ,  " + 
				"	lsp.s_programme_code as code ,  " + 
				"	lsp.s_programme_desc as title  " + 
				
				"	from " + 
				"	legacy_skills_programme lsp " ;
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
				"	lsp.learner_lp_status_desc as status ,  " + 
				"	lsp.s_programme_code as code ,  " + 
				"	lsp.s_programme_desc as title  " + 
				
				"	from " + 
				"	legacy_skills_programme lsp " +
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
				"	lsp.learner_lp_status_desc as status ,  " + 
				"	lsp.s_programme_code as code ,  " + 
				"	lsp.s_programme_desc as title  " + 
				
				"	from " + 
				"	legacy_skills_programme lsp " +
				"   where lsp.accreditation_number = :accreditationNumber ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accreditationNumber", accreditationNumber);
		return (List<LegacyDataReportBean>)super.nativeSelectSqlList(sql, LegacyDataReportBean.class, parameters);
	}
}
