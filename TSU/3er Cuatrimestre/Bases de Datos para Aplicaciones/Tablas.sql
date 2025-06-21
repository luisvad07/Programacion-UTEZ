CREATE TABLE ADMINISTRADOR(
    Correo varchar (30) not null,
    Contraseña varchar (30) not null,
    CONSTRAINT pk_ADMINISTRADOR
    PRIMARY KEY (Correo),
    CONSTRAINT empleado_empleado
    FOREIGN KEY(Correo) REFERENCES EMPLEADO(Correo)  
);


CREATE TABLE PRODUCTOS(
    Numero varchar (15) not null,
    Nombre varchar (30) not null,
    Descripcion varchar (50) not null,
    Categoria varchar (30) not null,
    Color varchar (10),
    Talla char (1),
    Correo varchar (30) not null,
    CONSTRAINT pk_PRODUCTOS
    PRIMARY KEY (Numero),
    CONSTRAINT fk_administrador_producto
            FOREIGN KEY (Correo)
            REFERENCES ADMINISTRADOR(Correo),
    CONSTRAINT fk_color_productor
        FOREIGN KEY (Color)
        REFERENCES COLOR(Id_Color),
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (Categoria)
        REFERENCES CATEGORIA(Id_Categoria),
    CONSTRAINT fk_talla_producto
        FOREIGN KEY (Talla)
        REFERENCES TALLA(Talla)            
);

CREATE TABLE LISTA_INVENTARIO(
    Id int not null,
    Costo_Unidades int not null,
    Unidades_Disponibles int not null,
    Correo varchar (30) not null,
    CONSTRAINT pk_LISTA_INVENTARIO
    PRIMARY KEY (Id),
    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (Unidades_Disponibles)
        REFERENCES PRODUCTOS(Numero),
    CONSTRAINT fk_administrador_inventario
            FOREIGN KEY (Correo)
            REFERENCES ADMINISTRADOR(Correo)    
);

CREATE TABLE HISTORIAL_PRECIOS(
    Fecha_Inicio date not null,
    Hora_Inicio time not null,
    Precio int not null,
    Fecha_Fin date,
    Hora_Fin time not null,
    Correo varchar (30) not null,
    CONSTRAINT pk_HISTORIAL_PRECIOS
    PRIMARY KEY (Fecha_Inicio,Hora_Inicio),
    CONSTRAINT fk_administrador_historial
            FOREIGN KEY (Correo)
            REFERENCES ADMINISTRADOR(Correo)
);

CREATE TABLE TALLA(
    Id_Talla int not null,
    Talla char (1) not null,
    CONSTRAINT pk_TALLA
    PRIMARY KEY (Id_Talla)
);

CREATE TABLE CATEGORIA(
    Id_Categoria int not null,
    Nombre_Categoria varchar (15) not null,
    CONSTRAINT pk_CATEGORIA
    PRIMARY KEY (Id_Categoria)
);

CREATE TABLE COLOR(
    Id_Color int not null,
    Color varchar (15) not null,
    CONSTRAINT pk_COLOR
    PRIMARY KEY (Id_Color)
);