package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceApprovalUnitStandart;

public class WorkplaceApprovalUnitStandartDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceApprovalUnitStandart
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalUnitStandart> allWorkplaceApprovalUnitStandart() throws Exception {
		return (List<WorkplaceApprovalUnitStandart>)super.getList("select o from WorkplaceApprovalUnitStandart o");
	}

	/**
	 * Get all WorkplaceApprovalUnitStandart between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceApprovalUnitStandart
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalUnitStandart> allWorkplaceApprovalUnitStandart(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceApprovalUnitStandart o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceApprovalUnitStandart>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceApprovalUnitStandart
 	 * @return a {@link haj.com.entity.WorkplaceApprovalUnitStandart}
 	 * @throws Exception global exception
 	 */
	public WorkplaceApprovalUnitStandart findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceApprovalUnitStandart o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceApprovalUnitStandart)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceApprovalUnitStandart by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceApprovalUnitStandart
  	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalUnitStandart> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceApprovalUnitStandart o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceApprovalUnitStandart>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalUnitStandart> findByWorkplaceapproval(Long workPlaceApprovalID) {
		String hql = "select o from WorkplaceApprovalUnitStandart o where o.workPlaceApproval.id = :workPlaceApprovalID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workPlaceApprovalID", workPlaceApprovalID);
		return (List<WorkplaceApprovalUnitStandart>)super.getList(hql, parameters);
	}
}

