package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UpdateAuditTrail;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.framework.IDataEntity;
import haj.com.provider.MySQLProvider;

public class UpdateAuditTrailDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UpdateAuditTrail
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrail> allUpdateAuditTrail() throws Exception {
		return (List<UpdateAuditTrail>)super.getList("select o from UpdateAuditTrail o");
	}

	/**
	 * Get all UpdateAuditTrail between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UpdateAuditTrail
 	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrail> allUpdateAuditTrail(Long from, int noRows) throws Exception {
	 	String hql = "select o from UpdateAuditTrail o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UpdateAuditTrail>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UpdateAuditTrail
 	 * @return a {@link haj.com.entity.UpdateAuditTrail}
 	 * @throws Exception global exception
 	 */
	public UpdateAuditTrail findByKey(Long id) throws Exception {
	 	String hql = "select o from UpdateAuditTrail o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UpdateAuditTrail)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UpdateAuditTrail by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UpdateAuditTrail
  	 * @return a list of {@link haj.com.entity.UpdateAuditTrail}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrail> findByName(String description) throws Exception {
	 	String hql = "select o from UpdateAuditTrail o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UpdateAuditTrail>)super.getList(hql, parameters);
	}
	
	public IDataEntity findClass(String className, Long id) throws Exception {
	 	String hql = "select o from "+className+" o where o.id = :id" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (IDataEntity)super.getUniqueResult(hql, parameters);
	}
}

