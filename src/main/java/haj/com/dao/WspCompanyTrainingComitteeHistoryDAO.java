package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspCompanyTrainingComitteeHistoryDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCompanyTrainingComitteeHistory
	 * 
	 * @author TechFinium
	 * @see WspCompanyTrainingComitteeHistory
	 * @return a list of
	 *         {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistory() throws Exception {
		return (List<WspCompanyTrainingComitteeHistory>) super.getList(
				"select o from WspCompanyTrainingComitteeHistory o");
	}

	/**
	 * Get all WspCompanyTrainingComitteeHistory between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see WspCompanyTrainingComitteeHistory
	 * @return a list of
	 *         {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistory(Long from, int noRows)
			throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<WspCompanyTrainingComitteeHistory>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see WspCompanyTrainingComitteeHistory
	 * @return a {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception
	 *             global exception
	 */
	public WspCompanyTrainingComitteeHistory findByKey(Long id) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (WspCompanyTrainingComitteeHistory) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCompanyTrainingComitteeHistory by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see WspCompanyTrainingComitteeHistory
	 * @return a list of
	 *         {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> findByName(String description) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<WspCompanyTrainingComitteeHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> findByWspId(Long wspId) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.wspLinkId = :wspId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<WspCompanyTrainingComitteeHistory>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass);
		return (List<WspCompanyTrainingComitteeHistory>) super.getList(hql, parameters);
	}
	
	
}