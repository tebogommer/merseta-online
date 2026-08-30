package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyProcessDAO.
 */
public class HostingCompanyProcessDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> allHostingCompanyProcess() throws Exception {
		return (List<HostingCompanyProcess>)super.getList("select o from HostingCompanyProcess o");
	}

	/**
	 * Get all HostingCompanyProcess between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> allHostingCompanyProcess(Long from, int noRows) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<HostingCompanyProcess>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	/**
	 * All hosting company process.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> allHostingCompanyProcess(HostingCompany hostingCompany) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o where o.hostingCompany.id = :hostingCompanyID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyID",	hostingCompany.getId());
		return (List<HostingCompanyProcess>)super.getList(hql, parameters);
	}
	
	/**
	 * All hosting company process.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @param processEnum
	 *            the process enum
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public HostingCompanyProcess allHostingCompanyProcess(HostingCompany hostingCompany, ConfigDocProcessEnum processEnum) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o where o.hostingCompany.id = :hostingCompanyID and o.workflowProcess = :processEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyID", hostingCompany.getId());
	    parameters.put("processEnum", processEnum);
		return (HostingCompanyProcess)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyProcess
	 */
	public HostingCompanyProcess findByKey(Long id) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o left join fetch o.nextProcess np where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HostingCompanyProcess)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HostingCompanyProcess by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyProcess
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> findByName(String description) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HostingCompanyProcess>)super.getList(hql, parameters);
	}
	
	/**
	 * Find previous HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param nextProcess the next process
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception global exception
	 * @see    HostingCompanyProcess
	 */
	@SuppressWarnings("unchecked")
	public HostingCompanyProcess findPreviousProcess(HostingCompanyProcess nextProcess) throws Exception {
	 	String hql = "select o from HostingCompanyProcess o where o.nextProcess.id = :nextProcessID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nextProcessID", nextProcess.getId());
		return (HostingCompanyProcess)super.getUniqueResult(hql, parameters);
	}
}

