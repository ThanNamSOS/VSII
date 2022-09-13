package vn.systems.claim.services.capture.Config;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.constants.Constants;

@Configuration
public class JASYPTConfig {
    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {
        String jasyptPass = System.getenv(Constants.CLAIM_CAPTURE_PWD_SECRET);
        if (StringUtils.isEmpty(jasyptPass)) {
            jasyptPass = Constants.JASYPT_PASS;
        }
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptPass);
        config.setAlgorithm(Constants.JASYPT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(Constants.JASYPT_NAME_PROVIDER);
        config.setSaltGeneratorClassName(Constants.JASYPT_GENERATORCLASSNAME);
        config.setStringOutputType(Constants.JASYPT_TYPE);
        encryptor.setConfig(config);
        return encryptor;
    }

    public static void main(String[] args) {
        String jasyptPass = System.getenv("IWS_E2IO_PWD_SECRETSS");
        if (StringUtils.isEmpty(jasyptPass)) {
            jasyptPass = "claim-capture";
        }
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptPass);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        System.out.println(encryptor.encrypt("Rstn2273"));

    }
}
