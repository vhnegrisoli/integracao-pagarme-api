package br.com.biot.integracaopagarmeapi.modulos.util;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

public class TokenUtil {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER = "bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY = "";

    public static String extrairTokenDoRequest(HttpServletRequest request) {
        var accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!isEmpty(accessToken)) {
            accessToken = accessToken.replace(BEARER, EMPTY);
            accessToken = accessToken.replace(EMPTY_SPACE, EMPTY);
        } else {
            accessToken = EMPTY;
        }
        return accessToken;
    }
}
