package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.EmployeesImportDAO;
import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesImport;
import haj.com.entity.EmployeesImportTraining;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DisabilityStatusService;
import haj.com.service.lookup.EmploymentTypeService;
import haj.com.service.lookup.EquityService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.FundingService;
import haj.com.service.lookup.InterventionLevelService;
import haj.com.service.lookup.NationalityService;
import haj.com.service.lookup.OccupationCategoryService;
import haj.com.service.lookup.QualificationService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesImportService.
 */
public class EmployeesImportService extends AbstractService {
	/** The dao. */
	private EmployeesImportDAO dao = new EmployeesImportDAO();

	/** The gender service. */
	private GenderService genderService = new GenderService();

	/** The ofo codes service. */
	private OfoCodesService ofoCodesService = new OfoCodesService();

	/** The municipality service. */
	private MunicipalityService municipalityService = new MunicipalityService();

	/** The equity service. */
	private EquityService equityService = new EquityService();

	/** The disability status service. */
	private DisabilityStatusService disabilityStatusService = new DisabilityStatusService();

	/** The nationality service. */
	private NationalityService nationalityService = new NationalityService();

	/** The employment type service. */
	private EmploymentTypeService employmentTypeService = new EmploymentTypeService();

	/** The occupation category service. */
	private OccupationCategoryService occupationCategoryService = new OccupationCategoryService();

	/** The funding service. */
	private FundingService fundingService = new FundingService();

	/** The qualification service. */
	private QualificationService qualificationService = new QualificationService();

	/** The etqa service. */
	private EtqaService etqaService = new EtqaService();

	/** The intervention level service. */
	private InterventionLevelService interventionLevelService = new InterventionLevelService();

