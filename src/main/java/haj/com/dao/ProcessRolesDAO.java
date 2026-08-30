package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessRolesDAO.
 */
public class ProcessRolesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProcessRoles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> allProcessRoles() throws Exception {
		return (List<ProcessRoles>)super.getList("select o from ProcessRoles o");
	}

	/**
	 * Get all ProcessRoles between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> allProcessRoles(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProcessRoles o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProcessRoles>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	/**
	 * Get all ProcessRoles between from and noRows.
	 *
	 * @author TechFinium
	 * @param hostingCompanyProcess the hosting company process
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> allProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
	 	String hql = "select o from ProcessRoles o "
	 				+ "left join fetch o.hostingCompanyProcess "
	 				+ "left join fetch o.roles "
	 				+ "left join fetch o.nextTaskRole "
	 				+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 				+ "order by o.roleOrder" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", hostingCompanyProcess.getId());
		return (List<ProcessRoles>)super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findByConfigDoc(ConfigDocProcessEnum workflowProcess) throws Exception {
	 	String hql = "select o from ProcessRoles o "
	 				+ "left join fetch o.hostingCompanyProcess "
	 				+ "left join fetch o.roles "
	 				+ "left join fetch o.nextTaskRole "
	 				+ "where o.hostingCompanyProcess.workflowProcess = :workflowProcess "
	 				+ "order by o.roleOrder" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("workflowProcess", workflowProcess);
		return (List<ProcessRoles>)super.getList(hql, parameters);
	}
	
	/**
	 * Get all ProcessRoles between from and noRows.
	 *
	 * @author TechFinium
	 * @param hostingCompanyProcess the hosting company process
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> firstProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "order by o.roleOrder" ;
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", hostingCompanyProcess.getId());
		return (List<ProcessRoles>)super.getList(hql, parameters,1);
	}	
	
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findByPosition(HostingCompanyProcess hostingCompanyProcess, int position) throws Exception {
	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "order by o.roleOrder" ;
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", hostingCompanyProcess.getId());
		return (List<ProcessRoles>)super.getList(hql, parameters,position,1);
	}	
	
	
	
	/**
	 * Get all ProcessRoles between from and noRows.
	 *
	 * @author TechFinium
	 * @param hostingCompanyProcess the hosting company process
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> lastProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "order by o.roleOrder desc" ;
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", hostingCompanyProcess.getId());
		return (List<ProcessRoles>)super.getList(hql, parameters,1);
	}

	/**
	 * Find next process roles.
	 *
	 * @param processRoles the process roles
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findNextProcessRoles(ProcessRoles processRoles) throws Exception {
	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "and o.roleOrder > :roleOrder "
	 			+ "order by o.roleOrder ";
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", processRoles.getHostingCompanyProcess().getId());
	    parameters.put("roleOrder", processRoles.getRoleOrder());
		return (List<ProcessRoles>)super.getList(hql, parameters,1);
	}

	/**
	 * Find previous process roles.
	 *
	 * @param processRoles the process roles
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findPreviousProcessRoles(ProcessRoles processRoles) throws Exception {
	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "and o.roleOrder < :roleOrder "
	 			+ "order by o.roleOrder desc" ;
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", processRoles.getHostingCompanyProcess().getId());
	    parameters.put("roleOrder", processRoles.getRoleOrder());
		return (List<ProcessRoles>)super.getList(hql, parameters,1);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findPreviousProcessRoles(ProcessRoles processRoles, Integer roleOrder) throws Exception {	 	
		String hql = "select o from ProcessRoles o "
	 			+ "left join fetch o.hostingCompanyProcess "
	 			+ "left join fetch o.roles "
	 			+ "left join fetch o.nextTaskRole "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "and o.roleOrder = :roleOrder" ;	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", processRoles.getHostingCompanyProcess().getId());
	    parameters.put("roleOrder", processRoles.getRoleOrder());
		return (List<ProcessRoles>)super.getList(hql, parameters,1);
	}
	/**
	 * Find previous process roles count.
	 *
	 * @param processRoles the process roles
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findPreviousProcessRolesCount(ProcessRoles processRoles) throws Exception {
	 	
		String hql = "select count(o) from ProcessRoles o "
	 			+ "where o.hostingCompanyProcess.id = :hostingCompanyProcessID "
	 			+ "and o.roleOrder < :roleOrder "
	 			+ "order by o.roleOrder desc" ;
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyProcessID", processRoles.getHostingCompanyProcess().getId());
	    parameters.put("roleOrder", processRoles.getRoleOrder());
		return (long)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	public ProcessRoles findByKey(Long id) throws Exception {
	 	String hql = "select o from ProcessRoles o left join fetch o.hostingCompanyProcess left join fetch o.roles where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProcessRoles)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProcessRoles by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception global exception
	 * @see    ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> findByName(String description) throws Exception {
	 	String hql = "select o from ProcessRoles o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProcessRoles>)super.getList(hql, parameters);
	}
	
	/**
	 * All process roles.
	 *
	 * @param first the first
	 * @param pageSize the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters the filters
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> allProcessRoles(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ProcessRoles o left join fetch o.hostingCompanyProcess left join fetch o.roles left join fetch o.nextTaskRole order by o.roleOrder";
		return (List<ProcessRoles>)super.sortAndFilter(first,pageSize,sortField,sortOrder,filters, hql);
	}
}

