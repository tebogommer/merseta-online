package haj.com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import haj.com.constants.HAJConstants;
import haj.com.framework.AbstractService;


public class ArchiveDocumentsService extends AbstractService {

	
	/* Testing methods */
	public List<String> viewFilesInDirectory() {
		List<String> paths = new ArrayList<>();
		String path = HAJConstants.ARCHIVE_PATH;
		File file = new File(path);
		File[] files = file.listFiles();
		paths = checkFileReturnNames(null, files, path);
		return paths;
	}

	private static List<String> checkFileReturnNames(File parentFile, File[] files, String pathString) {
		List<String> paths = new ArrayList<>();
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					checkFileReturnNames(file, file.listFiles(), pathString);
				} else {
					 String fname = FilenameUtils.getBaseName(file.getName().toString());
		             String oriFileName = FilenameUtils.getName(file.getName().toString());
		             String path = file.getPath();
		             String absoultePath = file.getAbsolutePath();
		             paths.add(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paths;
	}
	
	
	public static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesArray;
    }
	

}