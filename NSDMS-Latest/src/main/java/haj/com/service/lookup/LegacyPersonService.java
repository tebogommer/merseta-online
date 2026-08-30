package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyPersonDAO;
import haj.com.entity.Users;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.LegacyPerson;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyPersonService extends AbstractService {
	/** The dao. */
	private LegacyPersonDAO dao = new LegacyPersonDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	private static LegacyPersonService legacyPersonService = null;
	public static synchronized LegacyPersonService instance() {
		if (legacyPersonService == null) {
			legacyPersonService = new LegacyPersonService();
		}
		return legacyPersonService;
	}
	/**
	 * Get all LegacyPerson
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 * @return a list of {@link haj.com.entity.LegacyPerson}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyPerson> allLegacyPerson() throws Exception {
		return dao.allLegacyPerson();
	}

	/**
	 * Create or update LegacyPerson.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public void create(LegacyPerson entity) throws Exception {
		if (entity.getId() == null) {
			dao.create(entity);
		} else
			dao.update(entity);
	}

	/**
	 * Update LegacyPerson.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public void update(LegacyPerson entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * Delete LegacyPerson.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public void delete(LegacyPerson entity) throws Exception {
		dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyPerson}
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public LegacyPerson findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyPerson by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyPerson}
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public List<LegacyPerson> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Find LegacyPerson by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyPerson}
	 * @throws Exception
	 *             the exception
	 * @see LegacyPerson
	 */
	public  LegacyPerson findByIdNo(String idNo) throws Exception {
		return dao.findByIdNo(idNo);
	}

	/**
	 * Lazy load LegacyPerson
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyPerson}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyPerson> allLegacyPerson(int first, int pageSize) throws Exception {
		return dao.allLegacyPerson(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyPerson for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyPerson
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyPerson.class);
	}

	/**
	 * Lazy load LegacyPerson with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyPerson class
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
	 * @return a list of {@link haj.com.entity.LegacyPerson} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPerson(Class<LegacyPerson> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyPerson>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of LegacyPerson for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyPerson class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyPerson entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyPerson> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPersonRsaIdNumberEmpty(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyPerson o where o.validIdNumber is null";
		return (List<LegacyPerson>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLegacyPersonRsaIdNumberEmpty(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyPerson o where o.validIdNumber is null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPersonRsaIdNumberByValue(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyPerson o where o.validIdNumber = :value";
		filters.put("value", value);
		return (List<LegacyPerson>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLegacyPersonRsaIdNumberByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyPerson o where o.validIdNumber = :value";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyPerson> allEntries = allLegacyPerson();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyPerson> csvData) throws Exception {
		dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllValidRsaIdNumberEmpty() throws Exception {
		return dao.countAllValidRsaIdNumberEmpty();
	}

	public Integer countAllValidRsaIdNumberByValue(Boolean value) throws Exception {
		return dao.countAllValidRsaIdNumberByValue(value);
	}
	
	public List<LegacyPerson> allLegacyPersonNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyPersonNotProcessed(numberOfEntries);
	}
	
	public Integer countAllLegacyPersonNotProcessed() throws Exception {
		return dao.countAllLegacyPersonNotProcessed();
	}
	
	public Integer countAllEntriesByIdNo(String idNo) throws Exception {
		return dao.countAllEntriesByIdNo(idNo);
	}
	
	public boolean idNumberOnPersonFile(String idNo) throws Exception{
		return countAllEntriesByIdNo(idNo) > 0;
	}
	
	public Integer countAllEntriesByAlternativeIdNumber(String alternativeIdNo) throws Exception {
		return dao.countAllEntriesByAlternativeIdNumber(alternativeIdNo);
	}
	
	public boolean alternativeIdNumberOnPersonFile(String alternativeIdNo) throws Exception{
		return countAllEntriesByAlternativeIdNumber(alternativeIdNo) > 0;
	}

	public void validateRsaIdNumbers() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationRun();
			}
		}).start();
	}

	public void validiationRun() {
		try {
			Integer count = countAllLegacyPersonNotProcessed();	
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyPerson> lp = allLegacyPersonNotProcessed(2500);
				for (LegacyPerson legacyPerson : lp) {
					
					// Valid ID Number
//					try {
//						legacyPerson.setValidIdNumber(GenericUtility.checkRsaId(legacyPerson.getIdNo()));
//					} catch (Exception e) {
//						legacyPerson.setValidIdNumber(false);
//					}
//					
//					// Valid Alternate ID No
//					try {
//						legacyPerson.setValidAlternateIdNo(GenericUtility.checkRsaId(legacyPerson.getIdNo()));
//					} catch (Exception e) {
//						legacyPerson.setValidAlternateIdNo(false);
//					}
//					
//					// on home affairs file
//					try {
//						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyPerson.getIdNo().trim());
//						if (entriesFound > 0) {
//							legacyPerson.setAppearsOnHomeAffairsData(true);
//						} else {
//							legacyPerson.setAppearsOnHomeAffairsData(false);
//						}
//					} catch (Exception e) {
//						legacyPerson.setAppearsOnHomeAffairsData(false);
//					}
					
					// Validate full name matches on home affairs data (findByFullNamesAndIdNumber)
					try {
						String fullName = "";
						if (legacyPerson.getFirstName() != null && !legacyPerson.getFirstName().isEmpty()) {
							fullName += legacyPerson.getFirstName().trim() + " ";
						}
						if (legacyPerson.getMiddleNames() != null && !legacyPerson.getMiddleNames().isEmpty()) {
							fullName += legacyPerson.getMiddleNames().trim();
						}
						Integer entriesFoundFullNames = homeAffairsService.findByFullNamesAndIdNumber(legacyPerson.getIdNo().trim(), fullName, legacyPerson.getSurname().trim(), legacyPerson.getMaidenName().trim());
						if (entriesFoundFullNames > 0) {
							legacyPerson.setFullNamesMatchesOnHomeAffairsData(true);
						} else {
							legacyPerson.setFullNamesMatchesOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyPerson.setFullNamesMatchesOnHomeAffairsData(false);
					}
					
					legacyPerson.setProcessed(true);
					
					updateList.add(legacyPerson);
				}
				
				lp = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyPersonNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Persons Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Persons Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public void populateLegacyUser(Users user, LegacyPerson legacyPerson) throws Exception {
		if(legacyPerson !=null && legacyPerson.getId() != null) {
			if(legacyPerson.getIdNo() != null) {
				user.setRsaIDNumber(legacyPerson.getIdNo());
			}else if(legacyPerson.getAlternateIDNo() != null && !legacyPerson.getAlternateIDNo().equals("")&& !legacyPerson.getAlternateIDNo().equalsIgnoreCase("NULL")){
				user.setPassportNumber(legacyPerson.getAlternateIDNo());
			}else {
				throw new Exception("User does not have ID and Passport Number");
			}			
			user.setFirstName(legacyPerson.getFirstName());
			if(legacyPerson.getMaidenName() !=null) {
				user.setMiddleName(legacyPerson.getMiddleNames());
			}			
			user.setLastName(legacyPerson.getSurname());
			if(legacyPerson.getTitleDesc()!=null) {
				TitleService titleService = new TitleService();
				if(legacyPerson.getTitleDesc().equalsIgnoreCase("Adv")) {
					user.setTitle(titleService.findByKey(1l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Dr")) {
					user.setTitle(titleService.findByKey(2l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Miss")) {
					user.setTitle(titleService.findByKey(3l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Mr")) {
					user.setTitle(titleService.findByKey(4l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Ms")) {
					user.setTitle(titleService.findByKey(5l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Prof")) {
					user.setTitle(titleService.findByKey(6l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Rev")) {
					user.setTitle(titleService.findByKey(7l));
				}else if(legacyPerson.getTitleDesc().equalsIgnoreCase("Mrs")) {
					user.setTitle(titleService.findByKey(8l));
				}
			}
			
			if(legacyPerson.getDateOfBirth() != null) {				
				user.setDateOfBirth(new Date(legacyPerson.getDateOfBirth()));
			}
			
			if(legacyPerson.getEmail()!=null) {
				user.setEmail(legacyPerson.getEmail());
			}
			
			if(legacyPerson.getEquityDesc()!=null && !legacyPerson.getEquityDesc().equalsIgnoreCase("") && !legacyPerson.getEquityDesc().equalsIgnoreCase("null")) {
				EquityService equityService = new EquityService();
				if(legacyPerson.getEquityDesc().equalsIgnoreCase("Black: African")) {
					user.setEquity(equityService.findByKey(1l));
				}else if(legacyPerson.getEquityDesc().equalsIgnoreCase("Black: Coloured")) {
					user.setEquity(equityService.findByKey(2l));
				}else if(legacyPerson.getEquityDesc().equalsIgnoreCase("White")) {
					user.setEquity(equityService.findByKey(6l));
				}else if(legacyPerson.getEquityDesc().equalsIgnoreCase("Black: Indian/Asian")) {
					user.setEquity(equityService.findByKey(3l));
				}else {
					user.setEquity(equityService.findByKey(5l));
				}
			}
			
			user.setCellNumber(legacyPerson.getCellNo());
			user.setTelNumber(legacyPerson.getAlternatePhoneNo());
			
			if(legacyPerson.getIsDisability()!=null && !legacyPerson.getIsDisability().equalsIgnoreCase("") && !legacyPerson.getIsDisability().equalsIgnoreCase("Null")) {
				if(legacyPerson.getIsDisability().equalsIgnoreCase("0")){
					user.setDisability(YesNoEnum.No);
				}else if(legacyPerson.getIsDisability().equalsIgnoreCase("1")){
					user.setDisability(YesNoEnum.Yes);
				}
			}
			
			if(legacyPerson.getGenderDesc()!=null && !legacyPerson.getGenderDesc().equalsIgnoreCase("") && !legacyPerson.getGenderDesc().equalsIgnoreCase("Null")) {
				GenderService genderService = new GenderService();
				if(legacyPerson.getGenderDesc().equalsIgnoreCase("Female") ) {
					user.setGender(genderService.findByKey(2l));
				}else if(legacyPerson.getGenderDesc().equalsIgnoreCase("Male") ) {
					user.setGender(genderService.findByKey(1l));
				}				
			}
			
			if(legacyPerson.getNationalityDesc() != null && legacyPerson.getNationalityDesc().equalsIgnoreCase("South Africa")){
				NationalityService nationalityService = new NationalityService();
				user.setNationality(nationalityService.findByKey(1l));
			}
			
		}else {
			throw new Exception("User does not exist");
		}
	}
}
