package com.vsii.utils;

import com.vsii.constants.FileNetConstants;
import com.vsii.model.ErrorDetails;
import com.vsii.model.ErrorModel;
import filenet.vw.api.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchedulerListenerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerListenerUtils.class);

    public static ErrorModel buildErrorModel(String errorCode, ErrorDetails error) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setErrorCode(errorCode);
        List<ErrorDetails> lstErr = new ArrayList<>();
        lstErr.add(error);
        errorModel.setErrorDetails(lstErr);
        return errorModel;
    }

    public static ErrorModel buildErrorModel(String errorCode, Exception e) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setErrorCode(errorCode);
        List<ErrorDetails> lstErr = new ArrayList<>();
        ErrorDetails error = new ErrorDetails();
        error.setErrorRootCause(ExceptionUtils.getRootCauseMessage(e));
        error.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
        lstErr.add(error);
        errorModel.setErrorDetails(lstErr);
        return errorModel;
    }

    public static void createFolderFileError(List<File> fileError, String pathError) {
        for (File file : fileError) {
            String pathCopyFile = pathError + File.separatorChar + file.getName();
            File copyFile = new File(pathCopyFile);
            try {
                FileUtils.copyFile(file, copyFile);
            } catch (IOException e) {
                LOGGER.error("Copy File from " + file.getAbsolutePath() + " to " + copyFile.getAbsolutePath()
                        + "Error: " + ExceptionUtils.getStackTrace(e));
            }
        }
    }

    // Check D2C case use Application No
    // true if d2ccase, other false
    public static boolean isD2CCase(String appNo) {
        return (appNo.startsWith("D") || appNo.startsWith("d"));
    }

    public static List<VWStepElement> searchRoster(String rosterName, Map<String, Object> searchParams,
                                                   int maxCaseRetrieve, VWSession vwSession) throws Exception {
        List<VWStepElement> lstVWStepElement = new ArrayList<>();
        try {
            VWRoster roster = null;
            if ((rosterName == null) || (rosterName.trim().length() == 0)) {
                roster = vwSession.getRoster(FileNetConstants.DEFAULT_ROSTER);
            } else {
                roster = vwSession.getRoster(rosterName);
            }
            LOGGER.info("Roster " + roster);

            StringBuffer strQuery = new StringBuffer();
            List<Object> substituteValues = new ArrayList<>();
            int substituteChar = 1;

            for (String key : searchParams.keySet()) {
                Object val = searchParams.get(key);
                LOGGER.info("Val: " + val.toString());

                if (key.compareToIgnoreCase("@ProcessName") == 0) {
                    LOGGER.info("Key: " + key);
                    key = "F_WorkClassId";
                }
                if ("F_WorkClassId".compareToIgnoreCase(key) == 0) {
                    val = Integer.valueOf(getWorkflowID((String) val, vwSession));
                    LOGGER.info("Val: " + val);
                }

                if ((val instanceof List)) {
                    StringBuffer strSubQuery = new StringBuffer();
                    List<?> values = (List<?>) val;
                    for (Object value : values) {
                        if (strSubQuery.length() > 0)
                            strSubQuery.append(" OR ");
                        if (((value instanceof String)) && (((String) value).contains("%"))) {
                            strSubQuery.append(key).append(" LIKE :").append(substituteChar++);
                        } else
                            strSubQuery.append(key).append(" = :").append(substituteChar++);
                        if (key.compareToIgnoreCase("F_WobNum") == 0) {
                            substituteValues.add(new VWWorkObjectNumber((String) value));
                        } else if (key.compareToIgnoreCase("F_WorkFlowNumber") == 0) {
                            substituteValues.add(new VWWorkObjectNumber((String) value));
                        } else {
                            substituteValues.add(value);
                        }
                    }

                    strSubQuery.insert(0, "(").append(")");

                    if (strQuery.length() > 0)
                        strQuery.append(" AND ");
                    strQuery.append(strSubQuery);
                } else {
                    if (strQuery.length() > 0)
                        strQuery.append(" AND ");
                    if (((val instanceof String)) && (((String) val).contains("%"))) {
                        strQuery.append(key).append(" LIKE :").append(substituteChar++);
                    } else {
                        strQuery.append(key).append(" = :").append(substituteChar++);
                    }
                    if (key.compareToIgnoreCase("F_WobNum") == 0) {
                        substituteValues.add(new VWWorkObjectNumber((String) val));
                    } else if (key.compareToIgnoreCase("F_WorkFlowNumber") == 0) {
                        substituteValues.add(new VWWorkObjectNumber((String) val));
                    } else
                        substituteValues.add(val);
                }
            }
            roster.setBufferSize(maxCaseRetrieve);
            VWRosterQuery query = roster.createQuery(null, null, null, 3, strQuery.toString(),
                    substituteValues.toArray(), 4);
            VWStepElement vwStepElement = null;
            while (query.hasNext()) {
                VWRosterElement rosterElement = (VWRosterElement) query.next();
                vwStepElement = rosterElement.fetchStepElement(false, true);
                lstVWStepElement.add(vwStepElement);
            }
        } catch (VWException e) {
            throw new Exception(e.getMessage());
        }
        return lstVWStepElement;
    }

    public static int getWorkflowID(String workflowName, VWSession vwSession) {
        try {
            return vwSession.convertClassNameToId(workflowName, false);
        } catch (VWException e) {
            LOGGER.info(e.getMessage());
        }
        return -1;
    }

    public static void removeAllHiddenFile(File folder) {
        File[] hiddenFiles = folder.listFiles((FileFilter) HiddenFileFilter.HIDDEN);
        for (File file : hiddenFiles) {
            Path p = Paths.get(file.getAbsolutePath());
            try {
                Files.delete(p);
            } catch (IOException e) {
                LOGGER.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public static void releaseFile(File folder) {
        LOGGER.info("Start relase Lock folder " + folder);
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                releaseLock(file);
            }
        } else {
            releaseLock(folder);
        }
        LOGGER.info("End relase Lock folder " + folder);
    }

    public static void releaseLock(File file) {
        try (FileLock tryLock = new RandomAccessFile(file, "rw").getChannel().tryLock()) {
            if (tryLock != null) {
                LOGGER.info("File = " + file + ": lock = " + tryLock);
                tryLock.release();
                LOGGER.info("Release Lock File " + file);
                LOGGER.info("File = " + file + " after release lock. Lock = " + tryLock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
