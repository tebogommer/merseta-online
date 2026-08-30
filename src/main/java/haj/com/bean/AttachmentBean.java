package haj.com.bean;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class AttachmentBean.
 */
public class AttachmentBean implements Serializable {

	/** The filename. */
	private String filename;

	/** The file. */
	private byte[] file;

	/** The ext. */
	private String ext;



	/**
	 * Instantiates a new attachment bean.
	 */
	public AttachmentBean() {
		super();
	}



	/**
	 * Instantiates a new attachment bean.
	 *
	 * @param filename the filename
	 * @param file the file
	 */
	public AttachmentBean(String filename, byte[] file) {
		super();
		this.filename = filename;
		this.file = file;
	}

	public AttachmentBean(String filename, byte[] file, String ext) {
		super();
		this.filename = filename;
		this.file = file;
		this.ext = ext;
	}



	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}



	/**
	 * Gets the ext.
	 *
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}



	/**
	 * Sets the ext.
	 *
	 * @param ext the new ext
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}



	@Override
	public String toString() {
		return "AttachmentBean [filename=" + filename + ", ext=" + ext + "]";
	}


}
