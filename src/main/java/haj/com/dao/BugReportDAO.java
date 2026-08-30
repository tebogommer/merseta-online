package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.BugReport;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BugReportDAO.
 */
public class BugReportDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}


	/**
	 * Get all BugReport.
	 *
	 * @author TechFinium
	 * @return List<BugReport>
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	@SuppressWarnings("unchecked")
	public List<BugReport> allBugReport() throws Exception {
		return (List<BugReport>)super.getList("select o from BugReport o");
	}


	/**
	 * All bug report.
	 *
	 * @param from the from
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<BugReport> allBugReport(Long from, int noRows) throws Exception {
	 	String hql = "select o from BugReport o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<BugReport>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return BugReport
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	public BugReport findByKey(Long id) throws Exception {
	 	String hql = "select o from BugReport o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (BugReport)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find BugReport by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return List<BugReport>
	 * @throws Exception the exception
	 * @see    BugReport
	 */
	@SuppressWarnings("unchecked")
	public List<BugReport> findByName(String description) throws Exception {
	 	String hql = "select o from BugReport o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<BugReport>)super.getList(hql, parameters);
	}
}

