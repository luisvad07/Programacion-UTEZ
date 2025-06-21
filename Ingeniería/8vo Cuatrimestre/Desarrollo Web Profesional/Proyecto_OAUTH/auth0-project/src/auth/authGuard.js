import { getInstance } from './index';

export const authGuard = (to, from, next) => {
  const authService = getInstance();

  const fn = () => {
    // Si el usuario se encuentra autentificado, continua con la navegación
    // Lo mismo sería que si lo hacemos con el usuario de local host,
    // por lo que lo ideal sería validar en este mismo if la condición de usuario
    // aquí se valida si existe JWT de nuestro back.
    if (authService.isAuthenticated) {
      return next();
    }

    // En caso de que no se encuentre el usuario activo, lo redirigimos
    // a la vista de loging con el siguiente método.
    authService.loginWithRedirect({ appState: { targetUrl: to.fullPath } });
  };

  // se ejecuta la función en caso de que el servicio de oauth se encuentre activo.
  // en caso de que no se halle habilitado, sería conveniente avisar al usuario 
  // y continuar con otro método de autentificación.
  if (!authService.loading) {
    return fn();
  }

  // Prendemos un hilo de watch, un vez que la variable cambie llamaremos a la función
  // para que valide el usuario y continue con la navegación.
  authService.$watch('loading', loading => {
    if (loading === false) {
      return fn();
    }
  });
};