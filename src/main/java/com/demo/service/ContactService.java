package com.demo.service;

import com.demo.controller.payload.QueryParameter;
import com.demo.dto.ContactDTO;
import com.demo.exception.ConstraintsViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContactService {

    void create(ContactDTO contactDTO) throws ConstraintsViolationException;

    Page<ContactDTO> findDriverByParams(QueryParameter queryParameter);

    void uploadCSVFile(MultipartFile csvFile) throws IOException;
}
