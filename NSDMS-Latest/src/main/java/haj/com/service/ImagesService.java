package haj.com.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.primefaces.model.UploadedFile;

import haj.com.constants.HAJConstants;
import haj.com.dao.ImagesDAO;
import haj.com.entity.BugReport;
import haj.com.entity.HostingCompany;
import haj.com.entity.Images;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class ImagesService.
 */
public class ImagesService extends AbstractService {

	/** The dao. */
	private static ImagesDAO dao = new ImagesDAO();
	
	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(ImagesService.class);

	/**
	 * All images.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public static List<Images> allImages() throws Exception {
		return dao.allImages();
	}

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public static void create(Images entity) throws Exception {
		dao.create(entity);
	}

	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public static void update(Images entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public static void delete(Images entity) throws Exception {
		dao.delete(entity);
	}

	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Images> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Save profile pic.
	 *
	 * @param image the image
	 * @param file the file
	 * @param user the user
	 * @param save the save
	 * @throws Exception the exception
	 */
	public static void saveProfilePic(Images image, UploadedFile file, Users user, boolean save) throws Exception {
		Images profileImage = create(image, file.getContentType(), file.getFileName(), file.getContents(), file.getInputstream(), user, save);
		UsersService usersService=new UsersService();
		// Clearing user info in case the user update his info(e.g: email)  and upload pic
		user=usersService.findByKey(user.getId());
		user.setProfileImage(profileImage);
		dao.update(user);
	}

	/**
	 * Save profile pic mobile.
	 *
	 * @param image the image
	 * @param contents the contents
	 * @param user the user
	 * @param save the save
	 * @param name the name
	 * @param contentType the content type
	 * @throws Exception the exception
	 */
	public static void saveProfilePicMobile(Images image, byte[] contents, Users user, boolean save, String name, String contentType) throws Exception {
		Images profileImage = create(image, contentType, name, contents, null, user, save);
		user.setProfileImage(profileImage);
		dao.update(user);
	}

	/**
	 * Save hosting company profile pic.
	 *
	 * @param image the image
	 * @param file the file
	 * @param hostingCompany the hosting company
	 * @param save the save
	 * @throws Exception the exception
	 */
	public static void saveHostingCompanyProfilePic(Images image, UploadedFile file, HostingCompany hostingCompany, boolean save) throws Exception {
		Images profileImage = create(image, file.getContentType(), file.getFileName(), file.getContents(), file.getInputstream(), save);
		hostingCompany.setProfileImage(profileImage);
		dao.update(hostingCompany);
	}

	/**
	 * Save hosting company profile pic mobile.
	 *
	 * @param image the image
	 * @param contents the contents
	 * @param hostingCompany the hosting company
	 * @param save the save
	 * @param name the name
	 * @param contentType the content type
	 * @throws Exception the exception
	 */
	public static void saveHostingCompanyProfilePicMobile(Images image, byte[] contents, HostingCompany hostingCompany, boolean save, String name, String contentType) throws Exception {
		Images profileImage = create(image, contentType, name, contents, null, save);
		hostingCompany.setProfileImage(profileImage);
		dao.update(hostingCompany);
	}

	/**
	 * Save pic.
	 *
	 * @param image the image
	 * @param file the file
	 * @param save the save
	 * @throws Exception the exception
	 */
	public static void savePic(Images image, UploadedFile file, boolean save) throws Exception {
		create(image, file.getContentType(), file.getFileName(), file.getContents(), file.getInputstream(), save);

	}

	/**
	 * Creates the.
	 *
	 * @param image the image
	 * @param file the file
	 * @param user the user
	 * @param save the save
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images create(Images image, UploadedFile file, Users user, boolean save) throws Exception {
		return create(image, file.getContentType(), file.getFileName(), file.getContents(), file.getInputstream(), user, save);
	}

	/**
	 * Creates the.
	 *
	 * @param image the image
	 * @param contentType the content type
	 * @param fileName the file name
	 * @param contents the contents
	 * @param inputstream the inputstream
	 * @param user the user
	 * @param save the save
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images create(Images image, String contentType, String fileName, byte[] contents, InputStream inputstream, Users user, boolean save) throws Exception {
		String ext = FilenameUtils.getExtension(fileName);
		BufferedImage bImageFromConvert = null;
		if (inputstream != null) {
			bImageFromConvert = ImageIO.read(inputstream);
		} else {
			InputStream in = new ByteArrayInputStream(contents);
			bImageFromConvert = ImageIO.read(in);
		}

		/*
		 * if (bImageFromConvert.getHeight() > 90) { bImageFromConvert =
		 * Scalr.resize(bImageFromConvert, Scalr.Method.QUALITY,
		 * Scalr.Mode.FIT_TO_HEIGHT, 90); }
		 */
		/*
		 * if (bImageFromConvert.getWidth() > HAJConstants.MAX_IMG_WIDHT) {
		 * bImageFromConvert = Scalr.resize(bImageFromConvert,
		 * Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,
		 * HAJConstants.MAX_IMG_WIDHT); }
		 */
		if (image == null)
			image = new Images();
		image.setContentType(contentType);
		image.setCreateDate(new Date());
		// dp.setDetailsOfSecurity(detailsOfSecurity);
		image.setUsers(user);
		image.setSecurityPic(GenericUtility.convertBufferedImageToByteArray(bImageFromConvert, ext));
		image.setExtension(ext);
		image.setOriginalFname(fileName);
		image.setActive(Boolean.TRUE);
		String fname = "T_" + new Date().getTime() + "." + ext;
		image.setNewFname(fname);