	/**
	 * Get all Employees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public List<EmployeesImport> allEmployees() throws Exception {
		return dao.allEmployees();
	}

	/**
	 * Create or update Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public void create(EmployeesImport entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);

		validate(entity, true);
		this.dao.update(entity);
	}

	/**
	 * Update Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public void update(EmployeesImport entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public void delete(IDataEntity entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Delete.
	 *
	 * @param employeesImport
	 *            the employees import
	 * @throws Exception
	 *             the exception
	 */
	public void delete(EmployeesImport employeesImport) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (employeesImport != null) {
			List<EmployeesImportTraining> list = findEmployeesImportTrainingByEmplooyee(employeesImport);
			if (list != null && list.size() != 0) {
				entityList.addAll(list);
			}
			entityList.add(employeesImport);
			dao.deleteBatch(entityList);
		}
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.EmployeesImport}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public EmployeesImport findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Employees by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesImport
	 */
	public List<EmployeesImport> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Employees.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.EmployeesImport}
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesImport> allEmployees(int first, int pageSize) throws Exception {
		return dao.allEmployees(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Employees for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Employees
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(EmployeesImport.class);
	}

	/**
	 * Lazy load Employees with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Employees class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return a list of {@link haj.com.entity.EmployeesImport} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployees(Class<EmployeesImport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Wsp wsp) throws Exception {
		return resolveTraining((List<EmployeesImport>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters, wsp.getId()));

	}

	/**
	 * Lazy load Employees with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Employees class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return a list of {@link haj.com.entity.EmployeesImport} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesImport> allEmployees(Class<EmployeesImport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		return (List<EmployeesImport>) dao.sortAndFilterCompany(class1, first, pageSize, sortField, sortOrder, filters, company.getId());

	}

	/**
	 * Resolve training.
	 *
	 * @param list
	 *            the list
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<EmployeesImport> resolveTraining(List<EmployeesImport> list) throws Exception {
		for (EmployeesImport ei : list) {
			resolveTraining(ei);
		}
		return list;
	}

	/**
	 * Resolve training.
	 *
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	private void resolveTraining(EmployeesImport emp) throws Exception {
		emp.setPivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed, PivotNonPivotEnum.Pivotal));
		emp.setPivotalTrainingPlanned(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned, PivotNonPivotEnum.Pivotal));

		emp.setNonpivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed, PivotNonPivotEnum.NonPivotal));
		emp.setNonpivotalTrainingPlanned(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned, PivotNonPivotEnum.NonPivotal));

	}

	/**
	 * Get count of Employees for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Employees class
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return Number of rows in the Employees entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<EmployeesImport> entity, Map<String, Object> filters, Wsp wsp) throws Exception {
		return dao.count(entity, filters, wsp.getId());
	}

	/**
	 * Get count of Employees for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Employees class
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return Number of rows in the Employees entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<EmployeesImport> entity, Map<String, Object> filters, Company company) throws Exception {
		return dao.countCompany(entity, filters, company.getId());
	}

	/**
	 * Save.
	 *
	 * @param empImports
	 *            the emp imports
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void save(List<EmployeesImport> empImports, Wsp wsp, Company company) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		for (EmployeesImport ei : empImports) {
			// if (StringUtils.isBlank(ei.getUniqueId())) throw new Exception("The uniaue
			// identifier must be populated");
			if (wsp != null) ei.setWsp(wsp);
			else if (company != null) ei.setCompany(company);
			ei.setImported(Boolean.FALSE);
			validate(ei, false);
			entityList.add(ei);
		}
		dao.createBatch(entityList);

		// importData(wsp);
	}

	/**
	 * Validate.
	 *
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void validate(Company wsp) throws Exception {
		validate(resolveTraining(dao.allEmployeesNotImportedCompany(wsp.getId())));
	}

	/**
	 * Validate.
	 *
	 * @param list
	 *            the list
	 * @throws Exception
	 *             the exception
	 */
	public void validate(List<EmployeesImport> list) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		for (EmployeesImport ei : list) {
			validate(ei, true);
			entityList.add(ei);
		}
		dao.updateBatch(entityList);
	}

	/**
	 * Validate.
	 *
	 * @param ei
	 *            the ei
	 */
	private void validate(EmployeesImport ei, boolean doTrainingChecks) {
		ei.setErrorSort(999999l);
		ei.setError(null);
		IdPassportEnum idpass = null;
		// SDL and Site Number
		try {
			if (StringUtils.isBlank(ei.getSdlNumber()) && StringUtils.isBlank(ei.getSiteNumber())) throw new Exception("Missing SDL Number or Site number");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Missing SDL Number or Site number</li>" : (ei.getError() + "<li>" + "Missing SDL Number or Site number</li>"));
			ei.setErrorSort(1l);
		}

		// ID Type
		try {
			if (StringUtils.isBlank(ei.getIdType())) throw new Exception("Missing ID Type");
			if (IdPassportEnum.getIdPassportEnumByValue(Integer.valueOf(ei.getIdType().trim())) == null) throw new Exception("Wrong ID Type");
			else idpass = IdPassportEnum.getIdPassportEnumByValue(Integer.valueOf(ei.getIdType().trim()));
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>ID Type is invalid</li>" : (ei.getError() + "<li>" + "ID Type is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Employee ID
		try {
			if (StringUtils.isBlank(ei.getEmployeeID())) throw new Exception("Missing Employee ID");
			else {
				if (idpass != null && idpass == IdPassportEnum.RsaId && !GenericUtility.checkRsaId(ei.getEmployeeID().trim())) {
					ei.setError(ei.getError() == null ? "<li>Invalid RSA Id number</li>" : (ei.getError() + "<li>" + "Invalid RSA Id number</li>"));
				}
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Missing Employee ID</li>" : (ei.getError() + "<li>" + "Missing Employee ID</li>"));
			ei.setErrorSort(1l);
		}

		// First Name
		try {
			if (StringUtils.isBlank(ei.getFirstName())) throw new Exception("First Name missing");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Missing First Name</li>" : (ei.getError() + "<li>" + "Missing First Name</li>"));
			ei.setErrorSort(1l);
		}

		// Last Name
		try {
			if (StringUtils.isBlank(ei.getLastName())) throw new Exception("Last Name missing");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Missing Last Name</li>" : (ei.getError() + "<li>" + "Missing Last Name</li>"));
			ei.setErrorSort(1l);
		}

		// DOB
		try {
			if (idpass != null && idpass == IdPassportEnum.Passport) {
				if (ei.getDateOfBirth() == null) throw new Exception("Date of Birth is missing");
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Missing Date of Birth</li>" : (ei.getError() + "<li>" + "Missing Date of Birth</li>"));
			ei.setErrorSort(1l);
		}

		// Gender check
		try {
			if (idpass != null && idpass == IdPassportEnum.Passport) {
				if (genderService.findByCode(ei.getGender().trim()) == null) throw new Exception("Gender code is invalid");
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Gender code is invalid</li>" : (ei.getError() + "<li>" + "Gender code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Equity check
		try {
			if (equityService.findByCode(ei.getEquity().trim()) == null) throw new Exception("Equity code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Equity code is invalid</li>" : (ei.getError() + "<li>" + "Equity code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Municipality check
		try {
			if (municipalityService.findByCode(ei.getMunicipality().trim()) == null) throw new Exception("Municipality code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Municipality code is invalid</li>" : (ei.getError() + "<li>" + "Municipality code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Disability check
		try {
			if (disabilityStatusService.findByCode(ei.getDisability().trim()) == null) throw new Exception("Disability code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Disability code is invalid</li>" : (ei.getError() + "<li>" + "Disability code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Nationality check
		try {
			if (idpass != null && idpass == IdPassportEnum.Passport) {
				if (nationalityService.findByCode(ei.getNationality().trim()) == null) throw new Exception("Nationality code is invalid");
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Nationality code is invalid</li>" : (ei.getError() + "<li>" + "Nationality code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Employment type check
		try {
			if (employmentTypeService.findByCode(ei.getEmploymentType().trim()) == null) throw new Exception("Employment type code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Employment type code is invalid</li>" : (ei.getError() + "<li>" + "Employment type code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Employment status check
		/*
		 * try { if (StringUtils.isBlank(ei.getEmploymentStatus())) throw new
		 * Exception("Missing Employment status"); if
		 * (EmploymentStatusEnum.getEmploymentStatusEnumByValue(Integer.valueOf(ei.
		 * getEmploymentStatus().trim())) == null) throw new
		 * Exception("Employment status is invalid"); else idpass =
		 * IdPassportEnum.getIdPassportEnumByValue(Integer.valueOf(ei.getIdType().trim()
		 * )); } catch (Exception e) {
		 * ei.setError(ei.getError()==null?"<li>Employment status is invalid</li>":(ei.
		 * getError()+"<li>"+"Employment status is invalid</li>")); ei.setErrorSort(1l);
		 * }
		 * 
		 */

		// Ofo Code check
		try {
			if ((ei.getSpecialisationCode() == null || ei.getSpecialisationCode().isEmpty()) && (ei.getOfoCode() == null || ei.getOfoCode().isEmpty())) throw new Exception("Ofo or Specialisation code is required.");
			if (ei.getSpecialisationCode() == null || ei.getSpecialisationCode().isEmpty()) {
				if (ofoCodesService.findByCode(ei.getOfoCode().trim()) == null) throw new Exception("Ofo code is invalid");
			} else {
				if (ofoCodesService.findBySpecialisation(ei.getSpecialisationCode()) == null) throw new Exception("Specialisation code is invalid");
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>" + e.getMessage() + "</li>" : (ei.getError() + "<li>" + "Ofo code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		if (doTrainingChecks) {
			doTrainingChecks(ei);
		}

	}

	/**
	 * Do training checks.
	 *
	 * @param ei
	 *            the ei
	 */
	private void doTrainingChecks(EmployeesImport ei) {
		try {
			List<EmployeesImportTraining> l = findEmployeesImportTrainingByEmplooyee(ei);
			for (EmployeesImportTraining et : l) {
				doPivotalNonPivotalChecks(et);
				dao.update(et);
			}

		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	/**
	 * Do pivotal non pivotal checks.
	 *
	 * @param ei
	 *            the ei
	 */
	public void doPivotalNonPivotalChecks(EmployeesImportTraining ei) {
		ei.setError(null);
		ei.setErrorSort(999999l);
		// Pivotal specific checks
		if (ei.getPivotalNonpivotal() == PivotNonPivotEnum.Pivotal) {
			// SAQA id check
			try {
				if (StringUtils.isBlank(ei.getSaqaID()) || qualificationService.findByCode(ei.getSaqaID()) == null) throw new Exception("SAQA code is invalid");
			} catch (Exception e) {
				ei.setError(ei.getError() == null ? "<li>SAQA code is invalid</li>" : (ei.getError() + "<li>" + "SAQA code is invalid</li>"));
				ei.setErrorSort(1l);
			}

			// ETQA check
			try {
				if (StringUtils.isBlank(ei.getEtqa()) || etqaService.findByCode(ei.getEtqa()) == null) throw new Exception("ETQA code is invalid");
			} catch (Exception e) {
				ei.setError(ei.getError() == null ? "<li>ETQA code is invalid</li>" : (ei.getError() + "<li>" + "ETQA code is invalid</li>"));
				ei.setErrorSort(1l);
			}

			// Accreditation Number check
			try {
				if (StringUtils.isBlank(ei.getAccreditationNumber())) throw new Exception("Accreditation Number is required");
			} catch (Exception e) {
				ei.setError(ei.getError() == null ? "<li>Accreditation Number is required</li>" : (ei.getError() + "<li>" + "Accreditation Number is required</li>"));
				ei.setErrorSort(1l);
			}
		}

		// Funding code check
		try {
			if (!StringUtils.isBlank(ei.getSourceOfFunding()) && fundingService.findByCode(ei.getSourceOfFunding().trim()) == null) throw new Exception("Funding code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Funding code is invalid</li>" : (ei.getError() + "<li>" + "Funding code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Interention level
		try {
			if (!StringUtils.isBlank(ei.getInterventionLevel()) && interventionLevelService.findByCode(ei.getInterventionLevel()) == null) throw new Exception("Intervention level code is invalid");
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Intervention level code is invalid</li>" : (ei.getError() + "<li>" + "Intervention level code is invalid</li>"));
			ei.setErrorSort(1l);
		}

		// Intervention cost
		try {
			if (!StringUtils.isBlank(ei.getInterventionCost())) {
				Double.valueOf(ei.getInterventionCost().trim());
			}
		} catch (Exception e) {
			ei.setError(ei.getError() == null ? "<li>Intervention Cost is invalid</li>" : (ei.getError() + "<li>" + "Intervention Cost is invalid</li>"));
			ei.setErrorSort(1l);
		}
	}

	/**
	 * Delete all.
	 *
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void deleteAll(Company company) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		List<EmployeesImport> l = dao.allCompanyEmployees(company.getId());
		for (EmployeesImport employeesImport : l) {
			entityList.addAll(findEmployeesImportTrainingByEmplooyee(employeesImport));
			entityList.add(employeesImport);
			if (employeesImport.getEmployee() != null) {
				entityList.add(employeesImport.getEmployee());
			}
		}
		dao.deleteBatch(entityList);
	}

	public void deleteAllEmployees(Company company) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		List<EmployeesImport> l = dao.allCompanyEmployees(company.getId());
		List<Employees> list = dao.allEmployeesCompany(company.getId());
		for (EmployeesImport employeesImport : l) {
			entityList.addAll(findEmployeesImportTrainingByEmplooyee(employeesImport));
			entityList.add(employeesImport);
			if (employeesImport.getEmployee() != null) {
				list.remove(employeesImport.getEmployee());
				entityList.add(employeesImport.getEmployee());
			}
		}
		for (Employees employeesImport : list) {
			entityList.add(employeesImport);
		}
		dao.deleteBatch(entityList);
	}

	/**
	 * Import data.
	 *
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void importData(Wsp wsp) throws Exception {
		List<EmployeesImport> l = dao.allEmployees(wsp.getId());
		validate(l);
		for (EmployeesImport ei : l) {
			if (ei.getError() == null) {
				doImport(ei);
			}
		}

	}

	/**
	 * Import data.
	 *
	 * @param wsp
	 *            the wsp
	 * @throws Exception
	 *             the exception
	 */
	public void importData(Company company) throws Exception {
		List<EmployeesImport> l = dao.allCompanyEmployees(company.getId());
		validate(l);
		for (EmployeesImport ei : l) {
			if (ei.getError() == null) {
				doImport(ei);
			}
		}

	}

	/**
	 * Do import.
	 *
	 * @param ei
	 *            the ei
	 */
	private void doImport(EmployeesImport ei) {
		try {
			if (!ei.getImported()) {
				Employees emp = new Employees();
				emp.setWsp(ei.getWsp());
				emp.setCompany(ei.getCompany());
				emp.setEmployedUnEmployed(EmployedUnEmployedEnum.Employed);
				emp.setSdlNumber(ei.getSdlNumber());
				emp.setSiteNumber(ei.getSiteNumber());
				emp.setMunicipality(municipalityService.findByCode(ei.getMunicipality()));
				emp.setIdType(IdPassportEnum.getIdPassportEnumByValue(Integer.valueOf(ei.getIdType())));
				switch (emp.getIdType()) {
					case RsaId:
						emp.setRsaIDNumber(ei.getEmployeeID());
						break;
					case Passport:
						emp.setPassportNumber(ei.getEmployeeID());
						break;
					default:
						break;
				}
				emp.setFirstName(ei.getFirstName());
				emp.setLastName(ei.getLastName());
				emp.setEquity(equityService.findByCode(ei.getEquity()));
				emp.setDisability(disabilityStatusService.findByCode(ei.getDisability()));
				if (emp.getIdType() == IdPassportEnum.Passport) {
					emp.setDateOfBirth(ei.getDateOfBirth());
					emp.setGender(genderService.findByCode(ei.getGender()));
					emp.setNationality(nationalityService.findByCode(ei.getNationality()));
				} else {
					GenericUtility.calcIDData(emp);
				}
				emp.setEmploymentType(employmentTypeService.findByCode(ei.getEmploymentType()));
				emp.setEmploymentStatus(emp.getEmploymentType().getEmploymentStatus());
				if (ei.getSpecialisationCode() != null && !ei.getSpecialisationCode().isEmpty()) emp.setOfoCode(ofoCodesService.findBySpecialisation(ei.getSpecialisationCode()));
				else emp.setOfoCode(ofoCodesService.findByCode(ei.getOfoCode()));
				dao.create(emp);
				ei.setEmployee(emp);
				ei.setImported(Boolean.TRUE);
				dao.update(ei);
				importTraining(ei, emp);
			} else {
				importTraining(ei, ei.getEmployee());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			ei.setError(e.getMessage());
			try {
				dao.update(ei);
			} catch (Exception e2) {
				logger.fatal(e2);
			}
		}

	}

	/**
	 * Import training.
	 *
	 * @param ei
	 *            the ei
	 * @param emp
	 *            the emp
	 */
	private void importTraining(EmployeesImport ei, Employees emp) {
		try {
			List<EmployeesImportTraining> l = findEmployeesImportTrainingByEmplooyee(ei);
			for (EmployeesImportTraining employeesImportTraining : l) {
				if (!employeesImportTraining.getImported()) {
					doPivotalNonPivotalChecks(employeesImportTraining);
					dao.update(employeesImportTraining);
					if (employeesImportTraining.getError() == null) {
						createEmployeeTraining(employeesImportTraining, emp);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	/**
	 * Creates the employee training.
	 *
	 * @param ei
	 *            the ei
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	private void createEmployeeTraining(EmployeesImportTraining ei, Employees emp) throws Exception {
		EmployeesTraining et = new EmployeesTraining();
		et.setEmployee(emp);
		et.setCompletedPlanned(ei.getCompletedPlanned());
		et.setPivotNonPivot(ei.getPivotalNonpivotal());
		if (ei.getPivotalNonpivotal() == PivotNonPivotEnum.Pivotal) {
			et.setQualification(qualificationService.findByCode(ei.getSaqaID()));
			et.setNqfLevel(et.getQualification().getNqflevel());
			et.setInterventionLevel(et.getNqfLevel().getInterventionLevel());
			et.setEtqa(etqaService.findByCode(ei.getEtqa()));
			et.setAccreditationNumber(ei.getAccreditationNumber());
		} else {
			if (!StringUtils.isBlank(ei.getInterventionLevel())) {
				et.setInterventionLevel(interventionLevelService.findByCode(ei.getInterventionLevel()));
			}
		}

		if (!StringUtils.isBlank(ei.getInterventionCost())) {
			et.setInterventionCost(Double.valueOf(ei.getInterventionCost().trim()));
		}

		et.setInterventionTitle(ei.getInterventionTitle());
		et.setSourceOfFunding(fundingService.findByCode(ei.getSourceOfFunding()));

		dao.create(et);
		ei.setEmployeeTraining(et);
		ei.setImported(Boolean.TRUE);
		dao.update(ei);

	}

	/**
	 * Find by emplooyee planned done pivot non pivot.
	 *
	 * @param emp
	 *            the emp
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesImportTraining> findByEmplooyeePlannedDonePivotNonPivot(EmployeesImport emp, CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByEmplooyeePlannedDonePivotNonPivot(emp.getId(), completedPlanned, pivotNonPivot);
	}

	/**
	 * Save.
	 *
	 * @param empTrainingImports
	 *            the emp training imports
	 * @param wsp
	 *            the wsp
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotalNonpivotal
	 *            the pivotal nonpivotal
	 * @throws Exception
	 *             the exception
	 */
	public void save(List<EmployeesImportTraining> empTrainingImports, Wsp wsp, CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotalNonpivotal) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		for (EmployeesImportTraining ei : empTrainingImports) {
			if (StringUtils.isBlank(ei.getEmpUniqueId())) throw new Exception("The employee unique identifier must be populated");
			ei.setEmployeesImport(findForeignSystemKey(wsp, ei.getEmpUniqueId().trim()));
			if (ei.getEmployeesImport() == null) throw new Exception("Can't find employee unique identifier (identifier=" + ei.getEmpUniqueId().trim() + ")");
			ei.setCompletedPlanned(completedPlanned);
			ei.setPivotalNonpivotal(pivotalNonpivotal);
			ei.setImported(Boolean.FALSE);
			ei.setError(null);
			ei.setErrorSort(999999l);
			entityList.add(ei);
		}
		dao.createBatch(entityList);
		validateTraining(empTrainingImports);
	}

	/**
	 * Validate training.
	 *
	 * @param empTrainingImports
	 *            the emp training imports
	 * @throws Exception
	 *             the exception
	 */
	private void validateTraining(List<EmployeesImportTraining> empTrainingImports) throws Exception {
		for (EmployeesImportTraining et : empTrainingImports) {
			doPivotalNonPivotalChecks(et);
		}

	}

	/**
	 * Find foreign system key.
	 *
	 * @param wsp
	 *            the wsp
	 * @param uniqueId
	 *            the unique id
	 * @return the employees import
	 * @throws Exception
	 *             the exception
	 */
	public EmployeesImport findForeignSystemKey(Wsp wsp, String uniqueId) throws Exception {
		return dao.findForeignSystemKey(wsp.getId(), uniqueId);
	}

	/**
	 * Find employees import training by emplooyee.
	 *
	 * @param emp
	 *            the emp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesImportTraining> findEmployeesImportTrainingByEmplooyee(EmployeesImport emp) throws Exception {
		return dao.findEmployeesImportTrainingByEmplooyee(emp.getId());
	}

	/**
	 * Update.
	 *
	 * @param employeesImportTraining
	 *            the employees import training
	 * @throws Exception
	 *             the exception
	 */
	public void update(EmployeesImportTraining employeesImportTraining) throws Exception {
		doPivotalNonPivotalChecks(employeesImportTraining);
		dao.update(employeesImportTraining);
	}
}
