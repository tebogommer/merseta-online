package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkPlaceApprovalSkillsProgramme;

public class WorkPlaceApprovalSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkPlaceApprovalSkillsProgramme
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSkillsProgramme> allWorkPlaceApprovalSkillsProgramme() throws Exception {
		return (List<WorkPlaceApprovalSkillsProgramme>)super.getList("select o from WorkPlaceApprovalSkillsProgramme o");
	}

	/**
	 * Get all WorkPlaceApprovalSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSkillsProgramme> allWorkPlaceApprovalSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkPlaceApprovalSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 * @return a {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public WorkPlaceApprovalSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkPlaceApprovalSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkPlaceApprovalSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkPlaceApprovalSkillsProgramme
  	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkPlaceApprovalSkillsProgramme>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSkillsProgramme> findByWorkplaceapproval(Long workPlaceApprovalID) {
		String hql = "select o from WorkPlaceApprovalSkillsProgramme o where o.workPlaceApproval.id = :workPlaceApprovalID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workPlaceApprovalID", workPlaceApprovalID);
		return (List<WorkPlaceApprovalSkillsProgramme>)super.getList(hql, parameters);
	}
}

