public class Login {
    public String validarLogin(String usuario, String pass){
        String letrero = null;
        String U = "admin";
        String U1 = "alumno";
        String P = "admin123";
        String P1 = "alumno123";
        if ((usuario.equals(U) && pass.equals(P)) || (usuario.equals(U1) && pass.equals(P1))) {
            letrero = "Usuario Correcto!";
        } else {
            letrero = "Error! No coinciden!";
        }
        return letrero;
    }
}
