package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDTO {
    private Long id;
    @NotNull(message = "name can not be null!")
    @ApiModelProperty(name = "username", dataType = "String", value = "myname")
    private String name;
    @NotNull(message = "Password can not be null!")
    @ApiModelProperty(name = "photo", dataType = "String", value = "photo.jpg")
    private String photo;

    private ContactDTO() {
    }

    private ContactDTO(Long id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public static ContactDTOBuilder newBuilder() {
        return new ContactDTOBuilder();
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public static class ContactDTOBuilder {
        private Long id;
        private String name;
        private String photo;

        public ContactDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ContactDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ContactDTOBuilder setPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public ContactDTO createContactDTO() {
            return new ContactDTO(id, name, photo);
        }
    }
}
