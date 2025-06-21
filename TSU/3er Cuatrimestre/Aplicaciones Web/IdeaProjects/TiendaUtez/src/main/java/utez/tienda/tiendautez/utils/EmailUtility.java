package utez.tienda.tiendautez.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailUtility {
    ResourceBundle authenticationProperties;
    String email;
    String password;
    String host;
    String port;
    String enable;
    String auth;

    public boolean sendEmail(String newUser,String name){
        if (authenticationProperties==null){
            authenticationProperties= ResourceBundle.getBundle("mail_props");
            email= authenticationProperties.getString("email");
            password= authenticationProperties.getString("password");
            host= authenticationProperties.getString("host");
            port= authenticationProperties.getString("port");
            enable= authenticationProperties.getString("enable");
            auth= authenticationProperties.getString("auth");

            Properties props = new Properties();
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtp.port", port);
                    props.put("mail.smtp.auth", auth);
                    props.put("mail.smtp.starttls.enable", enable);
                    props.put("mail.smtp.ssl.protocols", "TLSv1.2");


            try{
                Session session = Session.getInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(email, password);
                            }
                        });
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email,
                        "Sistema UTEX"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(newUser));

                message.setSubject("Registro de cuenta");
                message.setContent("<strong>" +
                        "Hola gracias por registrarte como administrador" +
                        " a la Tienda en linea Utez"+
                        " con el correo " + newUser + "<br><br>" +
                        " tu contraseña por defecto es '1234' te recomendamos cambiarla " +
                        " a la brevedad " +
                        " att: Sistema Tienda Utez <br>" +
                        "</strong>", "TEXT/HTML");
                Transport.send(message);


            }catch (AddressException ae){
                System.out.println("Error AddressException "+ae);
            }catch (MessagingException me){
                System.out.println("Error MessagingException "+me);
            }catch (UnsupportedEncodingException uee){
                System.out.println("Error UnsupportedEncodingException "+uee);
            }

        }


        return false;
    }

    public boolean sendEmail2(String newUser,String name){
        boolean result = false;
        if (authenticationProperties==null){
            authenticationProperties= ResourceBundle.getBundle("mail_props");
            email= authenticationProperties.getString("email");
            password= authenticationProperties.getString("password");
            host= authenticationProperties.getString("host");
            port= authenticationProperties.getString("port");
            enable= authenticationProperties.getString("enable");
            auth= authenticationProperties.getString("auth");

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", auth);
            props.put("mail.smtp.starttls.enable", enable);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");


            try{
                Session session = Session.getInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(email, password);
                            }
                        });
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email,
                        "Sistema Tienda Utez"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(newUser));


                message.setSubject("Reinicio de cuenta");
                message.setContent("<strong>" +
                        "Hola root el admin "+ name + " del email "+ newUser + ":   <br><br>" +
                        "olvido su contraseña <br>" +
                        " ¿podria reinciarla? <br>" +
                        " att: Sistema Tienda Utez <br>" +
                        "</strong>", "TEXT/HTML");
                Transport.send(message);
                result = true;

            }catch (AddressException ae){
                System.out.println("Error AddressException "+ae);
            }catch (MessagingException me){
                System.out.println("Error MessagingException "+me);
            }catch (UnsupportedEncodingException uee){
                System.out.println("Error UnsupportedEncodingException "+uee);
            }

        }


        return result;
    }
}

