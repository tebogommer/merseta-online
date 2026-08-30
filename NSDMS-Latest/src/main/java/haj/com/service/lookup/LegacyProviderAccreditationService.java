package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyProviderAccreditationDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyProviderAccreditationService extends AbstractService {

	/** The dao. */
	private LegacyProviderAccreditationDAO dao = new LegacyProviderAccreditationDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService;
	
	/* Additional Service / DAO Levels */
	private ResolveByCodeLookupDAO resolveByCodeLookupDAO = new ResolveByCodeLookupDAO();

	/**
	 * Get all LegacyProviderAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditation() throws Exception {
		return dao.allLegacyProviderAccreditation();
	}

	/**
	 * Create or update LegacyProviderAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderAccreditation
	 */
	public void create(LegacyProviderAccreditation entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyProviderAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderAccreditation
	 */
	public void update(LegacyProviderAccreditation entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyProviderAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderAccreditation
	 */
	public void delete(LegacyProviderAccreditation entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyProviderAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderAccreditation
	 */
	public LegacyProviderAccreditation findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderAccreditation by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderAccreditation
	 */
	public List<LegacyProviderAccreditation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public List<LegacyProviderAccreditation> searchProvider(String accreditationNo) throws Exception {
		return dao.searchProvider(accreditationNo);
	}

	public List<LegacyProviderAccreditation> searchProviderVersionTwo(String accreditationNo) throws Exception {
		return dao.searchProviderVersionTwo(accreditationNo);
	}

	/**
	 * Lazy load LegacyProviderAccreditation
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditation(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderAccreditation(Long.valueOf(first), pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditationBySdlNumber(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, String sdlNumber) throws Exception {
		String hql = "select o from LegacyProviderAccreditation o where o.sdlNumber = :sdlNumber ";
		filters.put("sdlNumber", sdlNumber);
		return (List<LegacyProviderAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public int countAllLegacyProviderAccreditationBySdlNumber(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyProviderAccreditation o where o.sdlNumber = :sdlNumber ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditationlegacyOrganisationSitesLinkedSdlOrMainSdl(
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,
			String sdlNumber) throws Exception {
		String hql = "select o from LegacyProviderAccreditation o where (o.legacyOrganisationSitesLinkedSdl = :sdlNumber or o.legacyOrganisationSitesMainSdl = :sdlNumber ) ";
		filters.put("sdlNumber", sdlNumber.trim());
		return (List<LegacyProviderAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder,
				filters, hql);
	}

	public int countAllLegacyProviderAccreditationlegacyOrganisationSitesLinkedSdlOrMainSdl(Map<String, Object> filters)
			throws Exception {
		String hql = "select count(o) from LegacyProviderAccreditation o where (o.legacyOrganisationSitesLinkedSdl = :sdlNumber or o.legacyOrganisationSitesMainSdl = :sdlNumber ) ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of LegacyProviderAccreditation for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyProviderAccreditation
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyProviderAccreditation.class);
	}

	/**
	 * Lazy load LegacyProviderAccreditation with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyProviderAccreditation class
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
	 * @return a list of {@link haj.com.entity.LegacyProviderAccreditation}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderAccreditation> allLegacyProviderAccreditation(Class<LegacyProviderAccreditation> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyProviderAccreditation>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyProviderAccreditation for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyProviderAccreditation class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyProviderAccreditation entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyProviderAccreditation> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyProviderAccreditation> allEntries = allLegacyProviderAccreditation();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderAccreditation> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public int countEntriesByAccNumber(String accreditationNo) throws Exception {
		return dao.countEntriesByAccNumber(accreditationNo);
	}

	public List<LegacyProviderAccreditation> findBySdlNumber(String sdlNumber) throws Exception {
		return dao.findBySdlNumber(sdlNumber);
	}

	public void correctStatusValidiation() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (legacyOrganisationSitesService == null) {
						legacyOrganisationSitesService = new LegacyOrganisationSitesService();
					}
					List<IDataEntity> updateList = new ArrayList<IDataEntity>();
					List<LegacyProviderAccreditation> allLegacyProviderAccreditation = allLegacyProviderAccreditation();
					for (LegacyProviderAccreditation entry : allLegacyProviderAccreditation) {

						/*
						 * Check for duplicate acc number
						 */
						try {
							if (countEntriesByAccNumber(entry.getAccreditationNo()) == 1) {
								entry.setDuplicateAccNumber(false);
							} else {
								entry.setDuplicateAccNumber(true);
							}
						} catch (Exception e) {
							entry.setDuplicateAccNumber(true);
						}

						/*
						 * validStatus calc valid status -- Accredited - Full --
						 * Accredited - Provisional -- Expired
						 */
						try {
							try {
								// Determine valid status
								if ((entry.getProviderStatus() != null && !entry.getProviderStatus().isEmpty())
										&& (entry.getAccreditationEndDate() != null
												&& !entry.getAccreditationEndDate().isEmpty()
												&& !entry.getAccreditationEndDate().trim().toUpperCase()
														.equals("NULL"))) {
									Date modRegEndDate = GenericUtility.getStartOfDay(
											HAJConstants.sdfYYYYMMDD.parse(entry.getAccreditationEndDate().trim()));
									switch (entry.getProviderStatus().trim().toUpperCase()) {
									case "ACCREDITED - FULL":
										// date is after today
										if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
											entry.setValidStatus(true);
										} else {
											entry.setValidStatus(false);
										}
										break;
									case "ACCREDITED - PROVISIONAL":
										// date is after today
										if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
											entry.setValidStatus(true);
										} else {
											entry.setValidStatus(false);
										}
										break;
									case "PROGRAMME APPROVAL":
										// date is after today
										if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
											entry.setValidStatus(true);
										} else {
											entry.setValidStatus(false);
										}
										break;
									case "EXPIRED":
										// date is before today
										if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
											entry.setValidStatus(true);
										} else {
											entry.setValidStatus(false);
										}
										break;
									default:
										break;
									}
								} else {
									entry.setValidStatus(false);
								}
							} catch (Exception e) {
								entry.setValidStatus(false);
							}
						} catch (Exception e) {
						}

						try {
							if (entry.getSdlNumber() != null && !entry.getSdlNumber().isEmpty()) {
								List<LegacyOrganisationSites> list = legacyOrganisationSitesService
										.findBySdlNumberList(entry.getSdlNumber().trim());
								if (list != null && list.size() != 0) {

									LegacyOrganisationSites legacyOrganisationSites = list.get(0);

									if (legacyOrganisationSites.getMainSDL() != null
											&& !legacyOrganisationSites.getMainSDL().isEmpty()) {
										entry.setLegacyOrganisationSitesMainSdl(
												legacyOrganisationSites.getMainSDL().trim());
									}

									if (legacyOrganisationSites.getLinkedSdl() != null
											&& !legacyOrganisationSites.getLinkedSdl().isEmpty()) {
										entry.setLegacyOrganisationSitesLinkedSdl(
												legacyOrganisationSites.getLinkedSdl().trim());
									}

									legacyOrganisationSites = null;
								}
								list = null;
							}
						} catch (Exception e) {
						}

						updateList.add(entry);

					}

					allLegacyProviderAccreditation = null;

					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
					}

					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Legacy Provider Accreditation Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Error Legacy Provider Accreditation Process", "Processing Complete with Errors on site: " + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
					}
				}
			}

		}).start();
	}

	public LegacyProviderAccreditation runValidiationForEntry(LegacyProviderAccreditation entry) throws Exception {
		/*
		 * Check for duplicate acc number
		 */
		try {
			if (countEntriesByAccNumber(entry.getAccreditationNo()) == 1) {
				entry.setDuplicateAccNumber(false);
			} else {
				entry.setDuplicateAccNumber(true);
			}
		} catch (Exception e) {
			entry.setDuplicateAccNumber(true);
		}

		/*
		 * validStatus calc valid status -- Accredited - Full -- Accredited -
		 * Provisional -- Expired
		 */
		try {
			try {
				// Determine valid status
				if ((entry.getProviderStatus() != null && !entry.getProviderStatus().isEmpty()) && (entry.getAccreditationEndDate() != null && !entry.getAccreditationEndDate().isEmpty() && !entry.getAccreditationEndDate().trim().toUpperCase().equals("NULL"))) {
					Date modRegEndDate = GenericUtility.getStartOfDay(HAJConstants.sdfYYYYMMDD.parse(entry.getAccreditationEndDate().trim()));
					switch (entry.getProviderStatus().trim().toUpperCase()) {
					case "ACCREDITED - FULL":
						// date is after today
						if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
							entry.setValidStatus(true);
						} else {
							entry.setValidStatus(false);
						}
						break;
					case "ACCREDITED - PROVISIONAL":
						// date is after today
						if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
							entry.setValidStatus(true);
						} else {
							entry.setValidStatus(false);
						}
						break;
					case "PROGRAMME APPROVAL":
						// date is after today
						if (modRegEndDate != null && modRegEndDate.after(getSynchronizedDate())) {
							entry.setValidStatus(true);
						} else {
							entry.setValidStatus(false);
						}
						break;
					case "EXPIRED":
						// date is before today
						if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
							entry.setValidStatus(true);
						} else {
							entry.setValidStatus(false);
						}
						break;
					default:
						break;
					}
				} else {
					entry.setValidStatus(false);
				}
			} catch (Exception e) {
				entry.setValidStatus(false);
			}
		} catch (Exception e) {
		}

		try {
			if (entry.getSdlNumber() != null && !entry.getSdlNumber().isEmpty()) {
				List<LegacyOrganisationSites> list = legacyOrganisationSitesService.findBySdlNumberList(entry.getSdlNumber().trim());
				if (list != null && list.size() != 0) {
					LegacyOrganisationSites legacyOrganisationSites = list.get(0);
					if (legacyOrganisationSites.getMainSDL() != null && !legacyOrganisationSites.getMainSDL().isEmpty()) {
						entry.setLegacyOrganisationSitesMainSdl(legacyOrganisationSites.getMainSDL().trim());
					}

					if (legacyOrganisationSites.getLinkedSdl() != null && !legacyOrganisationSites.getLinkedSdl().isEmpty()) {
						entry.setLegacyOrganisationSitesLinkedSdl(legacyOrganisationSites.getLinkedSdl().trim());
					}

					legacyOrganisationSites = null;
				}
				list = null;
			}
		} catch (Exception e) {
		}
		
		try {
			entry.setLegacyOrganisationSites(resolveByCodeLookupDAO.findLegacyOrganisationSites(entry.getSdlNumber()));
		} catch (Exception e) {
			entry.setLegacyOrganisationSites(false);
		}
		
		try {
			entry.setLegacyOrganisationNonLevyPaying(resolveByCodeLookupDAO.findLegacyOrganisationNonLevyPaying(entry.getLinkedSdl()));
		} catch (Exception e) {
			entry.setLegacyOrganisationNonLevyPaying(false);
		}
		
		try {
			entry.setCheckCountByRefNumber(resolveByCodeLookupDAO.checkCountByRefNumber(entry.getLinkedSdl()));
		} catch (Exception e) {
			entry.setCheckCountByRefNumber(false);
		}

		return entry;
	}

}
