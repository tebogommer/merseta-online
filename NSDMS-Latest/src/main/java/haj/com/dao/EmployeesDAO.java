package haj.com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesImport;
import haj.com.entity.EmployeesImportTraining;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesDAO.
 */
public class EmployeesDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
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
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             global exception
	 * @see Employees
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployees() throws Exception {
		return (List<Employees>) super.getList("select o from Employees o");
	}

	/**
	 * Get all Employees between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             global exception
	 * @see Employees
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployees(Long from, int noRows) throws Exception {
		String hql = "select o from Employees o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Employees>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find employees import.
	 *
	 * @param empId
	 *            the emp id
	 * @return the employees import
	 * @throws Exception
	 *             the exception
	 */
	public EmployeesImport findEmployeesImport(Long empId) throws Exception {
		String hql = "select o from EmployeesImport o where o.employee.id = :empId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("empId", empId);
		return (EmployeesImport) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find employees training import.
	 *
	 * @param empTrainingId
	 *            the emp training id
	 * @return the employees import training
	 * @throws Exception
	 *             the exception
	 */
	public EmployeesImportTraining findEmployeesTrainingImport(Long empTrainingId) throws Exception {
		String hql = "select o from EmployeesImportTraining o where o.employeeTraining.id = :empTrainingId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("empTrainingId", empTrainingId);
		return (EmployeesImportTraining) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All employees training.
	 *
	 * @param empId
	 *            the emp id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> allEmployeesTraining(Long empId) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.id = :empId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("empId", empId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * All employees training count.
	 *
	 * @param empId
	 *            the emp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public long allEmployeesTrainingCount(Long empId) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.id = :empId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("empId", empId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All employees employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesEmployedCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspID and o.employedUnEmployed = :employed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("employed", EmployedUnEmployedEnum.Employed);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All employees not employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesNotEmployedCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspID and o.employedUnEmployed = :employed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("employed", EmployedUnEmployedEnum.UnEmployed);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * All employees employed.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployeesEmployed(Wsp wsp) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspID and o.employedUnEmployed = :employed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("employed", EmployedUnEmployedEnum.UnEmployed);
		return (List<Employees>) super.getList(hql, parameters);
	}

	/**
	 * All employees not employed.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployeesNotEmployed(Wsp wsp) throws Exception {
		String hql = "select o from Employees o where o.wsp.id = :wspID and o.employedUnEmployed = :employed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("employed", EmployedUnEmployedEnum.UnEmployed);

		return (List<Employees>) super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             global exception
	 * @see Employees
	 */
	public Employees findByKey(Long id) throws Exception {
		String hql = "select o from Employees o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Employees) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Employees by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             global exception
	 * @see Employees
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> findByName(String description) throws Exception {
		String hql = "select o from Employees o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Employees>) super.getList(hql, parameters);
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
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long id, boolean wsp) {
		String hql = "select o from " + entity.getName() + " o left join fetch o.site s left join fetch ";
		if (wsp)
			hql += "o.wsp w  where o.wsp.id = :id and o.employedUnEmployed = :employed";
		else
			hql += "o.company c where c.id = :id";
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("id", id);
		if (wsp)
			parms.put("employed", EmployedUnEmployedEnum.Employed);
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
				hql += " order by o." + sortField + " asc  ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc ";
				break;
			default:
				break;
			}
		}

		return getList(hql, filters, startingAt, pageSize);
	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            the entity
	 * @param filters
	 *            the filters
	 * @param wspId
	 *            the wsp id
	 * @return the int
	 */
	public int count(Class<?> entity, Map<String, Object> filters, Long wspId, boolean wsp) {
		String hql = "select count(o) from " + entity.getName() + " o ";
		if (wsp) {
			hql += "where o.wsp.id = :wspId and o.employedUnEmployed = :employed";			
		}else {
			hql += "where o.company.id = :wspId";					
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("wspId", wspId);
		if (wsp) {
			parms.put("employed", EmployedUnEmployedEnum.Employed);			
		}
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
	 * @param empId
	 *            the emp id
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEmplooyeePlannedDonePivotNonPivot(Long empId,
			CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o " + " where o.employee.id =  :empId "
				+ " and o.completedPlanned =  :completedPlanned " + " and o.pivotNonPivot =  :pivotNonPivot ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("empId", empId);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Count gender by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param genderId
	 *            the gender id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countGenderByEquityForWsp(Long equityId, Long genderId, Long wspId) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspId and  o.equity.id = :equityId and o.gender.id = :genderId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("genderId", genderId);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count disability by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param disabilityId
	 *            the disability id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countDisabilityByEquityForWsp(Long equityId, Long disabilityId, Long wspId) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspId and  o.equity.id = :equityId and o.disability.id = :disabilityId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("disabilityId", disabilityId);
		return (Long) super.getUniqueResult(hql, parameters);
	}
	
	public Long countByCompany(Company company) throws Exception {
		String hql = "select count(o) from Employees o where o.company.id =:companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count disabled by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countDisabledByEquityForWsp(Long equityId, Long wspId) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspId and o.equity.id = :equityId and o.disability.id not between 9 and 17";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count by employment status enum for wsp.
	 *
	 * @param employStat
	 *            the employ stat
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countByEmploymentStatusEnumForWsp(EmploymentStatusEnum employStat, Long wspId) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspId and  o.employmentStatus = :employStat";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("employStat", employStat);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count by age range for wsp.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countByAgeRangeForWsp(Date fromDate, Date toDate, Long wspId) throws Exception {
		String hql = "select count(o) from Employees o where o.wsp.id = :wspId and  date(o.dateOfBirth) between date(:toDate) and date(:fromDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count employees training by age range for wsp.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingByAgeRangeForWsp(Date fromDate, Date toDate, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and  date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate) and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		parameters.put("employedUnEmployed", employedUnEmployed);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count employees training gender by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param genderId
	 *            the gender id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingGenderByEquityForWsp(Long equityId, Long genderId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("genderId", genderId);
		parameters.put("employedUnEmployed", employedUnEmployed);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count employees training disability by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param disabilityId
	 *            the disability id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingDisabilityByEquityForWsp(Long equityId, Long disabilityId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and  o.employee.equity.id = :equityId and o.employee.disability.id = :disabilityId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("disabilityId", disabilityId);
		parameters.put("employedUnEmployed", employedUnEmployed);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count employees training disabled by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	// This depends on the data base entries for disability
	public Long countEmployeesTrainingDisabledByEquityForWsp(Long equityId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17 and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("employedUnEmployed", employedUnEmployed);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Count employees training by employment status enum for wsp.
	 *
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingByEmploymentStatusEnumForWsp(Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("employedUnEmployed", employedUnEmployed);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	/** The Constant leftJoins. */
	private static final String leftJoins = " "
			+ "left join fetch o.gender left join fetch o.equity left join fetch o.municipality left join fetch o.ofoCode left join fetch o.disability left join fetch o.wsp left join fetch o.highestQualTitle "
			+ "left join fetch o.occupationCategory left join fetch o.highestQualType left join fetch o.employmentStatus left join fetch o.interventionType left join fetch o.nqfLevel left join fetch o.interventionLevel left join fetch o.sourceOfFunding"
			+ "left join fetch o.nationality" + " ";

	@SuppressWarnings("unchecked")
	public List<Employees> findEmployeeByRsaIdAndCompany(String rsaIDNumber, Long companyID) {
		String hql = "select o from Employees o where o.rsaIDNumber = :rsaIDNumber and o.company.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("rsaIDNumber", rsaIDNumber);
		parameters.put("companyID", companyID);
		return (List<Employees>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Employees> findEmployeeByPassportIdAndCompany(String passportNumber, Long companyID) {
		String hql = "select o from Employees o where o.passportNumber = :passportNumber and o.company.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("passportNumber", passportNumber);
		parameters.put("companyID", companyID);
		return (List<Employees>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Employees> findEmployeeByRsaId(String rsaIDNumber) {
		String hql = "select o from Employees o where o.rsaIDNumber = :rsaIDNumber order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("rsaIDNumber", rsaIDNumber);
		return (List<Employees>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Employees> findEmployeeByPassportId(String passportNumber) {
		String hql = "select o from Employees o where o.passportNumber = :passportNumber order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("passportNumber", passportNumber);
		return (List<Employees>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Employees> findEmployeesByCompanyId(Long companyId) {
		String hql = "select o from Employees o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<Employees>) super.getList(hql, parameters);
	}
}
