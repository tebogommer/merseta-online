package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.Qualification;

public class DesignatedTradeLevelDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DesignatedTradeLevel
	 * 
	 * @author TechFinium
	 * @see DesignatedTradeLevel
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> allDesignatedTradeLevel() throws Exception {
		return (List<DesignatedTradeLevel>) super.getList("select o from DesignatedTradeLevel o");
	}

	/**
	 * Get all DesignatedTradeLevel between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DesignatedTradeLevel
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> allDesignatedTradeLevel(Long from, int noRows) throws Exception {
		String hql = "select o from DesignatedTradeLevel o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<DesignatedTradeLevel>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DesignatedTradeLevel
	 * @return a {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             global exception
	 */
	public DesignatedTradeLevel findByKey(Long id) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (DesignatedTradeLevel) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DesignatedTradeLevel by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DesignatedTradeLevel
	 * @return a list of {@link haj.com.entity.DesignatedTradeLevel}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findByName(String description) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> allDesignatedTradeLevelByDesignatedTradeType(Long designatedTradeTypeId) {
		String hql = "select o from DesignatedTradeLevel o where o.designatedTradeType.id = :designatedTradeTypeId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("designatedTradeTypeId", designatedTradeTypeId);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findByQualificationIdOrderByLevel(Long qualificationId) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.qualification.id = :qualificationId order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters);
	}
	
	public Integer countByQualificationIdOrderByLevel(Long qualificationId) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.qualification.id = :qualificationId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findByQualificationIdNotRecorded(Long qualificationId, Long companyLearnersID) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.qualification.id = :qualificationId and o.id not in (select x.designatedTradeLevel.id from CompanyLearnersProgress x where x.companyLearners.id = :companyLearnersID) order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("companyLearnersID", companyLearnersID);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findByQualificationIdNotRecordedSingle(Long qualificationId, Long companyLearnersID) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.qualification.id = :qualificationId and o.id not in (select x.designatedTradeLevel.id from CompanyLearnersProgress x where x.companyLearners.id = :companyLearnersID) order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("companyLearnersID", companyLearnersID);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters, 1);
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findBydesignatedTradeIdNotRecordedSingle(Long designatedTradeId, Long companyLearnersID) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.designatedTrade.id = :designatedTradeId and o.id not in (select x.designatedTradeLevel.id from CompanyLearnersProgress x where x.companyLearners.id = :companyLearnersID) order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("designatedTradeId", designatedTradeId);
		parameters.put("companyLearnersID", companyLearnersID);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters, 1);
	}

	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findByQualificationIdNextLevel(Long qualificationId, Long level) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where o.qualification.id = :qualificationId and o.level > :level order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("level", level);
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DesignatedTradeLevel> findDesignatedTradeLevelAutocomplete(String description) throws Exception {
		String hql = "select o from DesignatedTradeLevel o where (o.qualification.description LIKE :qualificationDescription or o.qualification.codeString like :qualificationCode) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationDescription", "%" + description + "%");
		parameters.put("qualificationCode", "%" + description + "%");
		return (List<DesignatedTradeLevel>) super.getList(hql, parameters,10);
	}

	public int countEntiresByQualificationId(Long qualificationId) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.qualification.id = :qualificationId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public Long findByQualificationIdNotRecordedCount(Long qualificationId, Long companyLearnersID) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.qualification.id = :qualificationId and o.id not in (select x.designatedTradeLevel.id from CompanyLearnersProgress x where x.companyLearners.id = :companyLearnersID) order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("companyLearnersID", companyLearnersID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public Long findBydesignatedTradeIdNotRecordedSingleCount(Long designatedTradeId, Long companyLearnersID) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.designatedTrade.id = :designatedTradeId and o.id not in (select x.designatedTradeLevel.id from CompanyLearnersProgress x where x.companyLearners.id = :companyLearnersID) order by o.level asc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("designatedTradeId", designatedTradeId);
		parameters.put("companyLearnersID", companyLearnersID);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public int countEntiresByDesignatedTradeId(Long designatedTradeId) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.designatedTrade.id = :designatedTradeId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("designatedTradeId", designatedTradeId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countDesignatedTradeLevelEntriesByQualificationId(Long qualificationId) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.qualification.id = :qualificationId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countDesignatedTradeLevelEntriesByQualificationIdAndNoCompletionOrder(Long qualificationId, Long level) throws Exception {
		String hql = "select count(o) from DesignatedTradeLevel o where o.qualification.id = :qualificationId and o.level = :level";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("qualificationId", qualificationId);
		parameters.put("level", level);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

}