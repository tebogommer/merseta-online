package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDAO.
 */
public class HostingCompanyDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception             global exception
	 * @see HostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompany> allHostingCompany() throws Exception {
		return (List<HostingCompany>) super.getList("select o from HostingCompany o");
	}
	
	/**
	 * Get all HostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception             global exception
	 * @see HostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompany> allHostingCompanyFirst() throws Exception {
		return (List<HostingCompany>) super.getList("select o from HostingCompany o", 1);
	}

	/**
	 * Get all HostingCompany between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception             global exception
	 * @see HostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompany> allHostingCompany(Long from, int noRows) throws Exception {
		String hql = "select o from HostingCompany o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<HostingCompany>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.HostingCompany}
	 * @throws Exception             global exception
	 * @see HostingCompany
	 */
	public HostingCompany findByKey(Long id) throws Exception {
		String hql = "select o from HostingCompany o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (HostingCompany) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HostingCompany by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.HostingCompany}
	 * @throws Exception             global exception
	 * @see HostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<HostingCompany> findByName(String description) throws Exception {
		String hql = "select o from HostingCompany o where (o.companyName like  :description or o.companyRegNumber like  :description) order by o.createDate asc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description.trim() + "%");
		return (List<HostingCompany>) super.getList(hql, parameters);
	}
}
