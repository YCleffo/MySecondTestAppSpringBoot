package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Response;
import ru.nikogosyan.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {

    private static final Logger log = LoggerFactory.getLogger(ModifySystemTimeResponseService.class);

    @Override
    public Response modify(Response response) {
        String oldSystemTime = response.getSystemTime();
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));

        log.info("Изменено SystemTime: с '{}' на '{}'", oldSystemTime, response.getSystemTime());
        return response;
    }
}