package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusDAO.
 */
public class EnrolmentStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception global exception
	 * @see    EnrolmentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> allEnrolmentStatus() throws Exception {
		return (List<EnrolmentStatus>)super.getList("select o from EnrolmentStatus o");
	}
	

	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> allEnrolmentStatusATR() throws Exception {
		return (List<EnrolmentStatus>)super.getList("select o from EnrolmentStatus o where o.appearOnATR = true");
	}
	
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> allEnrolmentStatusWSP() throws Exception {
		return (List<EnrolmentStatus>)super.getList("select o from EnrolmentStatus o where o.appearOnWSP = true");
	}

	/**
	 * Get all EnrolmentStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception global exception
	 * @see    EnrolmentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> allEnrolmentStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from EnrolmentStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EnrolmentStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception global exception
	 * @see    EnrolmentStatus
	 */
	public EnrolmentStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from EnrolmentStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EnrolmentStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EnrolmentStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception global exception
	 * @see    EnrolmentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> findByName(String description) throws Exception {
	 	String hql = "select o from EnrolmentStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EnrolmentStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param enrolmentStatus the enrolment status
	 * @return a {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception global exception
	 * @see    EnrolmentStatus
	 */
	public EnrolmentStatus findUniqueCode(EnrolmentStatus enrolmentStatus) throws Exception {
	 	String hql = "select o from EnrolmentStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (enrolmentStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", enrolmentStatus.getId());
	 	}
	   
	    parameters.put("code", enrolmentStatus.getCode());
		return (EnrolmentStatus)super.getUniqueResult(hql, parameters);
	}
}

