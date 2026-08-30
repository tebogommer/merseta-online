package haj.com.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import haj.com.dao.ReportDataDAO;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportDataService.
 */
public class ReportDataService extends AbstractService {
	/** The dao. */
	private ReportDataDAO dao = new ReportDataDAO();

	/** The Constant MerSETA_ETQA. */
	private static final String MerSETA_ETQA = "599";

	/**
	 * Count by age range wsp other SETA.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByAgeRangeWspOtherSETA(Integer fromAge, Integer toAge, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByAgeRangeWsp(fromAge, toAge, wspId);
		etl = etl.stream().filter(x -> x.getEtqa() != null).collect(Collectors.toList());
		return etl.stream().filter(x -> !x.getEtqa().getCode().equals(MerSETA_ETQA)).count();
	}

	/**
	 * Count by equity gender wsp other SETA.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEquityGenderWspOtherSETA(Long equityId, Long genderId, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEquityGenderWSP(equityId, genderId, wspId);
		etl = etl.stream().filter(x -> x.getEtqa() != null).collect(Collectors.toList());
		return etl.stream().filter(x -> !x.getEtqa().getCode().equals(MerSETA_ETQA)).count();
	}

	/**
	 * Count by disabled WSP other SETA.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByDisabledWSPOtherSETA(Long equityId, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByDisabledWSP(equityId, wspId);
		etl = etl.stream().filter(x -> x.getEtqa() != null).collect(Collectors.toList());
		return etl.stream().filter(x -> !x.getEtqa().getCode().equals(MerSETA_ETQA)).count();
	}

	/**
	 * Count by employed unemployed WSP other SETA.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEmployedUnemployedWSPOtherSETA(EmployedUnEmployedEnum employedUnEmployed, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEmployedUnemployedWSP(employedUnEmployed, wspId);
		etl = etl.stream().filter(x -> x.getEtqa() != null).collect(Collectors.toList());
		return etl.stream().filter(x -> !x.getEtqa().getCode().equals(MerSETA_ETQA)).count();
	}

	/**
	 * Count by wsp.
	 *
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByWsp(Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByWsp(wspId);
		return etl.stream().count();
	}

	/**
	 * Count by age range wsp.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByAgeRangeWsp(Integer fromAge, Integer toAge, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByAgeRangeWsp(fromAge, toAge, wspId);
		return etl.stream().count();
	}

	/**
	 * Count by equity gender wsp.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEquityGenderWsp(Long equityId, Long genderId, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEquityGenderWSP(equityId, genderId, wspId);
		return etl.stream().count();
	}

	/**
	 * Count by disabled WSP.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByDisabledWSP(Long equityId, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByDisabledWSP(equityId, wspId);
		return etl.stream().count();
	}

	/**
	 * Count by employed unemployed WSP.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEmployedUnemployedWSP(EmployedUnEmployedEnum employedUnEmployed, Long wspId) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEmployedUnemployedWSP(employedUnEmployed, wspId);
		return etl.stream().count();
	}

	/**
	 * Count by age range wsp pivot non pivot.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByAgeRangeWspPivotNonPivot(Integer fromAge, Integer toAge, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByAgeRangeWspPivotNonPivot(fromAge, toAge, wspId, pivotNonPivot);
		return etl.stream().count();
	}

	/**
	 * Count by equity gender wsp pivot non pivot.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEquityGenderWspPivotNonPivot(Long equityId, Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEquityGenderWSPPivotNonPivot(equityId, genderId, wspId, pivotNonPivot);
		return etl.stream().count();
	}

	/**
	 * Count by disabled WSP pivot non pivot.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByDisabledWSPPivotNonPivot(Long equityId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByDisabledWSPPivotNonPivot(equityId, wspId, pivotNonPivot);
		return etl.stream().count();
	}

	/**
	 * Count by employed unemployed WSP pivot non pivot.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum employedUnEmployed, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByEmployedUnemployedWSPPivotNonPivot(employedUnEmployed, wspId, pivotNonPivot);
		return etl.stream().count();
	}

	/**
	 * Count by age range wsp pivot non pivot completed planned.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByAgeRangeWspPivotNonPivotCompletedPlanned(Integer fromAge, Integer toAge, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		List<EmployeesTraining> etl = new ArrayList<>();
		etl = findByAgeRangeWspPivotNonPivotCompletedPlanned(fromAge, toAge, wspId, pivotNonPivot, completedPlanned);
		return etl.stream().count();
	}

	/**
	 * Find by wsp.
	 *
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByWsp(Long wspId) throws Exception {
		return dao.findByWsp(wspId);
	}

	/**
	 * Find by wsp pivot non pivot.
	 *
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByWspPivotNonPivot(Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByWspPivotNonPivot(wspId, pivotNonPivot);
	}

	/**
	 * Find by wsp pivot non pivot completed planned.
	 *
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByWspPivotNonPivotCompletedPlanned(Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		return dao.findByWspPivotNonPivotCompletedPlanned(wspId, pivotNonPivot, completedPlanned);
	}

	/**
	 * Find by age range wsp.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 * @throws ValidationException the validation exception
	 */
	public List<EmployeesTraining> findByAgeRangeWsp(Integer fromAge, Integer toAge, Long wspId) throws Exception, ValidationException {
		if (fromAge > toAge)
			throw new ValidationException("Date Range Incorrect");
		Integer signedIntMultiplier = -1;
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, signedIntMultiplier * fromAge);
		toDate.add(Calendar.YEAR, signedIntMultiplier * toAge);
		Date startDate = fromDate.getTime();
		Date endDate = toDate.getTime();
		return dao.findByAgeRangeWsp(startDate, endDate, wspId);
	}

	/**
	 * Find by age range wsp pivot non pivot.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 * @throws ValidationException the validation exception
	 */
	public List<EmployeesTraining> findByAgeRangeWspPivotNonPivot(Integer fromAge, Integer toAge, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception, ValidationException {
		if (fromAge > toAge)
			throw new ValidationException("Date Range Incorrect");
		Integer signedIntMultiplier = -1;
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, signedIntMultiplier * fromAge);
		toDate.add(Calendar.YEAR, signedIntMultiplier * toAge);
		Date startDate = fromDate.getTime();
		Date endDate = toDate.getTime();
		return dao.findByAgeRangeWspPivotNonPivot(startDate, endDate, wspId, pivotNonPivot);
	}

	/**
	 * Find by age range wsp pivot non pivot completed planned.
	 *
	 * @param fromAge the from age
	 * @param toAge the to age
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the list
	 * @throws Exception the exception
	 * @throws ValidationException the validation exception
	 */
	public List<EmployeesTraining> findByAgeRangeWspPivotNonPivotCompletedPlanned(Integer fromAge, Integer toAge, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception, ValidationException {
		if (fromAge > toAge)
			throw new ValidationException("Date Range Incorrect");
		Integer signedIntMultiplier = -1;
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, signedIntMultiplier * fromAge);
		toDate.add(Calendar.YEAR, signedIntMultiplier * toAge);
		Date startDate = fromDate.getTime();
		Date endDate = toDate.getTime();
		return dao.findByAgeRangeWspPivotNonPivotCompletedPlanned(startDate, endDate, wspId, pivotNonPivot, completedPlanned);
	}

	/**
	 * Find by equity gender WSP pivot non pivot.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByEquityGenderWSPPivotNonPivot(Long equityId, Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByEquityGenderWSPPivotNonPivot(equityId, genderId, wspId, pivotNonPivot);
	}

	/**
	 * Find by equity gender WSP pivot non pivot completed planned.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByEquityGenderWSPPivotNonPivotCompletedPlanned(Long equityId, Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		return dao.findByEquityGenderWSPPivotNonPivotCompletedPlanned(equityId, genderId, wspId, pivotNonPivot, completedPlanned);
	}

	/**
	 * Find by equity gender WSP.
	 *
	 * @param equityId the equity id
	 * @param genderId the gender id
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByEquityGenderWSP(Long equityId, Long genderId, Long wspId) throws Exception {
		return dao.findByEquityGenderWSP(equityId, genderId, wspId);
	}

	/**
	 * Find by disabled WSP.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByDisabledWSP(Long equityId, Long wspId) throws Exception {
		return dao.findByDisabledWSP(equityId, wspId);
	}

	/**
	 * Find by disabled WSP pivot non pivot.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByDisabledWSPPivotNonPivot(Long equityId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByDisabledWSPPivotNonPivot(equityId, wspId, pivotNonPivot);
	}

	/**
	 * Find by disabled WSP pivot non pivot completed planned.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByDisabledWSPPivotNonPivotCompletedPlanned(Long equityId, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		return dao.findByDisabledWSPPivotNonPivotCompletedPlanned(equityId, wspId, pivotNonPivot, completedPlanned);
	}

	/**
	 * Find by employed unemployed WSP.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByEmployedUnemployedWSP(EmployedUnEmployedEnum employedUnEmployed, Long wspId) throws Exception {
		return dao.findByEmployedUnemployedWSP(employedUnEmployed, wspId);
	}

	/**
	 * Find by employed unemployed WSP pivot non pivot.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<EmployeesTraining> findByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum employedUnEmployed, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByEmployedUnemployedWSPPivotNonPivot(employedUnEmployed, wspId, pivotNonPivot);
	}

}
