package utez.edu.mx.foodster.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import utez.edu.mx.foodster.dtos.categoriaspersonal.CategoriasPersonalDto;
import utez.edu.mx.foodster.dtos.categoriasservicios.CategoriasServiciosDto;
import utez.edu.mx.foodster.dtos.personal.PersonalDto;
import utez.edu.mx.foodster.dtos.roles.RolesDto;
import utez.edu.mx.foodster.dtos.servicios.ServiciosDto;
import utez.edu.mx.foodster.dtos.usuarios.UsuariosDto;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonal;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonalRepository;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServicios;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServiciosRepository;
import utez.edu.mx.foodster.entities.personal.PersonalRepository;
import utez.edu.mx.foodster.entities.roles.Roles;
import utez.edu.mx.foodster.entities.roles.RolesRepository;
import utez.edu.mx.foodster.entities.servicios.ServiciosRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.entities.usuarios.UsuariosRepository;
import utez.edu.mx.foodster.utils.Base64DummyImages;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class AppConfig {

    private final RolesRepository rolesRepository;
    private final UsuariosRepository usuariosRepository;

    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();
    private final PersonalRepository personalRepository;

    private final CategoriasPersonalRepository categoriasPersonalRepository;

    private final CategoriasServiciosRepository categoriasServiciosRepository;

    private final ServiciosRepository serviciosRepository;

    private static final String TELEFONO = "7777909013";


    @Autowired
    public AppConfig(RolesRepository rolesRepository, UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder, PersonalRepository personalRepository, CategoriasPersonalRepository categoriasPersonalRepository, CategoriasServiciosRepository categoriasServiciosRepository, ServiciosRepository serviciosRepository) {
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
        this.personalRepository = personalRepository;
        this.categoriasPersonalRepository = categoriasPersonalRepository;
        this.categoriasServiciosRepository = categoriasServiciosRepository;
        this.serviciosRepository = serviciosRepository;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void init() {
        initRoles();
        initCategories();
        initCategoriesService();
        initUsers();
        initServicios();
    }

    private void initRoles() {
        if (rolesRepository.count() != 0) return;

        saveRole("ADMIN");
        saveRole("CLIENTE");
        saveRole("PERSONAL");
    }

    private void saveRole(String roleName) {
        RolesDto rolesDto = new RolesDto(null, roleName, new Timestamp(System.currentTimeMillis()), true);
        rolesRepository.save(rolesDto.toEntity());
    }

    private void initCategories() {
        if (categoriasPersonalRepository.count() != 0) return;

        saveCategory("Mesero");
        saveCategory("Chef");
    }

    private void initCategoriesService() {
        if (categoriasServiciosRepository.count() != 0) return;
        saveCategoryService("Desayuno");
        saveCategoryService("Comida");
        saveCategoryService("Cena");
        saveCategoryService("Postre");
        saveCategoryService("Botana");
        saveCategoryService("Cerveza");
        saveCategoryService("Vino");
        saveCategoryService("Té");
        saveCategoryService("Refresco");
        saveCategoryService("Agua");
        saveCategoryService("Jugo");
        saveCategoryService("Licor");
        saveCategoryService("Cóctel");
        saveCategoryService("Cubiertos");
        saveCategoryService("Platos");
        saveCategoryService("Vasos");
        saveCategoryService("Servilletas");
        saveCategoryService("Manteles");
        saveCategoryService("Mesas");
        saveCategoryService("Sillas");
        saveCategoryService("Mantelería");
        saveCategoryService("Cristalería");
        saveCategoryService("Cubertería");
        saveCategoryService("Vajilla");
        saveCategoryService("Tarimas");
        saveCategoryService("Pistas");
    }

    private void saveCategoryService(String categoryName) {
        categoriasServiciosRepository.save(new CategoriasServiciosDto(null, categoryName, new Timestamp(System.currentTimeMillis()), true).toEntity());
    }

    private void saveCategory(String categoryName) {
        categoriasPersonalRepository.save(new CategoriasPersonalDto(null, categoryName, new Timestamp(System.currentTimeMillis()), true).toEntity());
    }

    private void initUsers() {
        if (usuariosRepository.count() != 0) return;

        rolesRepository.findAllByActiveOrderByUltimaModificacionDesc(true).forEach(this::processRole);
    }

    private void processRole(Roles roles) {
        String roleName = roles.getNombre();
        switch (roleName) {
            case "ADMIN" ->
                    saveUser(roles, "Cristian", "Jimenez", "Rodriguez", TELEFONO, "redalphasiete@gmail.com", "admin");
            case "CLIENTE" ->
                    saveUser(roles, "Juan", "Camaney", "Ramirez", TELEFONO, "juancamaney@yopmail.com", "cliente");
            case "PERSONAL" -> createPersonalUsers(roles);
            default -> throw new IllegalStateException("Unexpected value: " + roleName);
        }
    }

    private void saveUser(Roles roles, String name, String lastName1, String lastName2, String phone, String email, String password) {
        Set<Roles> rolesUser = new HashSet<>();
        rolesUser.add(roles);
        UsuariosDto usuariosDto = new UsuariosDto(null, name, lastName1, lastName2, phone, email, password, new Timestamp(System.currentTimeMillis()), true, rolesUser);
        Usuarios usuarios = usuariosDto.toEntity();
        usuarios.setContrasena(passwordEncoder.encode(usuarios.getContrasena()));
        usuariosRepository.save(usuarios);
    }

    private void createPersonalUsers(Roles roles) {
        String[] nombres = {"Juan", "Pedro", "Maria", "Jose", "Luis", "Ana", "Rosa", "Carlos", "Jorge", "Fernando", "Ricardo", "Roberto"};
        String[] apellidos = {"Rodriguez", "Juarez", "Jimenez", "Gonzalez", "Perez", "Lopez", "Garcia", "Hernandez", "Martinez", "Torres", "Sanchez", "Ramirez"};

        for (int i = 0; i < 100; i++) {
            String name = nombres[random.nextInt(nombres.length)];
            String lastName1 = apellidos[random.nextInt(apellidos.length)];
            String lastName2 = apellidos[random.nextInt(apellidos.length)];
            String email = "usuario" + i + "@yopmail.com";
            saveUser(roles, name, lastName1, lastName2, TELEFONO, email, "personal");

            String categoryName = i <= 50 ? "Chef" : "Mesero";
            CategoriasPersonal categoriasPersonal = categoriasPersonalRepository.findByNombreAndActive(categoryName, true);
            PersonalDto personalDto = new PersonalDto(null, usuariosRepository.findByCorreoAndActive(email, true), categoriasPersonal, new Timestamp(System.currentTimeMillis()), true);
            personalRepository.save(personalDto.toEntity());
        }
    }

    private void initServicios() {
        if (serviciosRepository.count() != 0) return;
        saveService("Desayuno", "Café", "Café americano", "Café con leche", "Café cortado", "Café expreso", "Café irlandés", "Café moca", "Café turco", "Café vienés", "Té", "Té chai", "Té de jazmín", "Té helado");
        saveService("Comida", "Hamburguesa", "Pizza", "Tacos", "Tortas", "Hot dogs", "Papas fritas", "Ensaladas", "Sopas", "Pasta", "Pollo", "Pescado", "Carne", "Mariscos", "Vegetariano", "Vegano");
        saveService("Cena", "Hamburguesa", "Pizza", "Tacos", "Tortas", "Hot dogs", "Ensaladas", "Sopas", "Pasta", "Pollo", "Pescado", "Carne", "Mariscos", "Vegetariano", "Vegano");
        saveService("Postre", "Pastel", "Gelatina", "Flan", "Helado", "Galletas", "Cupcakes", "Brownies", "Cheesecake", "Tiramisú", "Mousse", "Churros", "Crepa", "Waffles", "Donas", "Chocolates");
        saveService("Botana", "Papas fritas", "Palomitas", "Nueces", "Pistaches", "Almendras", "Cacahuates", "Chicharrones", "Tostadas", "Doritos", "Cacahuates japoneses", "Pepinos", "Zanahorias", "Jícama", "Pepinillos", "Chile", "Frutas", "Verduras", "Queso", "Jamón", "Salchichas", "Salami", "Chorizo", "Pepinillos", "Aceitunas", "Chiles en vinagre", "Salsas", "Guacamole", "Hummus", "Tzatziki", "Taramosalata", "Baba ganush", "Tabule", "Falafel", "Kibbeh", "Dolma");
        saveService("Cerveza", "Clara", "Oscura", "Roja", "Rubia", "Negra", "Ambar", "Pilsner", "Lager", "Ale", "Stout", "Porter", "Weizen", "Trigo", "IPA", "APA", "Doble", "Triple", "Cuádruple", "Quíntuple", "Sextuple", "Septuple", "Octuple", "Novena", "Décima", "Onceava", "Doceava", "Treceava", "Catorceava", "Quinceava", "Dieciséisava", "Diecisieteava", "Dieciochoava", "Diecinueveava", "Veinteava");
        saveService("Vino",


                "Tinto", "Blanco", "Rosado", "Espumoso", "Dulce", "Seco", "Semi-seco", "Semi-dulce", "Crianza", "Reserva", "Gran reserva", "Joven", "Roble", "Barrica", "Bodega", "Cava", "Champagne", "Prosecco", "Asti", "Lambrusco", "Moscato", "Riesling", "Chardonnay", "Sauvignon blanc", "Merlot", "Cabernet sauvignon", "Malbec", "Syrah", "Garnacha", "Tempranillo", "Pinot noir", "Zinfandel", "Petit verdot", "Marsanne", "Viognier", "Gewürztraminer", "Chenin blanc", "Pinot gris", "Albariño", "Verdejo", "Godello", "Palomino", "Pedro ximénez", "Sherry", "Jerez", "Oporto", "Madeira", "Marsala", "Vermut", "Vermouth", "Campari", "Aperol", "Cinzano", "Martini", "Punt e mes", "Carpano", "Cocchi", "Lillet", "Dubonnet", "Noilly prat", "Byrrh", "St. Raphael", "Suze", "Salers", "Génépi", "Chartreuse", "Bénédictine", "Grand marnier", "Cointreau", "Triple sec", "Curazao", "Blue curaz");
        saveService("Té", "Chai", "Earl grey", "Matcha", "Oolong", "Pu-erh", "Rojo", "Verde", "Blanco", "Amarillo");
        saveService("Refresco", "Coca cola", "Pepsi", "Sprite", "Fanta", "7up", "Mirinda", "Manzanita", "Squirt", "Jarritos", "Boing", "Peñafiel", "Ciel", "Bonafont", "Epura", "Electropura", "Nestlé", "Cristal", "Cifrut", "Del Valle", "Jumex", "Lala", "Santa Clara", "Alpura", "Sello Rojo", "La lechera", "Nestea", "Lipton", "Tang", "Clamato", "Sangría", "Sidral", "Tehuacán", "Topo Chico", "Villavicencio");
        saveService("Agua", "Natural", "Mineral", "De coco", "De frutas", "De sabores", "De manantial", "De pozo", "De lluvia", "De río", "De mar", "De lago", "De arroyo", "De poza", "De estanque", "De charco", "De laguna", "De cenote", "De ojo de agua", "De nacimiento", "De vertiente", "De acuífero", "De glaciar");
        saveService("Jugo", "Naranja", "Manzana", "Piña", "Toronja", "Mandarina", "Limonada", "Lima", "Uva", "Fresa", "Frambuesa", "Zarzamora", "Mora", "Arándano", "Cereza", "Guayaba", "Mango", "Papaya", "Sandía", "Melón", "Plátano", "Kiwi", "Pera", "Durazno", "Ciruela", "Higo", "Tamarindo", "Mamey", "Zapote", "Chicozapote", "Níspero", "Nectarina", "Albaricoque", "Coco", "Pomelo", "Granada", "Pitahaya", "Tuna", "Tamarindo", "Jamaica", "Té");
    }

    private void saveService(String categoryName, String... services) {
        CategoriasServicios categoriasServicios = categoriasServiciosRepository.findByNombreAndActive(categoryName, true);
        for (String service : services) {
            ServiciosDto serviciosDto = new ServiciosDto();
            serviciosDto.setNombre(service);
            serviciosDto.setDescripcion("Descripción de " + service);
            serviciosDto.setPrecio(random.nextDouble() * 100);
            serviciosDto.setPrecioDescuento(0.0);
            serviciosDto.setImagen(Base64DummyImages.PLACEHOLDER);
            serviciosDto.setExistencias((long) random.nextInt(100));
            serviciosDto.setCategoria(categoriasServicios);
            serviciosDto.setUltimaModificacion(new Timestamp(System.currentTimeMillis()));
            serviciosDto.setActive(true);
            serviciosRepository.save(serviciosDto.toEntity());
        }
    }


}
