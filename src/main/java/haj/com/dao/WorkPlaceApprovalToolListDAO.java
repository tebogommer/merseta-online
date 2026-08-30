package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkPlaceApprovalToolList;

public class WorkPlaceApprovalToolListDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkPlaceApprovalToolList
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalToolList
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalToolList> allWorkPlaceApprovalToolList() throws Exception {
		return (List<WorkPlaceApprovalToolList>)super.getList("select o from WorkPlaceApprovalToolList o");
	}

	/**
	 * Get all WorkPlaceApprovalToolList between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkPlaceApprovalToolList
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalToolList> allWorkPlaceApprovalToolList(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalToolList o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkPlaceApprovalToolList>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkPlaceApprovalToolList
 	 * @return a {@link haj.com.entity.WorkPlaceApprovalToolList}
 	 * @throws Exception global exception
 	 */
	public WorkPlaceApprovalToolList findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalToolList o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkPlaceApprovalToolList)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkPlaceApprovalToolList by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkPlaceApprovalToolList
  	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalToolList> findByName(String description) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalToolList o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkPlaceApprovalToolList>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalToolList> findByworkplaceapproval(Long workplaceapprovalID) {
		String hql = "select o from WorkPlaceApprovalToolList o where o.workPlaceApproval.id = :workplaceapprovalID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workplaceapprovalID", workplaceapprovalID);
		return (List<WorkPlaceApprovalToolList>)super.getList(hql, parameters);
	}
}

