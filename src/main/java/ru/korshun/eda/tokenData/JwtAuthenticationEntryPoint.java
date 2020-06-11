package ru.korshun.eda.tokenData;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.korshun.eda.controller.UserController;
import ru.korshun.eda.response.BaseResponse;
import ru.korshun.eda.utils.Functions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        final String expired = (String) httpServletRequest.getAttribute(JwtTokenProvider.TOKEN_ERROR_TAG);
        ObjectMapper mapper = new ObjectMapper();
        String responseMsg, error;

        if(expired != null) {
            error = expired;
            responseMsg = mapper.writeValueAsString(
                    new BaseResponse<>(HttpStatus.NOT_ACCEPTABLE, error, null));
        }

        else {
            error = e.getMessage();
            responseMsg = mapper.writeValueAsString(
                    new BaseResponse<>(HttpStatus.UNAUTHORIZED, error, null));
        }

        logger.error("Authorization error. Message - {}", error);
//        Functions
//                .getLogger(JwtAuthenticationEntryPoint.class)
//                .error("Authorization error. Message - {}", error);

//        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        httpServletResponse.getWriter().write(responseMsg);

    }
}
