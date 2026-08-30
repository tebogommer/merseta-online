package haj.com.ui;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.Users;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.DocumentTrackerService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class UploadDocUI.
 */
@ManagedBean(name = "uploadDocUI")
@SessionScoped
// @ViewScoped
public class UploadDocUI extends AbstractUI {

	/** The doc. */
	private Doc doc;

	/** The docs. */
	private List<Doc> docs;

	/** The docsfiltered. */
	private List<Doc> docsfiltered;

	/** The doc service. */
	private DocService docService = new DocService();

	/** The document trackers. */
	private List<DocumentTracker> documentTrackers;
	
	
	private DocumentTracker documentTracker;

	/** The document tracker service. */
	private DocumentTrackerService documentTrackerService = new DocumentTrackerService();

	/** The content. */
	private StreamedContent content;

	/** The docVersions. */
	private List<Doc> docVersions;

	private Users viewUser;

	/** The active user. */
	private Users activeUser;

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void runInit() throws Exception {
		initDoc();
	}

	/**
	 * Gets the docs.
	 *
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * Sets the docs.
	 *
	 * @param docs
	 *            the new docs
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	/**
	 * Gets the docsfiltered.
	 *
	 * @return the docsfiltered
	 */
	public List<Doc> getDocsfiltered() {
		return docsfiltered;
	}

	/**
	 * Sets the docsfiltered.
	 *
	 * @param docsfiltered
	 *            the new docsfiltered
	 */
	public void setDocsfiltered(List<Doc> docsfiltered) {
		this.docsfiltered = docsfiltered;
	}

