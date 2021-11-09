package com.demo.util;

import com.demo.controller.payload.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AppUtils {

    public static PageImpl emptyPage(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    public static Pageable generatePage(QueryParameter queryParameter) {
        List<Sort.Order> orders = new ArrayList<>();
        if (queryParameter.getSortBy() != null) {
            for (var item : queryParameter.getSortBy().entrySet()) {
                log.info("Order by: " + item.getKey() + " /" + item.getValue());
                orders.add(new Sort.Order(getSortDirection(item.getValue()), item.getKey()));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection("asc"), "creationDate"));
        }
        return PageRequest.of(queryParameter.getPageNo(), queryParameter.getPageSize(), Sort.by(orders));
    }

    private static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
