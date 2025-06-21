package server;

import com.mysql.cj.util.StringUtils;

import java.util.List;
import java.util.Random;

public class Method {
    private static Random random = new Random();

    public static List<BeanCURP> mostrarRegistro(String CURP){
        return (List<BeanCURP>) DaoCURP.findPerson(CURP);
    }

    public static Object savePerson(BeanCURP person){
        String message;
        if (DaoCURP.ingresar(person)) {
            message = "Persona Registrada Correctamente";
        }else {
            message = "Error al Registrar la persona";
        }
        return message;
    }


    public static final String[] MESES =
            {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dec"};

    public static final String[] MESES_VALOR = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public static String[] ENTIDAD_FEDERATIVA = {"AGUASCALIENTES",
            "BAJA CALIFORNIA NTE.",
            "BAJA CALIFORNIA SUR",
            "CAMPECHE",
            "COAHUILA",
            "COLIMA",
            "CHIAPAS",
            "CHIHUAHUA",
            "DISTRITO FEDERAL",
            "DURANGO",
            "GUANAJUATO",
            "GUERRERO",
            "HIDALGO",
            "JALISCO",
            "MEXICO",
            "MICHOACAN",
            "MORELOS",
            "NAYARIT",
            "NUEVO LEON",
            "OAXACA",
            "PUEBLA",
            "QUERETARO",
            "QUINTANA ROO",
            "SAN LUIS POTOSI",
            "SINALOA",
            "SONORA",
            "TABASCO",
            "TAMAULIPAS",
            "TLAXCALA",
            "VERACRUZ",
            "YUCATAN",
            "ZACATECAS",
            "SERV. EXTERIOR MEXICANO",
            "NACIDO EN EL EXTRANJERO"};

    public static String[] ENTIDAD_FEDERATIVA_VALOR = {"AS",
            "BC",
            "BS",
            "CC",
            "CL",
            "CM",
            "CS",
            "CH",
            "DF",
            "DG",
            "GT",
            "GR",
            "HG",
            "JC",
            "MC",
            "MN",
            "MS",
            "NT",
            "NL",
            "OC",
            "PL",
            "QT",
            "QR",
            "SP",
            "SL",
            "SR",
            "TC",
            "TS",
            "TL",
            "VZ",
            "YN",
            "ZS",
            "SM",
            "NE"};


    private static char[] VOCALS = {'A', 'E', 'I', 'O', 'U'};



// -------------------------- STATIC METHODS --------------------------

    public static String generar(String nombre, String apellido_Pat, String apellido_Mat, String sexo, String estado_Nac, String fecha_Nac) throws Exception {
        // limpiar los datos
        apellido_Pat = trim(apellido_Pat);
        apellido_Mat = trim(apellido_Mat);
        nombre = trim(nombre);
        sexo = trim(sexo);
        estado_Nac = trim(estado_Nac);

        // validar que los datos estan correctors
        String error = validarDatos(apellido_Pat, apellido_Mat, nombre, sexo, fecha_Nac, estado_Nac);
        if (error != null) {
            throw new Exception(error);
        }

        // generar Curp
        StringBuilder curp = new StringBuilder();
        curp.append(apellido_Pat.charAt(0));
        curp.append(primeraVocal(apellido_Pat.substring(1)));
        curp.append(apellido_Mat.charAt(0));
        curp.append(nombre.charAt(0));
        curp.append(fecha_Nac.substring(8,10));
        curp.append(fecha_Nac.substring(0,2));
        curp.append(fecha_Nac.substring( 3, 5 ) );
        curp.append(sexo);
        curp.append(estado_Nac);
        curp.append(primeraLetra(apellido_Pat.substring(1)));
        curp.append(primeraLetra(apellido_Mat.substring(1)));
        curp.append(primeraLetra( nombre.substring( 1 ) ) );
        curp.append(0);
        curp.append(random.nextInt(10));
        return curp.toString();
    }

    public static String trim( String s ) {
        return StringUtils.safeTrim(s).toUpperCase();
    }

    private static char primeraLetra( String s ) {
        int i = StringUtils.indexOfIgnoreCase( s, String.valueOf(VOCALS));
        if ( i >= 0 )
        {
            return s.charAt( i );
        }
        return 'X';
    }

    private static char primeraVocal(String s) {
        int i = StringUtils.indexOfIgnoreCase(s, String.valueOf(VOCALS));
        if ( i >= 0 )
        {
            return s.charAt( i );
        }
        return 'X';
    }

    private static String validarDatos(String primerApellido, String segundoApellido, String nombres, String sexo, String fechaDeNacimiento, String entidadDeNacimiento ) {


        return null;
    }
}
