package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.FLC;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class FLCDAO.
 */
public class FLCDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FLC.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception global exception
	 * @see    FLC
	 */
	@SuppressWarnings("unchecked")
	public List<FLC> allFLC() throws Exception {
		return (List<FLC>)super.getList("select o from FLC o");
	}

	/**
	 * Get all FLC between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception global exception
	 * @see    FLC
	 */
	@SuppressWarnings("unchecked")
	public List<FLC> allFLC(Long from, int noRows) throws Exception {
	 	String hql = "select o from FLC o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FLC>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FLC}
	 * @throws Exception global exception
	 * @see    FLC
	 */
	public FLC findByKey(Long id) throws Exception {
	 	String hql = "select o from FLC o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FLC)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FLC by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.FLC}
	 * @throws Exception global exception
	 * @see    FLC
	 */
	@SuppressWarnings("unchecked")
	public List<FLC> findByName(String description) throws Exception {
	 	String hql = "select o from FLC o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FLC>)super.getList(hql, parameters);
	}
}

