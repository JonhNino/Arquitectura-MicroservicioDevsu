package com.Devsu.CuentaMovimientosService.utils;

public class Constants {
    public static final String INICIO_MS="**********El Microservicio CuentaMovimientosServiceApplication se ha iniciado correctamente.**********";
    public static final String OK = "OK";
    public static final String BAD_REQUEST = "404";
    public static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String RETIRO = "Retiro";
    public static final String DEPOSITO = "Depósito";
    public static final String SALDO_NO_DISPONIBLE = "Saldo no disponible.";
    public static final String CUENTA_ELIMINADO = "CUENTA Eliminado: ";
    public static final String CUENTA_ACTUALIZADO = "CUENTA Actualizado: ";
    public static final String CUENTA_CREADO = "CUENTA Creado: ";
    public static final String MOVIMIENTO_CREADO = "MOVIMIENTO Creado: ";
    public static final String MOVIMIENTO_ELIMINADO = "MOVIMIENTO Eliminado: ";
    public static final String MOVIMIENTO_ACTUALIZADO = "MOVIMIENTOActualizado: ";
    public static final String USER_NOT_FOUND="User not found with id ";
    public static final String NUMERO_CUENTA = "numero_cuenta";
    public static final String TIPO_CUENTA = "tipo_cuenta";
    public static final String SALDO_INICIAL = "saldo_inicial";
    public static final String ESTADO = "estado";
    public static final String MOVIMIENTO_ID = "movimiento_id";
    public static final String FECHA = "fecha";
    public static final String TIPO_MOVIMIENTO = "tipo_movimiento";
    public static final String VALOR = "valor";
    public static final String SALDO = "saldo";

    public static final String CLIENTE_CUENTA_QUEUE_NAME = "clienteCuenta";
    public static final String MOVIMIENTO_CLIENTE_QUEUE_NAME = "movimientoCliente";

    // Constantes para los nombres de las colas
    public static final String CLIENTE_CUENTA_QUEUE_NAME1 = "${clienteCuenta.queue.name}";
    public static final String MOVIMIENTO_CLIENTE_QUEUE_NAME1= "${movimientoCliente.queue.name}";

    // Otros Strings que podrían ser útiles
    public static final String NULL_DATE_MESSAGE = "Received message with null dates: {}";
    public static final String JSON_DESERIALIZE_ERROR_MESSAGE = "Error deserializing JSON to Cliente";


}
