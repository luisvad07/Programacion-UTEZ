package server;

import com.mysql.cj.util.StringUtils;

import java.util.List;
import java.util.Random;

public class Method {
    private static Random r = new Random();

    public static List<BeanCURP> mostrarRegistro(String CURP){
        return (List<BeanCURP>) DaoCURP.findPerson(CURP);
    }

    public static String savePerson(BeanCURP person){
        String message;
        if (DaoCURP.ingresar(person)) {
            message = "Persona Registrada Correctamente";
        }else {
            message = "Error al Registrar la persona";
        }
        return message;
    }

    public static int generateRandomInt(int upperRange){
        return r.nextInt(upperRange);
    }

    public static String generarCurp(String apellido_Pat, String apellido_Mat, String nombre, String sexo, String fecha_Nac, String estado_Nac) {
        char ap = apellido_Pat.charAt(0);
        char ap2 = apellido_Pat.charAt(1);
        char am = apellido_Mat.charAt(0);
        char nm = nombre.charAt(0);
        char anio = fecha_Nac.charAt(8);
        char anio2 = fecha_Nac.charAt(9);
        char mes = fecha_Nac.charAt(3);
        char mes2 = fecha_Nac.charAt(4);
        char dia = fecha_Nac.charAt(0);
        char dia2 = fecha_Nac.charAt(1);
        char s = sexo.charAt(0);
        String est = null;
        switch (estado_Nac){
            case "Aguascalientes":
                est = "AS";
                break;
            case "Baja California":
                est = "BC";
                break;
            case "Baja California Sur":
                est = "BS";
                break;
            case "Campeche":
                est = "CC";
                break;
            case "Coahuila":
                est = "CL";
                break;
            case "Colima":
                est = "CM";
                break;
            case "Chiapas":
                est = "CS";
                break;
            case "Chihuahua":
                est = "CH";
                break;
            case "Distrito Federal":
                est = "DF";
                break;
            case "Durango":
                est = "DG";
                break;
            case "Guanajuato":
                est = "GT";
                break;
            case "Guerrero":
                est = "GR";
                break;
            case "Hidalgo":
                est = "HG";
                break;
            case "Jalisco":
                est = "JC";
                break;
            case "Estado de Mexico":
                est = "MC";
                break;
            case "Michoacan":
                est = "MN";
                break;
            case "Morelos":
                est = "MS";
                break;
            case "Nayarit":
                est = "NT";
                break;
            case "Nuevo Leon":
                est = "Nl";
                break;
            case "Oaxaca":
                est = "OC";
                break;
            case "Puebla":
                est = "PL";
                break;
            case "Queretaro":
                est = "QT";
                break;
            case "Quintana Roo":
                est = "QR";
                break;
            case "San Luis Potosi":
                est = "SP";
                break;
            case "Sinaloa":
                est = "SL";
                break;
            case "Sonora":
                est = "SR";
                break;
            case "Tabasco":
                est = "TC";
                break;
            case "Tamaulipas":
                est = "TS";
                break;
            case "Tlaxcala":
                est = "TL";
                break;
            case "Veracruz":
                est = "VZ";
                break;
            case "Yucatan":
                est = "YN";
                break;
            case "Zacatecas":
                est = "ZS";
                break;
            default:
                est = "NE"; //Nacido en el extranjero
                break;
        }
        char c14 = apellido_Pat.charAt(2);
        char c15 = apellido_Mat.charAt(2);
        char c16 = nombre.charAt(3);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char as = alphabet.charAt(r.nextInt(alphabet.length()));
        int num = generateRandomInt(10);
        String an= String.valueOf(num);

        String message = ap+ap2+am+nm+anio+anio2+mes+mes2+dia+dia2+s+est+c14+c15+c16+as+an;

        return message;
    }
}
