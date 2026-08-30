package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.entity.lookup.EnrolmentType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentTypeDAO.
 */
public class EnrolmentTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EnrolmentType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception global exception
	 * @see    EnrolmentType
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentType> allEnrolmentType() throws Exception {
		return (List<EnrolmentType>)super.getList("select o from EnrolmentType o");
	}

	/**
	 * Get all EnrolmentType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception global exception
	 * @see    EnrolmentType
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentType> allEnrolmentType(Long from, int noRows) throws Exception {
	 	String hql = "select o from EnrolmentType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EnrolmentType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentType}
	 * @throws Exception global exception
	 * @see    EnrolmentType
	 */
	public EnrolmentType findByKey(Long id) throws Exception {
	 	String hql = "select o from EnrolmentType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EnrolmentType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EnrolmentType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EnrolmentType}
	 * @throws Exception global exception
	 * @see    EnrolmentType
	 */
	@SuppressWarnings("unchecked")
	public List<EnrolmentType> findByName(String description) throws Exception {
	 	String hql = "select o from EnrolmentType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EnrolmentType>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by primary code.
	 *
	 * @author TechFinium
	 * @param enrolmentType the enrolment type
	 * @return a {@link haj.com.entity.employerApprovalStatus}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	public EnrolmentType findUniqueCode(EnrolmentType enrolmentType) throws Exception {
	 	String hql = "select o from EnrolmentType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (enrolmentType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", enrolmentType.getId());
	 	}
	   
	    parameters.put("code", enrolmentType.getCode());
		return (EnrolmentType)super.getUniqueResult(hql, parameters);
	}
}

