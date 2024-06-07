package com.Devsu.CuentaMovimientosService.dummy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String message;
}