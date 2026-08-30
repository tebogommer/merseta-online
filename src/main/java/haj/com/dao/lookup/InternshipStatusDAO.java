package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.InternshipStatus;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InternshipStatusDAO.
 */
public class InternshipStatusDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InternshipStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception global exception
	 * @see    InternshipStatus
	 */
	@SuppressWarnings("unchecked")
	public List<InternshipStatus> allInternshipStatus() throws Exception {
		return (List<InternshipStatus>)super.getList("select o from InternshipStatus o");
	}

	/**
	 * Get all InternshipStatus between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception global exception
	 * @see    InternshipStatus
	 */
	@SuppressWarnings("unchecked")
	public List<InternshipStatus> allInternshipStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from InternshipStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<InternshipStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InternshipStatus}
	 * @throws Exception global exception
	 * @see    InternshipStatus
	 */
	public InternshipStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from InternshipStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (InternshipStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InternshipStatus by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception global exception
	 * @see    InternshipStatus
	 */
	@SuppressWarnings("unchecked")
	public List<InternshipStatus> findByName(String description) throws Exception {
	 	String hql = "select o from InternshipStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<InternshipStatus>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param internshipStatus the internship status
	 * @return a {@link haj.com.entity.InternshipStatus}
	 * @throws Exception global exception
	 * @see    InternshipStatus
	 */
	 public InternshipStatus findUniqueCode(InternshipStatus internshipStatus) throws Exception {
		 	String hql = "select o from InternshipStatus o where o.code = :code " ;
		 	Map<String, Object> parameters = new Hashtable<String, Object>();
		 	if (internshipStatus.getId()!=null) {
		 		hql += " and o.id <> :id ";
		 		parameters.put("id", internshipStatus.getId());
		 	}
		   
		    parameters.put("code", internshipStatus.getCode());
			return (InternshipStatus)super.getUniqueResult(hql, parameters);
	 }
		
}

