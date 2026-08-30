package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ScarcityReason;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ScarcityReasonDAO.
 */
public class ScarcityReasonDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ScarcityReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception             global exception
	 * @see ScarcityReason
	 */
	@SuppressWarnings("unchecked")
	public List<ScarcityReason> allScarcityReason() throws Exception {
		return (List<ScarcityReason>) super.getList("select o from ScarcityReason o");
	}

	/**
	 * Get all ScarcityReason between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception             global exception
	 * @see ScarcityReason
	 */
	@SuppressWarnings("unchecked")
	public List<ScarcityReason> allScarcityReason(Long from, int noRows) throws Exception {
		String hql = "select o from ScarcityReason o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<ScarcityReason>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.ScarcityReason}
	 * @throws Exception             global exception
	 * @see ScarcityReason
	 */
	public ScarcityReason findByKey(Long id) throws Exception {
		String hql = "select o from ScarcityReason o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (ScarcityReason) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ScarcityReason by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception             global exception
	 * @see ScarcityReason
	 */
	@SuppressWarnings("unchecked")
	public List<ScarcityReason> findByName(String description) throws Exception {
		String hql = "select o from ScarcityReason o where o.description like  :description order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ScarcityReason>) super.getList(hql, parameters);
	}
	
	/**
	 * Find ScarcityReason by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param scarcityReason the scarcity reason
	 * @return the scarcity reason
	 * @throws Exception the exception
	 */
	public ScarcityReason findUniqueCode(ScarcityReason scarcityReason) throws Exception {
	 	String hql = "select o from ScarcityReason o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (scarcityReason.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", scarcityReason.getId());
	 	}
	   
	    parameters.put("code", scarcityReason.getCode());
		return (ScarcityReason)super.getUniqueResult(hql, parameters);
	}
}
