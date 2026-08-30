package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.GpBankAccountAlterAudit;

public class GpBankAccountAlterAuditDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GpBankAccountAlterAudit
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpBankAccountAlterAudit> allGpBankAccountAlterAudit() throws Exception {
		return (List<GpBankAccountAlterAudit>)super.getList("select o from GpBankAccountAlterAudit o");
	}

	/**
	 * Get all GpBankAccountAlterAudit between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GpBankAccountAlterAudit
 	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpBankAccountAlterAudit> allGpBankAccountAlterAudit(Long from, int noRows) throws Exception {
	 	String hql = "select o from GpBankAccountAlterAudit o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<GpBankAccountAlterAudit>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GpBankAccountAlterAudit
 	 * @return a {@link haj.com.entity.GpBankAccountAlterAudit}
 	 * @throws Exception global exception
 	 */
	public GpBankAccountAlterAudit findByKey(Long id) throws Exception {
	 	String hql = "select o from GpBankAccountAlterAudit o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (GpBankAccountAlterAudit)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GpBankAccountAlterAudit by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GpBankAccountAlterAudit
  	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpBankAccountAlterAudit> findByName(String description) throws Exception {
	 	String hql = "select o from GpBankAccountAlterAudit o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GpBankAccountAlterAudit>)super.getList(hql, parameters);
	}
}

