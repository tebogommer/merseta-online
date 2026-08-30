package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.QdfCompany;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QdfCompanyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QdfCompany
 	 * @author TechFinium 
 	 * @see    QdfCompany
 	 * @return a list of {@link haj.com.entity.QdfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompany> allQdfCompany() throws Exception {
		return (List<QdfCompany>)super.getList("select o from QdfCompany o");
	}

	/**
	 * Get all QdfCompany between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QdfCompany
 	 * @return a list of {@link haj.com.entity.QdfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompany> allQdfCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from QdfCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QdfCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QdfCompany
 	 * @return a {@link haj.com.entity.QdfCompany}
 	 * @throws Exception global exception
 	 */
	public QdfCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from QdfCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QdfCompany)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QdfCompany> findByQualificationCurriculumDevelopentID(QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) throws Exception {
	 	String hql = "select o from QdfCompany o "
	 			+ " inner join QualificationsCurriculumDevelopment qcd on qcd.id = o.qualificationsCurriculumDevelopment.id"
	 			+ " where qcd.id = :id and qcd.createUser.id = :userID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", qualificationsCurriculumDevelopment.getId());
	    parameters.put("userID", qualificationsCurriculumDevelopment.getCreateUser().getId());
		return (List<QdfCompany>)super.getList(hql, parameters);
	}

	/**
	 * Find QdfCompany by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QdfCompany
  	 * @return a list of {@link haj.com.entity.QdfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QdfCompany> findByName(String description) throws Exception {
	 	String hql = "select o from QdfCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QdfCompany>)super.getList(hql, parameters);
	}
}

