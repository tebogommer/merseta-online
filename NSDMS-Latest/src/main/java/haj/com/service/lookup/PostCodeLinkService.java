package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.lookup.PostCodeLinkDAO;
import haj.com.entity.Province;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.PostCodeLink;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.ProvinceService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class PostCodeLinkService extends AbstractService {
	
	/** The dao. */
	private PostCodeLinkDAO dao = new PostCodeLinkDAO();
	
	/* The Service Level */
	private ProvinceService provinceService = new ProvinceService();

	/**
	 * Get all PostCodeLink
 	 * @author TechFinium 
 	 * @see   PostCodeLink
 	 * @return a list of {@link haj.com.entity.PostCodeLink}
	 * @throws Exception the exception
 	 */
	public List<PostCodeLink> allPostCodeLink() throws Exception {
	  	return dao.allPostCodeLink();
	}


	/**
	 * Create or update PostCodeLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PostCodeLink
	 */
	public void create(PostCodeLink entity) throws Exception  {
		setNumberValueForPostCode(entity);
		if (entity.getId() ==null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * @param entity
	 * @throws NumberFormatException
	 */
	public void setNumberValueForPostCode(PostCodeLink entity) throws NumberFormatException {
		try {
			if (entity.getPostCodeUsedForSarsNumberValue() == null && entity.getPostCodeUsedForSars() != null && !entity.getPostCodeUsedForSars().trim().isEmpty()) {
				entity.setPostCodeUsedForSarsNumberValue(Integer.valueOf(entity.getPostCodeUsedForSars().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}


	/**
	 * Update  PostCodeLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PostCodeLink
	 */
	public void update(PostCodeLink entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PostCodeLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PostCodeLink
	 */
	public void delete(PostCodeLink entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PostCodeLink}
	 * @throws Exception the exception
	 * @see    PostCodeLink
	 */
	public PostCodeLink findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PostCodeLink by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PostCodeLink}
	 * @throws Exception the exception
	 * @see    PostCodeLink
	 */
	public List<PostCodeLink> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PostCodeLink
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PostCodeLink}
	 * @throws Exception the exception
	 */
	public List<PostCodeLink> allPostCodeLink(int first, int pageSize) throws Exception {
		return dao.allPostCodeLink(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PostCodeLink for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the PostCodeLink
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PostCodeLink.class);
	}
	
    /**
     * Lazy load PostCodeLink with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 PostCodeLink class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PostCodeLink} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PostCodeLink> allPostCodeLink(Class<PostCodeLink> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PostCodeLink>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PostCodeLink for lazy load with filters
     * @author TechFinium 
     * @param entity PostCodeLink class
     * @param filters the filters
     * @return Number of rows in the PostCodeLink entity
     * @throws Exception the exception     
     */	
	public int count(Class<PostCodeLink> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<PostCodeLink> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<PostCodeLink> allEntries = allPostCodeLink();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public void runValidiationsThread() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runValidiations();
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public void runValidiations() throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		List<PostCodeLink> link = allPostCodeLink();
		for (PostCodeLink entity : link) {
			if (entity.getPostCode() != null && !entity.getPostCode().trim().isEmpty()) {
				entity.setPostCodeUsedForSars(entity.getPostCode().trim());
			}
			if (entity.getProvinceCode() != null && !entity.getProvinceCode().trim().isEmpty()) {
				Province p = provinceService.findByCode(entity.getProvinceCode().trim());
				if (p != null && p.getId() != null) {
					entity.setProvince(p);
				}
			}
			setNumberValueForPostCode(entity);
			updateList.add(entity);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
	
	public void runValidiationForEntry(PostCodeLink entity) throws Exception {
		if (entity.getPostCode() != null && !entity.getPostCode().trim().isEmpty()) {
			entity.setPostCodeUsedForSars(entity.getPostCode().trim());
		}
		setNumberValueForPostCode(entity);
		if (entity.getProvinceCode() != null && !entity.getProvinceCode().trim().isEmpty()) {
			Province p = provinceService.findByCode(entity.getProvinceCode().trim());
			if (p != null && p.getId() != null) {
				entity.setProvince(p);
			}
		}
		update(entity);
	}
	
	public int countWhereNumberNotAssigned() throws Exception {
		return dao.countWhereNumberNotAssigned();
	}
	
	public int countByPostCodeLinkByNumberAssigned(int number) throws Exception {
		return dao.countByPostCodeLinkByNumberAssigned(number);
	}
	
	public List<PostCodeLink> findPostCodeLinkByNumberAssigned(int number) throws Exception {
		return dao.findPostCodeLinkByNumberAssigned(number);
	}
	
	public void populateMissingPostCodeInformation(int minNumber, int maxNumber) throws Exception {
		
		if (countWhereNumberNotAssigned() < 0) {
			throw new Exception("Please run the data process before populating missing post codes.");
		}
		
		List<Integer> createList = new ArrayList<>();
		for (int i = minNumber; i <= maxNumber; i++) {
			if (countByPostCodeLinkByNumberAssigned(i) == 0) {
				createList.add(i);
			}
		}
		
		if (!createList.isEmpty()) {
			List<IDataEntity> newEntries = new ArrayList<>();
			for (Integer newNumber : createList) {
				
				System.out.println(newNumber);
				PostCodeLink newEntry = new PostCodeLink();
				newEntry.setPostCodeUsedForSarsNumberValue(newNumber);
				String sarsPostCodeNumber = calculatePostCode(String.valueOf(newNumber));
				
				newEntry.setArea("UNKNOWN");
				newEntry.setProvinceCode("N");
				newEntry.setPostCode(sarsPostCodeNumber);
				newEntry.setPostCodeUsedForSars(sarsPostCodeNumber);
				
				try {
					Province p = provinceService.findByCode(newEntry.getProvinceCode().trim());
					if (p != null && p.getId() != null) {
						newEntry.setProvince(p);
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				newEntries.add(newEntry);
			}
			
			
			if (!newEntries.isEmpty()) {
				dao.createBatch(newEntries, 1000);
			}
			
		}
		
	}
	
	private String calculatePostCode(String postCodeData) throws Exception {
		if (postCodeData != null && !postCodeData.trim().isEmpty() && postCodeData.contains("/")) {
			postCodeData = postCodeData.replace("/", "");
		}
		if (postCodeData != null && !postCodeData.trim().isEmpty() && postCodeData.trim().length() > 0 && postCodeData.trim().length() < 4) {
			StringBuilder builder = new StringBuilder();
			int missing = 4 - postCodeData.trim().length();
			for (int i = 1; i <= missing; i++) {
				builder.append("0");
			}
			builder.append(postCodeData.trim());
			return builder.toString();
		} else {
			return postCodeData;
		}
	}
	
}
