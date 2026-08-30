package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LearnershipDevelopmentRegistrationDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + " left join fetch o.previousLearnership prvl " + " ";

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnershipDevelopmentRegistration
	 * 
	 * @author TechFinium
	 * @see LearnershipDevelopmentRegistration
	 * @return a list of
	 *         {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistration() throws Exception {
		return (List<LearnershipDevelopmentRegistration>) super.getList("select o from LearnershipDevelopmentRegistration o" +leftJoins);
	}

	/**
	 * Get all LearnershipDevelopmentRegistration between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see LearnershipDevelopmentRegistration
	 * @return a list of
	 *         {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistration(Long from, int noRows)
			throws Exception {
		String hql = "select o from LearnershipDevelopmentRegistration o "+leftJoins;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<LearnershipDevelopmentRegistration>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see LearnershipDevelopmentRegistration
	 * @return a {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             global exception
	 */
	public LearnershipDevelopmentRegistration findByKey(Long id) throws Exception {
		String hql = "select o from LearnershipDevelopmentRegistration o "+leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LearnershipDevelopmentRegistration) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnershipDevelopmentRegistration by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see LearnershipDevelopmentRegistration
	 * @return a list of
	 *         {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> findByName(String description) throws Exception {
		String hql = "select o from LearnershipDevelopmentRegistration o "+leftJoins+" where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LearnershipDevelopmentRegistration>) super.getList(hql, parameters);
	}
	
	/**
	 * JMB 07 05 2018
	 * 
	 * Find LearnershipDevelopmentRegistration by company Id
	 * 
	 * @author TechFinium
	 * @param companyId
	 * @see LearnershipDevelopmentRegistration
	 * @return a list of
	 *         {@link haj.com.entity.LearnershipDevelopmentRegistration}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> findByCompany(Long companyId) throws Exception {
		String hql = "select o from LearnershipDevelopmentRegistration o "+leftJoins+" where o.company.id = :companyId order by o.createDate desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<LearnershipDevelopmentRegistration>) super.getList(hql, parameters);
	}
	
	/**
	 * JMB 07 05 2018
	 * 
	 * Locates LearnershipDevelopmentRegistration by company id Lazy Load
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param companyId
	 * @return List<LearnershipDevelopmentRegistration>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> allLearnershipDevelopmentRegistrationByCompany(Class<LearnershipDevelopmentRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from LearnershipDevelopmentRegistration o " 
				+ leftJoins 
				+ " where o.company.id = :compId";
		filters.put("compId", companyId);
		return (List<LearnershipDevelopmentRegistration>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/**
	 * JMB 07 05 2018
	 * 
	 * Counts LearnershipDevelopmentRegistration by company id Lazy Load
	 * @param filters
	 * @param companyId
	 * @return long
	 * @throws Exception
	 */
	public long allLearnershipDevelopmentRegistrationByCompanyCount(Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select count(o) from LearnershipDevelopmentRegistration o  where o.company.id = :compId";
		filters.put("compId", companyId);
		return (long) super.getUniqueResult(hql, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LearnershipDevelopmentRegistration> findListLastApproved() throws Exception {
		ApprovalEnum approvalEnum=ApprovalEnum.Approved;
	 	String hql = "select o from LearnershipDevelopmentRegistration o where o.approvalEnum =:approvalEnum order by o.approvalDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("approvalEnum", approvalEnum);
		return (List<LearnershipDevelopmentRegistration>) super.getList(hql, parameters);
	}
}
