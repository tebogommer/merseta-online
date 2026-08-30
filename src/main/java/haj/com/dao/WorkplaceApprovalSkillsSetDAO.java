package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceApprovalSkillsSet;

public class WorkplaceApprovalSkillsSetDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceApprovalSkillsSet
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalSkillsSet> allWorkplaceApprovalSkillsSet() throws Exception {
		return (List<WorkplaceApprovalSkillsSet>)super.getList("select o from WorkplaceApprovalSkillsSet o");
	}

	/**
	 * Get all WorkplaceApprovalSkillsSet between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceApprovalSkillsSet
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalSkillsSet> allWorkplaceApprovalSkillsSet(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceApprovalSkillsSet o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceApprovalSkillsSet>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceApprovalSkillsSet
 	 * @return a {@link haj.com.entity.WorkplaceApprovalSkillsSet}
 	 * @throws Exception global exception
 	 */
	public WorkplaceApprovalSkillsSet findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceApprovalSkillsSet o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceApprovalSkillsSet)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceApprovalSkillsSet by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceApprovalSkillsSet
  	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalSkillsSet> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceApprovalSkillsSet o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceApprovalSkillsSet>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalSkillsSet> findByWorkplaceapproval(Long workplaceapprovalID) {
		String hql = "select o from WorkplaceApprovalSkillsSet o where o.workPlaceApproval.id = :workplaceapprovalID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workplaceapprovalID", workplaceapprovalID);
		return (List<WorkplaceApprovalSkillsSet>)super.getList(hql, parameters);
	}
}

