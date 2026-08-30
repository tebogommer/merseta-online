package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.QualificationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QualificationUnitStandardsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationUnitStandards
 	 * @author TechFinium 
 	 * @see    QualificationUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> allQualificationUnitStandards() throws Exception {
		return (List<QualificationUnitStandards>)super.getList("select o from QualificationUnitStandards o");
	}

	/**
	 * Get all QualificationUnitStandards between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QualificationUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> allQualificationUnitStandards(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationUnitStandards o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationUnitStandards>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QualificationUnitStandards
 	 * @return a {@link haj.com.entity.LearnerQualificationUnitStandards}
 	 * @throws Exception global exception
 	 */
	public QualificationUnitStandards findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationUnitStandards o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationUnitStandards)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationUnitStandards by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QualificationUnitStandards
  	 * @return a list of {@link haj.com.entity.LearnerQualificationUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationUnitStandards o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationUnitStandards>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> findByQualificationKey(Long qualificationID) throws Exception {
		String hql = "select o from QualificationUnitStandards o where o.qualification.id = :qualificationID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationID", qualificationID);
		return (List<QualificationUnitStandards>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QualificationUnitStandards> findByQualificationKey(Long qualificationID, Long summativeAssessmentReportID) throws Exception {
		String hql = "select o from QualificationUnitStandards o where o.qualification.id = :qualificationID and o.unitStandards.id not in(select x.unitStandards.id from SummativeAssessmentReportUnitStandards x where x.summativeAssessmentReport.id = :summativeAssessmentReportID)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationID", qualificationID);
	    parameters.put("summativeAssessmentReportID", summativeAssessmentReportID);
		return (List<QualificationUnitStandards>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardByQualificationKey(Long qualificationID) throws Exception {
		String hql = "select o.unitStandards from QualificationUnitStandards o where o.qualification.id = :qualificationID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationID", qualificationID);
		return (List<UnitStandards>)super.getList(hql, parameters);
	}
	
	public Integer countByUnitStandardAndLearnership(Long unitStandardId, Long qualificationID) throws Exception {
	 	String hql = "select count(o) from QualificationUnitStandards o where o.qualification.id =  :qualificationID and o.unitStandards.id = :unitStandardId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("qualificationID", qualificationID);
	    parameters.put("unitStandardId", unitStandardId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

