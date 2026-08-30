package haj.com.ui;

import java.io.ByteArrayInputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import haj.com.entity.DocByte;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewerUI.
 */
@ManagedBean(name = "viewerUI")
@SessionScoped
public class ViewerUI extends AbstractUI {

	/** The content. */
	private StreamedContent content;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {

	}

	/**
	 * On prerender.
	 *
	 * @param event
	 *            the event
	 */
	public void onPrerender(ComponentSystemEvent event) {
		try {
			if (getSessionUI().getSelDoc() != null) {

				DocByte docByte = DocService.instance().findDocByteByKey(getSessionUI().getSelDoc().getId());
				content = new DefaultStreamedContent(new ByteArrayInputStream(docByte.getData()), "application/pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public StreamedContent getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(StreamedContent content) {
		this.content = content;
	}

}
