from urllib.parse import quote_plus

# Clave secreta utilizada para firmar los tokens de acceso
SECRET_KEY = "09d25e094faa6ca2556c818166b7a9563b93f7099f6f0f4caa6cf63b88e8d3e7"

# Algoritmo utilizado para firmar los tokens de acceso
ALGORITHM = "HS256"

# Duración en minutos de validez de los tokens de acceso
ACCESS_TOKEN_EXPIRE_MINUTES = 30

# Nombre de la base de datos en MongoDB
DATABASE_NAME = "securityChat" 

# Nombre de la colección en MongoDB
COLLECTION_NAME = "chats"

# URI de conexión a la base de datos MongoDB
MONGO_URI = "mongodb://{}:{}@{}:{}/admin".format(
    quote_plus("admin"),
    quote_plus("CapybaraLoco323%"),
    "129.146.111.32",
    "27017"
)