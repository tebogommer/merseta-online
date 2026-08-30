package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.LearnerInduction;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LearnerInductionDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnerInduction
 	 * @author TechFinium 
 	 * @see    LearnerInduction
 	 * @return a list of {@link haj.com.entity.LearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerInduction> allLearnerInduction() throws Exception {
		return (List<LearnerInduction>)super.getList("select o from LearnerInduction o");
	}

	/**
	 * Get all LearnerInduction between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LearnerInduction
 	 * @return a list of {@link haj.com.entity.LearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerInduction> allLearnerInduction(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnerInduction o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LearnerInduction>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LearnerInduction
 	 * @return a {@link haj.com.entity.LearnerInduction}
 	 * @throws Exception global exception
 	 */
	public LearnerInduction findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnerInduction o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LearnerInduction)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnerInduction by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LearnerInduction
  	 * @return a list of {@link haj.com.entity.LearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnerInduction> findByName(String description) throws Exception {
	 	String hql = "select o from LearnerInduction o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnerInduction>)super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<LearnerInduction> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) throws Exception {
	 	String hql = "select o from LearnerInduction o where o.workplaceMonitoring.id = :workplaceMonitoringID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workplaceMonitoringID", workplaceMonitoring.getId());
		return (List<LearnerInduction>)super.getList(hql, parameters);
	}
}

