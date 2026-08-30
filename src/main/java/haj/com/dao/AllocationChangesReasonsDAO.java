package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.AllocationChangesReasons;
import haj.com.entity.DgAllocationParent;

public class AllocationChangesReasonsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AllocationChangesReasons
 	 * @author TechFinium 
 	 * @see    AllocationChangesReasons
 	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChangesReasons> allAllocationChangesReasons() throws Exception {
		return (List<AllocationChangesReasons>)super.getList("select o from AllocationChangesReasons o");
	}

	/**
	 * Get all AllocationChangesReasons between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AllocationChangesReasons
 	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChangesReasons> allAllocationChangesReasons(Long from, int noRows) throws Exception {
	 	String hql = "select o from AllocationChangesReasons o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AllocationChangesReasons>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	@SuppressWarnings("unchecked")
	public List<AllocationChangesReasons> findDGAllocationChangesReasons(DgAllocationParent dgAllocationParent) throws Exception {
	 	String hql = "select o from AllocationChangesReasons o where o.dgAllocationParent = :dgAllocationParentID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgAllocationParentID", dgAllocationParent);
		return (List<AllocationChangesReasons>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AllocationChangesReasons
 	 * @return a {@link haj.com.entity.AllocationChangesReasons}
 	 * @throws Exception global exception
 	 */
	public AllocationChangesReasons findByKey(Long id) throws Exception {
	 	String hql = "select o from AllocationChangesReasons o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AllocationChangesReasons)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AllocationChangesReasons by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AllocationChangesReasons
  	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AllocationChangesReasons> findByName(String description) throws Exception {
	 	String hql = "select o from AllocationChangesReasons o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AllocationChangesReasons>)super.getList(hql, parameters);
	}

	public AllocationChangesReasons findByDgAllocationParent(Long dgAllocationParentID) {
		String hql = "select o from AllocationChangesReasons o where o.dgAllocationParent.id = :dgAllocationParentID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgAllocationParentID", dgAllocationParentID);
		return (AllocationChangesReasons)super.getUniqueResult(hql, parameters);
	}
}

