package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.StructureStatusID;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusIDDAO.
 */
public class StructureStatusIDDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StructureStatusID.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception global exception
	 * @see    StructureStatusID
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatusID> allStructureStatusID() throws Exception {
		return (List<StructureStatusID>)super.getList("select o from StructureStatusID o");
	}

	/**
	 * Get all StructureStatusID between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception global exception
	 * @see    StructureStatusID
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatusID> allStructureStatusID(Long from, int noRows) throws Exception {
	 	String hql = "select o from StructureStatusID o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StructureStatusID>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StructureStatusID}
	 * @throws Exception global exception
	 * @see    StructureStatusID
	 */
	public StructureStatusID findByKey(Long id) throws Exception {
	 	String hql = "select o from StructureStatusID o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StructureStatusID)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StructureStatusID by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.StructureStatusID}
	 * @throws Exception global exception
	 * @see    StructureStatusID
	 */
	@SuppressWarnings("unchecked")
	public List<StructureStatusID> findByName(String description) throws Exception {
	 	String hql = "select o from StructureStatusID o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StructureStatusID>)super.getList(hql, parameters);
	}
}

