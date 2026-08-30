package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.YoutubeVideoDAO;
import haj.com.entity.lookup.YoutubeVideo;
import haj.com.framework.AbstractService;

public class YoutubeVideoService extends AbstractService {
	/** The dao. */
	private YoutubeVideoDAO dao = new YoutubeVideoDAO();

	/**
	 * Get all YoutubeVideo
 	 * @author TechFinium 
 	 * @see   YoutubeVideo
 	 * @return a list of {@link haj.com.entity.YoutubeVideo}
	 * @throws Exception the exception
 	 */
	public List<YoutubeVideo> allYoutubeVideo() throws Exception {
	  	return dao.allYoutubeVideo();
	}


	/**
	 * Create or update YoutubeVideo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     YoutubeVideo
	 */
	public void create(YoutubeVideo entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  YoutubeVideo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    YoutubeVideo
	 */
	public void update(YoutubeVideo entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  YoutubeVideo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    YoutubeVideo
	 */
	public void delete(YoutubeVideo entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.YoutubeVideo}
	 * @throws Exception the exception
	 * @see    YoutubeVideo
	 */
	public YoutubeVideo findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find YoutubeVideo by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.YoutubeVideo}
	 * @throws Exception the exception
	 * @see    YoutubeVideo
	 */
	public List<YoutubeVideo> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load YoutubeVideo
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.YoutubeVideo}
	 * @throws Exception the exception
	 */
	public List<YoutubeVideo> allYoutubeVideo(int first, int pageSize) throws Exception {
		return dao.allYoutubeVideo(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of YoutubeVideo for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the YoutubeVideo
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(YoutubeVideo.class);
	}
	
    /**
     * Lazy load YoutubeVideo with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 YoutubeVideo class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.YoutubeVideo} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<YoutubeVideo> allYoutubeVideo(Class<YoutubeVideo> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<YoutubeVideo>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of YoutubeVideo for lazy load with filters
     * @author TechFinium 
     * @param entity YoutubeVideo class
     * @param filters the filters
     * @return Number of rows in the YoutubeVideo entity
     * @throws Exception the exception     
     */	
	public int count(Class<YoutubeVideo> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
