package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UpdateAuditTrailChanges;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UpdateAuditTrailChangesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UpdateAuditTrailChanges
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrailChanges> allUpdateAuditTrailChanges() throws Exception {
		return (List<UpdateAuditTrailChanges>)super.getList("select o from UpdateAuditTrailChanges o");
	}

	/**
	 * Get all UpdateAuditTrailChanges between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UpdateAuditTrailChanges
 	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrailChanges> allUpdateAuditTrailChanges(Long from, int noRows) throws Exception {
	 	String hql = "select o from UpdateAuditTrailChanges o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UpdateAuditTrailChanges>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UpdateAuditTrailChanges
 	 * @return a {@link haj.com.entity.UpdateAuditTrailChanges}
 	 * @throws Exception global exception
 	 */
	public UpdateAuditTrailChanges findByKey(Long id) throws Exception {
	 	String hql = "select o from UpdateAuditTrailChanges o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UpdateAuditTrailChanges)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UpdateAuditTrailChanges by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UpdateAuditTrailChanges
  	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrailChanges> findByName(String description) throws Exception {
	 	String hql = "select o from UpdateAuditTrailChanges o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UpdateAuditTrailChanges>)super.getList(hql, parameters);
	}
}

