package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyLearnersTermination;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersTerminationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersTermination
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTermination> allCompanyLearnersTermination() throws Exception {
		return (List<CompanyLearnersTermination>) super.getList("select o from CompanyLearnersTermination o");
	}

	/**
	 * Get all CompanyLearnersTermination between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see CompanyLearnersTermination
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTermination> allCompanyLearnersTermination(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyLearnersTermination o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<CompanyLearnersTermination>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyLearnersTermination
	 * @return a {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             global exception
	 */
	public CompanyLearnersTermination findByKey(Long id) throws Exception {
		String hql = "select o from CompanyLearnersTermination o left join fetch o.companyLearners cl left join fetch cl.company where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (CompanyLearnersTermination) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersTermination by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see CompanyLearnersTermination
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTermination> findByName(String description) throws Exception {
		String hql = "select o from CompanyLearnersTermination o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyLearnersTermination>) super.getList(hql, parameters);
	}
}
