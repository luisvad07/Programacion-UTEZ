package server;

import Model.BeanPerson;
import Model.DaoPerson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

public class Methods {
    String nombre; String primerApellido; String segundoApellido; String sexo; String estado; Date fechaNac;
    String curp = "";

    DaoPerson dao = new DaoPerson();
    Random random = new Random();
    public String llenar(String nombre, String primerApellido, String segundoApellido, String sexo, String estado, String fechaNac) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.sexo = sexo;
        this.estado = estado;
        this.fechaNac = Date.valueOf(fechaNac);
        curp = generarCurp(nombre,primerApellido,segundoApellido,sexo,estado,fechaNac);
        dao.saverPerson( nombre,  primerApellido,  segundoApellido,  sexo,  estado,  this.fechaNac,  curp);
        return curp;
    }

    public String generarCurp(String nombre, String primerApellido, String segundoApellido, String sexo, String estado, String fechaNac){
        curp += primerApellido.charAt(0);
        char[] ap1Aux = primerApellido.toCharArray();
        char[] ap2Aux = segundoApellido.toCharArray();
        char[] nom = nombre.toCharArray();
        for (int i = 0; i < ap1Aux.length; i++) {
            String aux = ap1Aux[i] +"";
            if (aux.equalsIgnoreCase("a") || aux.equalsIgnoreCase("e") || aux.equalsIgnoreCase("i") || aux.equalsIgnoreCase("o") || aux.equalsIgnoreCase("u")){
                curp += aux.toUpperCase();
                break;
            }
        }
        curp += segundoApellido.charAt(0);
        curp += nombre.charAt(0);
        curp += fechaNac.substring(2,4) + fechaNac.substring(5,7) + fechaNac.substring(8,10);
        curp += sexo.equals("Hombre")?"H":"M";
        curp += estado.toUpperCase();

        for (int i = 1; i < ap1Aux.length; i++) {
            String aux = ap1Aux[i] +"";
            if (!aux.equalsIgnoreCase("a") && !aux.equalsIgnoreCase("e") && !aux.equalsIgnoreCase("i") && !aux.equalsIgnoreCase("o") && !aux.equalsIgnoreCase("u")){
                curp += aux.toUpperCase();
                break;
            }
        }
        for (int i = 1; i < ap2Aux.length; i++) {
            String aux = ap2Aux[i] +"";
            if (!aux.equalsIgnoreCase("a") && !aux.equalsIgnoreCase("e") && !aux.equalsIgnoreCase("i") && !aux.equalsIgnoreCase("o") && !aux.equalsIgnoreCase("u")){
                curp += aux.toUpperCase();
                break;
            }
        }
        for (int i = 1; i < nom.length; i++) {
            String aux = nom[i] +"";
            if (!aux.equalsIgnoreCase("a") && !aux.equalsIgnoreCase("e") && !aux.equalsIgnoreCase("i") && !aux.equalsIgnoreCase("o") && !aux.equalsIgnoreCase("u")){
                curp += aux.toUpperCase();
                break;
            }
        }

        String alfa = "abcdefghijklmnopqrstuvwxyz0123456789";
        int clave1 = random.nextInt(alfa.length());
        int clave2 = random.nextInt(alfa.length());
        String clave = alfa.substring(clave1,clave1+1).toUpperCase() + alfa.substring(clave2,clave2+1).toUpperCase();
        curp += clave;
        return curp;
    }

    public String buscar(String curp){
        ArrayList<BeanPerson> aux = dao.showPerson(curp);
        String response= "";
        for (BeanPerson person: aux){
            response = "Nombre completo: " + person.getNombre() + " " + person.getPrimerApellido() + " " + person.getSegundoApellido() + "\n" +
                    "Sexo: " + person.getSexo() + "\n" +
                    "Estado: " + person.getEstado() + "\n" +
                    "fecha de nacimiento: " + person.getFechaNac() + "\n" +
                    "CURP: " + person.getCurp() + "\n";
        }
        return response;
    }
}