	/**
	 * On prerender.
	 *
	 * @param event
	 *            the event
	 */
	public void onPrerender(ComponentSystemEvent event) {
		
		try {
			if (doc != null && doc.getId() != null) {
				System.out.println("onPrerender----"+doc.getId());
				DocByte docByte = docService.findDocByteByKey(doc.getId());
				if (doc != null && docByte != null && docByte.getData() != null) {
					content = new DefaultStreamedContent(new ByteArrayInputStream(docByte.getData()), "application/pdf");
				}
			} else if (doc != null && doc.getData() != null) {
				System.out.println("onPrerender----else");
				content = new DefaultStreamedContent(new ByteArrayInputStream(doc.getData()), "application/pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPrerender_vh(ComponentSystemEvent event) {
		doc =documentTracker.getDoc();
		try {
			if (doc != null && doc.getId() != null) {
				DocByte docByte = docService.findDocByteByKey(doc.getId());
				if (doc != null && docByte != null && docByte.getData() != null) {
					content = new DefaultStreamedContent(new ByteArrayInputStream(docByte.getData()), "application/pdf");
				}
			} else if (doc != null && doc.getData() != null) {
				content = new DefaultStreamedContent(new ByteArrayInputStream(doc.getData()), "application/pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Builds the stream content.
	 */
	public void buildStreamContent() {
		if (doc == null) {
			addInfoMessage("Document archived, please locate document on TPIMS folder named 'Archived Learner Documents'");
		}
		else {
			getSessionUI().setSelDoc(doc);
		}
		//commented by vh for jira issue 114 to stop creating versions of document while download and view 
//		try {
//			if (getSessionUI() != null && getSessionUI().getActiveUser() != null) {
//				DocumentTrackerService.create(getSessionUI().getActiveUser(), doc, DocumentTrackerEnum.Viewed);
//			} else if (viewUser != null) {
//					DocumentTrackerService.create(getSessionUI().getActiveUser(), doc, DocumentTrackerEnum.Viewed);
//			}
//			else if(activeUser !=null)
//			{
//				DocumentTrackerService.create(activeUser, doc, DocumentTrackerEnum.Viewed);
//			}
//		} catch (Exception e) {
//			logger.fatal(e);
//		}
	}
	

	/**
	 * Download.
	 */
	public void download() {
		try {
			Users user = getSessionUI().getActiveUser();
			if (user == null) {
				if (activeUser != null) {
					user = activeUser;
				}
			}
			//commented by vh for jira issue 114 to stop creating versions of document while download and view 
//			DocumentTrackerService.create(user, doc, DocumentTrackerEnum.Downloaded);
			DocByte docByte = docService.findDocByteByKey(doc.getId());
			if (docByte == null) {
				addInfoMessage("Document archived, please locate document on TPIMS folder named 'Archived Learner Documents'");
			}
			else {
				Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(doc), true);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Download.
	 */
	public void download_vh() {
		try {
			Users user = getSessionUI().getActiveUser();
			if (user == null) {
				if (activeUser != null) {
					user = activeUser;
				}
			}
//			System.out.println("doc.getId()---"+doc.getId());
//			System.out.println("doc.getOriginalFname()---"+doc.getOriginalFname());
//			System.out.println("doc.getVersionNo()---"+doc.getVersionNo());
//			System.out.println("doc.getExtension()---"+doc.getExtension());
			
			//commented by vh for jira issue 114 to stop creating versions of document while download and view 
//			DocumentTrackerService.create(user, doc, DocumentTrackerEnum.Downloaded);
			DocByte docByte = docService.findDocByteByKey(doc.getId());
			System.out.println("GenericUtility.buidFileName(doc)---"+GenericUtility.buidFileName(doc));
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(doc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download.
	 */
	public void download(Users user) {
		try {
			if (user == null) {
				if (activeUser != null) {
					user = activeUser;
				}
			}
			DocumentTrackerService.create(user, doc, DocumentTrackerEnum.Downloaded);
			DocByte docByte = docService.findDocByteByKey(doc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(doc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void downloadDocStore(String fileName, String downloadName) {
		try {
			Path path = Paths.get(HAJConstants.DOC_PATH + fileName);
			byte[] data = Files.readAllBytes(path);
			Faces.sendFile(data, downloadName, true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Safe file.
	 *
	 * @param event
	 *            the event
	 * @param doc
	 *            the doc
	 */
	public void safeFile(FileUploadEvent event, Doc doc) {
		try {
			docService.save(doc, event.getFile().getContents(), event.getFile().getFileName(), getSessionUI().getActiveUser(), this.docs);
			addInfoMessage(super.getEntryLanguage("your.file.has.been.uploaded.successfully"));
			// initDoc();
			findDoc(doc);
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Safe file.
	 *
	 * @param event
	 *            the event
	 * @param doc
	 *            the doc
	 * @param user
	 *            the user
	 */
	public void safeFile(FileUploadEvent event, Doc doc, Users user) {
		try {

			docService.save(doc, event.getFile().getContents(), event.getFile().getFileName(), user, this.docs);
			addInfoMessage(super.getEntryLanguage("your.file.has.been.uploaded.successfully"));
			// initDoc();
			findDoc(doc);

		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Safe file.
	 *
	 * @param event
	 *            the event
	 */
	public void safeFile(FileUploadEvent event) {
		try {

			docService.save(doc, event.getFile().getContents(), event.getFile().getFileName(), getSessionUI().getActiveUser(), this.docs);
			addInfoMessage(super.getEntryLanguage("your.file.has.been.uploaded.successfully"));
			// initDoc();
			findDoc(doc);

		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Find doc.
	 *
	 * @param doc
	 *            the doc
	 */
	public void findDoc(Doc doc) {
		try {
			this.docs = docService.search(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Inits the doc.
	 */
	public void initDoc() {
		this.doc = new Doc();
		// this.doc.setHostingCompany(getSessionUI().getHostingCompany());
		this.doc.setUsers(getSessionUI().getUser());

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
	 * Show history vh. 
	 */
	public void showHistory_vh() {
		// method added for jira 114
//		System.out.println("@@@@@----"+doc.getId());
		Long origninalDocId;
		if(this.docs!=null && this.docs.size()>0) {
			this.docs.clear();
		}
		
		try {
			Doc latestDoc=docService.findByKey(doc.getId());
			if(latestDoc.getDoc()!=null) {
				System.out.println("!!!!!----"+latestDoc.getDoc().getId());
				origninalDocId=latestDoc.getDoc().getId();
				List<Doc> docList =docService.byOriginalDoc(origninalDocId);
				Doc originalDoc=docService.findDocByKey(origninalDocId);
				docList.add(originalDoc);
				System.out.println("docList----"+docList.size());
				this.docs=docList;
			}
//			this.documentTrackers = documentTrackerService.byRoot(doc);
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
		System.out.println("in setDoc---");
		docService.checkDocData(doc);
		this.doc = doc;
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

	public List<Doc> getDocVersions() {
		return docVersions;
	}

	public void setDocVersions(List<Doc> docVersions) {
		this.docVersions = docVersions;
	}

	public void showVersions() {
		try {
			this.docVersions = docService.versions(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Users getViewUser() {
		return viewUser;
	}

	public void setViewUser(Users viewUser) {
		this.viewUser = viewUser;
	}

	public Users getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Users activeUser) {
		this.activeUser = activeUser;
	}
	
	public void storeFile(FileUploadEvent event) {
		// method added for jira 114
		
		System.out.println("in storeFile");
		System.out.println("doc.getId()----"+doc.getId());
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
}
