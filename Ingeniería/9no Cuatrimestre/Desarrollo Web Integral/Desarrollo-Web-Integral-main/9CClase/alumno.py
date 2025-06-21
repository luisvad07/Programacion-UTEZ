class alumno:

    def __init__(self, nombre, edad, clase):
        self.nombre = nombre
        self.edad = edad
        self.clase = clase

    def imprimir(self):
        return f"El alumno {self.nombre} tiene {self.edad} años de edad"

mialumno = alumno("Luis Valladolid", 21, "Desarrollo Web Integral")
print(mialumno.imprimir())