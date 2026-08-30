package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.CitizenResidentStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CitizenResidentStatusDAO.
 */
public class CitizenResidentStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception global exception
	 * @see    CitizenResidentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<CitizenResidentStatus> allCitizenResidentStatus() throws Exception {
		return (List<CitizenResidentStatus>)super.getList("select o from CitizenResidentStatus o");
	}

	/**
	 * Get all CitizenResidentStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception global exception
	 * @see    CitizenResidentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<CitizenResidentStatus> allCitizenResidentStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from CitizenResidentStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CitizenResidentStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception global exception
	 * @see    CitizenResidentStatus
	 */
	public CitizenResidentStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from CitizenResidentStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CitizenResidentStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CitizenResidentStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception global exception
	 * @see    CitizenResidentStatus
	 */
	@SuppressWarnings("unchecked")
	public List<CitizenResidentStatus> findByName(String description) throws Exception {
	 	String hql = "select o from CitizenResidentStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CitizenResidentStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find CitizenResidentStatus by UniqueCode Id.
	 *
	 * @param citizenResidentStatus the citizen resident status
	 * @return the citizen resident status
	 * @throws Exception the exception
	 */
	public CitizenResidentStatus findUniqueCode(CitizenResidentStatus citizenResidentStatus) throws Exception {
	 	String hql = "select o from CitizenResidentStatus o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (citizenResidentStatus.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", citizenResidentStatus.getId());
	 	}
	   
	    parameters.put("code", citizenResidentStatus.getCode());
		return (CitizenResidentStatus)super.getUniqueResult(hql, parameters);
	}
}

