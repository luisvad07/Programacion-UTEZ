package com.imagen_social.mac_morelos_api.config;

import com.imagen_social.mac_morelos_api.enums.allowedPlatforms.AllowedPlatforms;
import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.roles.Role;
import com.imagen_social.mac_morelos_api.models.roles.RoleRepository;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.models.categories.Category; // Import Category
import com.imagen_social.mac_morelos_api.models.categories.CategoryRepository; // Import CategoryRepository

import jakarta.annotation.PostConstruct;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap; // For categories map
import java.util.Map; // For categories map
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

@Configuration
public class AppConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository; // Inject CategoryRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional // Add Transactional to ensure all operations succeed or fail together
    public void init() {
        // 1. Initialize Roles
        initializeRoles();

        // 2. Initialize Default Users (if none exist)
        initializeDefaultUsers();

        // 3. Initialize Default Categories
        initializeDefaultCategories();
    }

    private void initializeRoles() {
        System.out.println("--- Inicializando Roles ---");
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepository.existsByName(roleEnum)) {
                String description = "";
                AllowedPlatforms allowedPlatforms;

                switch (roleEnum) {
                    case CIUDADANO:
                        description = "Usuario estándar que accede únicamente a la aplicación móvil.";
                        allowedPlatforms = AllowedPlatforms.MOBILE;
                        break;
                    case PROMOTOR:
                        description = "Usuario que gestiona promociones y eventos en la plataforma web.";
                        allowedPlatforms = AllowedPlatforms.WEB;
                        break;
                    case ADMINISTRADOR:
                        description = "Usuario con acceso total a la plataforma web para administrar el sistema.";
                        allowedPlatforms = AllowedPlatforms.WEB;
                        break;
                    case SUPERVISOR:
                        description = "Usuario encargado de supervisar actividades en la plataforma web.";
                        allowedPlatforms = AllowedPlatforms.WEB;
                        break;
                    case PERIODISTA:
                        description = "Usuario encargado de gestionar noticias en la plataforma.";
                        allowedPlatforms = AllowedPlatforms.WEB;
                        break;
                    default:
                        System.err.println("Advertencia: Rol no reconocido en el switch: " + roleEnum.name() + ". Usando valores predeterminados.");
                        description = "Rol no especificado.";
                        allowedPlatforms = AllowedPlatforms.WEB; // O un valor predeterminado seguro
                }

                Role role = Role.builder()
                        .name(roleEnum)
                        .description(description)
                        .allowedPlatforms(allowedPlatforms)
                        .build();

                roleRepository.save(role);
                System.out.println("Rol creado: " + roleEnum.name());
            } else {
                System.out.println("El rol ya existe: " + roleEnum.name());
            }
        }
        System.out.println("--- Inicialización de rol completada ---");
    }

    // Helper to create Timestamp from year, month, day
    private Timestamp createBirthdateTimestamp(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0); // Month is 0-based
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    private void initializeDefaultUsers() {
        System.out.println("--- Initializing Default Users ---");
        if (userRepository.count() == 0) {
            System.out.println("No users found in the database. Creating default users...");

            // --- Admin User ---
            try {
                Role adminRole = roleRepository.findByName(RoleEnum.ADMINISTRADOR)
                        .orElseThrow(() -> new RuntimeException("Rol ADMINISTRADOR no encontrado. Asegúrate de que exista en RoleEnum y se haya inicializado."));

                Address adminAddress = createAddress("Leona Vicario", "2", "A", "Antonio Barona", "62290", "Cuernavaca", "Morelos", "18.948976", "-99.200091");

                User adminUser = User.builder()
                        .firstName("Luis")
                        .lastName("Valladolid")
                        .email("luiseduardobahenacastillo007@gmail.com")
                        .phone("7772831100")
                        .password(passwordEncoder.encode("admin123$"))
                        .rfc("BACL030601R65")
                        .curp("BACL030601HMSHSSA9")
                        .birthdate(createBirthdateTimestamp(2003, 6, 01))
                        .status(true)
                        .role(adminRole)
                        .address(adminAddress) // Asocia la dirección al usuario
                        .createdAt(Timestamp.from(Instant.now())) // Tiempo actual
                        .updatedAt(Timestamp.from(Instant.now()))
                        .build();
                userRepository.save(adminUser);
                System.out.println("Usuario ADMINISTRADOR por defecto creado con éxito.");

            } catch (RuntimeException e) {
                System.err.println("Error creando usuario ADMINISTRADOR: " + e.getMessage());
            }


            // --- Ciudadano User ---
             try {
                Role ciudadanoRole = roleRepository.findByName(RoleEnum.CIUDADANO)
                        .orElseThrow(() -> new RuntimeException("Rol CIUDADANO no encontrado."));

                Address ciudadanoAddress = createAddress("Calle Falsa", "123", null, "Centro", "62000", "Cuernavaca", "Morelos", "18.9217", "-99.2340");

                User ciudadanoUser = User.builder()
                        .firstName("Ciudadano")
                        .lastName("Ejemplo")
                        .email("ciudadano@macmorelos.com")
                        .phone("7770000002")
                        .password(passwordEncoder.encode("CiudadanoPass123$"))
                        .rfc("XAXX010101001")
                        .curp("XXXX010101HXXAXXA1")
                        .birthdate(createBirthdateTimestamp(1995, 5, 15))
                        .status(true)
                        .role(ciudadanoRole)
                        .address(ciudadanoAddress)
                        .username("ciudadano01")
                        .createdAt(Timestamp.from(Instant.now()))
                        .updatedAt(Timestamp.from(Instant.now()))
                        .build();
                userRepository.save(ciudadanoUser);
                System.out.println("Usuario CIUDADANO por defecto creado con éxito.");
            } catch (RuntimeException e) {
                System.err.println("Error creando usuario CIUDADANO: " + e.getMessage());
            }

            // --- Promotor User ---
             try {
                Role promotorRole = roleRepository.findByName(RoleEnum.PROMOTOR)
                        .orElseThrow(() -> new RuntimeException("Rol PROMOTOR no encontrado."));

                Address promotorAddress = createAddress("Avenida Principal", "456", "B", "Lomas", "62100", "Cuernavaca", "Morelos", "18.9300", "-99.2400");

                User promotorUser = User.builder()
                        .firstName("Promotor")
                        .lastName("Activo")
                        .email("promotor@macmorelos.com")
                        .phone("7770000003")
                        .password(passwordEncoder.encode("PromotorPass123$"))
                        .rfc("XAXX010101002")
                        .curp("XXXX010101MXXAXXA2")
                        .birthdate(createBirthdateTimestamp(1988, 8, 20))
                        .status(true)
                        .role(promotorRole)
                        .address(promotorAddress)
                        .createdAt(Timestamp.from(Instant.now()))
                        .updatedAt(Timestamp.from(Instant.now()))
                        .build();
                userRepository.save(promotorUser);
                System.out.println("Usuario PROMOTOR por defecto creado con éxito.");
            } catch (RuntimeException e) {
                System.err.println("Error creando usuario PROMOTOR: " + e.getMessage());
            }


            // --- Supervisor User ---
             try {
                Role supervisorRole = roleRepository.findByName(RoleEnum.SUPERVISOR)
                        .orElseThrow(() -> new RuntimeException("Rol SUPERVISOR no encontrado."));

                Address supervisorAddress = createAddress("Boulevard Juárez", "789", null, "Delicias", "62120", "Cuernavaca", "Morelos", "18.9400", "-99.2350");

                User supervisorUser = User.builder()
                        .firstName("Supervisor")
                        .lastName("General")
                        .email("supervisor@macmorelos.com")
                        .phone("7770000004")
                        .password(passwordEncoder.encode("SupervisorPass123$"))
                        .rfc("XAXX010101003")
                        .curp("XXXX010101HXXAXXA3")
                        .birthdate(createBirthdateTimestamp(1985, 11, 10))
                        .status(true)
                        .role(supervisorRole)
                        .address(supervisorAddress)
                        .createdAt(Timestamp.from(Instant.now()))
                        .updatedAt(Timestamp.from(Instant.now()))
                        .build();
                userRepository.save(supervisorUser);
                System.out.println("Usuario SUPERVISOR por defecto creado con éxito.");
            } catch (RuntimeException e) {
                System.err.println("Error creando usuario SUPERVISOR: " + e.getMessage());
            }


            // --- Periodista User ---
             try {
                Role periodistaRole = roleRepository.findByName(RoleEnum.PERIODISTA)
                        .orElseThrow(() -> new RuntimeException("Rol PERIODISTA no encontrado."));

                Address periodistaAddress = createAddress("Calle Reforma", "101", "5", "Vista Hermosa", "62290", "Cuernavaca", "Morelos", "18.9350", "-99.2250");

                User periodistaUser = User.builder()
                        .firstName("Periodista")
                        .lastName("Informado")
                        .email("periodista@macmorelos.com")
                        .phone("7770000005")
                        .password(passwordEncoder.encode("PeriodistaPass123$"))
                        .rfc("XAXX010101004")
                        .curp("XXXX010101MXXAXXA4")
                        .birthdate(createBirthdateTimestamp(1992, 3, 25))
                        .status(true)
                        .role(periodistaRole)
                        .address(periodistaAddress)
                        .createdAt(Timestamp.from(Instant.now()))
                        .updatedAt(Timestamp.from(Instant.now()))
                        .build();
                userRepository.save(periodistaUser);
                System.out.println("Usuario PERIODISTA por defecto creado con éxito.");
             } catch (RuntimeException e) {
                 System.err.println("Error creando usuario PERIODISTA: " + e.getMessage());
             }

        } else {
            System.out.println("Los usuarios ya existen en la base de datos. Se omite la creación de usuarios predeterminada.");
        }
         System.out.println("--- Inicialización de usuario predeterminado completada ---");
    }

     // Helper method to create Address objects
    private Address createAddress(String street, String number, String interiorNumber, String neighborhood, String zipCode, String city, String state, String latitude, String longitude) {
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setInteriorNumber(interiorNumber);
        address.setNeighborhood(neighborhood);
        address.setZipCode(zipCode);
        address.setCity(city);
        address.setState(state);
        address.setCountry("México"); // Assuming country is always Mexico
        try {
            address.setLatitude(new BigDecimal(latitude));
            address.setLongitude(new BigDecimal(longitude));
        } catch (NumberFormatException e) {
             System.err.println("Error al analizar la latitud y longitud de la dirección: " + street + ", " + number + ". Configuración nula. Error: " + e.getMessage());
             address.setLatitude(null);
             address.setLongitude(null);
        }
        return address;
    }


    private void initializeDefaultCategories() {
        System.out.println("--- Initializing Default Categories ---");
        // Define categories to be created {Name: Description}
        Map<String, String> categoriesToCreate = new HashMap<>();
        categoriesToCreate.put("Política", "Noticias relacionadas con el gobierno, elecciones y asuntos políticos.");
        categoriesToCreate.put("Deportes", "Información sobre eventos deportivos, equipos y atletas.");
        categoriesToCreate.put("Cultura", "Artículos sobre arte, música, cine, literatura y eventos culturales.");
        categoriesToCreate.put("Seguridad", "Noticias sobre seguridad pública, incidentes y prevención del delito.");
        categoriesToCreate.put("Tecnología", "Novedades sobre gadgets, internet, software y avances tecnológicos.");
        categoriesToCreate.put("Economía", "Información sobre finanzas, negocios, mercados y tendencias económicas.");
        categoriesToCreate.put("Local", "Noticias específicas de la región o comunidad.");
        categoriesToCreate.put("Salud", "Artículos sobre bienestar, medicina, salud pública y consejos saludables.");
        categoriesToCreate.put("Educación", "Noticias relacionadas con escuelas, universidades y políticas educativas.");

        categoriesToCreate.forEach((name, description) -> {
            if (!categoryRepository.existsByName(name)) {
                Category category = Category.builder()
                        .name(name)
                        .description(description)
                        // newsList is initialized by default in the entity's @Builder.Default
                        .build();
                categoryRepository.save(category);
                System.out.println("Categoría creada: " + name);
            } else {
                System.out.println("La categoría ya existe: " + name);
            }
        });
        System.out.println("--- Inicialización de categoría completada ---");
    }
}