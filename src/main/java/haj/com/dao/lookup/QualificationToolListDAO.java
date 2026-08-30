package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationToolList;

public class QualificationToolListDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationToolList
 	 * @author TechFinium 
 	 * @see    QualificationToolList
 	 * @return a list of {@link haj.com.entity.QualificationToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolList> allQualificationToolList() throws Exception {
		return (List<QualificationToolList>)super.getList("select o from QualificationToolList o");
	}

	/**
	 * Get all QualificationToolList between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QualificationToolList
 	 * @return a list of {@link haj.com.entity.QualificationToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolList> allQualificationToolList(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationToolList o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationToolList>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QualificationToolList
 	 * @return a {@link haj.com.entity.QualificationToolList}
 	 * @throws Exception global exception
 	 */
	public QualificationToolList findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationToolList o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationToolList)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationToolList by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QualificationToolList
  	 * @return a list of {@link haj.com.entity.QualificationToolList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationToolList> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationToolList o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationToolList>)super.getList(hql, parameters);
	}

	public int coutQualification(Long qualificationID) {
		String hql = "select count(o) from QualificationToolList o where o.qualification.id = :qualificationID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationID", qualificationID);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public QualificationToolList findByQualification(Long qualificationID) {
		String hql = "select o from QualificationToolList o where o.qualification.id = :qualificationID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationID", qualificationID);
		return (QualificationToolList)super.getUniqueResult(hql, parameters);
	}
}

