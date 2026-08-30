package haj.com.dao.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.service.UnitStandardsService;

public class SaqaUsQualificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SaqaUsQualification
 	 * @author TechFinium 
 	 * @see    SaqaUsQualification
 	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> allSaqaUsQualification() throws Exception {
		return (List<SaqaUsQualification>)super.getList("select o from SaqaUsQualification o");
	}

	/**
	 * Get all SaqaUsQualification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SaqaUsQualification
 	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> allSaqaUsQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from SaqaUsQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SaqaUsQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SaqaUsQualification
 	 * @return a {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	public SaqaUsQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from SaqaUsQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SaqaUsQualification)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find SaqaUsQualification qualification ID and US
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SaqaUsQualification
  	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findByQualAndUS(Long qualID,Integer unitstandardid) throws Exception {
	 	String hql = "select o from SaqaUsQualification o where o.saqaQualification.id = :qualID and o.unitstandardid = :unitstandardid" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualID",qualID);
	    parameters.put("unitstandardid",unitstandardid.intValue());
		return (List<SaqaUsQualification>)super.getList(hql, parameters);
	}
	
	public SaqaUsQualification findByUS(Integer unitstandardid) throws Exception {
	 	String hql = "select o from SaqaUsQualification o where o.unitstandardid = :unitstandardid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("unitstandardid", unitstandardid.intValue());
		return (SaqaUsQualification)super.getUniqueResult(hql, parameters);
	}
	
	public SaqaQualification findQualificationByUS(Integer unitstandardid) throws Exception {
	 	String hql = "select o.saqaQualification from SaqaUsQualification o where o.unitstandardid = :unitstandardid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("unitstandardid", unitstandardid.intValue());
		return (SaqaQualification)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find SaqaUsQualification qualification ID 
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SaqaUsQualification
  	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByQualification(Long qualID) throws Exception {
	 	String hql = "select o from SaqaUsQualification o where o.saqaQualification.id = :qualID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualID",qualID);
	    ArrayList<UnitStandards> usList=new ArrayList<>();
	    List<SaqaUsQualification> list  = (List<SaqaUsQualification>)super.getList(hql, parameters);
	    for(SaqaUsQualification usQual:list)
	    {
	    	int id= usQual.getUnitstandardid();
	    	UnitStandardsService saqaUnitstandardService=new UnitStandardsService();
	    	UnitStandards us=saqaUnitstandardService.findByUnitStandartId(id);
	    	if(us !=null)
	    	{
	    		usList.add(us);
	    	}
	    	
	    }
		return  usList;
	}


	/**
	 * Find SaqaUsQualification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SaqaUsQualification
  	 * @return a list of {@link haj.com.entity.SaqaUsQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findByName(String description) throws Exception {
	 	String hql = "select o from SaqaUsQualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SaqaUsQualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findSaqaUsQualificationByUnitStandardId(int unitstandardid) throws Exception {
		String hql = "select o from SaqaUsQualification o where  o.unitstandardid = :unitstandardid";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("unitstandardid", unitstandardid);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findSaqaUsQualificationByLearningProgrammeQual(String learningprogrammequal, Date qualregistrationendDate) throws Exception {
		String hql = "select o from Qualification o where  o.learningprogrammequal = :learningprogrammequal and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learningprogrammequal", learningprogrammequal);
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	public Integer countLearningProgrammes(String learningprogrammequal, Date qualregistrationendDate) throws Exception {
		String hql = "select count(o) from Qualification o where  o.learningprogrammequal = :learningprogrammequal and date(o.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learningprogrammequal", learningprogrammequal);
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findSaqaUsQualificationByLearningProgrammeQual(String learningprogrammequal ) throws Exception {
		String hql = "select o from SaqaUsQualification o where  o.learningprogrammequal = :learningprogrammequal ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("learningprogrammequal", learningprogrammequal);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findSaqaUsQualificationByUnitStandardId(int unitstandardid, Date qualregistrationendDate) throws Exception {
		String hql = "select o from SaqaUsQualification o where  o.unitstandardid = :unitstandardid and date(o.saqaQualification.qualregistrationendDate) > date(:qualregistrationendDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("unitstandardid", unitstandardid);
		parameters.put("qualregistrationendDate", qualregistrationendDate);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findSaqaUsQualificationByQualificationIdAndUnitStandardId(int qualificationid, int unitstandardid) throws Exception {
		String hql = "select o from SaqaUsQualification o where o.qualificationid = :qualificationid and o.unitstandardid = :unitstandardid";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationid", qualificationid);
		parameters.put("unitstandardid", unitstandardid);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}
}



