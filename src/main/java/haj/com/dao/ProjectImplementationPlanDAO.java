package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.bean.PipReportBean;
import haj.com.entity.ActiveContracts;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ProjectImplementationPlanDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProjectImplementationPlan
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlan() throws Exception {
		return (List<ProjectImplementationPlan>) super.getList("select o from ProjectImplementationPlan o");
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlanNotLinkedToActiveContracts() throws Exception {
		return (List<ProjectImplementationPlan>) super.getList(
				"select o from ProjectImplementationPlan o where o.activeContracts is null");
	}

	/**
	 * Get all ProjectImplementationPlan between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see ProjectImplementationPlan
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> allProjectImplementationPlan(Long from, int noRows) throws Exception {
		String hql = "select o from ProjectImplementationPlan o ";
		Map<String, Object> parameters = new Hashtable<>();

		return (List<ProjectImplementationPlan>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see ProjectImplementationPlan
	 * @return a {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             global exception
	 */
	public ProjectImplementationPlan findByKey(Long id) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("id", id);
		return (ProjectImplementationPlan) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProjectImplementationPlan by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see ProjectImplementationPlan
	 * @return a list of {@link haj.com.entity.ProjectImplementationPlan}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByName(String description) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompany(Long companyId) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.totalAwardAmount > 0";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByWspWhereAwarded(Long wspId) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("wspId", wspId);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionType(Long companyId, Long interventionTypeID)
			throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndApproved(Long companyId,
			Long interventionTypeID) throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndSkillsProgramme(Long companyId,
			Long interventionTypeID, Long skillsProgramID) throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear and o.dgAllocation.mandatoryGrant.id in (select x.mandatoryGrant.id from MandatoryGrantRecommendation x and x.skillsProgram.id = :skillsProgramID)";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("skillsProgramID", skillsProgramID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndSkillsSet(Long companyId,
			Long interventionTypeID, Long skillsSetID) throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear and o.dgAllocation.mandatoryGrant.id in (select x.mandatoryGrant.id from MandatoryGrantRecommendation x and x.skillsSet.id = :skillsSetID)";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("skillsSetID", skillsSetID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndUnitStandards(Long companyId,
			Long interventionTypeID, Long unitStandardsID) throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear and o.dgAllocation.mandatoryGrant.id in (select x.mandatoryGrant.id from MandatoryGrantRecommendation x and x.unitStandards.id = :unitStandardsID)";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("unitStandardsID", unitStandardsID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndQualification(Long companyId,
			Long interventionTypeID, Long qualificationID) throws Exception {
		Integer finYear = 2019;
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.totalAwardAmount > 0 and o.wsp.finYear > :finYear and o.dgAllocation.mandatoryGrant.id in (select x.mandatoryGrant.id from MandatoryGrantRecommendation x and x.qualification.id = :qualificationID)";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("qualificationID", qualificationID);
		parameters.put("finYear", finYear);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByCompanyAndInterventionTypeAndStatus(Long companyId,
			Long interventionTypeID, ApprovalEnum status) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.company.id = :companyID and o.interventionType.id = :interventionTypeID and o.activeContracts.status = :status and o.totalAwardAmount > 0";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("companyID", companyId);
		parameters.put("interventionTypeID", interventionTypeID);
		parameters.put("status", status);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByActiveContract(ActiveContracts activeContracts) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.activeContracts.id = :activeContractsID";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("activeContractsID", activeContracts.getId());
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	public Integer countByActiveContract(ActiveContracts activeContracts) throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where o.activeContracts.id = :activeContractsID";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("activeContractsID", activeContracts.getId());
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByWsp(Long wspId) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("wspId", wspId);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectImplementationPlan> findByWspWhereTotalaountIsGreaterThanZero(Long wspId) throws Exception {
		String hql = "select o from ProjectImplementationPlan o where o.wsp.id = :wspId and o.totalAwardAmount > 0";
		Map<String, Object> parameters = new Hashtable<>();
		parameters.put("wspId", wspId);
		return (List<ProjectImplementationPlan>) super.getList(hql, parameters);
	}

	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName")
							|| entry.getKey().contains("levyNumber")) {
						hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}
		/*
		 * if (filters != null) { boolean ft = true; Map<String, Object> parms =
		 * new HashMap<String, Object>(); String hvar = null; for (Entry<String,
		 * Object> entry : filters.entrySet()) { hvar = entry.getKey(); if
		 * (hvar.contains(".")) { hvar = hvar.replaceAll("\\.", "");
		 * parms.put(hvar, entry.getValue()); } else { parms.put(entry.getKey(),
		 * entry.getValue()); } if (ft) {
		 * if(entry.getKey().contains("companyName")) { hql +=
		 * " and o.wsp.company.companyName" + entry.getKey() + " like " + " :" +
		 * hvar; ft = false; }else if(entry.getKey().contains("levyNumber")) {
		 * hql += " and o.wsp.company.levyNumber" + entry.getKey() + " like " +
		 * " :" + hvar; ft = false; } } else { hql += " and o." + entry.getKey()
		 * + " like " + " :" + hvar; } }
		 * 
		 * filters = parms; }
		 */

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				if (sortField.contains("companyName") || sortField.contains("tradingName")
						|| sortField.contains("levyNumber")) {
					hql += " and o.wsp." + sortField + " asc ";
				} else {
					hql += " order by o." + sortField + " asc ";
				}
				break;
			case DESCENDING:
				if (sortField.contains("companyName") || sortField.contains("tradingName")
						|| sortField.contains("levyNumber")) {
					hql += " and o.wsp." + sortField + " desc ";
				} else {
					hql += " order by o." + sortField + " desc ";
				}
				break;
			default:
				break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}
	
	public List<?> sortAndFilterSearchWhere(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) throws Exception {
		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName")
							|| entry.getKey().contains("levyNumber")) {
						hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}
		/*
		 * if (filters != null) { boolean ft = true; Map<String, Object> parms =
		 * new HashMap<String, Object>(); String hvar = null; for (Entry<String,
		 * Object> entry : filters.entrySet()) { hvar = entry.getKey(); if
		 * (hvar.contains(".")) { hvar = hvar.replaceAll("\\.", "");
		 * parms.put(hvar, entry.getValue()); } else { parms.put(entry.getKey(),
		 * entry.getValue()); } if (ft) {
		 * if(entry.getKey().contains("companyName")) { hql +=
		 * " and o.wsp.company.companyName" + entry.getKey() + " like " + " :" +
		 * hvar; ft = false; }else if(entry.getKey().contains("levyNumber")) {
		 * hql += " and o.wsp.company.levyNumber" + entry.getKey() + " like " +
		 * " :" + hvar; ft = false; } } else { hql += " and o." + entry.getKey()
		 * + " like " + " :" + hvar; } }
		 * 
		 * filters = parms; }
		 */

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				if (sortField.contains("companyName") || sortField.contains("tradingName")
						|| sortField.contains("levyNumber")) {
					hql += " and o.wsp." + sortField + " asc ";
				} else {
					hql += " order by o." + sortField + " asc ";
				}
				break;
			case DESCENDING:
				if (sortField.contains("companyName") || sortField.contains("tradingName")
						|| sortField.contains("levyNumber")) {
					hql += " and o.wsp." + sortField + " desc ";
				} else {
					hql += " order by o." + sortField + " desc ";
				}
				break;
			default:
				break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}

	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		String hql = "select count(o) from " + entity.getName() + " o where o.wsp is not null";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {

					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName")
							|| entry.getKey().contains("levyNumber")) {
						hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
	
	public int countSearchWhere(Map<String, Object> filters,String hql) {
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(entry.getKey())) {

					if (entry.getKey().contains("companyName") || entry.getKey().contains("tradingName")
							|| entry.getKey().contains("levyNumber")) {
						hql += " and o.wsp." + entry.getKey() + " like " + " :" + hvar;
					} else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}

				}
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}

	public int countByCloCrmProjectImplementationPlanByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType,
			Integer finYear) throws Exception {
		String hql = "select count(o) from ProjectImplementationPlan o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userID", userID);
		parameters.put("moaType", moaType);
		parameters.put("finYear", finYear);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	public List<PipReportBean> populatePipReportBean(Integer grantYear) throws Exception {
		String sql = "select    " + 
				"	c.levy_number as entityId   " + 
				"	, c.company_name as companyName   " + 
				"	, cham.description as chamber   " + 
				"	, r.description as region   " + 
				"	, CONCAT(c.levy_number, 'DGYR',w.fin_year) as referanceNumber   " + 
				"	, case   " + 
				"		when ac.id is not null then   " + 
				"			case   " + 
				"				when ac.moa_type = 'DGMOA' then 'DG MOA'   " + 
				"				when ac.moa_type = 'SpecialProject' then 'Special Project'   " + 
				"				else ''   " + 
				"			end   " + 
				"		when acw.id is not null then   " + 
				"			case   " + 
				"				when acw.moa_type = 'DGMOA' then 'DG MOA'   " + 
				"				when acw.moa_type = 'SpecialProject' then 'Special Project'   " + 
				"				else ''   " + 
				"			end   " + 
				"		else ''   " + 
				"	end as moaType   " + 
				"	, pip.intervention_type_description as category   " + 
				"	, pip.fully_funded_learner_awarded as fullyFundedLearnerAwarded   " + 
				"	, pip.projected_recruitment_date as projectedRecruitmentDate   " + 
				"	, pip.projected_induction_date as projectedInductionDate   " + 
				"	, pip.projected_registration_date as projectedDateOfRegistration   " + 
				"	, pip.estimated_progress_date as estimatedProgressDate   " + 
				"	, pip.estimated_completion_date as estimatedCompleteDate   " + 
				"	, case    " + 
				"		when pip.accredited_provider_identified = '0' then 'Yes'   " + 
				"		when pip.accredited_provider_identified = '1' then 'No'   " + 
				"		else 'N/A'   " + 
				"	end as accreditedProviderIdentified   " + 
				"	, case    " + 
				"		when pip.logistical_aspects_addressed = '0' then 'Yes'   " + 
				"		when pip.logistical_aspects_addressed = '1' then 'No'   " + 
				"		else 'N/A'   " + 
				"	end as logisticalAspectsAddressed   " + 
				"	, case   " + 
				"		when uClo.id is not null then CONCAT(uClo.first_name, ' ', uClo.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as cloFullName   " + 
				"	, case   " + 
				"		when primeSdfUser.id is not null then CONCAT(primeSdfUser.first_name, ' ', primeSdfUser.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as sdfFullName   " + 
				"	, case   " + 
				"		when primeSdfUser.id is not null then primeSdfUser.email   " + 
				"		else 'N/A'   " + 
				"	end as sdfEmail   " + 
				"	, case   " + 
				"		when primeSdfUser.id is not null then primeSdfUser.cell_number   " + 
				"		else 'N/A'   " + 
				"	end as sdfContactNumber   " + 
				"	, case   " + 
				"		when secSdfUser.id is not null then CONCAT(secSdfUser.first_name, ' ', secSdfUser.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as secSdfFullName   " + 
				"	, case   " + 
				"		when secSdfUser.id is not null then secSdfUser.email   " + 
				"		else 'N/A'   " + 
				"	end as secSdfEmail   " + 
				"	, case   " + 
				"		when secSdfUser.id is not null then secSdfUser.cell_number   " + 
				"		else 'N/A'   " + 
				"	end as secSdfContactNumber   " + 
				"from   " + 
				"    " + 
				"	project_implementation_plan pip   " + 
				"	   " + 
				"left join wsp w on w.id = pip.wsp_id   " + 
				"   " + 
				"left join company c on c.id = w.company_id   " + 
				"left join sic_code sc on sc.id = c.sic_code_id   " + 
				"left join chamber cham on cham.id = sc.chamber_id   " + 
				"left join address resAdd on resAdd.id = c.residential_address_id   " + 
				"left join region_town rt on rt.town_id = resAdd.town_id   " + 
				"left join region r on r.id = rt.region_id   " + 
				"   " + 
				"left join hosting_company_employees hceClo on hceClo.id = rt.clo_id   " + 
				"left join job_title jtClo on jtClo.id = hceClo.job_title_id   " + 
				"left join users uClo on uClo.id = hceClo.user_id   " + 
				"   " + 
				"left join active_contracts ac on ac.id = pip.active_contracts_id   " + 
				"   " + 
				"left join dg_allocation_parent dap on dap.wsp_id = w.id   " + 
				"left join active_contracts acw on acw.dg_allocation_parent_id = dap.id   " + 
				"   " + 
				"left join (   " + 
				"	   " + 
				"	select    " + 
				"		company_id as compId   " + 
				"		, Max(id) as sdfId   " + 
				"	from    " + 
				"		sdf_company   " + 
				"	where   " + 
				"		company_id is not null   " + 
				"		and sdf_id is not null   " + 
				"		and sdf_type_id = '1'   " + 
				"	group by    " + 
				"		company_id   " + 
				"   " + 
				") as compPrimry on compPrimry.compId = c.id   " + 
				"left join sdf_company primeSdf on primeSdf.id = compPrimry.sdfId   " + 
				"left join users primeSdfUser on  primeSdfUser.id = primeSdf.sdf_id   " + 
				"   " + 
				"left join (   " + 
				"	   " + 
				"	select    " + 
				"		company_id as compId   " + 
				"		, Max(id) as sdfId   " + 
				"	from    " + 
				"		sdf_company   " + 
				"	where   " + 
				"		company_id is not null   " + 
				"		and sdf_id is not null   " + 
				"		and sdf_type_id in (   " + 
				"			'5', '6', '7', '8', '9', '10'   " + 
				"		)   " + 
				"	group by    " + 
				"		company_id   " + 
				") as compSec on compSec.compId = c.id   " + 
				"left join sdf_company secSdf on secSdf.id = compSec.sdfId   " + 
				"left join users secSdfUser on secSdfUser.id = secSdf.sdf_id   " + 
				"   " + 
				"where   " + 
				"	(ac.moa_type = 'DGMOA' or acw.moa_type = 'DGMOA')   " + 
				"	and w.fin_year = " + grantYear.toString();
		return (List<PipReportBean>) super.nativeSelectSqlList(sql, PipReportBean.class);
	}
	
}
