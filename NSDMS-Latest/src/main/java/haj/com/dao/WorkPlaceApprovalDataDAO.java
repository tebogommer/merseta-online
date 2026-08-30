package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkPlaceApprovalData;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkPlaceApprovalDataDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkPlaceApprovalData
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalData
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalData> allWorkPlaceApprovalData() throws Exception {
		return (List<WorkPlaceApprovalData>)super.getList("select o from WorkPlaceApprovalData o");
	}

	/**
	 * Get all WorkPlaceApprovalData between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkPlaceApprovalData
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalData> allWorkPlaceApprovalData(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalData o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkPlaceApprovalData>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkPlaceApprovalData
 	 * @return a {@link haj.com.entity.WorkPlaceApprovalData}
 	 * @throws Exception global exception
 	 */
	public WorkPlaceApprovalData findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalData o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkPlaceApprovalData)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkPlaceApprovalData by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkPlaceApprovalData
  	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalData}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalData> findByName(String description) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalData o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkPlaceApprovalData>)super.getList(hql, parameters);
	}
}

