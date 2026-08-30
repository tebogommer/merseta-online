package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.EconomicStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EconomicStatusDAO.
 */
public class EconomicStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EconomicStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception global exception
	 * @see    EconomicStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EconomicStatus> allEconomicStatus() throws Exception {
		return (List<EconomicStatus>)super.getList("select o from EconomicStatus o");
	}

	/**
	 * Get all EconomicStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception global exception
	 * @see    EconomicStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EconomicStatus> allEconomicStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from EconomicStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EconomicStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EconomicStatus}
	 * @throws Exception global exception
	 * @see    EconomicStatus
	 */
	public EconomicStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from EconomicStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EconomicStatus)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the economic status
	 * @throws Exception the exception
	 */
	public EconomicStatus findByCode(String code) throws Exception {
	 	String hql = "select o from EconomicStatus o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (EconomicStatus)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find EconomicStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EconomicStatus}
	 * @throws Exception global exception
	 * @see    EconomicStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EconomicStatus> findByName(String description) throws Exception {
	 	String hql = "select o from EconomicStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EconomicStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param economicStatus the economic status
	 * @return the economic status
	 * @throws Exception the exception
	 */
	public EconomicStatus findUniqueCode(EconomicStatus economicStatus) throws Exception {
	 	String hql = "select o from EconomicStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (economicStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", economicStatus.getId());
	 	}
	   
	    parameters.put("code", economicStatus.getCode());
		return (EconomicStatus)super.getUniqueResult(hql, parameters);
	}
}

