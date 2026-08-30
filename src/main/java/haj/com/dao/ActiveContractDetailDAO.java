package haj.com.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ActiveContractDetailDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ActiveContractDetail
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetail() throws Exception {
		return (List<ActiveContractDetail>) super.getList("select o from ActiveContractDetail o");
	}

	/**
	 * Get all ActiveContractDetail between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see ActiveContractDetail
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> allActiveContractDetail(Long from, int noRows) throws Exception {
		String hql = "select o from ActiveContractDetail o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<ActiveContractDetail>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see ActiveContractDetail
	 * @return a {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             global exception
	 */
	public ActiveContractDetail findByKey(Long id) throws Exception {
		String hql = "select o from ActiveContractDetail o left join fetch o.companyLearners where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (ActiveContractDetail) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ActiveContractDetail by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see ActiveContractDetail
	 * @return a list of {@link haj.com.entity.ActiveContractDetail}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> findByName(String description) throws Exception {
		String hql = "select o from ActiveContractDetail o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<ActiveContractDetail>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractDetail> findNotProcessedForGp() throws Exception {
		String hql = "select o from ActiveContractDetail o "
					+ "left join fetch o.activeContracts ac "
					+ "left join fetch ac.dgAllocationParent dap "
					+ "left join fetch dap.wsp wsp "
					+ "where o.status = :status "
					+ "and o.id not in (select x.activeContractDetail.id from GpGrantBatchEntry x where x.activeContractDetail is not null) "
					+ "and (dap is null or wsp.finYear <> :finYearIgnored or wsp.finYear is null) "
					+ "order by o.createDate ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", ApprovalEnum.Approved);
		parameters.put("finYearIgnored", 2019);
		return (List<ActiveContractDetail>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findByCompanyYear(Company company, Integer year) throws Exception {
		String hql = "select o from ActiveContracts o where o.dgAllocationParent.wsp.company.id = :companyID and  o.dgAllocationParent.wsp.finYear = :year";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyID", company.getId());
		parameters.put("year", year);
		return (List<ActiveContracts>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<BigDecimal> findLastClosingBalance(ActiveContracts activeContracts) throws Exception {
		String hql = "select o.closingbalance from ActiveContractDetail o where o.activeContracts.id = :activeContractsID order by o.id desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("activeContractsID", activeContracts.getId());
		return (List<BigDecimal>) super.getList(hql, parameters, 1);
	}
	
	public Integer countActiveContractDetailByActiveContract(Long activeContractId) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.activeContracts.id = :activeContractId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("activeContractId", activeContractId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countActiveContractDetailByPip(Long pipId) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.projectImplementationPlan.id = :pipId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("pipId", pipId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findAllActiveContracts() throws Exception {
		String hql = "select distinct(o.activeContracts) from ActiveContractDetail o ";
		return (List<ActiveContracts>) super.getList(hql);
	}
	
	/*
	 * Count by company learner assigned to Tranch payment where not open 
	 */
	public Integer countByCompanyLearnerTranchAndNotEqualStatus(Long companyLearnerId, TrancheEnum tranchEnum, ApprovalEnum approvalEnum) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where o.companyLearners.id = :companyLearnersId and o.trancheEnum = :tranchEnum and o.status <> :approvalEnum";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("companyLearnerId", companyLearnerId);
		parameters.put("tranchEnum", tranchEnum);
		parameters.put("approvalEnum", approvalEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCloCrmActiveContractDetailRegionReportByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where "
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
	
	public int countActiveContractDetailReportByMoaTypeAndGrantYear(MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select count(o) from ActiveContractDetail o where "
				+ " o.activeContracts.moaType = :moaType and "
				+ " o.activeContracts.dgAllocationParent.wsp.finYear = :finYear and "
				+ " o.activeContracts.dgAllocationParent.wsp.company.nonSetaCompany = false";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("moaType", moaType);
		parameters.put("finYear", finYear);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
}
