package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.StructureStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusDAO.
 */
public class StructureStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StructureStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception global exception
	 * @see    StructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatus> allStructureStatus() throws Exception {
		return (List<StructureStatus>)super.getList("select o from StructureStatus o");
	}

	/**
	 * Get all StructureStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception global exception
	 * @see    StructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatus> allStructureStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from StructureStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StructureStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StructureStatus}
	 * @throws Exception global exception
	 * @see    StructureStatus
	 */
	public StructureStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from StructureStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StructureStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StructureStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception global exception
	 * @see    StructureStatus
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatus> findByName(String description) throws Exception {
	 	String hql = "select o from StructureStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StructureStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find StructureStatus bu UniqueId
	 * CSG 25/0/2017.
	 *
	 * @param structureStatus the structure status
	 * @return the structure status
	 * @throws Exception the exception
	 */
	public StructureStatus findUniqueCode(StructureStatus structureStatus) throws Exception {
	 	String hql = "select o from StructureStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (structureStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", structureStatus.getId());
	 	}
	   
	    parameters.put("code", structureStatus.getCode());
		return (StructureStatus)super.getUniqueResult(hql, parameters);
	}
}

