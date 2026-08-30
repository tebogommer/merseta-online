package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.entity.lookup.Designation;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationDAO.
 */
public class DesignationDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Designation.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> allDesignation() throws Exception {
		return (List<Designation>)super.getList("select o from Designation o");
	}
	
	/**
	 * Get all Designation Exclude CA.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> allDesignationExcludeCA() throws Exception {
		return (List<Designation>)super.getList("select o from Designation o where o.id <> 2 ");
	}
	
	/**
	 * Get all Designation Exclude CA.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> allTPApplicationDesignation() throws Exception {
		return (List<Designation>)super.getList("select o from Designation o where o.id <> 1 and o.id <> 2 and o.id <> 3 and o.id <> 4 ");
	}

	/**
	 * Get all Designation between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> allDesignation(Long from, int noRows) throws Exception {
	 	String hql = "select o from Designation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Designation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	public Designation findByKey(Long id) throws Exception {
	 	String hql = "select o from Designation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Designation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Designation by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    Designation
	 */
	@SuppressWarnings("unchecked")
	public List<Designation> findByName(String description) throws Exception {
	 	String hql = "select o from Designation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Designation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Designation> findSdpDesignations(Boolean sdpDesignation) throws Exception {
	 	String hql = "select o from Designation o where o.sdpDesignation = :sdpDesignation " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sdpDesignation", sdpDesignation);
		return (List<Designation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public Designation findByCode(String code) throws Exception {
	 	String hql = "select o from Designation o where o.code = :code " ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (Designation)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param designation the designation
	 * @return a {@link haj.com.entity.Designation}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	
	public Designation findUniqueCode(Designation designation) throws Exception {
	 	String hql = "select o from Designation o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (designation.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", designation.getId());
	 	}
	    parameters.put("code", designation.getCode());
		return (Designation)super.getUniqueResult(hql, parameters);
	}
}

