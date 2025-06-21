<template>
    <div class="container mt-5">
        <form @submit.prevent="submitForm" class="needs-validation" novalidate>
            <h2 class="mb-4">Formulario de Registro</h2>

            <div class="form-group">
            <div class="row">
                <div class="col">
                    <!-- Nombre -->
                    <label for="nombre">Nombre:</label>
                    <input v-model="nombre" type="text" id="nombre" class="form-control" required>
                    <div class="invalid-feedback">
                        Ingresa tu nombre.
                    </div>
                </div>
                <div class="col">
                    <!-- Apellido Paterno -->
                    <label for="apellido-paterno">Apellido Paterno:</label>
                    <input v-model="apellidoPaterno" type="text" id="apellido-paterno" class="form-control" required>
                    <div class="invalid-feedback">
                        Ingresa tu apellido paterno.
                    </div>
                </div>
                <div class="col">
                    <!-- Apellido Materno -->
                    <label for="apellido-materno">Apellido Materno</label>
                    <input v-model="apellidoMaterno" type="text" id="apellido-materno" class="form-control">
                    <div class="invalid-feedback">
                        Ingresa tu apellido materno.
                    </div>
                </div>
            </div>
            </div>
    
            <!-- Dirección -->
            <div class="form-group">
                <label>Dirección:</label>
                <div class="row">
                    <div class="col-3">
                        <input v-model="direccion.cp" type="text" placeholder="Código Postal" class="form-control" required>
                    </div>
                    <div class="col">
                        <input v-model="direccion.calle" type="text" placeholder="Calle" class="form-control" required>
                    </div>
                    <div class="col">
                        <input v-model="direccion.numero" type="text" placeholder="Número" class="form-control" required>
                    </div>
                    <div class="col">
                        <input v-model="direccion.ciudad" type="text" placeholder="Ciudad" class="form-control" required>
                    </div>
                </div>
            </div>
    
            <!-- Fecha de nacimiento -->
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input v-model="fechaNacimiento" type="date" class="form-control" required>
            </div>
    
            <!-- Correo electrónico -->
            <div class="form-group">
                <label for="email">Correo Electrónico:</label>
                <input v-model="email" type="email" class="form-control" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">
                <div class="invalid-feedback">
                    Ingresa un correo electrónico válido.
                </div>
            </div>
    
            <!-- Número telefónico -->
            <div class="form-group">
                <label for="telefono">Número Telefónico:</label>
                <input v-model="telefono" type="tel" class="form-control" required pattern="[0-9]{10}">
                <div class="invalid-feedback">
                    Ingresa un número telefónico válido.
                </div>
            </div>
            <br>
            <!-- Fotografía -->
            <div class="form-group">
                <label for="foto">Fotografía (PNG):</label>
                <input @change="handleFileChange" type="file" accept="image/png" class="form-control-file" required>
                <div class="invalid-feedback">
                    Sube una fotografía en formato PNG.
                </div>
            </div>
            <br>
            <!-- Mensaje de error -->
            <p v-if="error" class="text-danger">{{ error }}</p>
    
            <button type="submit" class="btn btn-primary mt-3">Enviar</button>     
        </form>

    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        nombre: '',
        apellidoPaterno: '',
        apellidoMaterno: '',
        direccion: {
          cp: '',
          calle: '',
          numero: '',
          ciudad: '',
        },
        fechaNacimiento: '',
        email: '',
        telefono: '',
        foto: null,
        error: '',
      };
    },
    methods: {
        submitForm() {
            // Lógica de validación
            if (!this.nombre || !this.apellidoPaterno || !this.direccion.cp || !this.direccion.calle || !this.direccion.numero || !this.direccion.ciudad || !this.fechaNacimiento || !this.email || !this.telefono || !this.foto) {
                this.error = 'Por favor, completa todos los campos del formulario.';
                return;
            }

            // Validar edad (menos de 18 años)
            const today = new Date();
            const birthDate = new Date(this.fechaNacimiento);
            const age = today.getFullYear() - birthDate.getFullYear();
            if (age < 18) {
                this.error = 'Debes ser mayor de 18 años para enviar el formulario.';
                return;
            }

            // Lógica de envío del formulario (simulada)
            // Aquí puedes enviar los datos al servidor, almacenar en una base de datos, etc.
            console.log('Formulario enviado con éxito');
            console.log('Datos:', {
                nombre: this.nombre,
                apellidoPaterno: this.apellidoPaterno,
                direccion: this.direccion,
                fechaNacimiento: this.fechaNacimiento,
                email: this.email,
                telefono: this.telefono,
                foto: this.foto.name,
            });
            this.registro = {
                nombre: this.nombre,
                direccion: `${this.direccion.calle} ${this.direccion.numero}, ${this.direccion.ciudad}, CP: ${this.direccion.cp}`,
                fechaNacimiento: this.fechaNacimiento,
                email: this.email,
                telefono: this.telefono,
                foto: this.foto ? this.foto.name : 'No adjuntada',
            };

            this.error = '';
        },
            resetForm() {
            // Reiniciar valores del formulario
                this.nombre = '';
                this.apellidoPaterno = '';
                this.apellidoMaterno = '';
                this.direccion.cp = '';
                this.direccion.calle = '';
                this.direccion.numero = '';
                this.direccion.ciudad = '';
                this.fechaNacimiento = '';
                this.email = '';
                this.telefono = '';
                this.foto = null;
                this.error = '';
            },

            handleFileChange(event) {
            // Manejar cambios en el input de archivo
                this.foto = event.target.files[0];
            },
        },
  };
  </script>

  