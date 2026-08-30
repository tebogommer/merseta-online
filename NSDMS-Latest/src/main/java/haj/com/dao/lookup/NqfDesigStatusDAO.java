package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NqfDesigStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfDesigStatusDAO.
 */
public class NqfDesigStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception global exception
	 * @see    NqfDesigStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NqfDesigStatus> allNqfDesigStatus() throws Exception {
		return (List<NqfDesigStatus>)super.getList("select o from NqfDesigStatus o");
	}

	/**
	 * Get all NqfDesigStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception global exception
	 * @see    NqfDesigStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NqfDesigStatus> allNqfDesigStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from NqfDesigStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NqfDesigStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception global exception
	 * @see    NqfDesigStatus
	 */
	public NqfDesigStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from NqfDesigStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NqfDesigStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NqfDesigStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception global exception
	 * @see    NqfDesigStatus
	 */
	@SuppressWarnings("unchecked")
	public List<NqfDesigStatus> findByName(String description) throws Exception {
	 	String hql = "select o from NqfDesigStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NqfDesigStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param nqfDesigStatus the nqf desig status
	 * @return the nqf desig status
	 * @throws Exception the exception
	 */
	public NqfDesigStatus findUniqueCode(NqfDesigStatus nqfDesigStatus) throws Exception {
	 	String hql = "select o from NqfDesigStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (nqfDesigStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", nqfDesigStatus.getId());
	 	}
	   
	    parameters.put("code", nqfDesigStatus.getCode());
		return (NqfDesigStatus)super.getUniqueResult(hql, parameters);
	}
}

