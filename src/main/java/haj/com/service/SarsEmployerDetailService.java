package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.SarsEmployerCompanyBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SarsEmployerDetailDAO;
import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.lookup.SICCode;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsEmployerDetailUpdateHistory;
import haj.com.sars.SarsFiles;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.SICCodeService;
import haj.com.service.lookup.SetaService;
import haj.com.utils.Initialize;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsEmployerDetailService.
 */
public class SarsEmployerDetailService extends AbstractService {
	/** The dao. */
	private SarsEmployerDetailDAO dao = new SarsEmployerDetailDAO();
	
	/** The sars levy details service. */
	private SarsLevyDetailsService sarsLevyDetailsService  =  new SarsLevyDetailsService();
	private SICCodeService  sicCodeService = new SICCodeService();
	private EtqaService etqaService;
	private SetaService setaService;
	private CompanyService companyService;

	/**
	 * Get all SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception the exception
	 * @see   SarsEmployerDetail
	 */
	public List<SarsEmployerDetail> allSarsEmployerDetail() throws Exception {
	  	return dao.allSarsEmployerDetail();
	}


	/**
	 * Create or update SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsEmployerDetail
	 */
	public void create(SarsEmployerDetail entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsEmployerDetail
	 */
	public void update(SarsEmployerDetail entity) throws Exception  {
		this.dao.update(entity);
	}
	
	public void updateAndCreateAuditUpdate(SarsEmployerDetail entity, Long sessionUserId) throws Exception  {
		// create audit
		SarsEmployerDetail currentDbEntry = findByKey(entity.getId());
		SarsEmployerDetailUpdateHistory audit = new SarsEmployerDetailUpdateHistory();
		org.apache.commons.beanutils.BeanUtils.copyProperties(audit, currentDbEntry);
		audit.setId(null);
		audit.setEmployerDetailLink(currentDbEntry.getId());
		audit.setDateUpdated(getSynchronizedDate());
		audit.setUserUpdateLink(sessionUserId);
		dao.create(audit);
		
		audit = null;
		currentDbEntry = null;
		// update entry
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsEmployerDetail.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsEmployerDetail
	 */
	public void delete(SarsEmployerDetail entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception the exception
	 * @see    SarsEmployerDetail
	 */
	public SarsEmployerDetail findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsEmployerDetail by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception the exception
	 * @see    SarsEmployerDetail
	 */
	public List<SarsEmployerDetail> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsEmployerDetail.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.sars.SarsEmployerDetail}
	 * @throws Exception the exception
	 */
	public List<SarsEmployerDetail> allSarsEmployerDetail(int first, int pageSize) throws Exception {
		return dao.allSarsEmployerDetail(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsEmployerDetail for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SarsEmployerDetail
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsEmployerDetail.class);
	}
	
    /**
     * Lazy load SarsEmployerDetail with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SarsEmployerDetail class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @param sarsFiles the sars files
     * @return  a list of {@link haj.com.sars.SarsEmployerDetail} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsEmployerDetail> allSarsEmployerDetail(Class<SarsEmployerDetail> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, SarsFiles sarsFiles) throws Exception {
		return resolveLevies( ( List<SarsEmployerDetail>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters,sarsFiles.getId()));
	
	}
	
    /**
     * Resolve levies.
     *
     * @param list the list
     * @return the list
     * @throws Exception the exception
     */
    private List<SarsEmployerDetail> resolveLevies(List<SarsEmployerDetail> list) throws Exception {
		for (SarsEmployerDetail s : list) {
			s.setLevies(sarsLevyDetailsService.findByMonthAndRef(s.getSarsFiles(), s.getRefNo()));
		}
		return list;
	}


	/**
	 * Get count of SarsEmployerDetail for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity SarsEmployerDetail class
	 * @param filters the filters
	 * @param sarsFiles the sars files
	 * @return Number of rows in the SarsEmployerDetail entity
	 * @throws Exception the exception
	 */	
	public int count(Class<SarsEmployerDetail> entity, Map<String, Object> filters, SarsFiles sarsFiles) throws Exception {
		return  dao.count(entity, filters,sarsFiles.getId());
	}
	
	/**
	 * Find by ref no.
	 *
	 * @param refNo the ref no
	 * @param sarsFiles the sars files
	 * @return the sars employer detail
	 * @throws Exception the exception
	 */
	public SarsEmployerDetail findByRefNo(String refNo, SarsFiles sarsFiles) throws Exception { 
		if (StringUtils.isBlank(refNo)) return null;
		return dao.findByRefNo(refNo,sarsFiles.getId());
	}
	
	/**
	 * Find SDL.
	 *
	 * @param refNo the ref no
	 * @return the sars employer detail
	 * @throws Exception the exception
	 */
	public SarsEmployerDetail findSDL(String refNo) throws Exception { 
		return dao.findSDL(refNo);
	}
	
	/**
	 * 
	 * @param sarsEmployerDetail
	 * @param company
	 * @return
	 */
	public boolean compareSarsEmployerToCustomer(SarsEmployerDetail sarsEmployerDetail, Company company) {
		 Initialize.initStringsIfNull(sarsEmployerDetail);
		 Initialize.initStringsIfNull(company);
		return (sarsEmployerDetail.getRegisteredNameOfEntity().trim().toUpperCase()+sarsEmployerDetail.getTradingName().trim().toUpperCase()+sarsEmployerDetail.getTradingStatus().trim().toUpperCase()).hashCode() 
				== 
			   (company.getCompanyName().trim().toUpperCase()+company.getTradingName().trim().toUpperCase()+company.getSarsTradingStatus().trim().toUpperCase()).hashCode();
	}
	
	public List<SarsEmployerDetail> populateFromSars(Long fileId) throws Exception { 
		return dao.populateFromSars(fileId);
	}
	
	public List<SarsEmployerCompanyBean> compareToSars(Long fileId) throws Exception { 
		return dao.compareToSars(fileId);
	}
	
	
	public void populateNewFromSARSThreaded(final Long fileId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					populateNewFromSARS(fileId);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public void populateNewFromSARS(Long fileId) throws Exception {
		List<SarsEmployerDetail> l = populateFromSars(fileId);
		Map<String,SICCode> m = new HashMap<String,SICCode>();
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		int cntr = 1, total = 0;
		for (SarsEmployerDetail sb : l) {
			Company c = new Company();
			c.setNonSetaCompany(false);
			c.setLevyNumber(sb.getRefNo().trim());
			c.setSicCode(sicCodeService.findByCode(m,sb.getSicCode2().trim()));
			c.setCompanyName(sb.getRegisteredNameOfEntity().trim().length()<71 ? sb.getRegisteredNameOfEntity().trim() : sb.getRegisteredNameOfEntity().trim().substring(0, 70));
			c.setTradingName(sb.getTradingName().trim());
			c.setCompanyRegistrationNumber(sb.getCompanyRegistrationNumber());
			c.setSarsTradingStatus(sb.getTradingStatus().trim());
			c.setCategorization(CategorizationEnum.Silver);
			try {
				if (etqaService == null) {
					etqaService = new EtqaService();
				}
				if (setaService == null) {
					setaService = new SetaService();
				}
				c.setEtqa(etqaService.findByCode(HAJConstants.HOSTING_MERSETA_ETQA));
				c.setSeta(setaService.findBySetmisCode(HAJConstants.HOSTING_MERSETA_ETQA_SETMIS));
			} catch (Exception e) {
				logger.fatal(e);
			}
			entityList.add(c); {
				if (cntr==1000) {
					logger.info("Done " +total);
					dao.createBatch(entityList);
					entityList.clear();
					cntr = 1;
				}
			}
			cntr++;
			total++;
		}
		
		if (entityList.size()>0) {
			dao.createBatch(entityList);
		}
		logger.info("TOTAL = "+total);
		
		 // assign site numbers to companies
		if (companyService == null) {
			companyService = new CompanyService();
		}
		companyService.updateLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned();
	}
	
	public String testValidiationsForCompanyInsert(Long sarsFileId) throws Exception {
		CompanyService companyServiceTest = new CompanyService();
		StringBuilder validiationErrors = new StringBuilder();
		List<SarsEmployerDetail> l = populateFromSars(sarsFileId);
		Map<String,SICCode> m = new HashMap<>();
		for (SarsEmployerDetail sb : l) {

			StringBuilder test = new StringBuilder();
			
			Company c = new Company();
			c.setNonSetaCompany(false);
			c.setLevyNumber(sb.getRefNo().trim());
			c.setSicCode(sicCodeService.findByCode(m,sb.getSicCode2().trim()));
			c.setCompanyName(sb.getRegisteredNameOfEntity().trim().length()<71 ? sb.getRegisteredNameOfEntity().trim() : sb.getRegisteredNameOfEntity().trim().substring(0, 70));
			c.setTradingName(sb.getTradingName().trim());
			c.setCompanyRegistrationNumber(sb.getCompanyRegistrationNumber());
			c.setSarsTradingStatus(sb.getTradingStatus().trim());
			c.setCategorization(CategorizationEnum.Silver);
			
			test = companyServiceTest.validiateCompanyInformation(c);
			if (!test.toString().trim().isEmpty()) {
				validiationErrors.append("--- Error For: <strong>" + sb.getRefNo() + "</strong>");
				validiationErrors.append("<br/>");
				validiationErrors.append(test.toString());
			}
			
		}
		return validiationErrors.toString();
	}
	
	public void populateUpdateExistingFromSARS(Long fileId) throws Exception {
		List<SarsEmployerCompanyBean> l = compareToSars(fileId);
		for (SarsEmployerCompanyBean sb : l) {
			sb.getCompany().setSicCode(sicCodeService.findByCode(sb.getSarsEmployerDetail().getSicCode2().trim()));
			sb.getCompany().setCompanyName(sb.getSarsEmployerDetail().getRegisteredNameOfEntity().trim());
			sb.getCompany().setTradingName(sb.getSarsEmployerDetail().getTradingName().trim());
			sb.getCompany().setCompanyRegistrationNumber(sb.getSarsEmployerDetail().getCompanyRegistrationNumber());
			sb.getCompany().setSarsTradingStatus(sb.getSarsEmployerDetail().getTradingStatus().trim());
			dao.update(sb.getCompany());
		}
	}
	
	public  List<SarsEmployerCompanyBean> sarsStatusChanged(Long fileId) throws Exception { 
		return dao.sarsStatusChanged(fileId);
	}


	public void procesPending(List<SarsEmployerCompanyBean> list) throws Exception  {
		for (SarsEmployerCompanyBean sb : list) {
			if (!"A".equals(sb.getSarsEmployerDetail().getTradingStatus().trim())) {
				if (sb.getCompany().getCompanyStatus() == CompanyStatusEnum.Pending) {
					sb.getCompany().setCompanyStatus(CompanyStatusEnum.InActive);
					dao.update(sb.getCompany());
				}
			}
		}
		
	}
	
	
	public List<SarsEmployerCompanyBean> nonMatchingSicCodes() throws Exception { 
		return dao.nonMatchingSicCodes();
	}
	
	public List<SarsEmployerDetail> findByRefNumberReturnOneResult(String refNumber) throws Exception {
		return dao.findByRefNumberReturnOneResult(refNumber);
	}
	
	public Integer countByRefNumber(String refNumber) throws Exception {
		return dao.countByRefNumber(refNumber);
	}

}
