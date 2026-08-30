package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesDAO.
 */
public class TestDAO extends AbstractDAO {

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
	 * Get all Sites.
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 * global exception
	 * @see Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> allSites() throws Exception {
		return (List<Sites>) super.getList("select o from Sites o");
	}

	@SuppressWarnings("unchecked")
	public List<Users> allUsers() throws Exception {
		return (List<Users>) super.getList("select o from Users o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompany() throws Exception {
		return (List<Company>) super.getList("select o from Company o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> allAddress() throws Exception {
		return (List<Address>) super.getList("select o from Address o");
	}

	/**
	 * Get all Sites between from and noRows.
	 * @author TechFinium
	 * @param from
	 * the from
	 * @param noRows
	 * the no rows
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 * global exception
	 * @see Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> allSites(Long from, int noRows) throws Exception {
		String              hql        = "select o from Sites o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Sites>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.Sites}
	 * @throws Exception
	 * global exception
	 * @see Sites
	 */
	public Sites findByKey(Long id) throws Exception {
		// String hql = "select o from Sites o where o.id = :id " ;
		String              hql        = "select o from Sites o left join fetch o.registeredAddress ra left join fetch ra.municipality  where o.id = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Sites) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Sites by description.
	 * @author TechFinium
	 * @param description
	 * the description
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 * global exception
	 * @see Sites
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> findByName(String description) throws Exception {
		String              hql        = "select o from Sites o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Sites>) super.getList(hql, parameters);
	}
}