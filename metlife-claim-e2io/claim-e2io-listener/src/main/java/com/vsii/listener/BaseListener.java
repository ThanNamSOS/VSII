package com.vsii.listener;

import com.vsii.config.PropertiesConfig;
import com.vsii.repository.FormRepository;
import com.vsii.service.*;
import com.vsii.service.impl.ClaimAdditionalServiceImpl;
import com.vsii.service.impl.FileServiceImpl;
import com.vsii.service.impl.ValidateServiceImpl;
import filenet.vw.api.VWSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class BaseListener {
    public static final String LOG_DATE_FORMAT = "yyyyMMdd HH:mm:ss.SSS";

    @Autowired
    protected PropertiesConfig propertiesConfig;

    @Autowired
    protected VWSession vwSession;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    ValidateServiceImpl validateService;

    @Autowired
    FormRepository formRepository;

    @Autowired
    ClaimRequestService claimRequestService;


    @Autowired
    ClaimBenefitInfoService claimBenefitInfoService;

    @Autowired
    ClaimCaseService claimCaseService;

    @Autowired
    ClaimAdditionalService claimAdditionalService;

    @Autowired
    ClaimAdditionalBenefitService additionalBenefitService;

    @Autowired
    ClaimAdditionalBenefitAttService claimAdditionalBenefitAttService;

    @Autowired
    ClaimBenefitService claimBenefitService;

    @Autowired
    ClaimBenefitAttService claimBenefitAttService;
}
