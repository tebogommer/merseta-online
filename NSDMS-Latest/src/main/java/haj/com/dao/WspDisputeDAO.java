package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Wsp;
import haj.com.entity.WspDispute;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WspDisputeDAO.
 */
public class WspDisputeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspDispute.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception global exception
	 * @see    WspDispute
	 */
	@SuppressWarnings("unchecked")
	public List<WspDispute> allWspDispute() throws Exception {
		return (List<WspDispute>)super.getList("select o from WspDispute o");
	}

	/**
	 * Get all WspDispute between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception global exception
	 * @see    WspDispute
	 */
	@SuppressWarnings("unchecked")
	public List<WspDispute> allWspDispute(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspDispute o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspDispute>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspDispute}
	 * @throws Exception global exception
	 * @see    WspDispute
	 */
	public WspDispute findByKey(Long id) throws Exception {
	 	String hql = "select o from WspDispute o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspDispute)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspDispute by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception global exception
	 * @see    WspDispute
	 */
	@SuppressWarnings("unchecked")
	public List<WspDispute> findByName(String description) throws Exception {
	 	String hql = "select o from WspDispute o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspDispute>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by wsp.
	 *
	 * @param wsp the wsp
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspDispute> findByWsp(Wsp wsp) throws Exception {
	 	String hql = "select o from WspDispute o left join fetch o.createUser left join fetch o.wsp where o.wsp.id = :wspID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspID", wsp.getId());
		return (List<WspDispute>)super.getList(hql, parameters);
	}
}

