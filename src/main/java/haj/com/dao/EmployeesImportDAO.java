package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Employees;
import haj.com.entity.EmployeesImport;
import haj.com.entity.EmployeesImportTraining;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesImportDAO.
 */
public class EmployeesImportDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Employees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception global exception
	 * @see    EmployeesImport
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployees() throws Exception {
		return (List<EmployeesImport>)super.getList("select o from EmployeesImport o");
	}

	/**
	 * All employees not imported.
	 *
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployeesNotImported(Long wspId) throws Exception {
		String hql = "select o from EmployeesImport o where o.company.id = :wspId and o.imported = false";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<EmployeesImport>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployeesNotImportedCompany(Long wspId) throws Exception {
		String hql = "select o from EmployeesImport o where o.company.id = :wspId and o.imported = false";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<EmployeesImport>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployeesCompany(Long wspId) throws Exception {
		String hql = "select o from Employees o where o.company.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<Employees>) super.getList(hql, parameters);
	}

	/**
	 * All employees.
	 *
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployees(Long wspId) throws Exception {
		String hql = "select o from EmployeesImport o where o.wsp.id = :wspId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<EmployeesImport>)super.getList(hql, parameters);
	}
	
	/**
	 * All employees.
	 *
	 * @param companyID the coompany id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allCompanyEmployees(Long companyID) throws Exception {
		String hql = "select o from EmployeesImport o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyID);
		return (List<EmployeesImport>)super.getList(hql, parameters);
	}
	
	/**
	 * Get all Employees between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception global exception
	 * @see    EmployeesImport
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployees(Long from, int noRows) throws Exception {
	 	String hql = "select o from EmployeesImport o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<EmployeesImport>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmployeesImport}
	 * @throws Exception global exception
	 * @see    EmployeesImport
	 */
	public EmployeesImport findByKey(Long id) throws Exception {
	 	String hql = "select o from EmployeesImport o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (EmployeesImport)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find foreign system key.
	 *
	 * @param wspId the wsp id
	 * @param uniqueId the unique id
	 * @return the employees import
	 * @throws Exception the exception
	 */
	public EmployeesImport findForeignSystemKey(Long wspId,String uniqueId) throws Exception {
	 	String hql = "select o from EmployeesImport o where o.wsp.id = :wspId and o.uniqueId = :uniqueId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
	    parameters.put("uniqueId", uniqueId);
		return (EmployeesImport)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find Employees by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception global exception
	 * @see    EmployeesImport
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> findByName(String description) throws Exception {
	 	String hql = "select o from EmployeesImport o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<EmployeesImport>)super.getList(hql, parameters);
	}
	
	/**
	 * Sort and filter.
	 *
	 * @param entity the entity
	 * @param startingAt the starting at
	 * @param pageSize the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters the filters
	 * @param wspId the wsp id
	 * @return the list
	 */
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long wspId) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.wsp w  where o.wsp.id = :wspId ";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("wspId",wspId);
		if (filters != null) {
			boolean ft = true;
			
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				hql += " order by o." + sortField + " asc , o.errorSort asc ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc , o.errorSort asc ";
				break;
			default:
				break;
			}
		}
		else {
			hql += " order by o.errorSort asc ";
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Sort and filter.
	 *
	 * @param entity the entity
	 * @param startingAt the starting at
	 * @param pageSize the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters the filters
	 * @param wspId the wsp id
	 * @return the list
	 */
	public List<?> sortAndFilterCompany(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyID) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.company c  where o.company.id = :companyID and o.imported <> true";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("companyID",companyID);
		if (filters != null) {
			boolean ft = true;
			
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				hql += " order by o." + sortField + " asc , o.errorSort asc ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc , o.errorSort asc ";
				break;
			default:
				break;
			}
		}
		else {
			hql += " order by o.errorSort asc ";
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 *
	 * @param entity the entity
	 * @param filters the filters
	 * @param wspId the wsp id
	 * @return the int
	 */
	public int count(Class<?> entity, Map<String, Object> filters, Long wspId) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp.id = :wspId ";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("wspId",wspId);
		if (filters != null) {
			boolean ft = true;
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	/**
	 * Count.
	 *
	 * @param entity the entity
	 * @param filters the filters
	 * @param wspId the wsp id
	 * @return the int
	 */
	public int countCompany(Class<?> entity, Map<String, Object> filters, Long companyID) {
		String hql = "select count(o) from " + entity.getName() + " o where o.company.id = :companyID and o.imported <> true ";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("companyID",companyID);
		if (filters != null) {
			boolean ft = true;
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	
	/**
	 * Find by emplooyee planned done pivot non pivot.
	 *
	 * @param empId the emp id
	 * @param completedPlanned the completed planned
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImportTraining> findByEmplooyeePlannedDonePivotNonPivot(Long empId,CompletedPlannedEnum completedPlanned,PivotNonPivotEnum pivotNonPivot) throws Exception {
	 	String hql = "select o from EmployeesImportTraining o "
	 			+ " where o.employeesImport.id =  :empId " 
	 			+ " and o.completedPlanned =  :completedPlanned "
	 			+ " and o.pivotalNonpivotal =  :pivotNonPivot "
	 			+ " order by o.errorSort asc";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("empId", empId);
	    parameters.put("completedPlanned", completedPlanned);
	    parameters.put("pivotNonPivot", pivotNonPivot);
		return (List<EmployeesImportTraining>)super.getList(hql, parameters);
	}
	
	/**
	 * Find employees import training by emplooyee.
	 *
	 * @param empId the emp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImportTraining> findEmployeesImportTrainingByEmplooyee(Long empId) throws Exception {
	 	String hql = "select o from EmployeesImportTraining o "
	 			+ " where o.employeesImport.id =  :empId " 
	 			+ " order by o.errorSort asc";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("empId", empId);
		return (List<EmployeesImportTraining>)super.getList(hql, parameters);
	}
}

