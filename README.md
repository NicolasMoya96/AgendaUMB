# AgendaUMB

Aplicación móvil Android que permite agregar y consultar eventos de agenda guardados en una base de datos MySQL externa.

## Tecnologías usadas

- Android Studio / Java
- Volley (peticiones HTTP)
- XAMPP (Apache + PHP)
- MySQL Workbench

## ¿Qué hace?

- Agregar eventos con fecha, asunto, actividad, categoría y prioridad
- Consultar todos los eventos guardados en MySQL
- Filtrar eventos por categoría

## Estructura

```
AgendaUMB/
├── app/java/com.example.agendaumb/
│   ├── Constantes.java       → URL del servidor
│   ├── Agenda.java           → Modelo de datos
│   ├── MainActivity.java     → Pantalla de inicio
│   └── AgendaActivity.java   → Pantalla principal
├── res/layout/
│   ├── activity_main.xml
│   └── activity_agenda.xml
└── C:/xampp/htdocs/agendaUMB/
    ├── DatabaseConfig.php    → Conexión MySQL
    ├── agregar.php           → INSERT
    └── consultar.php         → SELECT
```

## Cómo ejecutar

1. Iniciar **Apache** en XAMPP
2. Abrir **MySQL Workbench** y verificar que el servidor está activo
3. Verificar PHP en Chrome: `http://localhost/agendaUMB/consultar.php`
4. Correr la app en el **emulador** de Android Studio

> La IP del servidor en el emulador es siempre `10.0.2.2`

## Autor

Nicolás Moya — Universidad Manuela Beltrán — 2025
