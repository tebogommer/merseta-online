package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.entity.lookup.EmployerApprovalStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployerApprovalStatusDAO.
 */
public class EmployerApprovalStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception global exception
	 * @see    EmployerApprovalStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EmployerApprovalStatus> allEmployerApprovalStatus() throws Exception {
		return (List<EmployerApprovalStatus>)super.getList("select o from EmployerApprovalStatus o");
	}

	/**
	 * Get all EmployerApprovalStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception global exception
	 * @see    EmployerApprovalStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EmployerApprovalStatus> allEmployerApprovalStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from EmployerApprovalStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EmployerApprovalStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception global exception
	 * @see    EmployerApprovalStatus
	 */
	public EmployerApprovalStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from EmployerApprovalStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EmployerApprovalStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EmployerApprovalStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception global exception
	 * @see    EmployerApprovalStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EmployerApprovalStatus> findByName(String description) throws Exception {
	 	String hql = "select o from EmployerApprovalStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EmployerApprovalStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by primary code.
	 *
	 * @author TechFinium
	 * @param employerApprovalStatus the employer approval status
	 * @return a {@link haj.com.entity.employerApprovalStatus}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	public EmployerApprovalStatus findUniqueCode(EmployerApprovalStatus employerApprovalStatus) throws Exception {
	 	String hql = "select o from EmployerApprovalStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (employerApprovalStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", employerApprovalStatus.getId());
	 	}
	   
	    parameters.put("code", employerApprovalStatus.getCode());
		return (EmployerApprovalStatus)super.getUniqueResult(hql, parameters);
	}
}

