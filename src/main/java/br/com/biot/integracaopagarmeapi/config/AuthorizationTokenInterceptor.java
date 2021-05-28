package br.com.biot.integracaopagarmeapi.config;

import br.com.biot.integracaopagarmeapi.modulos.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.util.ObjectUtils.isEmpty;

public class AuthorizationTokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String ENDPOINT_PROTEGIDO = "/api/";
    private static final String BEARER = "bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY = "";
    private static final String OPTIONS_METHOD = "OPTIONS";

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (possuiUrlQueNecessitaAutenticacao(request)) {
            return isOptions(request) || possuiAuthorizationHeaderComTokenValido(request);
        }
        return true;
    }

    private boolean possuiUrlQueNecessitaAutenticacao(HttpServletRequest request) {
        return request.getRequestURI().contains(ENDPOINT_PROTEGIDO);
    }

    private boolean possuiAuthorizationHeaderComTokenValido(HttpServletRequest request) {
        var token = extractBearerFromToken(request.getHeader(AUTHORIZATION_HEADER));
        return jwtService.possuiUsuarioAutenticado(token);
    }

    private String extractBearerFromToken(String accessToken) {
        if (!isEmpty(accessToken)) {
            accessToken = accessToken.replace(BEARER, EMPTY);
            accessToken = accessToken.replace(EMPTY_SPACE, EMPTY);
        } else {
            accessToken = EMPTY;
        }
        return accessToken;
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equals(OPTIONS_METHOD);
    }
}
