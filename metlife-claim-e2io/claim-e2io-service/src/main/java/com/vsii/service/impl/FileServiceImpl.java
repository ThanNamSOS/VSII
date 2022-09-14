package com.vsii.service.impl;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PageIterator;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.vsii.constants.FileNetConstants;
import com.vsii.entity.ClaimBenefitEntity;
import com.vsii.entity.Form;
import com.vsii.model.*;
import com.vsii.repository.ClaimBenefitRepository;
import com.vsii.repository.FormRepository;
import com.vsii.service.FileService;
import com.vsii.utils.DateUtils;
import filenet.vw.api.VWAttachment;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepElement;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ClaimBenefitRepository benefitRepository;

    @Override
    public String buildFolderPath(String folderParent, String folderChild) {
        return folderParent + File.separatorChar + folderChild;
    }

    @Override
    public List<Form> listForm() {
        return formRepository.findAll();
    }

    @Override
    public File moveFolder(File sourceFile, File targetFile) {
        try {
            LOGGER.info(
                    "Start Move folder from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath());
//			FileUtils.moveDirectory(sourceFile, targetFile);
            Path source = Paths.get(sourceFile.getAbsolutePath());
            if (targetFile.exists()) {
                LOGGER.error("TargetFile: " + targetFile + " already exists!! Create new targetFile");
                String parent = targetFile.getParent();
                String newName = targetFile.getName() + "." + DateUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                        + UUID.randomUUID().toString();
                targetFile = new File(buildFolderPath(parent, newName));
                LOGGER.info("New targetFile Path: " + targetFile.getAbsolutePath());
            }
            Path target = Paths.get(targetFile.getAbsolutePath());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
//            move(source, target);
            LOGGER.info("End Move folder from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath());
            return targetFile;
        } catch (Exception e) {
            LOGGER.error("Move folder from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath()
                    + "Error: " + ExceptionUtils.getStackTrace(e));
            LOGGER.info("End Move folder from " + sourceFile.getAbsolutePath() + " to " + targetFile.getAbsolutePath());
            return targetFile;
        }
    }

    @Override
    public void buildErrorFile(String pathFolder, ErrorModel errorModel) {
        File file = new File(pathFolder);
        String fileErrorName = file.getName() + "." + DateUtils.format(new Date(), "yyyyMMddHHmmssSSS") + ".xml";
        String pathFile = buildFolderPath(file.getParent(), fileErrorName);
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer;
        try (FileOutputStream outStream = new FileOutputStream(new File(pathFile))) {
            transformer = tf.newTransformer();
            String xmlString = jaxbObjectToXML(errorModel);
            transformer.transform(new DOMSource(stringXMLtoDocument(xmlString)), new StreamResult(outStream));
        } catch (Exception e) {
            LOGGER.error("Build file Error fail: " + ExceptionUtils.getStackTrace(e));
        }
    }

    public static String jaxbObjectToXML(ErrorModel errorModel) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ErrorModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(errorModel, sw);
            return sw.toString();
        } catch (JAXBException e) {
            LOGGER.error("Format ErrorModel fail: " + ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    public static Document stringXMLtoDocument(String xmlString) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            LOGGER.error("Build Document from string fail: " + ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    // Folder Filenet create
    public Folder createFolder(ObjectStore os, JsonObject jsonObject, ClaimModel claimModel, String source, String pathSaveFolder) throws ParseException {
        String classId = FileNetConstants.CLAIM_FOLDER;
        Folder folder = Factory.Folder.createInstance(os, classId);
        folder.set_FolderName(claimModel.getClaimId());
        Folder parent = getFolderParent(os, "North", pathSaveFolder);
        folder.set_Parent(parent);
        folder.save(RefreshMode.REFRESH);
        // update properties folder
        updateProperties(folder, jsonObject, claimModel, source);
        folder.save(RefreshMode.REFRESH);
        return folder;
    }

    private void database(JsonObject jsonObject) {

    }

    public Folder getFolderParent(ObjectStore os, String region, String pathSaveFolder) {
        String path = pathSaveFolder + region;
        return Factory.Folder.fetchInstance(os, path, null);
    }

    public void updateProperties(EngineObject folder, JsonObject jsonObject, ClaimModel claimModel, String source) throws ParseException {




        if (folder.getProperties().find(FileNetConstants.FolderName) != null) {
            folder.getProperties().putValue(FileNetConstants.FolderName, claimModel.getClaimId());
        }
        if (folder.getProperties().find(FileNetConstants.ClaimId) != null) {
            folder.getProperties().putValue(FileNetConstants.ClaimId, claimModel.getClaimId());
        }
        if (folder.getProperties().find(FileNetConstants.BenCode) != null) {
            folder.getProperties().putValue(FileNetConstants.BenCode, claimModel.getBenCode());
        }
        if (folder.getProperties().find(FileNetConstants.ClaimRequestId) != null) {
            folder.getProperties().putValue(FileNetConstants.ClaimRequestId, jsonObject.getClaimRequestId());
        }
        if (folder.getProperties().find(FileNetConstants.ClaimRequestFrom) != null) {
            folder.getProperties().putValue(FileNetConstants.ClaimRequestFrom, jsonObject.getClaimRequestForm());
        }
        if (folder.getProperties().find(FileNetConstants.PolicyNo) != null) {
            folder.getProperties().putValue(FileNetConstants.PolicyNo, jsonObject.getPolicyNumber());
        }
        if (folder.getProperties().find(FileNetConstants.Source) != null) {
            folder.getProperties().putValue(FileNetConstants.Source, source);
        }
        if (folder.getProperties().find(FileNetConstants.OwnerClientId) != null) {
            folder.getProperties().putValue(FileNetConstants.OwnerClientId, jsonObject.getOwner().getClientId());
        }
        if (folder.getProperties().find(FileNetConstants.OwnerName) != null) {
            folder.getProperties().putValue(FileNetConstants.OwnerName, jsonObject.getOwner().getName());
        }
///
        if (folder.getProperties().find(FileNetConstants.SubmissionTimestamp) != null) {
            if (!jsonObject.getSubmissionTimestamp().equals("") && jsonObject.getSubmissionTimestamp() != null) {
                Timestamp submissionTimestamp = DateUtils.convertTimestamp(jsonObject.getSubmissionTimestamp());
                folder.getProperties().putValue(FileNetConstants.SubmissionTimestamp, submissionTimestamp);
            }
        }
        if (folder.getProperties().find(FileNetConstants.OwnerClientNumber) != null) {
            folder.getProperties().putValue(FileNetConstants.OwnerClientNumber, jsonObject.getOwner().getClientNumber());
        }
        if (folder.getProperties().find(FileNetConstants.OwnerCitizenId) != null) {
            folder.getProperties().putValue(FileNetConstants.OwnerCitizenId, jsonObject.getOwner().getCitizenId());
        }
        if (folder.getProperties().find(FileNetConstants.LaClientId) != null) {
            folder.getProperties().putValue(FileNetConstants.LaClientId, jsonObject.getLifeAssured().getClientId());
        }

        LifeAssured lifeAssured = jsonObject.getLifeAssured();
        if (lifeAssured != null) {
            if (folder.getProperties().find(FileNetConstants.LaClientNumber) != null) {
                folder.getProperties().putValue(FileNetConstants.LaClientNumber, lifeAssured.getClientNumber());
            }
            if (folder.getProperties().find(FileNetConstants.LaName) != null) {
                folder.getProperties().putValue(FileNetConstants.LaName, lifeAssured.getName());
            }
            if (folder.getProperties().find(FileNetConstants.LaLifeNo) != null) {
                folder.getProperties().putValue(FileNetConstants.LaLifeNo, lifeAssured.getLifeNo());
            }
            if (folder.getProperties().find(FileNetConstants.LaCitizenId) != null) {
                folder.getProperties().putValue(FileNetConstants.LaCitizenId, lifeAssured.getCitizenId());
            }

            for (ClaimBenefit claimBenefit : lifeAssured.getClaimBenefits()) {
                if (claimBenefit.getClaimId() != null && !Objects.equals(claimBenefit.getClaimId(), "")) {
                    if (folder.getProperties().find(FileNetConstants.BenEndDate) != null) {
                        if (!claimBenefit.getEndDate().equals("") && claimBenefit.getEndDate() != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = formatter.parse(claimBenefit.getEndDate());
                            folder.getProperties().putValue(FileNetConstants.BenEndDate, date);
                        }
                    }
                    if (folder.getProperties().find(FileNetConstants.BenStartDate) != null) {
                        if (!claimBenefit.getStartDate().equals("") && claimBenefit.getStartDate() != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = formatter.parse(claimBenefit.getStartDate());
                            folder.getProperties().putValue(FileNetConstants.BenStartDate, date);
                        }
                    }
                }
            }
        }

        PaymentInfo paymentInfo = jsonObject.getPaymentInfo();
        if (folder.getProperties().find(FileNetConstants.PayPolicyNumber) != null) {
            folder.getProperties().putValue(FileNetConstants.PayPolicyNumber, paymentInfo.getPolicyNumber());
        }
        if (folder.getProperties().find(FileNetConstants.PayMode) != null) {
            folder.getProperties().putValue(FileNetConstants.PayMode, paymentInfo.getMode());
        }
        if (folder.getProperties().find(FileNetConstants.PayAccOwnerName) != null) {
            folder.getProperties().putValue(FileNetConstants.PayAccOwnerName, paymentInfo.getAccountOwnerName());
        }
        if (folder.getProperties().find(FileNetConstants.PayBank) != null) {
            folder.getProperties().putValue(FileNetConstants.PayBank, paymentInfo.getBank());
        }
        if (folder.getProperties().find(FileNetConstants.PayReceiverName) != null) {
            folder.getProperties().putValue(FileNetConstants.PayReceiverName, paymentInfo.getReceiverName());
        }
        if (folder.getProperties().find(FileNetConstants.PayAccNumber) != null) {
            folder.getProperties().putValue(FileNetConstants.PayAccNumber, paymentInfo.getAccountNumber());
        }
        if (folder.getProperties().find(FileNetConstants.PayReceiverCityzenId) != null) {
            folder.getProperties().putValue(FileNetConstants.PayReceiverCityzenId, paymentInfo.getReceiverCitizenId());
        }

        if (folder.getProperties().find(FileNetConstants.PayReveiverCidIssueDate) != null) {
            if (!paymentInfo.getReceiverCidIssueDate().equals("") && paymentInfo.getReceiverCidIssueDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(paymentInfo.getReceiverCidIssueDate());
                folder.getProperties().putValue(FileNetConstants.PayReveiverCidIssueDate, date);
            }

        }

        if (folder.getProperties().find(FileNetConstants.PayReceiveCidIssuePlace) != null) {
            folder.getProperties().putValue(FileNetConstants.PayReceiveCidIssuePlace, paymentInfo.getReceiverCidIssuePlace());
        }
        if (folder.getProperties().find(FileNetConstants.PayReceiveBank) != null) {
            folder.getProperties().putValue(FileNetConstants.PayReceiveBank, paymentInfo.getReceiverBank());
        }


        for (ClaimBenefit claimBenefit : jsonObject.getLifeAssured().getClaimBenefits()) {
            if (claimBenefit.getClaimId() != null && !claimBenefit.getClaimId().equals("")) {
                if (folder.getProperties().find(FileNetConstants.IcdDescription) != null) {
                    folder.getProperties().putValue(FileNetConstants.IcdDescription, claimBenefit.getDiagnostic().getDescription());
                }
                if (folder.getProperties().find(FileNetConstants.IcdCode) != null) {
                    folder.getProperties().putValue(FileNetConstants.IcdCode, claimBenefit.getDiagnostic().getCode());
                }
            }
        }


        Fatca fatca = jsonObject.getFatca();
        if (folder.getProperties().find(FileNetConstants.FatcaCode) != null) {
            folder.getProperties().putValue(FileNetConstants.FatcaCode, fatca.getCode());
        }
        if (folder.getProperties().find(FileNetConstants.FatcaDescription) != null) {
            folder.getProperties().putValue(FileNetConstants.FatcaDescription, fatca.getDescription());
        }
        if (folder.getProperties().find(FileNetConstants.FatcaDescription) != null) {
            folder.getProperties().putValue(FileNetConstants.FatcaDescription, fatca.getDescription());
        }

    }


    public void createDocument(Folder folder, File file, ObjectStore os, JsonObject jsonObject, ClaimModel claimModel) throws Exception {
        try {
            String formId = file.getName().split("_", -1)[0];
            InputStream inputStream = null;
            Exception fileEx = null;
            for (int i = 0; i < 5; i++) {
                try {
                    inputStream = FileUtils.openInputStream(file);
                } catch (Exception e) {
                    LOGGER.info("Document " + file.getPath() + " not found! Wait " + 5 + " ms "
                            + (i + 1) + " time!");
                    fileEx = e;
                    Thread.sleep(5);
                }
                if (inputStream != null) {
                    break;
                }
            }
            if (inputStream == null) {
                if (fileEx != null) {
                    throw fileEx;
                }
                throw new Exception("Cannot access the file " + file.getPath());
            }
            com.filenet.api.core.Document doc = Factory.Document.createInstance(os, FileNetConstants.DOCUMENT_DEFAULT);
            ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
            ContentElementList contentElementList = Factory.ContentElement.createList();
            contentTransfer.setCaptureSource(inputStream);
            contentElementList.add(contentTransfer);
            doc.set_ContentElements(contentElementList);
            Path path = file.toPath();
            String mimeType = Files.probeContentType(path);
            contentTransfer.set_RetrievalName(file.getName());
            contentTransfer.set_ContentType(mimeType);
            doc.set_MimeType(mimeType);
            doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            com.filenet.api.property.Properties prop = doc.getProperties();

            prop.putValue(FileNetConstants.CLAIM_ID, claimModel.getClaimId());
            prop.putValue(FileNetConstants.CLAIM_REQUEST_ID, jsonObject.getClaimRequestId());
            prop.putValue(FileNetConstants.FORM_ID, formId);
            prop.putValue(FileNetConstants.DOC_TITLE, file.getName().split("_", -1)[0]);
            Owner owner = jsonObject.getOwner();
            prop.putValue(FileNetConstants.OWNER_NAME, owner.getName());
            prop.putValue(FileNetConstants.OWNER_CITIZEN_ID, owner.getCitizenId());

            LifeAssured lifeAssured = jsonObject.getLifeAssured();

            for (ClaimBenefit claimBenefit : lifeAssured.getClaimBenefits()) {
                if (claimBenefit.getClaimId() != null && !Objects.equals(claimBenefit.getClaimId(), "")) {
                    prop.putValue(FileNetConstants.BEN_CODE, claimBenefit.getBenCode());
                    prop.putValue(FileNetConstants.BEN_END_DATE, convertDate(claimBenefit.getEndDate()));
                    prop.putValue(FileNetConstants.BEN_START_DATE, convertDate(claimBenefit.getStartDate()));

                    Diagnostic diagnostic = claimBenefit.getDiagnostic();
                    if (diagnostic != null) {
                        prop.putValue(FileNetConstants.DIANOSTIC_CODE, diagnostic.getCode());
                        prop.putValue(FileNetConstants.DIANOSTIC_DESCRIPTION, diagnostic.getDescription());
                    }
                }
            }

            if (lifeAssured != null) {
                prop.putValue(FileNetConstants.LA_CLIENT_ID, lifeAssured.getClientId());
                prop.putValue(FileNetConstants.LA_NAME, lifeAssured.getName());
                prop.putValue(FileNetConstants.LA_CITIZEN_ID, lifeAssured.getClientId());
                prop.putValue(FileNetConstants.LA_CLIENT_NUMBER, lifeAssured.getClientNumber());
                prop.putValue(FileNetConstants.LA_LIFE_NO, lifeAssured.getLifeNo());
                prop.putValue(FileNetConstants.LA_CITIZEN_ID, lifeAssured.getCitizenId());
            }
            doc.save(RefreshMode.REFRESH);
            ReferentialContainmentRelationship rc = folder.file(doc, AutoUniqueName.AUTO_UNIQUE,
                    file.getName(), DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
            rc.save(RefreshMode.REFRESH);
            LOGGER.info("date: " + doc.getProperties().getDateTimeValue("DateCreated"));
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error("Create Document " + file.getAbsolutePath() + " Error: " + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public Date convertDate(String dateInput) throws ParseException {
        if (!dateInput.equals("") && dateInput != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateInput);
            return date;
        }
        return null;
    }

    public void createDocumentAdd(Folder folder, File file, ObjectStore os, JsonObjectFollowup jsonObject, ClaimModel claimModel) throws Exception {
        try {
            String formId = file.getName().split("_", -1)[0];
            InputStream inputStream = null;
            Exception fileEx = null;
            for (int i = 0; i < 5; i++) {
                try {
                    inputStream = FileUtils.openInputStream(file);
                } catch (Exception e) {
                    LOGGER.info("Document " + file.getPath() + " not found! Wait " + 5 + " ms "
                            + (i + 1) + " time!");
                    fileEx = e;
                    Thread.sleep(5);
                }
                if (inputStream != null) {
                    break;
                }
            }
            if (inputStream == null) {
                if (fileEx != null) {
                    throw fileEx;
                }
                throw new Exception("Cannot access the file " + file.getPath());
            }
            com.filenet.api.core.Document doc = Factory.Document.createInstance(os, FileNetConstants.DOCUMENT_DEFAULT);
            ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
            ContentElementList contentElementList = Factory.ContentElement.createList();
            contentTransfer.setCaptureSource(inputStream);
            contentElementList.add(contentTransfer);
            doc.set_ContentElements(contentElementList);
            Path path = file.toPath();
            String mimeType = Files.probeContentType(path);
            contentTransfer.set_RetrievalName(file.getName());
            contentTransfer.set_ContentType(mimeType);
            doc.set_MimeType(mimeType);
            doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            com.filenet.api.property.Properties prop = doc.getProperties();
            prop.putValue(FileNetConstants.DOC_TITLE, file.getName().split("_", -1)[0]);

            prop.putValue(FileNetConstants.CLAIM_ID, claimModel.getClaimId());
            prop.putValue(FileNetConstants.CLAIM_REQUEST_ID, jsonObject.getClaimRequestId());
            prop.putValue(FileNetConstants.FORM_ID, formId);


            //prop.putValue("BenCode", claimModel.getBenCode());

            doc.save(RefreshMode.REFRESH);
            ReferentialContainmentRelationship rc = folder.file(doc, AutoUniqueName.AUTO_UNIQUE,
                    file.getName(), DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
            rc.save(RefreshMode.REFRESH);
            LOGGER.info("date: " + doc.getProperties().getDateTimeValue("DateCreated"));
        } catch (Exception e) {
            LOGGER.error("Create Document " + file.getAbsolutePath() + " Error: " + ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public Folder findByAppNo(ObjectStore os, String appNo) {
        String strQuery = "";
        strQuery = "SELECT m.* FROM ClaimFolder m WHERE FolderName='" + appNo + "'";
        SearchSQL sqlObject = new SearchSQL();
        sqlObject.setQueryString(strQuery);
        SearchScope searchScope = new SearchScope(os);
        PropertyFilter filterList = null;
        IndependentObjectSet set = searchScope.fetchObjects(sqlObject, null, filterList, Boolean.valueOf(true));
        PageIterator p = set.pageIterator();
        Folder f = null;
        while (p.nextPage()) {
            for (Object obj : p.getCurrentPage()) {
                f = (Folder) obj;
                if (f != null) {
                    break;
                }
            }
        }
        return f;
    }

    // WorkFlow create
    public void createWorkFlow(Containable f, VWSession vwSession, ClaimModel claimModel, JsonObject jsonObject) {
        try {
            VWStepElement launched = null;
            launched = vwSession.createWorkflow("NBWorkflow");
            launched.setParameterValue("F_Subject", f.get_Name(), true);
            launched.setParameterValue("ApplicationNo", f.get_Name(), true);
            setWFParameter(claimModel, launched, jsonObject);
            VWAttachment vwAtt = new VWAttachment();
            vwAtt.setAttachmentDescription("");
            vwAtt.setAttachmentName("PolicyFolder");
            vwAtt.setType(2);
            vwAtt.setLibraryType(3);
            vwAtt.setLibraryName("IWBPM");
            vwAtt.setId(f.get_Id().toString());
            launched.setParameterValue("PolicyFolder", vwAtt, false);
            launched.setSelectedResponse(FileNetConstants.DEFAULT_SELECTED_RESPONSE);
            launched.doDispatch();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void setWFParameter(ClaimModel claimModel, VWStepElement launched, JsonObject jsonObject) throws ParseException {

        Owner owner = jsonObject.getOwner();
        LifeAssured lifeAssured = jsonObject.getLifeAssured();
        launched.setParameterValue(FileNetConstants.CLAIM_ID, claimModel.getClaimId(), true);
        launched.setParameterValue(FileNetConstants.CLAIM_REQUEST_ID, claimModel.getClaimRequestId(), true);

        launched.setParameterValue(FileNetConstants.SOURCE, jsonObject.getSource(), true);
        if (!jsonObject.getSubmissionTimestamp().equals("") && jsonObject.getSubmissionTimestamp() != null) {
            launched.setParameterValue(FileNetConstants.SUBMISSION_TIME, DateUtils.convertTimestamp(jsonObject.getSubmissionTimestamp()), true);
        }
        launched.setParameterValue(FileNetConstants.POLICY_NUMBER, jsonObject.getPolicyNumber(), true);
        if (owner != null) {
            launched.setParameterValue(FileNetConstants.OWNER_CITIZEN_ID, owner.getCitizenId(), true);
            launched.setParameterValue(FileNetConstants.OWNER_CLIENT_NUMBER, owner.getClientNumber(), true);
            launched.setParameterValue(FileNetConstants.OWNER_NAME, owner.getName(), true);
            launched.setParameterValue(FileNetConstants.OWNER_CLIENT_ID, owner.getClientId(), true);
        }
        if (lifeAssured != null) {
            launched.setParameterValue(FileNetConstants.LA_CLIENT_ID, lifeAssured.getClientId(), true);
            launched.setParameterValue(FileNetConstants.LA_CLIENT_NUMBER, lifeAssured.getClientNumber(), true);
            launched.setParameterValue(FileNetConstants.LA_LIFE_NO, lifeAssured.getLifeNo(), true);
            launched.setParameterValue(FileNetConstants.LA_NAME, lifeAssured.getName(), true);
            launched.setParameterValue(FileNetConstants.LA_CITIZEN_ID, lifeAssured.getCitizenId(), true);
            for (ClaimBenefit claimBenefit : lifeAssured.getClaimBenefits()) {
                if (claimBenefit.getClaimId() != null && !claimBenefit.getClaimId().equals("")) {
                    if (!claimBenefit.getStartDate().equals("") && claimBenefit.getStartDate() != null) {
                        launched.setParameterValue(FileNetConstants.INCUR_DATE, convertDate(claimBenefit.getStartDate()), true);
                    }
                    if (!claimBenefit.getEndDate().equals("") && claimBenefit.getEndDate() != null) {
                        launched.setParameterValue(FileNetConstants.DISCHAR_DATE, convertDate(claimBenefit.getEndDate()), true);
                    }
                }
                launched.setParameterValue(FileNetConstants.BEN_CODE, claimBenefit.getBenCode(), true);
            }

        }

    }

//     public static void main(String args[]) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat();
//        Date parsedDate = dateFormat.parse("2022-05-27T15:25:30.445+07:00");
//        System.out.println(parsedDate);
//      System.out.println(Date.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse("2022-05-27T15:25:30.445+07:00"));;
//
//        String isoOffsetDateTimeString = "2022-05-27T15:25:30.445+07:00";
//
//        System.out.println(oldfashionedDate);
//     }
}
