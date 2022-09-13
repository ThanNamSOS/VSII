package com.vsii.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PropertiesConfig {
    // folder name
    @Value(value = "${com.bml.folder.release}")
    private String folderRelease;

    @Value(value = "${com.bml.folder.error}")
    private String folderError;

    @Value(value = "${com.bml.folder.backup}")
    private String folderBackup;
    @Value(value = "${com.bml.folder.webserviceNC}")
    private String folderWebserviceNC;
    @Value(value = "${com.bml.folder.WebServiceADD}")
    private String folderWebserviceADD;
    @Value(value = "${com.bml.folder.ScanNCPPX}")
    private String folderScanNCPPX;
    @Value(value = "${com.bml.folder.ScanADDPPX}")
    private String folderScanADDPPX;
    @Value(value = "${com.bml.folder.ImportPPX}")
    private String folderImportPPX;

    @Value(value = "${com.bml.folder.webserviceNC.delimiter}")
    private String folderDelimiterWebserviceNC;
    @Value(value = "${com.bml.folder.webservice-add.delimiter}")
    private String folderDelimiterWebserviceAdd;

    @Value(value = "${com.bml.folder.webservice-add.fieldLength}")
    private int fieldLengthWebserviceAdd;
    @Value(value = "${com.bml.folder.webserviceNC.fieldLength}")
    private int fieldLengthWebserviceNC;

    @Value(value = "${com.bml.folder.webservice-scan-add.delimiter}")
    private String folderDelimiterScanAdd;
    @Value(value = "${com.bml.folder.webservice-scan-add.fieldLength}")
    private int fieldLengthScanAdd;

    @Value(value = "${com.bml.folder.webservice-scan-new.delimiter}")
    private String folderDelimiterScanNew;
    @Value(value = "${com.bml.folder.webservice-scan-new.fieldLength}")
    private int fieldLengthScanNew;

    @Value(value = "${com.bml.folder.webservice-import.delimiter}")
    private String folderDelimiterImport;
    @Value(value = "${com.bml.folder.webservice-import.fieldLength}")
    private int fieldLengthImport;

    @Value(value = "${spring.kafka.topic.name.new}")
    private String topicNameNew;
    @Value(value = "${spring.kafka.topic.name.add}")
    private String topicNameAdd;
    @Value(value = "${spring.kafka.topic.name.scan-new}")
    private String topicNameScanNew;
    @Value(value = "${spring.kafka.topic.name.import}")
    private String topicNameImport;
    @Value(value = "${spring.kafka.topic.name.scan-add}")
    private String topicNameScanADD;


}
