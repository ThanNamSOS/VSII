package vn.systems.claim.services.capture.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Config {
    @Value("${WebServiceNCPPX-local}")
    public String folderParentLocal;

    @Value("${WebServiceNCPPX-server}")
    public String folderParentServer;

    @Value("${WebServiceADDPPX-local}")
    public String folderParentAddLocal;

    @Value("${WebServiceADDPPX-server}")
    public String folderParentAddServer;


}
