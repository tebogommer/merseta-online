package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SICCode;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SICCodeDAO.
 */
public class SICCodeDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SICCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception             global exception
	 * @see SICCode
	 */
	@SuppressWarnings("unchecked")
	public List<SICCode> allSICCode() throws Exception {
		return (List<SICCode>) super.getList("select o from SICCode o");
	}

	/**
	 * Get all SICCode between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception             global exception
	 * @see SICCode
	 */
	@SuppressWarnings("unchecked")
	public List<SICCode> allSICCode(Long from, int noRows) throws Exception {
		String hql = "select o from SICCode o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SICCode>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.SICCode}
	 * @throws Exception             global exception
	 * @see SICCode
	 */
	public SICCode findByKey(Long id) throws Exception {
		String hql = "select o from SICCode o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SICCode) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SICCode by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.SICCode}
	 * @throws Exception             global exception
	 * @see SICCode
	 */
	@SuppressWarnings("unchecked")
	public List<SICCode> findByName(String description) throws Exception {
		String hql = "select o from SICCode o where o.description like  :description or o.code like :description order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description.trim() + "%");
		return (List<SICCode>) super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param sICCode the s IC code
	 * @return a {@link haj.com.entity.SICCode}
	 * @throws Exception             global exception
	 * @see SICCode
	 */
	
    public SICCode findUniqueCode(SICCode sICCode) throws Exception {
	 	String hql = "select o from SICCode o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (sICCode.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", sICCode.getId());
	 	}
	   
	    parameters.put("code", sICCode.getCode());
		return (SICCode)super.getUniqueResult(hql, parameters);
	}
   
    public SICCode findByCode(String code) throws Exception {
	 	String hql = "select o from SICCode o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (SICCode)super.getUniqueResult(hql, parameters);
	}
}
