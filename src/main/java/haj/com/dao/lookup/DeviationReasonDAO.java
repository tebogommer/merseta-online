package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.DeviationReason;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviationReasonDAO.
 */
public class DeviationReasonDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DeviationReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception global exception
	 * @see    DeviationReason
	 */
	@SuppressWarnings("unchecked")
	public List<DeviationReason> allDeviationReason() throws Exception {
		return (List<DeviationReason>)super.getList("select o from DeviationReason o");
	}

	/**
	 * Get all DeviationReason between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception global exception
	 * @see    DeviationReason
	 */
	@SuppressWarnings("unchecked")
	public List<DeviationReason> allDeviationReason(Long from, int noRows) throws Exception {
	 	String hql = "select o from DeviationReason o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DeviationReason>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DeviationReason}
	 * @throws Exception global exception
	 * @see    DeviationReason
	 */
	public DeviationReason findByKey(Long id) throws Exception {
	 	String hql = "select o from DeviationReason o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DeviationReason)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DeviationReason by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception global exception
	 * @see    DeviationReason
	 */
	@SuppressWarnings("unchecked")
	public List<DeviationReason> findByName(String description) throws Exception {
	 	String hql = "select o from DeviationReason o where o.description like :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DeviationReason>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param DeviationReason the abet band
	 * @return the abet band
	 * @throws Exception the exception
	 */
	public DeviationReason findUniqueCode(DeviationReason DeviationReason) throws Exception {
	 	String hql = "select o from DeviationReason o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (DeviationReason.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", DeviationReason.getId());
	 	}
	   
	    parameters.put("code", DeviationReason.getCode());
		return (DeviationReason)super.getUniqueResult(hql, parameters);
	}
}

