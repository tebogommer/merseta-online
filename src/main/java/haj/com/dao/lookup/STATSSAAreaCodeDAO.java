package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.STATSSAAreaCode;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class STATSSAAreaCodeDAO.
 */
public class STATSSAAreaCodeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all STATSSAAreaCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception global exception
	 * @see    STATSSAAreaCode
	 */
	@SuppressWarnings("unchecked")
	public List<STATSSAAreaCode> allSTATSSAAreaCode() throws Exception {
		return (List<STATSSAAreaCode>)super.getList("select o from STATSSAAreaCode o");
	}

	/**
	 * Get all STATSSAAreaCode between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception global exception
	 * @see    STATSSAAreaCode
	 */
	@SuppressWarnings("unchecked")
	public List<STATSSAAreaCode> allSTATSSAAreaCode(Long from, int noRows) throws Exception {
	 	String hql = "select o from STATSSAAreaCode o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<STATSSAAreaCode>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception global exception
	 * @see    STATSSAAreaCode
	 */
	public STATSSAAreaCode findByKey(Long id) throws Exception {
	 	String hql = "select o from STATSSAAreaCode o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (STATSSAAreaCode)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find STATSSAAreaCode by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception global exception
	 * @see    STATSSAAreaCode
	 */
	@SuppressWarnings("unchecked")
	public List<STATSSAAreaCode> findByName(String description) throws Exception {
	 	String hql = "select o from STATSSAAreaCode o where o.description like :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<STATSSAAreaCode>)super.getList(hql, parameters, 10);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param STATSSAAreaCode the STATSSA area code
	 * @return a {@link haj.com.entity.STATSSAAreaCode}
	 * @throws Exception global exception
	 * @see    STATSSAAreaCode
	 */
	public STATSSAAreaCode findUniqueCode(STATSSAAreaCode STATSSAAreaCode) throws Exception {
	 	String hql = "select o from STATSSAAreaCode o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (STATSSAAreaCode.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", STATSSAAreaCode.getId());
	 	}
	   
	    parameters.put("code", STATSSAAreaCode.getCode());
		return (STATSSAAreaCode)super.getUniqueResult(hql, parameters);
	}
}

