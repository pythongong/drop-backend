package com.drop.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static void out(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}
