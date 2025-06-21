package mx.edu.utez.MiProyectoServlet.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailUtility {
    ResourceBundle authenticationPropieties;
    String email;
    String password;
    String host;
    String port;
    String enable;
    String auth;

    public boolean sendEmail(String newUser, String name, String lastname){
        if (authenticationPropieties == null){
            authenticationPropieties = ResourceBundle.getBundle("mail_props");
            email = authenticationPropieties.getString("email");
            password =authenticationPropieties.getString("password");
            host = authenticationPropieties.getString("host");
            port =authenticationPropieties.getString("port");
            enable = authenticationPropieties.getString("enable");
            auth =authenticationPropieties.getString("auth");
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.startfils.enable", enable);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        try {
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(email,password);
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email,
                    "Sistema UTEX 3A"));
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(newUser));
            message.setSubject("Registro de Cuenta");
            message.setContent("<strong>" +
                    "Hola" + name + " " + lastname + ": <br><br>" +
                    "Tu cuenta ha sido registrada correctamente<br>"+
                    "Favor de No contestar este correo"+
                    "</strong>","text/html");
            Transport.send(message);
        }catch (AddressException ae){
            System.out.println("Error AddressException-> "+ae);
        }catch (MessagingException me){
            System.out.println("Error MessagingException-> "+me);
        }catch (UnsupportedEncodingException uee){
            System.out.println("Error UnsupportedEncodingException-> "+uee);
        }
        return false;
    }
}
