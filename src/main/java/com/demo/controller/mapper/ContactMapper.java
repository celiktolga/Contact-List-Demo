package com.demo.controller.mapper;

import com.demo.dto.ContactDTO;
import com.demo.entity.Contact;
import org.apache.commons.csv.CSVRecord;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper {
    public static Contact toModel(ContactDTO dto) {
        Contact model = new Contact();
        model.setName(dto.getName());
        model.setPhoto(dto.getPhoto());
        return model;
    }

    public static ContactDTO toDTO(Contact model) {
        ContactDTO.ContactDTOBuilder contactDTOBuilder = ContactDTO.newBuilder()
                .setId(model.getId())
                .setName(model.getName())
                .setPhoto(model.getPhoto());

        return contactDTOBuilder.createContactDTO();
    }

    public static List<ContactDTO> toDTOList(List<Contact> model) {
        return model.stream()
                .map(ContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Contact csvTOModel(CSVRecord data) {
        return Contact.builder()
                .dateCreated(ZonedDateTime.now())
                .name(data.get(0))
                .photo(data.get(1))
                .build();
    }

}
