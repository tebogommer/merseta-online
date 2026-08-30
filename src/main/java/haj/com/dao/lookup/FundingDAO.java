package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Funding;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class FundingDAO.
 */
public class FundingDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Funding.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception             global exception
	 * @see Funding
	 */
	@SuppressWarnings("unchecked")
	public List<Funding> allFunding() throws Exception {
		return (List<Funding>) super.getList("select o from Funding o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Funding> allFunding(Boolean appearOnWSP) throws Exception {
		String hql = "select o from Funding o where o.appearOnWsp =:appearOnWSP";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("appearOnWSP", appearOnWSP);
		return (List<Funding>) super.getList(hql, parameters);
	}

	/**
	 * Get all Funding between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception             global exception
	 * @see Funding
	 */
	@SuppressWarnings("unchecked")
	public List<Funding> allFunding(Long from, int noRows) throws Exception {
		String hql = "select o from Funding o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Funding>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.Funding}
	 * @throws Exception             global exception
	 * @see Funding
	 */
	public Funding findByKey(Long id) throws Exception {
		String hql = "select o from Funding o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Funding) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the funding
	 * @throws Exception the exception
	 */
	public Funding findByCode(String code) throws Exception {
		String hql = "select o from Funding o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Funding) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find Funding by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.Funding}
	 * @throws Exception             global exception
	 * @see Funding
	 */
	@SuppressWarnings("unchecked")
	public List<Funding> findByName(String description) throws Exception {
		String hql = "select o from Funding o where o.description like  :description order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Funding>) super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param funding the funding
	 * @return a {@link haj.com.entity.Funding}
	 * @throws Exception  global exception
	 * @see Funding
	 */
	   public Funding findUniqueCode(Funding funding) throws Exception {
		 	String hql = "select o from Funding o where o.code = :code " ;
		 	Map<String, Object> parameters = new Hashtable<String, Object>();
		 	if (funding.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", funding.getId());
		 	}
		   
		    parameters.put("code", funding.getCode());
			return (Funding)super.getUniqueResult(hql, parameters);
		}

	@SuppressWarnings("unchecked")
	public List<Funding> findByExcludeMerSETAKey() throws Exception {
		String hql = "select o from Funding o where o.id not in (6,7)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<Funding>) super.getList(hql, parameters);
	}
}
