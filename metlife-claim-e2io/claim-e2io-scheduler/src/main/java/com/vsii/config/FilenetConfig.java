//package com.vsii.config;
//
//import filenet.vw.api.VWSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//
//import java.io.IOException;
//
//
//
//@Configuration
//public class FilenetConfig {
//
//    // workflow properties
//    @Value(value = "${com.bml.filenet.wf-ceConnURL}")
//    private String wfCEConnURL;
//
//    @Value(value = "${com.bml.filenet.wf-username}")
//    private String wfUserName;
//
//    @Value(value = "${com.bml.filenet.wf-password}")
//    private String wfPassWord;
//
//    @Value(value = "${com.bml.filenet.wf-jaasConfigPath}")
//    private String wfJAASConfigPath;
//
//    @Value(value = "${com.bml.filenet.wf-peConnPt}")
//    private String wfPEConnPt;
//
//    @Value(value = "${com.bml.filenet.wf-fnDomain}")
//    private String wfFNDomain;
//
//    @Value("classpath:conf/jaas.conf.WebSphere")
//    private Resource wfJASSConfig;
//
//    @Bean
//    public VWSession vwSession() throws IOException {
//        VWSession vwSession = new VWSession();
//        vwSession.setBootstrapCEURI(wfCEConnURL);
//        String path = wfJASSConfig.getURL().getPath();
//        System.setProperty("java.security.auth.login.config", path);
//        vwSession.logonByDomain(wfFNDomain, wfUserName, wfPassWord, wfPEConnPt);
//        return vwSession;
//    }
//}
