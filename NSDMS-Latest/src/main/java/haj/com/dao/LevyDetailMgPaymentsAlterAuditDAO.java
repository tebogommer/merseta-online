package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.LevyDetailMgPaymentsAlterAudit;

public class LevyDetailMgPaymentsAlterAuditDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LevyDetailMgPaymentsAlterAudit
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LevyDetailMgPaymentsAlterAudit> allLevyDetailMgPaymentsAlterAudit() throws Exception {
		return (List<LevyDetailMgPaymentsAlterAudit>)super.getList("select o from LevyDetailMgPaymentsAlterAudit o");
	}

	/**
	 * Get all LevyDetailMgPaymentsAlterAudit between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LevyDetailMgPaymentsAlterAudit> allLevyDetailMgPaymentsAlterAudit(Long from, int noRows) throws Exception {
	 	String hql = "select o from LevyDetailMgPaymentsAlterAudit o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LevyDetailMgPaymentsAlterAudit>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 * @return a {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
 	 * @throws Exception global exception
 	 */
	public LevyDetailMgPaymentsAlterAudit findByKey(Long id) throws Exception {
	 	String hql = "select o from LevyDetailMgPaymentsAlterAudit o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LevyDetailMgPaymentsAlterAudit)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LevyDetailMgPaymentsAlterAudit by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LevyDetailMgPaymentsAlterAudit
  	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LevyDetailMgPaymentsAlterAudit> findByName(String description) throws Exception {
	 	String hql = "select o from LevyDetailMgPaymentsAlterAudit o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LevyDetailMgPaymentsAlterAudit>)super.getList(hql, parameters);
	}
}

