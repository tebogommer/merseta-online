package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.EmployeesTraining;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportDataDAO.
 */
public class ReportDataDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Find by wsp.
	 *
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByWsp(Long wspId) throws Exception {
		String hql = "select o from EmployeesTraining o left fetch join o.employee where o.employee.wsp.id = :wspId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by wsp pivot non pivot.
	 *
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByWspPivotNonPivot(Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o left fetch join o.employee where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByWspPivotNonPivotCompletedPlanned(Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		String hql = "select o from EmployeesTraining o left fetch join o.employee where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.completedPlanned = :completedPlanned";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("completedPlanned", completedPlanned);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by age range wsp.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByAgeRangeWsp(Date fromDate, Date toDate, Long wspId) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by age range wsp pivot non pivot.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByAgeRangeWspPivotNonPivot(Date fromDate, Date toDate, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by age range wsp pivot non pivot completed planned.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @param wspId the wsp id
	 * @param pivotNonPivot the pivot non pivot
	 * @param completedPlanned the completed planned
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByAgeRangeWspPivotNonPivotCompletedPlanned(Date fromDate, Date toDate, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.completedPlanned = :completedPlanned and date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEquityGenderWSPPivotNonPivot(Long equityId, Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("equityId", equityId);
		parameters.put("genderId", genderId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEquityGenderWSPPivotNonPivotCompletedPlanned(Long equityId, Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.completedPlanned = :completedPlanned and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("equityId", equityId);
		parameters.put("genderId", genderId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEquityGenderWSP(Long equityId, Long genderId, Long wspId) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		parameters.put("genderId", genderId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by disabled WSP.
	 *
	 * @param equityId the equity id
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByDisabledWSP(Long equityId, Long wspId) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("equityId", equityId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByDisabledWSPPivotNonPivot(Long equityId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("equityId", equityId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByDisabledWSPPivotNonPivotCompletedPlanned(Long equityId, Long wspId, PivotNonPivotEnum pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.completedPlanned = :completedPlanned and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("completedPlanned", completedPlanned);
		parameters.put("equityId", equityId);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}

	/**
	 * Find by employed unemployed WSP.
	 *
	 * @param employedUnEmployed the employed un employed
	 * @param wspId the wsp id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEmployedUnemployedWSP(EmployedUnEmployedEnum employedUnEmployed, Long wspId) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("employedUnEmployed", employedUnEmployed);
		List<EmployeesTraining> l = (List<EmployeesTraining>) super.getList(hql, parameters);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<EmployeesTraining> findByEmployedUnemployedWSPPivotNonPivot(EmployedUnEmployedEnum employedUnEmployed, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
		String hql = "select o from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.employee.employedUnEmployed = :employedUnEmployed";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wspId);
		parameters.put("pivotNonPivot", pivotNonPivot);
		parameters.put("employedUnEmployed", employedUnEmployed);
		return (List<EmployeesTraining>) super.getList(hql, parameters);
	}
	/*
	 * @SuppressWarnings("unchecked") public List<EmployeesTraining>
	 * findByEmployedUnemployedWSPPivotNonPivotCompletedPlanned(
	 * EmployedUnEmployedEnum employedUnEmployed, Long wspId, PivotNonPivotEnum
	 * pivotNonPivot, CompletedPlannedEnum completedPlanned) throws Exception {
	 * String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.pivotNonPivot = :pivotNonPivot and o.completedPlanned = :completedPlanned and o.employee.employedUnEmployed = :employedUnEmployed"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("pivotNonPivot",
	 * pivotNonPivot); parameters.put("completedPlanned", completedPlanned);
	 * parameters.put("employedUnEmployed", employedUnEmployed); return
	 * (List<EmployeesTraining>) super.getList(hql, parameters); }
	 * 
	 *//** This counts by Gender and Equity for Entity Employees */
	/*
	 * public Long countGenderEquityOnEmployees(Long equityId, Long genderId,
	 * Long wspId) throws Exception { String hql =
	 * "select count(o) from Employees o where o.wsp.id = :wspId and  o.equity.id = :equityId and o.gender.id = :genderId "
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("genderId", genderId); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 *//** This counts by Gender and Equity for Entity EmployeesTraining */
	/*
	 * public Long countGenderEquityOnEmployeesTraining(Long equityId, Long
	 * genderId, Long wspId) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("genderId", genderId); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 *//**
		 * This counts by Gender, Equity, EmployedUnEmployedEnum,
		 * CompletedPlannedEnum and PivotNonPivotEnum for Entity
		 * EmployeesTraining
		 */
	/*
	 * public Long
	 * countGenderEquityEmploymentEnumCompletedEnumPivotEnumOnEmployeesTraining(
	 * Long equityId, Long genderId, Long wspId, EmployedUnEmployedEnum
	 * employedUnEmployed, CompletedPlannedEnum completedPlanned,
	 * PivotNonPivotEnum pivotNonPivot) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("genderId", genderId);
	 * parameters.put("employedUnEmployed", employedUnEmployed);
	 * parameters.put("completedPlanned", completedPlanned);
	 * parameters.put("pivotNonPivot", pivotNonPivot); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countGenderEquityPivotEnumOnEmployeesTraining(Long equityId,
	 * Long genderId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws
	 * Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("genderId", genderId); parameters.put("pivotNonPivot",
	 * pivotNonPivot); return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countDisabilityEquityOnEmployees(Long equityId, Long
	 * disabilityId, Long wspId) throws Exception { String hql =
	 * "select count(o) from Employees o where o.wsp.id = :wspId and  o.equity.id = :equityId and o.disability.id = :disabilityId "
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("disabilityId", disabilityId); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countDisabilityEquityOnEmployeesTraining(Long equityId, Long
	 * disabilityId, Long wspId) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and  o.employee.equity.id = :equityId and o.employee.disability.id = :disabilityId"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("disabilityId", disabilityId); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long
	 * countDisabilityEquityEmploymentEnumCompletedEnumPivotEnumOnEmployeesTrainingOnEmployeesTraining
	 * (Long equityId, Long disabilityId, Long wspId, EmployedUnEmployedEnum
	 * employedUnEmployed, CompletedPlannedEnum completedPlanned,
	 * PivotNonPivotEnum pivotNonPivot) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and  o.employee.equity.id = :equityId and o.employee.disability.id = :disabilityId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("disabilityId", disabilityId);
	 * parameters.put("employedUnEmployed", employedUnEmployed);
	 * parameters.put("completedPlanned", completedPlanned);
	 * parameters.put("pivotNonPivot", pivotNonPivot); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * // This depends on the data base entries for disability public Long
	 * countDisabledEquityOnEmployees(Long equityId, Long wspId) throws
	 * Exception { String hql =
	 * "select count(o) from Employees o where o.wsp.id = :wspId and o.equity.id = :equityId and o.disability.id not between 9 and 17"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * // This depends on the data base entries for disability public Long
	 * countDisabledEquityOnEmployeesTraining(Long equityId, Long wspId) throws
	 * Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * // This depends on the data base entries for disability public Long
	 * countDisabledEquityEmploymentEnumCompletedEnumPivotEnumOnEmployeesTraining
	 * (Long equityId, Long wspId, EmployedUnEmployedEnum employedUnEmployed,
	 * CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotNonPivot)
	 * throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17 and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("employedUnEmployed", employedUnEmployed);
	 * parameters.put("completedPlanned", completedPlanned);
	 * parameters.put("pivotNonPivot", pivotNonPivot); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countDisabledEquityPivotEnumOnEmployeesTraining(Long
	 * equityId, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
	 * String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.equity.id = :equityId and o.employee.disability.id not between 9 and 17 and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("equityId", equityId);
	 * parameters.put("pivotNonPivot", pivotNonPivot); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countAgeRangeOnEmployees(Date fromDate, Date toDate, Long
	 * wspId) throws Exception { String hql =
	 * "select count(o) from Employees o where o.wsp.id = :wspId and  date(o.dateOfBirth) between date(:toDate) and date(:fromDate)"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("fromDate", fromDate);
	 * parameters.put("toDate", toDate); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countAgeRangeOnEmployeesTraining(Date fromDate, Date toDate,
	 * Long wspId) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate)"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("fromDate", fromDate);
	 * parameters.put("toDate", toDate); return (Long)
	 * super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long
	 * countAgeRangeEmploymentEnumCompletedEnumPivotEnumOnEmployeesTraining(Date
	 * fromDate, Date toDate, Long wspId, EmployedUnEmployedEnum
	 * employedUnEmployed, CompletedPlannedEnum completedPlanned,
	 * PivotNonPivotEnum pivotNonPivot) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and  date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate) and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("fromDate", fromDate);
	 * parameters.put("toDate", toDate); parameters.put("employedUnEmployed",
	 * employedUnEmployed); parameters.put("completedPlanned",
	 * completedPlanned); parameters.put("pivotNonPivot", pivotNonPivot); return
	 * (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countAgeRangePivotEnumOnEmployeesTraining(Date fromDate, Date
	 * toDate, Long wspId, PivotNonPivotEnum pivotNonPivot) throws Exception {
	 * String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and  date(o.employee.dateOfBirth) between date(:toDate) and date(:fromDate) and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("fromDate", fromDate);
	 * parameters.put("toDate", toDate); parameters.put("pivotNonPivot",
	 * pivotNonPivot); return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countEmploymentEnumOnEmployees(Long wspId,
	 * EmployedUnEmployedEnum employedUnEmployed) throws Exception { String hql
	 * =
	 * "select count(o) from Employees o where o.wsp.id = :wspId and  o.employedUnEmployed = :employedUnEmployed"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("employedUnEmployed",
	 * employedUnEmployed); return (Long) super.getUniqueResult(hql,
	 * parameters); }
	 * 
	 * public Long countEmploymentEnumOnEmployeesTraining(Long wspId,
	 * EmployedUnEmployedEnum employedUnEmployed) throws Exception { String hql
	 * =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("employedUnEmployed",
	 * employedUnEmployed); return (Long) super.getUniqueResult(hql,
	 * parameters); }
	 * 
	 * public Long
	 * countEmploymentEnumCompletedEnumPivotEnumOnEmployeesTraining(Long wspId,
	 * EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum
	 * completedPlanned, PivotNonPivotEnum pivotNonPivot) throws Exception {
	 * String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("employedUnEmployed",
	 * employedUnEmployed); parameters.put("completedPlanned",
	 * completedPlanned); parameters.put("pivotNonPivot", pivotNonPivot); return
	 * (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countEmployedUnEmployedEnumPivotEnumOnEmployeesTraining(Long
	 * wspId, EmployedUnEmployedEnum employedUnEmployed, PivotNonPivotEnum
	 * pivotNonPivot) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.employedUnEmployed = :employedUnEmployed and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("employedUnEmployed",
	 * employedUnEmployed); parameters.put("pivotNonPivot", pivotNonPivot);
	 * return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long countEtqaEmploymentEnumOnEmployeesTraining(Long wspId, String
	 * etqaCode) throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.etqa.code like :etqaCode and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("etqaId",
	 * etqaCode.trim()); return (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 * public Long
	 * countEtqaEmploymentEnumCompletedEnumPivotEnumOnEmployeesTraining(Long
	 * wspId, String etqaCode, EmployedUnEmployedEnum employedUnEmployed,
	 * CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotNonPivot)
	 * throws Exception { String hql =
	 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.etqa.code like :etqaCode and o.employee.employedUnEmployed = :employedUnEmployed and o.completedPlanned  = :completedPlanned and o.pivotNonPivot = :pivotNonPivot"
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("wspId", wspId); parameters.put("etqaCode",
	 * etqaCode.trim()); parameters.put("employedUnEmployed",
	 * employedUnEmployed); parameters.put("completedPlanned",
	 * completedPlanned); parameters.put("pivotNonPivot", pivotNonPivot); return
	 * (Long) super.getUniqueResult(hql, parameters); }
	 * 
	 *//** This counts by Gender and Equity for Entity EmployeesTraining *//*
																			 * public
																			 * Long
																			 * countEtqaGenderEquityOnEmployeesTraining
																			 * (
																			 * Long
																			 * equityId,
																			 * String
																			 * etqaCode,
																			 * Long
																			 * genderId,
																			 * Long
																			 * wspId)
																			 * throws
																			 * Exception
																			 * {
																			 * String
																			 * hql
																			 * =
																			 * "select count(o) from EmployeesTraining o where o.employee.wsp.id = :wspId and o.employee.etqa.code like :etqaCode and  o.employee.equity.id = :equityId and o.employee.gender.id = :genderId"
																			 * ;
																			 * Map
																			 * <
																			 * String,
																			 * Object>
																			 * parameters
																			 * =
																			 * new
																			 * Hashtable
																			 * <
																			 * String,
																			 * Object
																			 * >
																			 * (
																			 * )
																			 * ;
																			 * parameters
																			 * .
																			 * put
																			 * (
																			 * "wspId",
																			 * wspId
																			 * )
																			 * ;
																			 * parameters
																			 * .
																			 * put
																			 * (
																			 * "etqaCode",
																			 * etqaCode
																			 * .
																			 * trim
																			 * (
																			 * )
																			 * )
																			 * ;
																			 * parameters
																			 * .
																			 * put
																			 * (
																			 * "equityId",
																			 * equityId
																			 * )
																			 * ;
																			 * parameters
																			 * .
																			 * put
																			 * (
																			 * "genderId",
																			 * genderId
																			 * )
																			 * ;
																			 * return
																			 * (Long)
																			 * super
																			 * .
																			 * getUniqueResult
																			 * (
																			 * hql,
																			 * parameters
																			 * )
																			 * ;
																			 * }
																			 */

}
