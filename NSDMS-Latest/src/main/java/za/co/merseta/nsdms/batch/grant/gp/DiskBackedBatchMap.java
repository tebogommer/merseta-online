package za.co.merseta.nsdms.batch.grant.gp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class DiskBackedBatchMap implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Map<String, Map<String, String>>> batchState = new HashMap<>();
    private static DiskBackedBatchMap INSTANCE = null;
    private static final String BATCH_STATE_FILE_NAME = "batch_state.ser";
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(DiskBackedBatchMap.class);
    private DiskBackedBatchMap() {
    }

    public static DiskBackedBatchMap getInstance() {
        logger.info("getInstance() - {}", "START");
        if (INSTANCE == null) {
            synchronized (DiskBackedBatchMap.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DiskBackedBatchMap();
                    INSTANCE.batchState = getState();
                }
            }
        }
        logger.info("getInstance() - {}", "END");
        return INSTANCE;
    }

    public synchronized void put(@SuppressWarnings("rawtypes") Class callerClass, Object batchID, Object entryID) {
        logger.info("put() - {}", "START");
        logger.trace("Parameters Received \n \t - callerClass={}, batchID={}, entryID={}",callerClass.getName(), batchID, entryID);
        String batch = String.format("%s", batchID);
        String entry = String.format("%s",entryID);
        String mainKey = callerClass.getName();
        Map<String, Map<String, String>> batchMap = INSTANCE.batchState.get(mainKey);
        if (batchMap == null) {
            batchMap = new HashMap<>();
            INSTANCE.batchState.put(mainKey, batchMap);
        }

        Map<String, String> entryMap = batchMap.get(batch);

        if (entryMap == null) {
            entryMap = new HashMap<>();
            entryMap.put(entry, entry);
            batchMap.put(batch, entryMap);
        } else {
            entryMap.put(entry, entry);
        }

        saveState();
        logger.info("put() - {}", "END");
    }

    public synchronized int remove(@SuppressWarnings("rawtypes")Class callerClass, Object batchID, Object entryID) {
        logger.info("remove() - {}", "START");
        logger.trace("Parameters Received \n \t - callerClass={}, batchID={}, entryID={}",callerClass.getName(), batchID, entryID);        
        String batch = String.format("%s", batchID);
        String entry = String.format("%s",entryID);
        String mainKey = callerClass.getName();
        int batchMapSize = 0;

        Map<String, Map<String, String>> batchMap = INSTANCE.batchState.get(mainKey);
        if (batchMap != null) {
            Map<String, String> entryMap = batchMap.get(batch);
            if (entryMap != null) {
                entryMap.remove(entry);
                batchMapSize = entryMap.size();
                if(batchMapSize == 0){
                    batchMap.remove(batch);
                    INSTANCE.batchState.remove(mainKey);
                }
            }
        }
        saveState();
        logger.debug("Response Returned- {}", batchMapSize);                
        logger.info("remove() - {}", "END");        
        return batchMapSize;
    }

    private static Map<String, Map<String, Map<String, String>>> getState() {
        logger.info("getState() - {}", "START");        
        ObjectInputStream objectinputstream = null;
        Map<String, Map<String, Map<String, String>>> batchState = null;

        try {
            System.out.println(getTempFileName());
            FileInputStream streamIn = new FileInputStream(getTempFileName());
            objectinputstream = new ObjectInputStream(streamIn);
            batchState = (Map<String, Map<String, Map<String, String>>>) objectinputstream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (Exception e) {

                }
            }
        }

        if (batchState == null) {
            batchState = new HashMap<>();
        }
        logger.info("getState() - {}", "END");
        return batchState;
    }

    private static void saveState() {
        logger.info("saveState() - {}", "START");        
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(getTempFileName(), false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(getInstance().batchState);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception e) {

                }
            }
        }
        logger.info("saveState() - {}", "END");                
    }

    private static String getTempFileName() {
        logger.info("getTempFileName() - {}", "START");                        
        String tempFolder = System.getProperty("java.io.tmpdir");
        String tempFileName = null;
        if (tempFolder == null) {
            NSDMSConfiguration.getString("nsdms.temp.folder", "/tmp");
        }
        tempFileName = String.format("%s/%s", tempFolder, BATCH_STATE_FILE_NAME);
        logger.debug("Response Returned- {}", tempFileName);                        
        logger.info("getTempFileName() - {}", "END");                                
        return tempFileName;
    }
}
