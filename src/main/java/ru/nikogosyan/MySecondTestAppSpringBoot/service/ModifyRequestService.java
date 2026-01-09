package ru.nikogosyan.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.nikogosyan.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
