package vn.systems.claim.services.capture.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import vn.claim.capture.model.*;
import vn.systems.claim.services.capture.service.ClaimService;
import vn.systems.claim.services.capture.service.FileAddService;
import vn.systems.claim.services.capture.service.FileService;
import vn.systems.claim.services.capture.service.Impl.FileServiceImpl;
import vn.util.Utils;

@RestController
@RequestMapping("/claimCapture")
public class ClaimController {
    @Autowired
    private ClaimService claimService;
    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private FileAddService fileAddService;

    @PostMapping("/uploadDocument")
    public Mono<ResponseObject> uploadDocument(@RequestBody JsonObject jsonObject) {
        try {
            ResponseObject responseInvalid = fileService.validateJsonObject(jsonObject);
            if (responseInvalid == null) {
                boolean saveFile = claimService.createFolderParentToServer(jsonObject);
                if (saveFile) {
                    for (ClaimBenefit claimBen : jsonObject.getLifeAssured().getClaimBenefits()) {
                        for (Attachment at : claimBen.getAttachments()) {
                            at.setData("");
                        }
                    }
                    return Mono.just(Utils.responseSuccess(jsonObject));
                } else {
                    claimService.createFolderParentToLocal(jsonObject);
                    for (ClaimBenefit claimBen : jsonObject.getLifeAssured().getClaimBenefits()) {
                        for (Attachment at : claimBen.getAttachments()) {
                            at.setData("");
                        }
                    }
                    return Mono.just(Utils.responseCreateFileLocalSuccess(jsonObject));
                }
            } else {
                return Mono.just(responseInvalid);
            }
        } catch (Exception e) {
            for (ClaimBenefit claimBen : jsonObject.getLifeAssured().getClaimBenefits()) {
                for (Attachment at : claimBen.getAttachments()) {
                    at.setData("");
                }
            }
            return Mono.just(Utils.responseUnSuccess(jsonObject));
        }
    }

    @PostMapping("/addDocument")
    public Mono<ResponseObject> addDocument(@RequestBody JsonObjectFollowup jsonObjectFollowup) {
        try {
            ResponseObject responseInvalid = fileService.validateJsonObjectFollowUp(jsonObjectFollowup);
            if (responseInvalid == null) {
                boolean saveFileAdd = fileAddService.createFolderParentToServer(jsonObjectFollowup);
                if (saveFileAdd) {
                    return Mono.just(Utils.responseSuccessJsonObjectFolowUp(jsonObjectFollowup));
                } else {
                    fileAddService.createFolderParentToLocal(jsonObjectFollowup);
                    return Mono.just(Utils.responseCreateFileLocalSuccessJsonObjectFollowUp(jsonObjectFollowup));
                }
            } else {
                return Mono.just(responseInvalid);
            }
        } catch (Exception e) {
            return Mono.just(Utils.responseUnSuccessJsonObjectFollowUp(jsonObjectFollowup));
        }
    }
}
