package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NonNqfIntervStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class NonNqfIntervStatusDAO.
 */
public class NonNqfIntervStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception global exception
	 * @see    NonNqfIntervStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NonNqfIntervStatus> allNonNqfIntervStatus() throws Exception {
		return (List<NonNqfIntervStatus>)super.getList("select o from NonNqfIntervStatus o");
	}

	/**
	 * Get all NonNqfIntervStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception global exception
	 * @see    NonNqfIntervStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NonNqfIntervStatus> allNonNqfIntervStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from NonNqfIntervStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NonNqfIntervStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception global exception
	 * @see    NonNqfIntervStatus
	 */
	public NonNqfIntervStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from NonNqfIntervStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonNqfIntervStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NonNqfIntervStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception global exception
	 * @see    NonNqfIntervStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NonNqfIntervStatus> findByName(String description) throws Exception {
	 	String hql = "select o from NonNqfIntervStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NonNqfIntervStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param nonNqfIntervStatus the non nqf interv status
	 * @return a {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception global exception
	 * @see    NonNqfIntervStatus
	 */
	
    public NonNqfIntervStatus findUniqueCode(NonNqfIntervStatus nonNqfIntervStatus) throws Exception {
	 	String hql = "select o from NonNqfIntervStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (nonNqfIntervStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", nonNqfIntervStatus.getId());
	 	}
	   
	    parameters.put("code", nonNqfIntervStatus.getCode());
		return (NonNqfIntervStatus)super.getUniqueResult(hql, parameters);
	}
}

