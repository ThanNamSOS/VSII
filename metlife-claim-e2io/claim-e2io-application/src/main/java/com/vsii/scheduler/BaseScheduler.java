package com.vsii.scheduler;

import com.vsii.config.PropertiesConfig;
import com.vsii.service.impl.FileServiceImpl;
import com.vsii.service.impl.ValidateServiceImpl;
import com.vsii.utils.JMSUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class BaseScheduler {

    @Autowired
    protected JMSUtils jmsUtils;

    @Autowired
    protected PropertiesConfig propertiesConfig;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    ValidateServiceImpl validateService;
}
