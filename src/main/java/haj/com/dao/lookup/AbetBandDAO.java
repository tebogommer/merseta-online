package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AbetBandDAO.
 */
public class AbetBandDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AbetBand.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	@SuppressWarnings("unchecked")
	public List<AbetBand> allAbetBand() throws Exception {
		return (List<AbetBand>)super.getList("select o from AbetBand o");
	}

	/**
	 * Get all AbetBand between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	@SuppressWarnings("unchecked")
	public List<AbetBand> allAbetBand(Long from, int noRows) throws Exception {
	 	String hql = "select o from AbetBand o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AbetBand>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AbetBand}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	public AbetBand findByKey(Long id) throws Exception {
	 	String hql = "select o from AbetBand o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AbetBand)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AbetBand by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	@SuppressWarnings("unchecked")
	public List<AbetBand> findByName(String description) throws Exception {
	 	String hql = "select o from AbetBand o where o.description like :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AbetBand>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param abetBand the abet band
	 * @return the abet band
	 * @throws Exception the exception
	 */
	public AbetBand findUniqueCode(AbetBand abetBand) throws Exception {
	 	String hql = "select o from AbetBand o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (abetBand.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", abetBand.getId());
	 	}
	   
	    parameters.put("code", abetBand.getCode());
		return (AbetBand)super.getUniqueResult(hql, parameters);
	}
}

