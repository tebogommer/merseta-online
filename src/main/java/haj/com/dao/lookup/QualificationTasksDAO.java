package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.QualificationTasks;

public class QualificationTasksDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationTasks
 	 * @author TechFinium 
 	 * @see    QualificationTasks
 	 * @return a list of {@link haj.com.entity.QualificationTasks}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationTasks> allQualificationTasks() throws Exception {
		return (List<QualificationTasks>)super.getList("select o from QualificationTasks o");
	}

	/**
	 * Get all QualificationTasks between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QualificationTasks
 	 * @return a list of {@link haj.com.entity.QualificationTasks}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationTasks> allQualificationTasks(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationTasks o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationTasks>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QualificationTasks
 	 * @return a {@link haj.com.entity.QualificationTasks}
 	 * @throws Exception global exception
 	 */
	public QualificationTasks findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationTasks o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationTasks)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationTasks by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QualificationTasks
  	 * @return a list of {@link haj.com.entity.QualificationTasks}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationTasks> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationTasks o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationTasks>)super.getList(hql, parameters);
	}
}

