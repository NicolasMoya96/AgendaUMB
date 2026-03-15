package com.example.agendaumb;

/**
 * URLs del servidor PHP.
 *
 * 10.0.2.2 es la IP especial del emulador Android Studio
 * para acceder al localhost del computador host.
 * Si usas un celular fisico en WiFi, cambia por la
 * IP de ipconfig (Adaptador Wi-Fi → Direccion IPv4).
 */
public class Constantes {

    // IP del emulador -> PC host (NO cambiar para emulador)
    private static final String IP   = "10.0.2.2";
    private static final String BASE = "http://" + IP + "/agendaUMB/";

    // Endpoints PHP
    public static final String URL_AGREGAR   = BASE + "agregar.php";
    public static final String URL_CONSULTAR = BASE + "consultar.php";
}
