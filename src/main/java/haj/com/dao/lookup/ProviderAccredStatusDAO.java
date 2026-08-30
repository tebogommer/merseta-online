package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProviderAccredStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccredStatusDAO.
 */
public class ProviderAccredStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception global exception
	 * @see    ProviderAccredStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccredStatus> allProviderAccredStatus() throws Exception {
		return (List<ProviderAccredStatus>)super.getList("select o from ProviderAccredStatus o");
	}

	/**
	 * Get all ProviderAccredStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception global exception
	 * @see    ProviderAccredStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccredStatus> allProviderAccredStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderAccredStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderAccredStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception global exception
	 * @see    ProviderAccredStatus
	 */
	public ProviderAccredStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderAccredStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderAccredStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderAccredStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception global exception
	 * @see    ProviderAccredStatus
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccredStatus> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderAccredStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderAccredStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param providerAccredStatus the provider accred status
	 * @return a {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception global exception
	 * @see    ProviderAccredStatus
	 */
	
    public ProviderAccredStatus findUniqueCode(ProviderAccredStatus providerAccredStatus) throws Exception {
	 	String hql = "select o from ProviderAccredStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (providerAccredStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", providerAccredStatus.getId());
	 	}
	   
	    parameters.put("code", providerAccredStatus.getCode());
		return (ProviderAccredStatus)super.getUniqueResult(hql, parameters);
	}
}

