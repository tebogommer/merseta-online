package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.DesignationStructureStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationStructureStatusDAO.
 */
public class DesignationStructureStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception global exception
	 * @see    DesignationStructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DesignationStructureStatus> allDesignationStructureStatus() throws Exception {
		return (List<DesignationStructureStatus>)super.getList("select o from DesignationStructureStatus o");
	}

	/**
	 * Get all DesignationStructureStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception global exception
	 * @see    DesignationStructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DesignationStructureStatus> allDesignationStructureStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from DesignationStructureStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DesignationStructureStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception global exception
	 * @see    DesignationStructureStatus
	 */
	public DesignationStructureStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from DesignationStructureStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DesignationStructureStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DesignationStructureStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception global exception
	 * @see    DesignationStructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<DesignationStructureStatus> findByName(String description) throws Exception {
	 	String hql = "select o from DesignationStructureStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DesignationStructureStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find DesignationStructureStatus by UniqueId
	 * CSG 24/10/2017.
	 *
	 * @param designationStructureStatus the designation structure status
	 * @return the designation structure status
	 * @throws Exception the exception
	 */
	public DesignationStructureStatus findUniqueCode(DesignationStructureStatus designationStructureStatus) throws Exception {
	 	String hql = "select o from DesignationStructureStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (designationStructureStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", designationStructureStatus.getId());
	 	}
	   
	    parameters.put("code", designationStructureStatus.getCode());
		return (DesignationStructureStatus)super.getUniqueResult(hql, parameters);
	}
}

