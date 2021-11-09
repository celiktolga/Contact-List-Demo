package com.demo.service;

import com.demo.controller.mapper.ContactMapper;
import com.demo.controller.payload.QueryParameter;
import com.demo.dao.ContactRepository;
import com.demo.dto.ContactDTO;
import com.demo.entity.Contact;
import com.demo.exception.ConstraintsViolationException;
import com.demo.util.AppUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some domain specific things.
 * <p/>
 */
@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository repository;

    @Override
    public void create(ContactDTO dto) throws ConstraintsViolationException {

        try {
            repository.save(ContactMapper.toModel(dto));
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a entity: {}", dto, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }

    @Override
    public Page<ContactDTO> findDriverByParams(QueryParameter queryParameter) {
        Pageable pageable = AppUtils.generatePage(queryParameter);
        Page<Contact> contactPage = repository.findAll(pageable);
        if (!contactPage.isEmpty())
            return new PageImpl<>(contactPage.stream()
                    .map(ContactMapper::toDTO)
                    .collect(Collectors.toList()), pageable, contactPage.getTotalElements());
        return AppUtils.emptyPage(pageable);
    }

    @Override
    public void uploadCSVFile(MultipartFile csvFile) throws IOException {
        List<CSVRecord> csvParsedRecords = readCSVFiles(csvFile);

        List<Contact> fileList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        csvParsedRecords.forEach(csvRecord -> {
            if (count.getAndIncrement() != 0) {//get rid of header
                Contact model = ContactMapper.csvTOModel(csvRecord);
                fileList.add(model);
            }
        });

        fileList.forEach(contact -> {
            repository.save(contact);
        });
    }

    private List<CSVRecord> readCSVFiles(MultipartFile file) throws IOException {

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            return csvParser.getRecords();
        } catch (IOException ioEx) {
            throw new IOException();
        }
    }
}
