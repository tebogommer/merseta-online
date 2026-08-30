package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.WorkplaceSurveyType;
import haj.com.entity.lookup.LearnerMonitoringSurvey;

public class LearnerMonitoringSurveyDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerMonitoringSurvey
	 * 
	 * @author TechFinium
	 * @see LearnerMonitoringSurvey
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> allLearnerMonitoringSurvey() throws Exception {
		return (List<LearnerMonitoringSurvey>) super.getList("select o from LearnerMonitoringSurvey o");
	}

	/**
	 * Get all LearnerMonitoringSurvey between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see LearnerMonitoringSurvey
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> allLearnerMonitoringSurvey(Long from, int noRows) throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LearnerMonitoringSurvey>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see LearnerMonitoringSurvey
	 * @return a {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             global exception
	 */
	public LearnerMonitoringSurvey findByKey(Long id) throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LearnerMonitoringSurvey) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerMonitoringSurvey by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see LearnerMonitoringSurvey
	 * @return a list of {@link haj.com.entity.LearnerMonitoringSurvey}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> findByName(String description) throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LearnerMonitoringSurvey>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring, WorkplaceSurveyType workplaceSurveyType) throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o where o.workplaceMonitoring.id = :workplaceMonitoringID and o.workplaceSurveyType = :workplaceSurveyType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		parameters.put("workplaceSurveyType", workplaceSurveyType);
		return (List<LearnerMonitoringSurvey>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<LearnerMonitoringSurvey> findLookups() throws Exception {
		String hql = "select o from LearnerMonitoringSurvey o where o.workplaceMonitoring is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<LearnerMonitoringSurvey>) super.getList(hql, parameters);
	}

}
