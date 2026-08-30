package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.LegacyProviderApplicationAlterationAudit;

public class LegacyProviderApplicationAlterationAuditDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderApplicationAlterationAudit
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationAlterationAudit> allLegacyProviderApplicationAlterationAudit() throws Exception {
		return (List<LegacyProviderApplicationAlterationAudit>)super.getList("select o from LegacyProviderApplicationAlterationAudit o");
	}

	/**
	 * Get all LegacyProviderApplicationAlterationAudit between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationAlterationAudit> allLegacyProviderApplicationAlterationAudit(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationAlterationAudit o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderApplicationAlterationAudit>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 * @return a {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderApplicationAlterationAudit findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationAlterationAudit o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderApplicationAlterationAudit)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderApplicationAlterationAudit by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderApplicationAlterationAudit
  	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationAlterationAudit> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderApplicationAlterationAudit o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderApplicationAlterationAudit>)super.getList(hql, parameters);
	}
}

