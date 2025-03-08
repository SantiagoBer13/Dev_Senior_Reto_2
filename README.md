# Sistema de Gestión de Emergencias

Este sistema permite administrar y coordinar diferentes tipos de emergencias (incendios, accidentes vehiculares y robos), asignando los recursos apropiados (bomberos, ambulancias o policías) según el tipo y gravedad de cada caso.

## Características principales

- Gestión de múltiples tipos de emergencias
- Asignación inteligente de recursos basada en disponibilidad y necesidad
- Cálculo de tiempos estimados de llegada basados en ubicación
- Seguimiento del estado de las emergencias
- Informes de estado de casos activos y finalizados

## Estructura del proyecto

El sistema está diseñado siguiendo patrones de diseño orientado a objetos, incluyendo:

- Patrón Factory para la creación de emergencias y respuestas
- Patrón Singleton para las clases de gestión (Manager)
- Interfaces para implementar polimorfismo
- Herencia para especializar tipos de emergencias
- Patrón Observer para permitir la coordinación entre diferentes equipos de respuesta y asegurar que todos completen sus tareas antes de finalizar la emergencia.

### Componentes principales

#### Enumeraciones

- **TypeEmergency**: Define los tipos de emergencias (INCENDIO, ACCIDENTE_VEHICULAR, ROBO)
- **TypeRespuesta**: Define los tipos de respuesta (INCENDIO, MEDICA, SEGURIDAD)
- **Gravedad**: Define los niveles de gravedad (BAJA, MEDIA, ALTA)

#### Interfaces

- **IResponder**: Define los comportamientos comunes para todos los equipos de respuesta

#### Clases base

- **Ubication**: Maneja las coordenadas y descripciones de ubicaciones
- **Emergency**: Clase base para todos los tipos de emergencias
- **Persona**: Representa al personal que atiende emergencias
- **EmergenciaAsignacion**: Vincula emergencias con equipos de respuesta asignados

#### Clases de emergencias específicas

- **Incendio**: Especialización para emergencias de tipo incendio
- **AccidenteVehicular**: Especialización para accidentes vehiculares
- **Robo**: Especialización para emergencias de seguridad

#### Clases de respondedores

- **Bombero**: Personal especializado en atender incendios
- **Ambulancia**: Personal médico para emergencias de salud
- **Policia**: Personal de seguridad para emergencias policiales

#### Gestores (Managers)

- **ManageBomberos**: Gestiona el personal y casos de bomberos
- **ManageAmbulancias**: Gestiona el personal y casos médicos
- **ManagePolicias**: Gestiona el personal y casos policiales

#### Factories

- **EmergencyFactory**: Crea instancias específicas de emergencias
- **RespuestaFactory**: Coordina la asignación de recursos a emergencias

#### Controlador principal

- **SistemaEmergencia**: Interfaz principal del sistema que coordina todas las operaciones

## Flujo de trabajo

1. Se registra una nueva emergencia en el sistema
2. Se clasifica según su tipo, gravedad y ubicación
3. El sistema asigna automáticamente los recursos necesarios
4. Se calcula el tiempo estimado de llegada
5. Se realiza seguimiento del estado de la emergencia
6. Al finalizar la atención, se liberan los recursos asignados

## Cálculo de tiempos de llegada

El sistema calcula los tiempos de llegada utilizando la distancia Manhattan entre la ubicación de la emergencia y la ubicación de la estación de respuesta, considerando:

- Distancia entre puntos
- Velocidad promedio de los vehículos
- Conversión a unidades de tiempo apropiadas (segundos, minutos)

## Requisitos del sistema

- Java 8 o superior
- Memoria suficiente para almacenar datos de emergencias y personal

## Uso del sistema

El sistema proporciona un menú de opciones que permite:
1. Registrar nuevas emergencias
2. Consultar recursos disponibles
3. Ver estadísticas y estado de casos
4. Gestionar el personal de respuesta
