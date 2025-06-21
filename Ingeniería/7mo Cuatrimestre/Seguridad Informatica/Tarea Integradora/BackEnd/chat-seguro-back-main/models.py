from pydantic import BaseModel

# Definición de la clase Token para representar un token de acceso
class Token(BaseModel):
    access_token: str
    token_type: str

# Definición de la clase TokenData para representar los datos de un token
class TokenData(BaseModel):
    username: str | None = None

# Definición de la clase User para representar un usuario
class User(BaseModel):
    username: str
    email: str | None = None
    full_name: str | None = None
    disabled: bool | None = None
    hashed_password: str | None = None

# Definición de la clase Login para representar los datos de inicio de sesión
class Login(BaseModel):
    username: str
    password: str

# Definición de la clase Chat para representar un chat
class Chat(BaseModel):
    usernameFrom: str | None = None
    usernameTo: str | None = None
    key: int | None = None

# Definición de la clase ChatMessage para representar un mensaje de chat
class ChatMessage(BaseModel):
    username: str | None = None
    message: str
    timestamp: str | None = None

# Definición de la clase UserInDB que hereda de User y agrega el campo hashed_password
class UserInDB(User):
    hashed_password: str