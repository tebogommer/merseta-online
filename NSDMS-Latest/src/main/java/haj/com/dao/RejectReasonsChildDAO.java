package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsChildDAO.
 */
public class RejectReasonsChildDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasonsChild
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> allRejectReasonsChild() throws Exception {
		return (List<RejectReasonsChild>) super.getList("select o from RejectReasonsChild o");
	}

	/**
	 * Get all RejectReasonsChild between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasonsChild
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> allRejectReasonsChild(Long from, int noRows) throws Exception {
		String hql = "select o from RejectReasonsChild o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<RejectReasonsChild>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasonsChild
	 */
	public RejectReasonsChild findByKey(Long id) throws Exception {
		String hql = "select o from RejectReasonsChild o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (RejectReasonsChild) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RejectReasonsChild by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasonsChild
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByName(String description) throws Exception {
		String hql = "select o from RejectReasonsChild o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findBySDF(Users users) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.user.id = :userID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userID", users.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByCompany(Company company) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByCompanyBankingDetails(Company company) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.company.id = :companyID and o.bankingDetails is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByCompanyBankingDetails(Company company, BankingDetails bankingDetails) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.company.id = :companyID and o.bankingDetails.id = :bankingDetailsID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("bankingDetailsID", bankingDetails.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByAM(AssessorModeratorApplication assessorModeratorApplication) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.assessorModeratorApplication.id = :assessorModeratorApplicationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("assessorModeratorApplicationID", assessorModeratorApplication.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByWsp(Wsp wsp) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from RejectReasonsChild o left join fetch o.rejectReasons where o.targetClass = :targetClass and o.targetKey = :targetKey";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass);
		return (List<RejectReasonsChild>) super.getList(hql, parameters);
	}
}
