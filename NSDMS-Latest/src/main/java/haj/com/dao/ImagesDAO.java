package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Images;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ImagesDAO.
 */
public class ImagesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * All images.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> allImages() throws Exception {
		return (List<Images>)super.getList("select o from Images o");
	}
	
	/**
	 * All image links.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> allImageLinks() throws Exception {
		return (List<String>)super.getList("select o.image.newFname from Items o where o.image.newFname is not null");
	}

/*
	@SuppressWarnings("unchecked")
	public List<Images> findByCompany(Integer companyId) throws Exception {
	 	String hql = "select o from Images o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<Images>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<Images> byField(long key) throws Exception  {
		String hql = "select o from Images o where o.key = :key";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("key", key);
	    return (List<Images>)super.getList(hql, parameters);
	}
*/

	/**
 * Find by key.
 *
 * @param id the id
 * @return the images
 * @throws Exception the exception
 */
public Images findByKey(Long id) throws Exception {
	 	String hql = "select o from Images o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Images)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findByName(String description) throws Exception {
	 	String hql = "select o from Images o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Images>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by details of security.
	 *
	 * @param detailsOfSecurityId the details of security id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findByDetailsOfSecurity(Long detailsOfSecurityId) throws Exception {
	 	String hql = "select o from Images o where o.detailsOfSecurity.id = :detailsOfSecurityId " ;
	 	 Map<String, Object> parameters = new Hashtable<String, Object>();
	 	 parameters.put("detailsOfSecurityId", detailsOfSecurityId);
		return (List<Images>)super.getList(hql, parameters);
	}
	
	
	/**
	 * Find images by prescription.
	 *
	 * @param prescriptionId the prescription id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findImagesByPrescription(Long prescriptionId) throws Exception {
		String hql = "select o from Images o left join fetch o.prescription where o.prescription.id = :prescriptionId order by o.createDate desc" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("prescriptionId", prescriptionId);
		return (List<Images>)super.getList(hql, parameters);
	}
	
	/**
	 * Find images by bug report.
	 *
	 * @param bugReportId the bug report id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findImagesByBugReport(Long bugReportId) throws Exception {
		String hql = "select o from Images o left join fetch o.bugReport where o.bugReport.id = :bugReportId order by o.createDate desc" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("bugReportId", bugReportId);
		return (List<Images>)super.getList(hql, parameters);
	}
	
	/**
	 * Find images by conditions.
	 *
	 * @param conditionsId the conditions id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findImagesByConditions(Long conditionsId) throws Exception {
		String hql = "select o from Images o left join fetch o.bugReport where o.conditions.id = :conditionsId order by o.createDate desc" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("conditionsId", conditionsId);
		return (List<Images>)super.getList(hql, parameters);
	}

	/**
	 * Find by prescription.
	 *
	 * @param prescriptionId the prescription id
	 * @return the images
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public Images findByPrescription(Long prescriptionId) throws Exception {
	 	String hql = "select o from Images o left join fetch o.prescription where o.prescription.id = :prescriptionId order by o.createDate" ;
	 	 Map<String, Object> parameters = new Hashtable<String, Object>();
	 	 parameters.put("prescriptionId", prescriptionId);
	 	return (Images)super.getUniqueResult(hql, parameters);
	}
	
	
	
	/**
	 * Find images by medication.
	 *
	 * @param medicationId the medication id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Images> findImagesByMedication(Long medicationId) throws Exception {
		String hql = "select o from Images o left join fetch o.medication where o.medication.id = :medicationId order by o.createDate desc" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("medicationId", medicationId);
		return (List<Images>)super.getList(hql, parameters);
	}
}

