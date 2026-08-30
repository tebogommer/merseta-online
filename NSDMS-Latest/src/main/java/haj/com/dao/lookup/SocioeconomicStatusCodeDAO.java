package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SocioeconomicStatusCode;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SocioeconomicStatusCodeDAO.
 */
public class SocioeconomicStatusCodeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SocioeconomicStatusCode.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception global exception
	 * @see    SocioeconomicStatusCode
	 */
	@SuppressWarnings("unchecked")
	public List<SocioeconomicStatusCode> allSocioeconomicStatusCode() throws Exception {
		return (List<SocioeconomicStatusCode>)super.getList("select o from SocioeconomicStatusCode o");
	}

	/**
	 * Get all SocioeconomicStatusCode between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception global exception
	 * @see    SocioeconomicStatusCode
	 */
	@SuppressWarnings("unchecked")
	public List<SocioeconomicStatusCode> allSocioeconomicStatusCode(Long from, int noRows) throws Exception {
	 	String hql = "select o from SocioeconomicStatusCode o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SocioeconomicStatusCode>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception global exception
	 * @see    SocioeconomicStatusCode
	 */
	public SocioeconomicStatusCode findByKey(Long id) throws Exception {
	 	String hql = "select o from SocioeconomicStatusCode o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SocioeconomicStatusCode)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SocioeconomicStatusCode by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SocioeconomicStatusCode}
	 * @throws Exception global exception
	 * @see    SocioeconomicStatusCode
	 */
	@SuppressWarnings("unchecked")
	public List<SocioeconomicStatusCode> findByName(String description) throws Exception {
	 	String hql = "select o from SocioeconomicStatusCode o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SocioeconomicStatusCode>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param socioeconomicStatusCode the socioeconomic status code
	 * @return the socioeconomic status code
	 * @throws Exception the exception
	 */
	public SocioeconomicStatusCode findUniqueCode(SocioeconomicStatusCode socioeconomicStatusCode) throws Exception {
	 	String hql = "select o from SocioeconomicStatusCode o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (socioeconomicStatusCode.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", socioeconomicStatusCode.getId());
	 	}
	   
	    parameters.put("code", socioeconomicStatusCode.getCode());
		return (SocioeconomicStatusCode)super.getUniqueResult(hql, parameters);
	}
}

