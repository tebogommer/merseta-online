package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ProviderApplicationSuspensionDeActivated;

public class ProviderApplicationSuspensionDeActivatedDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderApplicationSuspensionDeActivated
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationSuspensionDeActivated> allProviderApplicationSuspensionDeActivated() throws Exception {
		return (List<ProviderApplicationSuspensionDeActivated>)super.getList("select o from ProviderApplicationSuspensionDeActivated o");
	}

	/**
	 * Get all ProviderApplicationSuspensionDeActivated between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationSuspensionDeActivated> allProviderApplicationSuspensionDeActivated(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderApplicationSuspensionDeActivated o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderApplicationSuspensionDeActivated>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 * @return a {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
 	 * @throws Exception global exception
 	 */
	public ProviderApplicationSuspensionDeActivated findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderApplicationSuspensionDeActivated o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderApplicationSuspensionDeActivated)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderApplicationSuspensionDeActivated by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ProviderApplicationSuspensionDeActivated
  	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationSuspensionDeActivated> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderApplicationSuspensionDeActivated o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderApplicationSuspensionDeActivated>)super.getList(hql, parameters);
	}
}

