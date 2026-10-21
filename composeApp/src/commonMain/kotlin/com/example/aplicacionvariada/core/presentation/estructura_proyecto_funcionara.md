
def generate_project_md(project_name):
content = f"""
# Proyecto {project_name}

Este proyecto sigue una estructura modular y bien definida para garantizar la escalabilidad, mantenibilidad y facilidad de colaboración. A continuación se detallan las convenciones y la organización de los directorios y archivos dentro del proyecto.

## Estructura del Proyecto

La estructura de directorios debe seguir esta convención:

```
{project_name}
│
├── implementation
│ ├── analytics
│ │ └── {project_name}Analytics.kt
│ ├── data
│ │ ├── local
│ │ │ └── {project_name}LocalData.kt
│ │ ├── mapper
│ │ │ └── {project_name}Mapper.kt
│ │ ├── remote
│ │ │ └── {project_name}RemoteData.kt
│ │ └── repository
│ │ └── {project_name}Repository.kt
│ ├── domain
│ │ ├── model
│ │ │ └── {project_name}Model.kt
│ │ ├── repository
│ │ │ └── {project_name}RepositoryInterface.kt
│ │ ├── useCase
│ │ │ └── {project_name}UseCase.kt
│ │ ├── navigation
│ │ │ └── {project_name}Navigation.kt
│ │ └── performance
│ │ └── {project_name}Performance.kt
│ └── presentation
│ ├── di
│ │ └── {project_name}DI.kt
│ ├── intentModel
│ │ ├── {project_name}Effect.kt
│ │ ├── {project_name}Intent.kt
│ │ └── {project_name}State.kt
│ ├── model
│ │ └── {project_name}Mapper.kt
│ └── ui
│ ├── {project_name}ActivitiesListFragment.kt
│ ├── {project_name}ActivitiesListScreen.kt
│ ├── {project_name}FakeMapScreen.kt
│ └── {project_name}MapScheduleScreen.kt
└── viewmodel
└── {project_name}ViewModel.kt
└── navigation
└── {project_name}NavigationHandler.kt
└── wiring
└── {project_name}Wiring.kt
```

### Descripción de las Carpetas

#### 1. **`implementation/analytics`**
- Aquí van las herramientas y configuraciones relacionadas con el análisis y tracking de la aplicación.
- **Archivo**: `{project_name}Analytics.kt`

#### 2. **`implementation/data`**
- **`local/`**: Contiene las clases relacionadas con el almacenamiento local (bases de datos, SharedPreferences, etc.).
- **Archivo**: `{project_name}LocalData.kt`
- **`mapper/`**: Contiene las clases encargadas de transformar datos entre modelos de diferentes capas.
- **Archivo**: `{project_name}Mapper.kt`
- **`remote/`**: Contiene las clases encargadas de la comunicación con servidores o APIs externas.
- **Archivo**: `{project_name}RemoteData.kt`
- **`repository/`**: Define los repositorios de datos que implementan la lógica de acceso a los datos.
- **Archivo**: `{project_name}Repository.kt`

#### 3. **`implementation/domain`**
- **`model/`**: Contiene los modelos del dominio que se usan en las operaciones lógicas.
- **Archivo**: `{project_name}Model.kt`
- **`repository/`**: Contiene interfaces y clases que definen las operaciones del repositorio.
- **Archivo**: `{project_name}RepositoryInterface.kt`
- **`useCase/`**: Define los casos de uso que el sistema realiza.
- **Archivo**: `{project_name}UseCase.kt`
- **`navigation/`**: Contiene las clases relacionadas con la navegación entre pantallas.
- **Archivo**: `{project_name}Navigation.kt`
- **`performance/`**: Contiene el código relacionado con el monitoreo y optimización del rendimiento.
- **Archivo**: `{project_name}Performance.kt`

#### 4. **`implementation/presentation`**
- **`di/`**: Contiene configuraciones de dependencias.
- **Archivo**: `{project_name}DI.kt`
- **`intentModel/`**: Contiene las clases para manejar los efectos e intenciones de la interfaz.
    - **Archivos**:
        - `{project_name}Effect.kt`
        - `{project_name}Intent.kt`
        - `{project_name}State.kt`
- **`model/`**: Define los mappers y transformadores entre las capas.
- **Archivo**: `{project_name}Mapper.kt`
- **`ui/`**: Contiene las pantallas y fragmentos de la interfaz de usuario.
    - **Archivos**:
        - `{project_name}ActivitiesListFragment.kt`
        - `{project_name}ActivitiesListScreen.kt`
        - `{project_name}FakeMapScreen.kt`
        - `{project_name}MapScheduleScreen.kt`

#### 5. **`viewmodel`**
- Contiene las clases `{project_name}ViewModel` que gestionan el estado de la interfaz.
- **Archivo**: `{project_name}ViewModel.kt`

#### 6. **`navigation`**
- Contiene las clases relacionadas con la navegación entre las pantallas de la aplicación.
- **Archivo**: `{project_name}NavigationHandler.kt`

#### 7. **`wiring`**
- Define las conexiones entre los módulos y registros de dependencias.
- **Archivo**: `{project_name}Wiring.kt`

---

## Reglas Generales

1. **Uso de Kotlin**: Todo el proyecto debe ser desarrollado utilizando Kotlin. Evitar el uso de Java u otros lenguajes.
2. **Archivos `.kt`**: Todos los archivos del proyecto deben tener la extensión `.kt` para mantener la consistencia en la extensión del código.
3. **Convención de Nombres**:
    - Usar nombres claros y descriptivos para las clases, funciones y variables.
    - Los nombres de las clases deben seguir el estilo PascalCase (por ejemplo, `{project_name}ViewModel`).
    - Los nombres de las funciones deben usar camelCase (por ejemplo, `get{project_name}()`).
    - Las constantes deben ser completamente en mayúsculas con guiones bajos (por ejemplo, `MAX_RETRIES`).
4. **Modularidad**: El proyecto debe ser modular, cada módulo debe ser independiente, con claras interfaces y responsabilidades bien definidas.
5. **Documentación**: Toda clase o función debe tener comentarios explicativos, especialmente las lógicas complejas.
6. **Manejo de Dependencias**: Se debe usar un gestor de dependencias como Dagger o Hilt para la inyección de dependencias.
7. **Pruebas Unitarias**: Las pruebas unitarias deben cubrir cada caso de uso o función importante. Se debe utilizar JUnit o cualquier otro marco compatible con Kotlin.

## Conclusión

Este archivo de reglas y estructura está diseñado para garantizar que el código sea limpio, modular y fácil de mantener. Asegúrate de seguir estas convenciones en cada fase del desarrollo y organiza los archivos según lo especificado. Si tienes alguna duda, no dudes en preguntar.