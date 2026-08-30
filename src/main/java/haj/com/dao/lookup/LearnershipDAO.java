package haj.com.dao.lookup;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Learnership;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnershipDAO.
 */
public class LearnershipDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Learnership.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception global exception
	 * @see    Learnership
	 */
	@SuppressWarnings("unchecked")
	public List<Learnership> allLearnership() throws Exception {
		return (List<Learnership>)super.getList("select o from Learnership o");
	}

	/**
	 * Get all Learnership between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception global exception
	 * @see    Learnership
	 */
	@SuppressWarnings("unchecked")
	public List<Learnership> allLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from Learnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Learnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Learnership}
	 * @throws Exception global exception
	 * @see    Learnership
	 */
	public Learnership findByKey(Long id) throws Exception {
	 	String hql = "select o from Learnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Learnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Learnership by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception global exception
	 * @see    Learnership
	 */
	@SuppressWarnings("unchecked")
	public List<Learnership> findByName(String description) throws Exception {
	 	String hql = "select o from Learnership o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Learnership>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param learnership the learnership
	 * @return a {@link haj.com.entity.Learnership}
	 * @throws Exception global exception
	 * @see    Learnership
	 */
    public Learnership findUniqueCode(Learnership learnership) throws Exception {
	 	String hql = "select o from Learnership o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (learnership.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", learnership.getId());
	 	}
	   
	    parameters.put("code", learnership.getCode());
		return (Learnership)super.getUniqueResult(hql, parameters);
	}
    
    public Learnership findUniqueSetmisCode(Learnership learnership) throws Exception {
	 	String hql = "select o from Learnership o where o.setmisCode = :setmisCode " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (learnership.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", learnership.getId());
	 	}
	   
	    parameters.put("setmisCode", learnership.getSetmisCode());
		return (Learnership)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Learnership> findByQualification(Long qualification)throws Exception {
		String hql = "select o from Learnership o where o.qualification.id = :qualification " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualification", qualification);
		return (List<Learnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Learnership> findAllQualification() throws Exception{
		String hql = "select o from Learnership o where o.qualification is not null " ;
		return (List<Learnership>)super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Learnership> findAllQualification(String description) throws Exception{
		String hql = "select o from Learnership o where o.qualification is not null and (o.code like :description or o.qualification.description like :description or o.qualification.codeString like :description) order by o.description" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    return (List<Learnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Learnership> completeLearnershipQualificationLastDateOfEnrollment(String description, Date lastDateForEnrolment) throws Exception{
		String hql = "select o from Learnership o where o.qualification is not null and date(o.qualification.lastDateForEnrolment) > date(:lastDateForEnrolment) and (o.code like :description or o.qualification.description like :description or o.qualification.codeString like :description) order by o.description" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    parameters.put("lastDateForEnrolment", lastDateForEnrolment);
	    return (List<Learnership>)super.getList(hql, parameters);
	}
	
	public Learnership findByCode(String code) throws Exception {
	 	String hql = "select o from Learnership o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code.trim());
		return (Learnership)super.getUniqueResult(hql, parameters);
	}
	
	public Learnership findByCode(String code,String codeString) throws Exception {
	 	String hql = "select o from Learnership o where o.code = :code and o.qualification.codeString = :codeString " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code.trim());
	    parameters.put("codeString", codeString.trim());
		return (Learnership)super.getUniqueResult(hql, parameters);
	}
	 
	 @SuppressWarnings("unchecked")
	public List<Learnership> findAllLearnership(String description) throws Exception{
		String hql = "select o from Learnership o where o.code like :description order by o.description" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    return (List<Learnership>)super.getList(hql, parameters);
	}
	 
	public int countLearnershipByCode(String code)  throws Exception {
		String hql = "select count(o) from Learnership o where o.code = :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", "" + code.trim());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}

