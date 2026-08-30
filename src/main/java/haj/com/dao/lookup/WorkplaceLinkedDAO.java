package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.WorkplaceLinked;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkplaceLinkedDAO.
 */
public class WorkplaceLinkedDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception global exception
	 * @see    WorkplaceLinked
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceLinked> allWorkplaceLinked() throws Exception {
		return (List<WorkplaceLinked>)super.getList("select o from WorkplaceLinked o");
	}

	/**
	 * Get all WorkplaceLinked between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception global exception
	 * @see    WorkplaceLinked
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceLinked> allWorkplaceLinked(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceLinked o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceLinked>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception global exception
	 * @see    WorkplaceLinked
	 */
	public WorkplaceLinked findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceLinked o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceLinked)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceLinked by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception global exception
	 * @see    WorkplaceLinked
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceLinked> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceLinked o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceLinked>)super.getList(hql, parameters);
	}
}

