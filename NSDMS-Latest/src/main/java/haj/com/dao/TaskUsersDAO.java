package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskUsersDAO.
 */
public class TaskUsersDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TaskUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception global exception
	 * @see    TaskUsers
	 */
	@SuppressWarnings("unchecked")
	public List<TaskUsers> allTaskUsers() throws Exception {
		return (List<TaskUsers>)super.getList("select o from TaskUsers o");
	}

	/**
	 * Get all TaskUsers between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception global exception
	 * @see    TaskUsers
	 */
	@SuppressWarnings("unchecked")
	public List<TaskUsers> allTaskUsers(Long from, int noRows) throws Exception {
	 	String hql = "select o from TaskUsers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TaskUsers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TaskUsers}
	 * @throws Exception global exception
	 * @see    TaskUsers
	 */
	public TaskUsers findByKey(Long id) throws Exception {
	 	String hql = "select o from TaskUsers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TaskUsers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TaskUsers by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception global exception
	 * @see    TaskUsers
	 */
	@SuppressWarnings("unchecked")
	public List<TaskUsers> findByName(String description) throws Exception {
	 	String hql = "select o from TaskUsers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TaskUsers>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskUsers> findByTask(Tasks tasks) throws Exception {
	 	String hql = "select o from TaskUsers o where o.task.id = :taskID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("taskID", tasks.getId());
		return (List<TaskUsers>)super.getList(hql, parameters);
	}
	
//	@SuppressWarnings("unchecked")
//	public List<TaskUsers> findByTask(Tasks tasks) throws Exception {
//	 	String hql = "\r\n" + 
//	 			"select u.first_name from task_users tu\r\n" + 
//	 			"inner join users u on u.id = tu.user_id\r\n" + 
//	 			" where tu.task_id = 987;" ;
//	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("taskID", tasks.getId());
//		return (List<TaskUsers>)super.getList(hql, parameters);
//	}
	
	
}

