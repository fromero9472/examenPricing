# PricingService

## Descripción del Proyecto:

PricingService es una aplicación que proporciona servicios relacionados con la gestión de precios. Permite autenticar usuarios y generar tokens JWT, así como obtener listas de precios en función de parámetros como la marca, el producto y la fecha de aplicación.

## Capas de la Aplicación:

### 1. Controlador (com.example.controller)

#### AuthController
- **Propósito:** Gestiona la autenticación de usuarios y la generación de tokens JWT.
- **Ruta:** /generate-token
- **Métodos:**
   - `generateToken()`: Autentica al usuario y genera un token JWT válido por 24 horas.

#### PriceController
- **Propósito:** Maneja las solicitudes relacionadas con la obtención de listas de precios.
- **Ruta:** /getPriceList
- **Métodos:**
   - `getPriceList(Map<String, Object> params)`: Obtiene una lista de precios según los parámetros proporcionados en el cuerpo de la solicitud.

### 2. Modelo (com.example.model)

#### Price
- **Propósito:** Representa la entidad de precio con información como la marca, fechas de inicio y fin, lista de precios, producto, prioridad, precio y moneda.
- **Constructor:**
   - `Price(Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long priceList, Long productId, Integer priority, Double price, String curr)`: Constructor para crear instancias de precios.
- **Notas:** Anotado con JPA para la persistencia en la base de datos.

#### User
- **Propósito:** Representa la entidad de usuario con campos de nombre de usuario y contraseña.
- **Constructor:**
   - `User(String username, String password)`: Constructor para crear instancias de usuarios.

### 3. Repositorio (com.example.repository)

#### PriceRepository
- **Propósito:** Interactúa con la base de datos para realizar operaciones relacionadas con la entidad de precio.
- **Métodos:**
   - `findByBrandIdAndProductIdAndStartDate(Long brandId, Long productId, LocalDateTime startDate)`: Busca precios en función de la marca, el producto y la fecha de inicio.

### 4. Servicio (com.example.service)

#### PriceService
- **Propósito:** Proporciona lógica de negocio para la gestión de precios.
- **Métodos:**
   - `getPriceList(Long brandId, Long productId, LocalDateTime applicationDate)`: Obtiene una lista de precios según la marca, el producto y la fecha de aplicación.

#### UserService
- **Propósito:** Simula la gestión de usuarios y sus credenciales.
- **Métodos:**
   - `findByUsername(String username)`: Busca un usuario en función del nombre de usuario.

### 5. Configuración (com.example.config)

#### DataLoadConfig
- **Propósito:** Carga datos de ejemplo en la base de datos al iniciar la aplicación.

#### JwtAuthenticationFilter
- **Propósito:** Filtra las solicitudes para autenticar usuarios mediante tokens JWT.

#### SecurityConfig
- **Propósito:** Configura la seguridad de la aplicación, permitiendo el acceso a ciertos endpoints sin autenticación.

### 6. Archivo de Propiedades (application.properties)

- **Configuración del servidor, base de datos H2, Hibernate y JWT.**

### 7. Pruebas Unitarias (test/service/PriceServiceTest)

- **Pruebas del servicio PriceService utilizando Mockito para simular el comportamiento del repositorio.**
- **Configuración Adicional:**
   - La aplicación utiliza una base de datos H2 en memoria.
   - Las credenciales de usuario para la autenticación son definidas en UserService.

### Ejecución de Pruebas Unitarias:

Se han realizado pruebas unitarias para validar el comportamiento del servicio PriceService.

### Notas Importantes:

- Asegúrese de proporcionar las credenciales correctas al realizar solicitudes a /generate-token.

### Configuración:

- Puerto del servidor: 8080
- Contexto de servlet: /api
- Nombre de la aplicación: PricingService

### SonarCloud URL
 -https://sonarcloud.io/project/overview?id=fromero9472_examenPricing
