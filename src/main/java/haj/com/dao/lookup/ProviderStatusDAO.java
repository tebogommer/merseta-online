package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProviderStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderStatusDAO.
 */
public class ProviderStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception global exception
	 * @see    ProviderStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderStatus> allProviderStatus() throws Exception {
		return (List<ProviderStatus>)super.getList("select o from ProviderStatus o");
	}

	/**
	 * Get all ProviderStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception global exception
	 * @see    ProviderStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderStatus> allProviderStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderStatus}
	 * @throws Exception global exception
	 * @see    ProviderStatus
	 */
	public ProviderStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception global exception
	 * @see    ProviderStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderStatus> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param providerStatus the provider status
	 * @return a {@link haj.com.entity.ProviderStatus}
	 * @throws Exception global exception
	 * @see    ProviderStatus
	 */
	public ProviderStatus findUniqueCode(ProviderStatus providerStatus) throws Exception {
	 	String hql = "select o from ProviderStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (providerStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", providerStatus.getId());
	 	}
	   
	    parameters.put("code", providerStatus.getCode());
		return (ProviderStatus)super.getUniqueResult(hql, parameters);
	}
	
	public ProviderStatus findByCode(String code) throws Exception {
	 	String hql = "select o from ProviderStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (ProviderStatus)super.getUniqueResult(hql, parameters);
	}
}


