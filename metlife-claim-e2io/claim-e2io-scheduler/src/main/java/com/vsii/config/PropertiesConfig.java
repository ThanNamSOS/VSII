//package com.vsii.config;
//
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.convert.Delimiter;
//import org.springframework.stereotype.Component;
//
//@Component
//@Data
//public class PropertiesConfig {
//
//    Delimiter folder;
//
//    @Value(value = "${com.bml.folder.webservice-new.delimiter}")
//    private String folderDelimiterWebserviceNew;
//
//    @Value(value = "${com.bml.folder.webservice-add.delimiter}")
//    private String folderDelimiterWebserviceAdd;
//
//    @Value(value = "${com.bml.file.delimiter}")
//    private String fileDelimiter;
//
//
//    // folder name
//    @Value(value = "${com.bml.folder.release}")
//    private String folderRelease;
//
//    @Value(value = "${com.bml.folder.error}")
//    private String folderError;
//
//    @Value(value = "${com.bml.folder.backup}")
//    private String folderBackup;
//
//
//    // filenet properties
//    @Value(value = "${com.bml.filenet.uri}")
//    private String uri;
//
//    @Value(value = "${com.bml.filenet.osName}")
//    private String osName;
//
//    @Value(value = "${com.bml.filenet.stanza}")
//    private String stanza;
//
//    @Value(value = "${com.bml.filenet.username}")
//    private String userName;
//
//    @Value(value = "${com.bml.filenet.password}")
//    private String passWord;
//
////    @Value(value = "${spring.kafka.topic.name.scan-new}")
////    private String topicNameScanNew;
////    @Value(value = "${spring.kafka.topic.name.import}")
////    private String topicNameImport;
////    @Value(value = "${spring.kafka.topic.name.scan-add}")
////    private String topicNameScanADD;
////
////    @Value(value = "${spring.kafka.topic.name.new}")
////    private String topicName;
////    @Value(value = "${spring.kafka.topic.name.add}")
////
////    private String topicNameAdd;
////    //NameFolder
////    @Value(value = "${com.bml.folder.webserviceNC}")
////    private String folderWebserviceNC;
////    @Value(value = "${com.bml.folder.WebServiceADD}")
////    private String folderWebserviceADD;
////    @Value(value = "${com.bml.folder.ScanNCPPX}")
////    private String folderScanNCPPX;
////    @Value(value = "${com.bml.folder.ScanADDPPX}")
////    private String folderScanADDPPX;
////    @Value(value = "${com.bml.folder.ImportPPX}")
////    private String folderImportPPX;
////    @Value(value = "${com.bml.filenet.save.folder}")
////    private String pathSaveFolder;
//}
