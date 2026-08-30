package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearnerUsers;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyLearnerUsersDAO.
 */
public class CompanyLearnerUsersDAO extends AbstractDAO {

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
	 * Get all CompanyLearnerUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyLearnerUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> allCompanyLearnerUsers() throws Exception {
		return (List<CompanyLearnerUsers>) super.getList("select o from CompanyLearnerUsers o");
	}

	/**
	 * Get all CompanyLearnerUsers between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyLearnerUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> allCompanyLearnerUsers(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyLearnerUsers o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyLearnerUsers>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyLearnerUsers
	 */
	public CompanyLearnerUsers findByKey(Long id) throws Exception {
		String hql = "select o from CompanyLearnerUsers o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyLearnerUsers) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnerUsers by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyLearnerUsers
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> findByCompany(Long companyID) throws Exception {
		String hql = "select o from CompanyLearnerUsers o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<CompanyLearnerUsers>) super.getList(hql, parameters);
	}

	public CompanyLearnerUsers findByCompanyAndUser(Long companyID, Long userID) throws Exception {
		String hql = "select o from CompanyLearnerUsers o where o.company.id = :companyID and o.user.id = :userID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		parameters.put("userID", userID);
		return (CompanyLearnerUsers) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExtractErrorList> findExportErrors() throws Exception {
		String hql = "select o from ExtractErrorList o";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<ExtractErrorList>) super.getList(hql, parameters);
	}

	public int countCompanyLearnerUsers(Users user, Company company) {
		String hql = "select count(o) from CompanyLearnerUsers o where o.company.id = :companyId and o.user.id = :userId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("userId", user.getId());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}
