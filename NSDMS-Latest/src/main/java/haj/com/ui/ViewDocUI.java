package haj.com.ui;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.DocumentTrackerService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewDocUI.
 */
@ManagedBean(name = "viewDocUI")
@ViewScoped
public class ViewDocUI extends AbstractUI {

	/** The doc service. */
	private DocService docService = new DocService();

	/** The id. */
	private Long id;

	/** The valid link. */
	private boolean validLink;

	/** The doc. */
	private Doc doc;

	/** The file. */
	private StreamedContent file;

	/** The document trackers. */
	private List<DocumentTracker> documentTrackers;

	/** The document tracker service. */
	private DocumentTrackerService documentTrackerService = new DocumentTrackerService();

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
		validLink = false;
		if (super.getParameter("imageref", false) != null) {
			this.id = Long.valueOf((String) super.getParameter("imageref", false));
			this.validLink = true;
			doc = docService.findByKey(id);
			if (doc == null) {
				this.validLink = false;
			} else {
				this.validLink = true;
				showHistory();
				getSessionUI().setSelDoc(doc);
			}
		}
	}

	/**
	 * Checks if is valid link.
	 *
	 * @return true, if is valid link
	 */
	public boolean isValidLink() {
		return validLink;
	}

	/**
	 * Sets the valid link.
	 *
	 * @param validLink
	 *            the new valid link
	 */
	public void setValidLink(boolean validLink) {
		this.validLink = validLink;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc
	 *            the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Download.
	 */
	public void download() {
		try {
			DocByte docByte = docService.findDocByteByKey(doc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(doc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public StreamedContent getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(StreamedContent file) {
		this.file = file;
	}

	/**
	 * On prerender.
	 *
	 * @param event
	 *            the event
	 */
	public void onPrerender(ComponentSystemEvent event) {
		try {
			if ("PDF".equals(doc.getExtension().trim().toUpperCase())) {
				DocByte docByte = docService.findDocByteByKey(doc.getId());
				file = new DefaultStreamedContent(new ByteArrayInputStream(docByte.getData()), "application/pdf");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show history.
	 */
	public void showHistory() {
		try {
			this.documentTrackers = documentTrackerService.byRoot(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the document trackers.
	 *
	 * @return the document trackers
	 */
	public List<DocumentTracker> getDocumentTrackers() {
		return documentTrackers;
	}

	/**
	 * Sets the document trackers.
	 *
	 * @param documentTrackers
	 *            the new document trackers
	 */
	public void setDocumentTrackers(List<DocumentTracker> documentTrackers) {
		this.documentTrackers = documentTrackers;
	}
}
