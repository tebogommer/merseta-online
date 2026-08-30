package haj.com.ui;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.Images;
import haj.com.service.DocService;
import haj.com.service.ImagesService;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageStreamer.
 */
@ManagedBean
@ApplicationScoped
public class ImageStreamer implements Serializable {

	/**
	 * Gets the image.
	 *
	 * @return the image
	 * @throws Exception
	 *             the exception
	 */
	public StreamedContent getImage() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
			try {
				Images image = ImagesService.findByKey(Long.valueOf(imageId));
				return new DefaultStreamedContent(new ByteArrayInputStream(image.getSecurityPic()), image.getContentType().trim(), image.getOriginalFname().trim());
			} catch (Exception e) {
				return new DefaultStreamedContent();
			}
		}
	}

	/**
	 * Gets the doc image.
	 *
	 * @return the doc image
	 * @throws Exception
	 *             the exception
	 */
	public StreamedContent getDocImage() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String docid = context.getExternalContext().getRequestParameterMap().get("docid");
			try {
				Doc doc = DocService.instance().findByKey(Long.valueOf(docid));
				DocByte docByte = DocService.instance().findDocByteByKey(Long.valueOf(docid));
				return new DefaultStreamedContent(new ByteArrayInputStream(docByte.getData()), "image/" + doc.getExtension().trim(), doc.getOriginalFname().trim());
			} catch (Exception e) {
				return new DefaultStreamedContent();
			}
		}
	}

}