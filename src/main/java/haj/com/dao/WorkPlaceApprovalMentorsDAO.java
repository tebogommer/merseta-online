package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkPlaceApprovalMentorsDAO.
 */
public class WorkPlaceApprovalMentorsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkPlaceApprovalMentors.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception global exception
	 * @see    WorkPlaceApprovalMentors
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalMentors> allWorkPlaceApprovalMentors() throws Exception {
		return (List<WorkPlaceApprovalMentors>)super.getList("select o from WorkPlaceApprovalMentors o");
	}

	/**
	 * Get all WorkPlaceApprovalMentors between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception global exception
	 * @see    WorkPlaceApprovalMentors
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalMentors> allWorkPlaceApprovalMentors(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalMentors o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkPlaceApprovalMentors>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception global exception
	 * @see    WorkPlaceApprovalMentors
	 */
	public WorkPlaceApprovalMentors findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalMentors o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkPlaceApprovalMentors)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkPlaceApprovalMentors by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalMentors}
	 * @throws Exception global exception
	 * @see    WorkPlaceApprovalMentors
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalMentors> findByName(String description) throws Exception {
	 	String hql = "select o from WorkPlaceApprovalMentors o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkPlaceApprovalMentors>)super.getList(hql, parameters);
	}
}

