package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.DisabilityStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabilityStatusDAO.
 */
public class DisabilityStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DisabilityStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> allDisabilityStatus() throws Exception {
		return (List<DisabilityStatus>)super.getList("select o from DisabilityStatus o");
	}

	/**
	 * Get all DisabilityStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> allDisabilityStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from DisabilityStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DisabilityStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	public DisabilityStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DisabilityStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the disability status
	 * @throws Exception the exception
	 */
	public DisabilityStatus findByCode(String code) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (DisabilityStatus)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find DisabilityStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> findByName(String description) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.description like  :description or o.code like description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DisabilityStatus>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> findByNameCanSelect(String description) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.description like  :description or o.code like description and o.canSelect = true" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DisabilityStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find DisabilityStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> completeDisabilityStatusExcludeNone(String description, Long id) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.id <> :id and (o.description like  :description or o.code like description)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    parameters.put("id", id);
		return (List<DisabilityStatus>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> completeDisabilityCanSelect(String description, Boolean canSelect) throws Exception {
	 	String hql = "select o from DisabilityStatus o where o.canSelect = :canSelect and (o.description like  :description or o.code like description)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    parameters.put("canSelect", canSelect);
		return (List<DisabilityStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param disabilityStatus the disability status
	 * @return a {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception global exception
	 * @see    DisabilityStatus
	 */
	  public DisabilityStatus findUniqueCode(DisabilityStatus disabilityStatus) throws Exception {
		 	String hql = "select o from DisabilityStatus o where o.code = :code " ;
		 	Map<String, Object> parameters = new Hashtable<String, Object>();
		 	if (disabilityStatus.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", disabilityStatus.getId());
		 	}
		   
		    parameters.put("code", disabilityStatus.getCode());
			return (DisabilityStatus)super.getUniqueResult(hql, parameters);
		}
}

