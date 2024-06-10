package com.devsu.ClientePersonaService.utils;

public class Constants {

    public static final String INICIO_MS="**********El Microservicio ClientePersonaServiceApplication se ha iniciado correctamente.**********";
    public static final String OK = "OK";
    public static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String BAD_REQUEST = "404";
    public static final String ERROR = "ERROR";
    public static final String CLIENTE_ELIMINADO = "CLIENTE Eliminado: ";
    public static final String CLIENTE_CREADO = "CLIENTE Creado: ";
    public static final String CLIENTE_ACTUALIZADO = "CLIENTE Actualizado: ";
    public static final String FECHA_INCORRECTA = "Fecha Incorrecta";
    public static final String REPORTE_CREADO = "Reporte Creado";
    public static final String CLIENTE_NO_CUENTAS = "El cliente no tiene cuentas asociadas";
    public static final String CLIENTE_NO_MOVIMIENTOS = "El Cliente no tiene Movimientos asociadas en estas fechas";

    public static final String CLIENTE_CUENTA_QUEUE = "clienteCuentaQueue";
    public static final String USER_NOT_FOUND ="User not found with id ";

    public static final String CLIENTE_NO_NOMBRE= "El Cliente no tiene Nombre";
    public static final String ERROR_PROCESSING_JSON="Error processing JSON";
    public static final String RECEIVEND_MESSAGE="Received message from movimientoCliente {}";
    public static final String EMPTY_MESSAGE="Empty message received";
    public static final String ERROR_DESERIALIZING_MOVIMIENTCLIENTE="Error deserializing JSON from movimientoCliente";
    public static final String NO_ACCOUNTS_FOR_CLIENT="No accounts found for client";
    public static final String ERROR_DESERIALIZING_CLIENTID="Error deserializing JSON to extract clienteId";
    public static final String DEPOSITO="Depósito";
    public static final String RETIRO ="Retiro";
    public static final String CLIENTE_FECHAS_JSON =  "ClientFechas JSON: {}";
    public static final String ERROR_CONVERTING_CLIENTFECHAS="Error converting ClientFechas to JSON";
}
