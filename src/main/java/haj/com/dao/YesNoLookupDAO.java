package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.YesNoLookup;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class YesNoLookupDAO.
 */
public class YesNoLookupDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all YesNoLookup.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception global exception
	 * @see    YesNoLookup
	 */
	@SuppressWarnings("unchecked")
	public List<YesNoLookup> allYesNoLookup() throws Exception {
		return (List<YesNoLookup>)super.getList("select o from YesNoLookup o");
	}

	/**
	 * Get all YesNoLookup between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception global exception
	 * @see    YesNoLookup
	 */
	@SuppressWarnings("unchecked")
	public List<YesNoLookup> allYesNoLookup(Long from, int noRows) throws Exception {
	 	String hql = "select o from YesNoLookup o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<YesNoLookup>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.YesNoLookup}
	 * @throws Exception global exception
	 * @see    YesNoLookup
	 */
	public YesNoLookup findByKey(Long id) throws Exception {
	 	String hql = "select o from YesNoLookup o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (YesNoLookup)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find YesNoLookup by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception global exception
	 * @see    YesNoLookup
	 */
	@SuppressWarnings("unchecked")
	public List<YesNoLookup> findByName(String description) throws Exception {
	 	String hql = "select o from YesNoLookup o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<YesNoLookup>)super.getList(hql, parameters);
	}
}

