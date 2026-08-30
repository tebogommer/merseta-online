package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.YoutubeVideo;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.YoutubeVideoService;

@ManagedBean(name = "youtubevideoUI")
@ViewScoped
public class YoutubeVideoUI extends AbstractUI {

	private YoutubeVideoService service = new YoutubeVideoService();
	private List<YoutubeVideo> youtubevideoList = null;
	private List<YoutubeVideo> youtubevideofilteredList = null;
	private YoutubeVideo youtubevideo = null;
	private LazyDataModel<YoutubeVideo> dataModel;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all YoutubeVideo and prepare a for a create of a new
	 * YoutubeVideo
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		youtubevideoInfo();
	}

	/**
	 * Get all YoutubeVideo for data table
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 */
	public void youtubevideoInfo() {
		try {
			youtubevideoList = service.allYoutubeVideo();
			dataModel = new LazyDataModel<YoutubeVideo>() {

				private static final long serialVersionUID = 1L;
				private List<YoutubeVideo> retorno = new ArrayList<YoutubeVideo>();

				@Override
				public List<YoutubeVideo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

					try {
						retorno = service.allYoutubeVideo(YoutubeVideo.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(YoutubeVideo.class, filters));
					} catch (Exception e) {
						logger.fatal(e);
					}
					return retorno;
				}

				@Override
				public Object getRowKey(YoutubeVideo obj) {
					return obj.getId();
				}

				@Override
				public YoutubeVideo getRowData(String rowKey) {
					for (YoutubeVideo obj : retorno) {
						if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
					}
					return null;
				}

			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert YoutubeVideo into database
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 */
	public void youtubevideoInsert() {
		try {
			service.create(this.youtubevideo);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			youtubevideoInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update YoutubeVideo in database
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 */
	public void youtubevideoUpdate() {
		try {
			service.update(this.youtubevideo);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			youtubevideoInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete YoutubeVideo from database
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 */
	public void youtubevideoDelete() {
		try {
			service.delete(this.youtubevideo);
			prepareNew();
			youtubevideoInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of YoutubeVideo
	 * 
	 * @author TechFinium
	 * @see YoutubeVideo
	 */
	public void prepareNew() {
		youtubevideo = new YoutubeVideo();
	}

	/*
	 * public List<SelectItem> getYoutubeVideoDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * youtubevideoInfo(); for (YoutubeVideo ug : youtubevideoList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<YoutubeVideo> complete(String desc) {
		List<YoutubeVideo> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<YoutubeVideo> getYoutubeVideoList() {
		return youtubevideoList;
	}

	public void setYoutubeVideoList(List<YoutubeVideo> youtubevideoList) {
		this.youtubevideoList = youtubevideoList;
	}

	public YoutubeVideo getYoutubevideo() {
		return youtubevideo;
	}

	public void setYoutubevideo(YoutubeVideo youtubevideo) {
		this.youtubevideo = youtubevideo;
	}

	public List<YoutubeVideo> getYoutubeVideofilteredList() {
		return youtubevideofilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param youtubevideofilteredList
	 *            the new youtubevideofilteredList list
	 * @see YoutubeVideo
	 */
	public void setYoutubeVideofilteredList(List<YoutubeVideo> youtubevideofilteredList) {
		this.youtubevideofilteredList = youtubevideofilteredList;
	}

	public LazyDataModel<YoutubeVideo> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<YoutubeVideo> dataModel) {
		this.dataModel = dataModel;
	}

	public List<YoutubeVideo> getYoutubevideoList() {
		return youtubevideoList;
	}

	public void setYoutubevideoList(List<YoutubeVideo> youtubevideoList) {
		this.youtubevideoList = youtubevideoList;
	}

}
