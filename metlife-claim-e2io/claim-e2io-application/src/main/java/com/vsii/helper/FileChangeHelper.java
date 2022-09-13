package com.vsii.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileChangeHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileChangeHelper.class);

    public static List<File> getFolderChange(Set<ChangedFiles> changeSet) {
        List<File> lstFolder = new ArrayList<>();
        List<ChangedFile> lstChangeFile = new ArrayList<>();
        File sourceFolder = changeSet.iterator().next().getSourceDirectory();
        lstFolder.add(sourceFolder);
        for (ChangedFiles changedFiles : changeSet) {
            lstChangeFile.addAll(changedFiles.getFiles());
        }
        for (ChangedFile changedFile : lstChangeFile) {
            LOGGER.info(
                    "File " + changedFile.getFile().getAbsolutePath() + " change with Type = " + changedFile.getType());
            if (changedFile.getType() == Type.ADD) {
                File parentFile = changedFile.getFile().getParentFile();
                if (!lstFolder.contains(parentFile)) {
                    lstFolder.add(parentFile);
                }
            }
        }
        lstFolder.remove(sourceFolder);
        return lstFolder;
    }
}
