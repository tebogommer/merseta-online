package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProviderType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderTypeDAO.
 */
public class ProviderTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception global exception
	 * @see    ProviderType
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderType> allProviderType() throws Exception {
		return (List<ProviderType>)super.getList("select o from ProviderType o where o.appearOnWsp = true");
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderType> allProviderTypeNotWSP() throws Exception {
		return (List<ProviderType>)super.getList("select o from ProviderType o");
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderType> allProviderTypeForProviderRegistration() throws Exception {
		return (List<ProviderType>)super.getList("select o from ProviderType o where o.appearOnProviderReg = true");
	}

	/**
	 * Get all ProviderType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception global exception
	 * @see    ProviderType
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderType> allProviderType(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderType}
	 * @throws Exception global exception
	 * @see    ProviderType
	 */
	public ProviderType findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProviderType}
	 * @throws Exception global exception
	 * @see    ProviderType
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderType> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderType>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderType> findByDescription(String description) throws Exception {
	 	String hql = "select o from ProviderType o where o.description = :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description);
		return (List<ProviderType>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find ProviderType by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param providerType the provider type
	 * @return the provider type
	 * @throws Exception the exception
	 */
	public ProviderType findUniqueCode(ProviderType providerType) throws Exception {
	 	String hql = "select o from ProviderType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (providerType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", providerType.getId());
	 	}
	   
	    parameters.put("code", providerType.getCode());
		return (ProviderType)super.getUniqueResult(hql, parameters);
	}
}

