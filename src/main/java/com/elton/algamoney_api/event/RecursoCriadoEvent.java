package com.elton.algamoney_api.event;


import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

public class RecursoCriadoEvent  extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 7965596466730354902L;

    private HttpServletResponse response;
    private Long codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCodigo() {
        return codigo;
    }
}
