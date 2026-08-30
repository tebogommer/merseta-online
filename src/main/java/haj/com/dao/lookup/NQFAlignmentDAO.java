package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NQFAlignment;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class NQFAlignmentDAO.
 */
public class NQFAlignmentDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NQFAlignment.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception global exception
	 * @see    NQFAlignment
	 */
	@SuppressWarnings("unchecked")
	public List<NQFAlignment> allNQFAlignment() throws Exception {
		return (List<NQFAlignment>)super.getList("select o from NQFAlignment o");
	}

	/**
	 * Get all NQFAlignment between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception global exception
	 * @see    NQFAlignment
	 */
	@SuppressWarnings("unchecked")
	public List<NQFAlignment> allNQFAlignment(Long from, int noRows) throws Exception {
	 	String hql = "select o from NQFAlignment o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NQFAlignment>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NQFAlignment}
	 * @throws Exception global exception
	 * @see    NQFAlignment
	 */
	public NQFAlignment findByKey(Long id) throws Exception {
	 	String hql = "select o from NQFAlignment o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NQFAlignment)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NQFAlignment by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.NQFAlignment}
	 * @throws Exception global exception
	 * @see    NQFAlignment
	 */
	@SuppressWarnings("unchecked")
	public List<NQFAlignment> findByName(String description) throws Exception {
	 	String hql = "select o from NQFAlignment o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NQFAlignment>)super.getList(hql, parameters);
	}
}

