package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProviderClass;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderClassDAO.
 */
public class ProviderClassDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderClass.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception global exception
	 * @see    ProviderClass
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderClass> allProviderClass() throws Exception {
		return (List<ProviderClass>)super.getList("select o from ProviderClass o");
	}

	/**
	 * Get all ProviderClass between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception global exception
	 * @see    ProviderClass
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderClass> allProviderClass(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderClass o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderClass>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderClass}
	 * @throws Exception global exception
	 * @see    ProviderClass
	 */
	public ProviderClass findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderClass o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderClass)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderClass by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProviderClass}
	 * @throws Exception global exception
	 * @see    ProviderClass
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderClass> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderClass o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderClass>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderClass> findByDescription(String description) throws Exception {
	 	String hql = "select o from ProviderClass o where o.description = :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description);
		return (List<ProviderClass>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param providerClass the provider class
	 * @return the provider class
	 * @throws Exception the exception
	 */
	public ProviderClass findUniqueCode(ProviderClass providerClass) throws Exception {
	 	String hql = "select o from ProviderClass o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (providerClass.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", providerClass.getId());
	 	}
	   
	    parameters.put("code", providerClass.getCode());
		return (ProviderClass)super.getUniqueResult(hql, parameters);
	}
	
    public List<ProviderClass> allProviderClassExUnknownAndInterrim() throws Exception {
        return (List<ProviderClass>)super.getList("select o from ProviderClass o where o.id <> 1 and o.id <> 5");
    }

}

