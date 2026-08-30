package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.AdministrationOfAQPLearners;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AdministrationOfAQPDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AdministrationOfAQP
	 * 
	 * @author TechFinium
	 * @see AdministrationOfAQP
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrationOfAQP> allAdministrationOfAQP() throws Exception {
		return (List<AdministrationOfAQP>) super.getList("select o from AdministrationOfAQP o");
	}

	/**
	 * Get all AdministrationOfAQP between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see AdministrationOfAQP
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrationOfAQP> allAdministrationOfAQP(Long from, int noRows) throws Exception {
		String hql = "select o from AdministrationOfAQP o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<AdministrationOfAQP>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see AdministrationOfAQP
	 * @return a {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             global exception
	 */
	public AdministrationOfAQP findByKey(Long id) throws Exception {
		String hql = "select o from AdministrationOfAQP o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AdministrationOfAQP) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AdministrationOfAQP by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see AdministrationOfAQP
	 * @return a list of {@link haj.com.entity.AdministrationOfAQP}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrationOfAQP> findByName(String description) throws Exception {
		String hql = "select o from AdministrationOfAQP o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<AdministrationOfAQP>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<AdministrationOfAQP> allAdministrationOfAQP(Users u, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from AdministrationOfAQP o where o.qualification.id in (select x.qualification.id from CompanyLearners x where x.user.id = :userid)";
		filters.put("userid", u.getId());
		return (List<AdministrationOfAQP>) super.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Users u, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AdministrationOfAQP o where o.qualification.id in (select x.qualification.id from CompanyLearners x where x.user.id = :userid)";
		filters.put("userid", u.getId());
		return super.countWhere(filters, hql);
	}

	public AdministrationOfAQPLearners findAdministrationOfAQPLearners(String rsaID, String passportNumber) throws Exception {
		String hql = "select o from AdministrationOfAQPLearners o where o.companyLearners.user.rsaIDNumber = :idNumber or o.companyLearners.user.passportNumber = :passportNumber";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNumber", rsaID);
		parameters.put("passportNumber", passportNumber);
		return (AdministrationOfAQPLearners) super.getUniqueResult(hql, parameters);
	}

}
