package com.vsii.config;

import com.vsii.scheduler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Configuration
public class FolderWatcherConfig {

    @Autowired
    private PropertiesConfig properties;

    private static final long POLLINTERVAL = 2000L;

    private static final long QUIETPERIOD = 1000L;

    @Bean(name = "webserviceNewClaimFileSystemWatcher")
    public FileSystemWatcher webserviceNewClaimFileSystemWatcher(PropertiesConfig properites) {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(POLLINTERVAL),
                Duration.ofMillis(QUIETPERIOD));
        fileSystemWatcher.addSourceDirectory(
                new File(properites.getFolderRelease() + File.separator + properites.getFolderWebserviceNC()));
        fileSystemWatcher.addListener(new WebserviceNewScheduler());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @Bean(name = "webserviceAddClaimFileSystemWatcher")
    public FileSystemWatcher webserviceAddClaimFileSystemWatcher(PropertiesConfig properites) {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(POLLINTERVAL + 2000L),
                Duration.ofMillis(QUIETPERIOD + 2000L));
        fileSystemWatcher.addSourceDirectory(
                new File(properites.getFolderRelease() + File.separator + properites.getFolderWebserviceADD()));
        fileSystemWatcher.addListener(new WebserviceAddScheduler());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @Bean(name = "ScanNewClaimFileSystemWatcher")
    public FileSystemWatcher ScanNewClaimFileSystemWatcher(PropertiesConfig properites) {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(POLLINTERVAL),
                Duration.ofMillis(QUIETPERIOD));
        fileSystemWatcher.addSourceDirectory(
                new File(properites.getFolderRelease() + File.separator + properites.getFolderScanNCPPX()));
        fileSystemWatcher.addListener(new ScanNewScheduler());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @Bean(name = "ScanAddClaimFileSystemWatcher")
    public FileSystemWatcher ScanAddClaimFileSystemWatcher(PropertiesConfig properites) {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(POLLINTERVAL + 1000L),
                Duration.ofMillis(QUIETPERIOD + 1000L));
        fileSystemWatcher.addSourceDirectory(
                new File(properites.getFolderRelease() + File.separator + properites.getFolderScanADDPPX()));
        fileSystemWatcher.addListener(new ScanAddScheduler());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @Bean(name = "ImportClaimFileSystemWatcher")
    public FileSystemWatcher ImportClaimFileSystemWatcher(PropertiesConfig properites) {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(POLLINTERVAL + 1000L),
                Duration.ofMillis(QUIETPERIOD + 1000L));
        fileSystemWatcher.addSourceDirectory(
                new File(properites.getFolderRelease() + File.separator + properites.getFolderImportPPX()));
        fileSystemWatcher.addListener(new ImportScheduler());
        fileSystemWatcher.start();
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        webserviceNewClaimFileSystemWatcher(properties).stop();
        webserviceAddClaimFileSystemWatcher(properties).stop();
        ScanNewClaimFileSystemWatcher(properties).stop();
        ScanAddClaimFileSystemWatcher(properties).stop();
        ImportClaimFileSystemWatcher(properties).stop();
    }

}
