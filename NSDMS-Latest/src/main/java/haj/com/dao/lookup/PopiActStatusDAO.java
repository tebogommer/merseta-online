package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.PopiActStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class PopiActStatusDAO.
 */
public class PopiActStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PopiActStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception global exception
	 * @see    PopiActStatus
	 */
	@SuppressWarnings("unchecked")
	public List<PopiActStatus> allPopiActStatus() throws Exception {
		return (List<PopiActStatus>)super.getList("select o from PopiActStatus o");
	}

	/**
	 * Get all PopiActStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception global exception
	 * @see    PopiActStatus
	 */
	@SuppressWarnings("unchecked")
	public List<PopiActStatus> allPopiActStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from PopiActStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PopiActStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PopiActStatus}
	 * @throws Exception global exception
	 * @see    PopiActStatus
	 */
	public PopiActStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from PopiActStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PopiActStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PopiActStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception global exception
	 * @see    PopiActStatus
	 */
	@SuppressWarnings("unchecked")
	public List<PopiActStatus> findByName(String description) throws Exception {
	 	String hql = "select o from PopiActStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PopiActStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by primary code.
	 *
	 * @author TechFinium
	 * @param popiActStatus the popi act status
	 * @return a {@link haj.com.entity.PopiActStatus}
	 * @throws Exception global exception
	 * @see    PopiActStatus
	 */
	public PopiActStatus findUniqueCode(PopiActStatus popiActStatus) throws Exception {
	 	String hql = "select o from PopiActStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (popiActStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", popiActStatus.getId());
	 	}
	   
	    parameters.put("code", popiActStatus.getCode());
		return (PopiActStatus)super.getUniqueResult(hql, parameters);
	}
}

