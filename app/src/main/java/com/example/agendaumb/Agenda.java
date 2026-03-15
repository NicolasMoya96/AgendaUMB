package com.example.agendaumb;

/**
 * Clase modelo — Agenda
 * Corresponde al diagrama UML del profesor:
 *   - id_agenda: int
 *   - fecha:     char  (String en Java)
 *   - asunto:    char
 *   - actividad: char
 *   + agregar():   void
 *   + consultar(): void
 * Se agregan campos extra para que la app se vea mas avanzada.
 */
public class Agenda {

    // Atributos del UML
    private int    id_agenda;
    private String fecha;
    private String asunto;
    private String actividad;

    // Atributos extras
    private String categoria;
    private String prioridad;
    private int    activo;

    public Agenda() {}

    // Constructor para nuevo evento (sin id)
    public Agenda(String fecha, String asunto, String actividad,
                  String categoria, String prioridad, int activo) {
        this.fecha      = fecha;
        this.asunto     = asunto;
        this.actividad  = actividad;
        this.categoria  = categoria;
        this.prioridad  = prioridad;
        this.activo     = activo;
    }

    // Constructor completo (al leer de MySQL)
    public Agenda(int id_agenda, String fecha, String asunto,
                  String actividad, String categoria,
                  String prioridad, int activo) {
        this.id_agenda  = id_agenda;
        this.fecha      = fecha;
        this.asunto     = asunto;
        this.actividad  = actividad;
        this.categoria  = categoria;
        this.prioridad  = prioridad;
        this.activo     = activo;
    }

    // Getters
    public int    getId_agenda()  { return id_agenda; }
    public String getFecha()      { return fecha; }
    public String getAsunto()     { return asunto; }
    public String getActividad()  { return actividad; }
    public String getCategoria()  { return categoria; }
    public String getPrioridad()  { return prioridad; }
    public int    getActivo()     { return activo; }
    public boolean isActivo()     { return activo == 1; }

    // Setters
    public void setId_agenda(int v)    { this.id_agenda = v; }
    public void setFecha(String v)     { this.fecha = v; }
    public void setAsunto(String v)    { this.asunto = v; }
    public void setActividad(String v) { this.actividad = v; }
    public void setCategoria(String v) { this.categoria = v; }
    public void setPrioridad(String v) { this.prioridad = v; }
    public void setActivo(int v)       { this.activo = v; }
}
