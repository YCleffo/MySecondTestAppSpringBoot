package ru.nikogosyan.MySecondTestAppSpringBoot.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Response;

import java.util.UUID;

@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService implements ModifyResponseService {
    private static final Logger log = LoggerFactory.getLogger(ModifyOperationUidResponseService.class);
    @Override
    public Response modify(Response response) {
        UUID uuid = UUID.randomUUID();
        String oldOperationUid = response.getOperationUid();
        response.setOperationUid(uuid.toString());
        log.info("Изменен OperationUid: с '{}' на '{}'", oldOperationUid, response.getOperationUid());
        return response;
    }
}
