package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.temp.QualificationTemp;
import haj.com.entity.lookup.temp.SaqaQualificationTemp;
import haj.com.entity.lookup.temp.SaqaUnitstandardTemp;
import haj.com.entity.lookup.temp.UnitStandardsTemp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaQualificationDAO.
 */
public class SaqaQualificationDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SaqaQualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception global exception
	 * @see    SaqaQualification
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> allSaqaQualification() throws Exception {
		return (List<SaqaQualification>)super.getList("select o from SaqaQualification o");
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> allSaqaQualificationWithNoNqfLink() throws Exception {
		return (List<SaqaQualification>)super.getList("select o from SaqaQualification o where o.nqflevel is null");
	}

	/**
	 * Get all SaqaQualification between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception global exception
	 * @see    SaqaQualification
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> allSaqaQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from SaqaQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SaqaQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SaqaQualification}
	 * @throws Exception global exception
	 * @see    SaqaQualification
	 */
	public SaqaQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from SaqaQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SaqaQualification)super.getUniqueResult(hql, parameters);
	}


	/**
	 * Find by qual ID.
	 *
	 * @param qualificationid the qualificationid
	 * @return the saqa qualification
	 * @throws Exception the exception
	 */
	public SaqaQualification findByQualID(Integer qualificationid) throws Exception {
	 	String hql = "select o from SaqaQualification o where o.qualificationid = :qualificationid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationid", qualificationid);
		return (SaqaQualification)super.getUniqueResult(hql, parameters);
	}


	/**
	 * Find SaqaQualification by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception global exception
	 * @see    SaqaQualification
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> findByName(String description) throws Exception {
	 	String hql = "select o from SaqaQualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SaqaQualification>)super.getList(hql, parameters);
	}
	
	// *********************************************************************************************************************************** //
	
	/**
	 * 
	 * PLEASE NOTE ******* THIS IS JUST FOR THE QUARTERLY QUALIFICATION AND UNIT STANDARDS IMPORT !!!!!!!!!!!!!! 
	 * 
	 * @return List<SaqaQualificationTemp>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationTemp> allQualificationTemp() throws Exception {
		return (List<QualificationTemp>)super.getList("select o from QualificationTemp o");
	}
	
	/**
	 * 
	 * PLEASE NOTE ******* THIS IS JUST FOR THE QUARTERLY QUALIFICATION AND UNIT STANDARDS IMPORT !!!!!!!!!!!!!! 
	 * 
	 * @return List<SaqaQualificationTemp>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaQualificationTemp> allSaqaQualificationTemp() throws Exception {
		return (List<SaqaQualificationTemp>)super.getList("select o from SaqaQualificationTemp o");
	}
	
	/**
	 * 
	 * PLEASE NOTE ******* THIS IS JUST FOR THE QUARTERLY QUALIFICATION AND UNIT STANDARDS IMPORT !!!!!!!!!!!!!! 
	 * 
	 * @return List<SaqaQualificationTemp>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandardsTemp> allUnitStandardsTemp() throws Exception {
		return (List<UnitStandardsTemp>)super.getList("select o from UnitStandardsTemp o");
	}
	
	/**
	 * 
	 * PLEASE NOTE ******* THIS IS JUST FOR THE QUARTERLY QUALIFICATION AND UNIT STANDARDS IMPORT !!!!!!!!!!!!!! 
	 * 
	 * @return List<SaqaQualificationTemp>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUnitstandardTemp> allSaqaUnitStandardsTemp() throws Exception {
		return (List<SaqaUnitstandardTemp>)super.getList("select o from SaqaUnitstandardTemp o");
	}
	
	// *********************************************************************************************************************************** //
}

