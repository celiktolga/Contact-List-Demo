package com.demo.controller;

import com.demo.constants.ErrorCodes;
import com.demo.controller.payload.QueryParameter;
import com.demo.dto.ContactDTO;
import com.demo.exception.InvalidCSVException;
import com.demo.predicates.Predicates;
import com.demo.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Contact Controller")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @ApiOperation(value = "getContact")
    @GetMapping
    public ModelAndView getDriver(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.setPageNo(pageNo);
        queryParameter.setPageSize(pageSize);
        Map<String, String> sortBy = new HashMap<String, String>() {{
            put("name", "asc");
        }};
        queryParameter.setSortBy(sortBy);
        Page<ContactDTO> page = contactService.findDriverByParams(queryParameter);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("response", page);
        modelAndView.addObject("isOrderedByName", true);
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @ApiOperation(value = "uploadContact")
    @PostMapping("/upload")
    public ModelAndView compareCSVTransactionRecords(@RequestPart("csvFile") MultipartFile csvFile) throws IOException {
        if (!Predicates.isCSVFormat.test(csvFile))
            throw new InvalidCSVException(ErrorCodes.INVALID_CSV);
        contactService.uploadCSVFile(csvFile);
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> sortBy = new HashMap<String, String>() {{
            put("id", "asc");
        }};
        Page<ContactDTO> page = contactService.findDriverByParams(new QueryParameter(0, 10, sortBy));
        modelAndView.addObject("response", page);
        modelAndView.addObject("isOrderedByName", false);
        modelAndView.setViewName("list");
        return modelAndView;
    }
}
