package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.BankingDetailsReportBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BankingDetailsDAO.
 */
public class BankingDetailsDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all BankingDetails.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception             global exception
	 * @see BankingDetails
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> allBankingDetails() throws Exception {
		return (List<BankingDetails>) super.getList("select o from BankingDetails o");
	}

	/**
	 * Get all BankingDetails between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception             global exception
	 * @see BankingDetails
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> allBankingDetails(Long from, int noRows) throws Exception {
		String hql = "select o from BankingDetails o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<BankingDetails>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.BankingDetails}
	 * @throws Exception             global exception
	 * @see BankingDetails
	 */
	public BankingDetails findByKey(Long id) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank left join fetch o.company where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (BankingDetails) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find BankingDetails by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception             global exception
	 * @see BankingDetails
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByName(String description) throws Exception {
		String hql = "select o from BankingDetails o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<BankingDetails>) super.getList(hql, parameters);
	}

	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompany(Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<BankingDetails>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company lates.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompanyLates(Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID and o.status <> :status order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("status", ApprovalEnum.Rejected);
		return (List<BankingDetails>) super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompanyLatestApproved(Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID and o.status = :status order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("status", ApprovalEnum.Approved);
		return (List<BankingDetails>) super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompanyLatestReturnFirstResult(Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID and o.status <> :status order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("status", ApprovalEnum.Rejected);
		return (List<BankingDetails>) super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompanyLatest(Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<BankingDetails>) super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<BankingDetails> findByCompanyBeforeLatest(Company company, BankingDetails bankingDetails) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.bank where o.company.id = :companyID and o.id <> :latestId order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("latestId", bankingDetails.getId());
		return (List<BankingDetails>) super.getList(hql, parameters, 1);
	}

	/**
	 * Find by company count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		String hql = "select count(o) from BankingDetails o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	public SDFCompany findPrimarySDF(Company company) throws Exception {
		String hql = "select o from SDFCompany o left join fetch o.sdfType left join fetch o.sdf where o.company.id = :companyID and o.sdfType.id = :prmID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("prmID", HAJConstants.PRIMARY_ID);
		return (SDFCompany) super.getUniqueResult(hql, parameters);
	}
	
	public List<BankingDetailsReportBean> populateCompanyBankingDetailsReport() throws Exception {
		String sql = "select    " + 
				"	c.levy_number as entityId   " + 
				"	, c.company_name as companyName   " + 
				"	, c.company_registration_number as companyRegNumber   " + 
				"	, c.trading_name as tradingName   " + 
				"	, case   " + 
				"		when b.id is not null then b.description   " + 
				"		else 'N/A'   " + 
				"	end as bankName   " + 
				"	, case   " + 
				"		when bd.id is not null and bd.bank_acc_number is not null then bd.bank_acc_number   " + 
				"		else 'N/A'   " + 
				"	end as accountNumber   " + 
				"	, case   " + 
				"		when bd.id is not null and bd.branch_code is not null then bd.branch_code   " + 
				"		else 'N/A'   " + 
				"	end as branchCode   " + 
				"	, case   " + 
				"		when bd.id is not null then bd.bank_holder   " + 
				"		else 'N/A'   " + 
				"	end as bankHolder   " + 
				"	, case   " + 
				"		when bd.status in (0) then 'Approved'   " + 
				"		when bd.status in (1) then 'Rejected'		   " + 
				"		when bd.status in (3) then 'Pending Approval'		   " + 
				"		when bd.status in (4) then 'Pending Sign Off'   " + 
				"		else 'N/A'   " + 
				"	end as bankDetailStatus   " + 
				"	, bd.approval_date as approvalDate   " + 
				"	, case   " + 
				"		when u.id is not null then CONCAT(u.first_name, ' ', u.last_name)   " + 
				"		else 'N/A'   " + 
				"	end as approvedBy   " + 
				"from    " + 
				"	company c   " + 
				"left join (   " + 
				"	select    " + 
				"		company_id as companyId   " + 
				"		, MAX(id) as maxBankId   " + 
				"	from    " + 
				"		banking_details    " + 
				"	group by    " + 
				"		company_id	   " + 
				") maxBank on maxBank.companyId = c.id   " + 
				"left join banking_details bd on bd.id = maxBank.maxBankId   " + 
				"left join bank b on b.id = bd.bank_id   " + 
				"left join users u on u.id = bd.approval_user_id   " + 
				"where   " + 
				"	c.company_status = '1'   " + 
				"	and (c.non_seta_company is null or c.non_seta_company = false)";
		return (List<BankingDetailsReportBean>) super.nativeSelectSqlList(sql, BankingDetailsReportBean.class);
	}
}
