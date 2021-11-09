package com.demo.controller.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryParameter {
    @ApiModelProperty(name = "pageNo", dataType = "String", value = "pageNo", example = "0")
    private Integer pageNo;
    @ApiModelProperty(name = "pageSize", dataType = "String", value = "pageSize", example = "10")
    private Integer pageSize;
    @ApiModelProperty(name = "sortBy", dataType = "Map", value = "sortBy", example = "{\"creationDate\": \"name\"}")
    private Map<String, String> sortBy;
}
