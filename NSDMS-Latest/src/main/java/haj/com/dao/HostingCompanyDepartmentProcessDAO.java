package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyProcess;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentProcessDAO.
 */
public class HostingCompanyDepartmentProcessDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentProcess> allHostingCompanyDepartmentProcess() throws Exception {
		return (List<HostingCompanyDepartmentProcess>)super.getList("select o from HostingCompanyDepartmentProcess o");
	}

	/**
	 * Get all HostingCompanyDepartmentProcess between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentProcess> allHostingCompanyDepartmentProcess(Long from, int noRows) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentProcess o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HostingCompanyDepartmentProcess>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public HostingCompanyDepartmentProcess findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentProcess o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyDepartmentProcess)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HostingCompanyDepartmentProcess by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentProcess> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyDepartmentProcess o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyDepartmentProcess>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find by department.
	 *
	 * @param id the id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentProcess> findByDepartment(Long id) throws Exception {
	 	String hql = "select  distinct(o) from HostingCompanyDepartmentProcess o left join fetch o.hostingCompanyProcess hcp left join fetch hcp.nextProcess np where o.hostingCompanyDepartments.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<HostingCompanyDepartmentProcess>)super.getList(hql, parameters);
	}
	
	/**
	 * Find not used.
	 *
	 * @param id the id
	 * @param hostingCompanyId the hosting company id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> findNotUsed(Long id, Long hostingCompanyId ) throws Exception {
	 	String hql = "select distinct(o) from HostingCompanyProcess o where o.workflowProcess is not null and o.hostingCompany.id =  :hostingCompanyId "
	 			+ " and o.id not in (select x.hostingCompanyProcess.id from HostingCompanyDepartmentProcess x  where x.hostingCompanyDepartments.id = :id ) " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
	    parameters.put("hostingCompanyId", hostingCompanyId);
		return (List<HostingCompanyProcess>)super.getList(hql, parameters);
	}
}

