
# Proyecto MapScheduleCompose

Este proyecto sigue una estructura modular y bien definida para garantizar la escalabilidad, mantenibilidad y facilidad de colaboración. A continuación se detallan las convenciones y la organización de los directorios y archivos dentro del proyecto.

## Estructura del Proyecto

La estructura de directorios debe seguir esta convención:

```
mapschedulecompose
│
├── implementation
│   ├── analytics
│   ├── data
│   │   ├── local
│   │   ├── mapper
│   │   ├── remote
│   │   └── repository
│   ├── domain
│   │   ├── model
│   │   ├── repository
│   │   ├── useCase
│   │   ├── navigation
│   │   └── performance
│   └── presentation
│       ├── di
│       ├── intentModel
│       │   ├── MapScheduleEffect
│       │   ├── MapScheduleIntent
│       │   └── MapScheduleState.kt
│       ├── model
│       │   └── mapper.kt
│       └── ui
│           ├── ActivitiesListFragment
│           ├── ActivitiesListScreen.kt
│           ├── FakeMapScreen.kt
│           └── MapScheduleScreen.kt
└── viewmodel
    └── MapScheduleViewModel
└── navigation
└── wiring
```

### Descripción de las Carpetas

#### 1. **`implementation/analytics`**
   - Aquí van las herramientas y configuraciones relacionadas con el análisis y tracking de la aplicación.
   - Reglas:
     - Todo lo relacionado con la recolección de datos de uso de la aplicación debe ubicarse aquí.

#### 2. **`implementation/data`**
   - **`local/`**: Contiene las clases relacionadas con el almacenamiento local (bases de datos, SharedPreferences, etc.).
   - **`mapper/`**: Contiene las clases encargadas de transformar datos entre modelos de diferentes capas (por ejemplo, entre la capa de datos y la capa de dominio).
   - **`remote/`**: Contiene las clases encargadas de la comunicación con servidores o APIs externas.
   - **`repository/`**: Define los repositorios de datos que implementan la lógica de acceso a los datos, ya sea local o remoto.

#### 3. **`implementation/domain`**
   - **`model/`**: Contiene los modelos del dominio que se usan en las operaciones lógicas.
   - **`repository/`**: Contiene interfaces y clases que definen las operaciones del repositorio en el dominio.
   - **`useCase/`**: Define los casos de uso, que son acciones o procesos lógicos que el sistema realiza.
   - **`navigation/`**: Contiene las clases o módulos relacionados con la navegación entre pantallas o componentes dentro de la aplicación.
   - **`performance/`**: Contiene el código relacionado con el monitoreo y optimización del rendimiento de la aplicación.

#### 4. **`implementation/presentation`**
   - **`di/`**: Contiene configuraciones de dependencias (inyección de dependencias).
   - **`intentModel/`**: Contiene las clases `MapScheduleEffect`, `MapScheduleIntent` y `MapScheduleState.kt` para el manejo de los efectos e intenciones de la interfaz.
   - **`model/`**: Define los mappers y transformadores entre las distintas capas.
   - **`ui/`**: Contiene las pantallas y los fragmentos de la interfaz de usuario (por ejemplo, `ActivitiesListScreen`, `MapScheduleScreen`).

#### 5. **`viewmodel`**
   - Contiene las clases `MapScheduleViewModel` que gestionan el estado de la interfaz y se comunican con la capa de dominio.

#### 6. **`navigation`**
   - Contiene las clases relacionadas con la navegación entre las pantallas de la aplicación. Debería ser lo más sencilla posible y contener solo las rutas y las lógicas de transición.

#### 7. **`wiring`**
   - Define las conexiones entre los módulos, configuraciones iniciales y registros de dependencias. Aquí se debe gestionar todo lo relacionado con la creación de instancias y el ensamblaje de los componentes del proyecto.

---

## Reglas Generales

1. **Uso de Kotlin**: Todo el proyecto debe ser desarrollado utilizando Kotlin. Evitar el uso de Java u otros lenguajes.
2. **Archivos `.kt`**: Todos los archivos del proyecto deben tener la extensión `.kt` para mantener la consistencia en la extensión del código.
3. **Convención de Nombres**:
   - Usar nombres claros y descriptivos para las clases, funciones y variables.
   - Los nombres de las clases deben seguir el estilo PascalCase (por ejemplo, `MapScheduleViewModel`).
   - Los nombres de las funciones deben usar camelCase (por ejemplo, `getMapSchedule()`).
   - Las constantes deben ser completamente en mayúsculas con guiones bajos (por ejemplo, `MAX_RETRIES`).
4. **Modularidad**: El proyecto debe ser modular, cada módulo debe ser independiente, con claras interfaces y responsabilidades bien definidas.
5. **Documentación**: Toda clase o función debe tener comentarios explicativos, especialmente las lógicas complejas.
6. **Manejo de Dependencias**: Se debe usar un gestor de dependencias como Dagger o Hilt para la inyección de dependencias.
7. **Pruebas Unitarias**: Las pruebas unitarias deben cubrir cada caso de uso o función importante. Se debe utilizar JUnit o cualquier otro marco compatible con Kotlin.

## Conclusión

Este archivo de reglas y estructura está diseñado para garantizar que el código sea limpio, modular y fácil de mantener. Asegúrate de seguir estas convenciones en cada fase del desarrollo y organiza los archivos según lo especificado. Si tienes alguna duda, no dudes en preguntar.
