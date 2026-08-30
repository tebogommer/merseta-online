package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.WspCalData;
import haj.com.dao.WspCalculationDataDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.Wsp;
import haj.com.entity.WspCalculationData;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspCalculationDataService extends AbstractService {
	/** The dao. */
	private WspCalculationDataDAO dao = new WspCalculationDataDAO();
	private ResolveByCodeLookupDAO lookupDAO = new ResolveByCodeLookupDAO();
	
	/* Service Levels */
	private CompanyService companyService = new CompanyService();
	private WspService wspService;
	
	
	private static WspCalculationDataService wspCalculationDataService;
	public static synchronized WspCalculationDataService instance() {
		if (wspCalculationDataService == null) {
			wspCalculationDataService = new WspCalculationDataService();
		}
		return wspCalculationDataService;
	}
	
	
	/**
	 * Get all WspCalculationData
	 * 
	 * @author TechFinium
	 * @see WspCalculationData
	 * @return a list of {@link haj.com.entity.WspCalculationData}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspCalculationData> allWspCalculationData() throws Exception {
		return dao.allWspCalculationData();
	}

	/**
	 * Create or update WspCalculationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspCalculationData
	 */
	public void create(WspCalculationData entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WspCalculationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspCalculationData
	 */
	public void update(WspCalculationData entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WspCalculationData.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspCalculationData
	 */
	public void delete(WspCalculationData entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WspCalculationData}
	 * @throws Exception
	 *             the exception
	 * @see WspCalculationData
	 */
	public WspCalculationData findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WspCalculationData by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WspCalculationData}
	 * @throws Exception
	 *             the exception
	 * @see WspCalculationData
	 */
	public List<WspCalculationData> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WspCalculationData
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCalculationData}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspCalculationData> allWspCalculationData(int first, int pageSize) throws Exception {
		return dao.allWspCalculationData(Long.valueOf(first), pageSize);
	}

	public void save(List<WspCalculationData> calculationData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) calculationData);
	}

	/**
	 * Get count of WspCalculationData for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WspCalculationData
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspCalculationData.class);
	}

	/**
	 * Lazy load WspCalculationData with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WspCalculationData class
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
	 * @return a list of {@link haj.com.entity.WspCalculationData} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspCalculationData> allWspCalculationData(Class<WspCalculationData> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WspCalculationData>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WspCalculationData for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WspCalculationData class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WspCalculationData entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WspCalculationData> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/**
	 * Counts how many entries assigned to company
	 * @param company
	 * @return int
	 * @throws Exception
	 */
	public int findByCompanyCount(Company company) throws Exception {
		return dao.findByCompanyCount(company.getId()).intValue();
	}
	
	public List<WspCalData> calPercentageByCompany(Long companyId) throws Exception {
		return dao.calPercentageByCompany(companyId);
	}
	
	public List<WspCalData> calcDeviationAmountByCompanyAndFinYear(Long companyId, Integer currentFinYear, Integer previousFinYear) throws Exception {
		return dao.calcDeviationAmountByCompanyAndFinYear(companyId, currentFinYear, previousFinYear);
	}
	
	public List<WspCalData> calcDeviationAmountByCompanyIdsAndFinYearsByStatus(Long currentFinYearCompany, Integer currentFinYear, Long previousFinYearCompany, Integer previousFinYear) throws Exception {
		return dao.calcDeviationAmountByCompanyIdsAndFinYearsByStatus(currentFinYearCompany, currentFinYear, previousFinYearCompany, previousFinYear);
	}

		
	
	/**
	 * Locates the DeviationAmount for wsp
	 * 
	 * @see Wsp
	 * @see Company
	 * @see WspCalData
	 * @param wsp
	 * @return amount
	 * @throws Exception
	 */
	public BigDecimal calculateAndReturnDeviationAmount(Wsp wsp) throws Exception{
		BigDecimal amount = null;
		List<WspCalData> calcData = new ArrayList<>();
		if (wsp.getFinYear() == null) {
			return amount;
		}
		if (wsp.getFinYear() == 2019) {
			calcData = calPercentageByCompany(wsp.getCompany().getId());
		} else {
			int previousYear = wsp.getFinYear() - 1;
			calcData = calcDeviationAmountByCompanyAndFinYear(wsp.getCompany().getId(), wsp.getFinYear(), previousYear);
		}
		
		if (calcData.size() > 0) {
			amount = calcData.get(0).getPercentage();
		}
		return amount;	
	}
	
	public BigDecimal calculateAndReturnDeviationAmountVersionTwo(Wsp wsp) throws Exception{
		BigDecimal amount = null;
		List<WspCalData> calcData = new ArrayList<>();
		
		if (wsp.getFinYear() == 2019) {
			
			calcData = calPercentageByCompany(wsp.getCompany().getId());
			
		} else {
			
			int previousYear = wsp.getFinYear() - 1;
			int currentYear = wsp.getFinYear();
			
			boolean usePreviousCompany = false;
			
			// check if there is a WSP assigned to company for previous year
			if (wspService == null) {
				wspService = new WspService();
			}
			if (companyService == null) {
				companyService = new CompanyService();
			}
			
			Company previousCompany = null;
			
			if (wspService.countByCompanyAndFinYearAndWspApprovedRejectedStatus(wsp.getCompany().getId(), previousYear) == 0) {
				if (wsp.getCompany() != null && wsp.getCompany().getPreviousCompany() != null && wsp.getCompany().getPreviousCompany().getId() != null) {
					previousCompany = companyService.findByKey(wsp.getCompany().getPreviousCompany().getId());
					if (previousCompany != null && wspService.countByCompanyAndFinYearAndWspApprovedRejectedStatus(previousCompany.getId(), previousYear) != 0) {
						usePreviousCompany = true;
					}
				}
			}
			
			if (usePreviousCompany) {
				calcData = calcDeviationAmountByCompanyIdsAndFinYearsByStatus(wsp.getCompany().getId(), currentYear, previousCompany.getId(), previousYear);
			} else {
				calcData = calcDeviationAmountByCompanyAndFinYear(wsp.getCompany().getId(), currentYear, previousYear);
			}
			
		}
		
		if (calcData.size() > 0) {
			amount = calcData.get(0).getPercentage();
		}
		return amount;	
	}
	
	public boolean calculateDeviationByWsp(Wsp wsp) throws Exception {
		boolean grantDeviated = false;
		BigDecimal minReq = (BigDecimal.valueOf(60));
		List<WspCalData> calcDataReportBean = new ArrayList<>();
		if (wsp.getFinYear() == 2019) {
			calcDataReportBean = calPercentageByCompany(wsp.getCompany().getId());
		} else {
			Integer previousYear = wsp.getFinYear() - 1;
			calcDataReportBean = calcDeviationAmountByCompanyAndFinYear(wsp.getCompany().getId(), wsp.getFinYear(), previousYear);
		}
		for (WspCalData data : calcDataReportBean) {	
			if (data.getPercentage() != null) {
				if (data.getPercentage().doubleValue() < minReq.doubleValue()) {
					grantDeviated = true;
				}else {
					grantDeviated = false;
				}
			}
			break;
		}
		return grantDeviated;
	}
	
	public boolean calculateDeviationByWspVersionTwo(Wsp wsp) throws Exception {
		boolean grantDeviated = false;
		BigDecimal minReq = (BigDecimal.valueOf(60));
		List<WspCalData> calcDataReportBean = new ArrayList<>();
		if (wsp.getFinYear() == 2019) {
			calcDataReportBean = calPercentageByCompany(wsp.getCompany().getId());
		} else {
			Integer previousYear = wsp.getFinYear() - 1;
			calcDataReportBean = calcDeviationAmountByCompanyAndFinYear(wsp.getCompany().getId(), wsp.getFinYear(), previousYear);
		}
		for (WspCalData data : calcDataReportBean) {	
			if (data.getPercentage() != null) {
				if (data.getPercentage().doubleValue() < minReq.doubleValue()) {
					grantDeviated = true;
				}else {
					grantDeviated = false;
				}
			}
			break;
		}
		return grantDeviated;
	}
		
}
