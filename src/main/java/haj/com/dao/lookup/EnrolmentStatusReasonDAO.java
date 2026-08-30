package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.EnrolmentStatusReason;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusReasonDAO.
 */
public class EnrolmentStatusReasonDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception global exception
	 * @see    EnrolmentStatusReason
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatusReason> allEnrolmentStatusReason() throws Exception {
		return (List<EnrolmentStatusReason>)super.getList("select o from EnrolmentStatusReason o");
	}

	/**
	 * Get all EnrolmentStatusReason between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception global exception
	 * @see    EnrolmentStatusReason
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatusReason> allEnrolmentStatusReason(Long from, int noRows) throws Exception {
	 	String hql = "select o from EnrolmentStatusReason o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EnrolmentStatusReason>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception global exception
	 * @see    EnrolmentStatusReason
	 */
	public EnrolmentStatusReason findByKey(Long id) throws Exception {
	 	String hql = "select o from EnrolmentStatusReason o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EnrolmentStatusReason)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EnrolmentStatusReason by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception global exception
	 * @see    EnrolmentStatusReason
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatusReason> findByName(String description) throws Exception {
	 	String hql = "select o from EnrolmentStatusReason o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EnrolmentStatusReason>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param enrolmentStatusReason the enrolment status reason
	 * @return the enrolment status reason
	 * @throws Exception the exception
	 */
	public EnrolmentStatusReason findUniqueCode(EnrolmentStatusReason enrolmentStatusReason) throws Exception {
	 	String hql = "select o from EnrolmentStatusReason o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (enrolmentStatusReason.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", enrolmentStatusReason.getId());
	 	}
	   
	    parameters.put("code", enrolmentStatusReason.getCode());
		return (EnrolmentStatusReason)super.getUniqueResult(hql, parameters);
	}
}

