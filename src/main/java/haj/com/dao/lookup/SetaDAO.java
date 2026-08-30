package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Seta;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SetaDAO.
 */
public class SetaDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Seta.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception global exception
	 * @see    Seta
	 */
	@SuppressWarnings("unchecked")
	public List<Seta> allSeta() throws Exception {
		return (List<Seta>)super.getList("select o from Seta o");
	}

	/**
	 * Get all Seta between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception global exception
	 * @see    Seta
	 */
	@SuppressWarnings("unchecked")
	public List<Seta> allSeta(Long from, int noRows) throws Exception {
	 	String hql = "select o from Seta o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Seta>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Seta}
	 * @throws Exception global exception
	 * @see    Seta
	 */
	public Seta findByKey(Long id) throws Exception {
	 	String hql = "select o from Seta o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Seta)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Seta by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Seta}
	 * @throws Exception global exception
	 * @see    Seta
	 */
	@SuppressWarnings("unchecked")
	public List<Seta> findByName(String description) throws Exception {
	 	String hql = "select o from Seta o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Seta>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Seta by IniqueId
	 * CSG 25/10/2017.
	 *
	 * @param seta the seta
	 * @return the seta
	 * @throws Exception the exception
	 */
	public Seta findUniqueCode(Seta seta) throws Exception {
	 	String hql = "select o from Seta o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (seta.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", seta.getId());
	 	}
	   
	    parameters.put("code", seta.getCode());
		return (Seta)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the etqa
	 * @throws Exception the exception
	 */
	public Seta findBySetmisCode(String code) throws Exception {
	 	String hql = "select o from Seta o where o.setmisCode = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (Seta)super.getUniqueResult(hql, parameters);
	}
}

