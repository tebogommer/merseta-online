package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.EmploymentType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmploymentTypeDAO.
 */
public class EmploymentTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all EmploymentType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception global exception
	 * @see    EmploymentType
	 */
	@SuppressWarnings("unchecked")
	public List<EmploymentType> allEmploymentType() throws Exception {
		return (List<EmploymentType>)super.getList("select o from EmploymentType o");
	}

	/**
	 * Get all EmploymentType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception global exception
	 * @see    EmploymentType
	 */
	@SuppressWarnings("unchecked")
	public List<EmploymentType> allEmploymentType(Long from, int noRows) throws Exception {
	 	String hql = "select o from EmploymentType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EmploymentType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmploymentType}
	 * @throws Exception global exception
	 * @see    EmploymentType
	 */
	public EmploymentType findByKey(Long id) throws Exception {
	 	String hql = "select o from EmploymentType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EmploymentType)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the employment type
	 * @throws Exception the exception
	 */
	public EmploymentType findByCode(String code) throws Exception {
	 	String hql = "select o from EmploymentType o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (EmploymentType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find EmploymentType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EmploymentType}
	 * @throws Exception global exception
	 * @see    EmploymentType
	 */
	@SuppressWarnings("unchecked")
	public List<EmploymentType> findByName(String description) throws Exception {
	 	String hql = "select o from EmploymentType o where o.description like  :description";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EmploymentType>)super.getList(hql, parameters);
	}
	
	/**
	 * Find EmploymentType by UniqueId
	 * CSG 23/10/2017.
	 *
	 * @param employmentType the employment type
	 * @return the employment type
	 * @throws Exception the exception
	 */
	public EmploymentType findUniqueCode(EmploymentType employmentType) throws Exception {
	 	String hql = "select o from EmploymentType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (employmentType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", employmentType.getId());
	 	}
	   
	    parameters.put("code", employmentType.getCode());
		return (EmploymentType)super.getUniqueResult(hql, parameters);
	}
}

