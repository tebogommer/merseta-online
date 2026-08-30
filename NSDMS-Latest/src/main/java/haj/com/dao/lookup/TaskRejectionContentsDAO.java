package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TaskRejectionContents;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskRejectionContentsDAO.
 */
public class TaskRejectionContentsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception global exception
	 * @see    TaskRejectionContents
	 */
	@SuppressWarnings("unchecked")
	public List<TaskRejectionContents> allTaskRejectionContents() throws Exception {
		return (List<TaskRejectionContents>)super.getList("select o from TaskRejectionContents o");
	}

	/**
	 * Get all TaskRejectionContents between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception global exception
	 * @see    TaskRejectionContents
	 */
	@SuppressWarnings("unchecked")
	public List<TaskRejectionContents> allTaskRejectionContents(Long from, int noRows) throws Exception {
	 	String hql = "select o from TaskRejectionContents o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TaskRejectionContents>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception global exception
	 * @see    TaskRejectionContents
	 */
	public TaskRejectionContents findByKey(Long id) throws Exception {
	 	String hql = "select o from TaskRejectionContents o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TaskRejectionContents)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TaskRejectionContents by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception global exception
	 * @see    TaskRejectionContents
	 */
	@SuppressWarnings("unchecked")
	public List<TaskRejectionContents> findByName(String description) throws Exception {
	 	String hql = "select o from TaskRejectionContents o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TaskRejectionContents>)super.getList(hql, parameters);
	}
}

