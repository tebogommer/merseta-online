package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.QualificationToolKit;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QualificationToolKitDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationToolKit
 	 * @author TechFinium 
 	 * @see    QualificationToolKit
 	 * @return a list of {@link haj.com.entity.QualificationToolKit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolKit> allQualificationToolKit() throws Exception {
		return (List<QualificationToolKit>)super.getList("select o from QualificationToolKit o");
	}

	/**
	 * Get all QualificationToolKit between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QualificationToolKit
 	 * @return a list of {@link haj.com.entity.QualificationToolKit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolKit> allQualificationToolKit(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationToolKit o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationToolKit>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QualificationToolKit
 	 * @return a {@link haj.com.entity.QualificationToolKit}
 	 * @throws Exception global exception
 	 */
	public QualificationToolKit findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationToolKit o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationToolKit)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationToolKit by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QualificationToolKit
  	 * @return a list of {@link haj.com.entity.QualificationToolKit}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolKit> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationToolKit o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationToolKit>)super.getList(hql, parameters);
	}
	
	/**
	 * counts number of entries by qualification id
 	 * @author TechFinium 
 	 * @param qualificationId the qualification id
 	 * @see    QualificationToolKit
 	 * @return a int
 	 * @throws Exception global exception
 	 */
	public int countByQualificationId(Long qualificationId) throws Exception {
	 	String hql = "select count(o) from QualificationToolKit o where o.qualification.id = :qualificationId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("qualificationId", qualificationId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/**
	 * counts number of entries by qualification id
 	 * @author TechFinium 
 	 * @param qualificationId the qualification id
 	 * @see    QualificationToolKit
 	 * @return a int
 	 * @throws Exception global exception
 	 */
	public int countByQualificationIdIgnoreQualificationToolKitId(Long qualificationId, Long qualificationToolKitId) throws Exception {
	 	String hql = "select count(o) from QualificationToolKit o where o.qualification.id = :qualificationId and o.id <> :qualificationToolKitId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("qualificationId", qualificationId);
	    parameters.put("qualificationToolKitId", qualificationToolKitId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