		if (save) {
			dao.create(image);
		}
		FileUtils.writeByteArrayToFile(new File(HAJConstants.DOC_PATH + fname), image.getSecurityPic());
		resize(image);
		return image;
	}

	/**
	 * Creates the.
	 *
	 * @param image the image
	 * @param contentType the content type
	 * @param fileName the file name
	 * @param contents the contents
	 * @param inputstream the inputstream
	 * @param save the save
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images create(Images image, String contentType, String fileName, byte[] contents, InputStream inputstream, boolean save) throws Exception {
		String ext = FilenameUtils.getExtension(fileName);
		BufferedImage bImageFromConvert = null;
		if (inputstream != null) {
			bImageFromConvert = ImageIO.read(inputstream);
		} else {
			InputStream in = new ByteArrayInputStream(contents);
			bImageFromConvert = ImageIO.read(in);
		}

		/*
		 * if (bImageFromConvert.getHeight() > 90) { bImageFromConvert =
		 * Scalr.resize(bImageFromConvert, Scalr.Method.QUALITY,
		 * Scalr.Mode.FIT_TO_HEIGHT, 90); }
		 */
		/*
		 * if (bImageFromConvert.getWidth() > HAJConstants.MAX_IMG_WIDHT) {
		 * bImageFromConvert = Scalr.resize(bImageFromConvert,
		 * Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,
		 * HAJConstants.MAX_IMG_WIDHT); }
		 */
		if (image == null)
			image = new Images();
		image.setContentType(contentType);
		image.setCreateDate(new Date());
		// dp.setDetailsOfSecurity(detailsOfSecurity);
		image.setSecurityPic(GenericUtility.convertBufferedImageToByteArray(bImageFromConvert, ext));
		image.setExtension(ext);
		image.setOriginalFname(fileName);
		image.setActive(Boolean.TRUE);
		String fname = "T_" + new Date().getTime() + "." + ext;
		image.setNewFname(fname);

		if (save) {
			dao.create(image);
		}
		FileUtils.writeByteArrayToFile(new File(HAJConstants.DOC_PATH + fname), image.getSecurityPic());
		resize(image);
		return image;
	}

	/**
	 * Creates the.
	 *
	 * @param contentType the content type
	 * @param fileName the file name
	 * @param contents the contents
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images create(String contentType, String fileName, byte[] contents) throws Exception {
		String ext = FilenameUtils.getExtension(fileName);
		String fname = "T_" + new Date().getTime() + "." + ext;

		Images image = new Images();
		image.setContentType(contentType);
		image.setCreateDate(new Date());
		image.setActive(Boolean.TRUE);
		image.setExtension(ext);
		image.setNewFname(fname);
		dao.create(image);
		// System.out.println(HAJConstants.DOC_PATH);
		FileUtils.writeByteArrayToFile(new File(HAJConstants.DOC_PATH + fname), contents);
		return image;
	}

	/*
	 * public List<Images> findByDetailsOfSecurity(DetailsOfSecurity
	 * detailsOfSecurity) throws Exception { return
	 * dao.findByDetailsOfSecurity(detailsOfSecurity.getId()); }
	 * 
	 * public List<Images> findByCompany(haj.com.entity.Company company) throws
	 * Exception { return dao.findByCompany(company.getId()); }
	 */

	/**
	 * Resize all images.
	 *
	 * @throws Exception the exception
	 */
	public static void resizeAllImages() throws Exception {
		List<Images> all = allImages();
		for (Images img : all) {
			if (img.getNewFname() != null) {
				resize(img);
			}
		}

	}

	/**
	 * Resize.
	 *
	 * @param image the image
	 * @throws Exception the exception
	 */
	public static void resize(Images image) throws Exception {

		File file = new File(HAJConstants.DOC_PATH + image.getNewFname());

		if (file.exists()) {

			BufferedImage bImageFromConvert = ImageIO.read(file);

			String ext = image.getNewFname().substring(image.getNewFname().indexOf(".") + 1);
			String fname = image.getNewFname().substring(0, image.getNewFname().indexOf(".")) + "_small." + ext;

			/*
			 * if (bImageFromConvert.getHeight() > 90) { bImageFromConvert =
			 * Scalr.resize(bImageFromConvert, Scalr.Method.QUALITY,
			 * Scalr.Mode.FIT_TO_HEIGHT, 90); }
			 */
			if (bImageFromConvert.getWidth() > HAJConstants.MAX_IMG_WIDHT) {
				bImageFromConvert = Scalr.resize(bImageFromConvert, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, HAJConstants.MAX_IMG_WIDHT);
			}

			FileUtils.writeByteArrayToFile(new File(HAJConstants.DOC_PATH + fname), GenericUtility.convertBufferedImageToByteArray(bImageFromConvert, ext));
		}
	}

	
	/**
	 * Find by bug report.
	 *
	 * @param bugReport the bug report
	 * @return the images
	 * @throws Exception the exception
	 */
	public static Images findByBugReport(BugReport bugReport) throws Exception {
		List<Images> images = dao.findImagesByBugReport(bugReport.getId());
		if(images.size() > 0)
			return images.get(0);
		else
			return null;
	}
	
	/**
	 * Save bug report pic.
	 *
	 * @param image the image
	 * @param file the file
	 * @param bugReport the bug report
	 * @param save the save
	 * @throws Exception the exception
	 */
	public static void saveBugReportPic(Images image, UploadedFile file, BugReport bugReport, boolean save) throws Exception { 
		Images images = create(image, file.getContentType(), file.getFileName(), file.getContents(), file.getInputstream(),  save);
		bugReport.setBugImage(images);
	}
	
}
