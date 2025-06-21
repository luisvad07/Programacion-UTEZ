package com.school.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.citas.models.Ventanilla.Ventanilla;
import com.school.citas.models.Ventanilla.VentanillaRepository;
import com.school.citas.utils.CustomResponse;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class VentanillaService {

    @Value("spring.mail.username")
    private String mail;
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private VentanillaRepository ventanillaRepository;

    ///Todos las ventanillas
    @Transactional(readOnly = true)
    public CustomResponse<List<Ventanilla>> getAll(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Ventanilla>> getAllActive(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Ventanilla>> getAllInactive(){
        return new CustomResponse<>(
                this.ventanillaRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Ventanilla por id
    @Transactional(readOnly = true)
    public CustomResponse<Ventanilla> getOne(Long id) {
        Optional<Ventanilla> ventOptional = this.ventanillaRepository.findById(id);
        if (ventOptional.isPresent()) {
            return new CustomResponse<>(
                    ventOptional.get(),
                    false,
                    200,
                    "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario Ventanilla no encontrado"
            );
        }
    }

    //Insertar un Ventanilla
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Ventanilla> insert(Ventanilla ventanilla) throws MessagingException{
        if (this.ventanillaRepository.existsByCorreoElectronico(ventanilla.getCorreoElectronico())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El correo de esta Ventanilla ya existe!"
            );
        }

        //envio de correos electronicos
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setTo(ventanilla.getCorreoElectronico());
        messageHelper.setFrom(mail);
        messageHelper.setSubject("Mensaje de Confirmación de Creación de Cuenta");

        // Configurar el contenido del mensaje con diseño personalizado
        String htmlContent = "<html>" +
        "<head>" +
        "<style>" +
        ".card {" +
        "width: 500px;" +
        "height: 500px;" +
        "margin: 0 auto;" +
        "background-color: #f8fbfe;" +
        "border-radius: 8px;" +
        "border: 1px solid #ccc;" +
        "}" +
        ".tools {" +
        "display: flex;" +
        "align-items: center;" +
        "padding: 9px;" +
        "}" +
        ".circle {" +
        "padding: 0 4px;" +
        "}" +
        ".box {" +
        "display: inline-block;" +
        "align-items: center;" +
        "width: 10px;" +
        "height: 10px;" +
        "padding: 1px;" +
        "border-radius: 50%;" +
        "}" +
        ".red {" +
        "background-color: #ff605c;" +
        "}" +
        ".yellow {" +
        "background-color: #ffbd44;" +
        "}" +
        ".green {" +
        "background-color: #00ca4e;" +
        "}" +
        ".card__content {" +
        "display: flex;" +
        "justify-content: center;" +
        "align-items: center;" +
        "height: calc(100% - 36px);" +
        "}" +
        ".card__content > div {" +
        "padding-left: 10px;" +
        "}" +
        "h1 {" +
        "font-size: 24px;" +
        "color: #ff605c;" +
        "font-family: 'Courier New', monospace;" +
        "margin-bottom: 8px;" +
        "}" +
        "h4 {" +
        "font-size: 18px;" +
        "color: #264b99;" +
        "font-family: 'Courier New', monospace;" +
        "margin-bottom: 4px;" +
        "}" +
        "img {" +
        "max-width: 100%;" +
        "height: auto;" +
        "margin-bottom: 12px;" +
        "}" +
        "</style>" +
        "</head>" +
        "<body>" +
        "<div class=\"card\">" +
        "<div class=\"tools\">" +
        "<div class=\"circle\">" +
        "<span class=\"red box\"></span>" +
        "</div>" +
        "<div class=\"circle\">" +
        "<span class=\"yellow box\"></span>" +
        "</div>" +
        "<div class=\"circle\">" +
        "<span class=\"green box\"></span>" +
        "</div>" +
        "</div>" +
        "<div class=\"card__content\">" +
        "<div>" +
        "<h1 style=\"color: #ff605c;\">¡Hola! "+ ventanilla.getNombreVent()+" " + ventanilla.getApePaternoVent() +" </h1>" +
        "<h4 style=\"color: #264B99;\">Bienvenido al Sistema CITAT</h4>" +
        "<h4 style=\"color: #58BEC4;\">Usuario Ventanilla</h4>" +
        "<img src='cid:logoImage' />" +
        "<h4 style=\"color: #BAB9BC;\">" +
        "Este es tu correo para ingresar sesión " + ventanilla.getCorreoElectronico() +  " con tu contraseña " + ventanilla.getPass() +
        "</h4>" +
        "<br>" +
        "<div>" +
        "<center><img src=\"cid:logoImage\" alt=\"Logo\"> width=\"50px\" height=\"50px\"></center>"+
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</body>" +
        "</html>";
        messageHelper.setText(htmlContent, true);
        
        // Adjuntar la imagen como recurso en línea
        FileSystemResource imageResource = new FileSystemResource(new File("src/main/resources/static/images/citat.jpeg"));
        messageHelper.addInline("logoImage", imageResource);
        
        // Enviar el correo electrónico
        this.javaMailSender.send(mimeMessage);

        return new CustomResponse<>(
                this.ventanillaRepository.saveAndFlush(ventanilla),
                false,
                200,
                "Ventanilla registrado correctamente"
        );
    }

    //Actualizar un ventanilla
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Ventanilla> update(Ventanilla ventanilla){
        if(!this.ventanillaRepository.existsById(ventanilla.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡La Ventanilla que quieres buscar no existe!"
            );
        return new CustomResponse<>(
                this.ventanillaRepository.saveAndFlush(ventanilla),
                false,
                200,
                "Ventanilla actualizada correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Ventanilla ventanilla){
        if(!this.ventanillaRepository.existsById(ventanilla.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "¡La Ventanilla que quieres buscar no existe!"
            );
        }
        return new CustomResponse<>(
                this.ventanillaRepository.updateStatusById(
                        ventanilla.isStatus(), ventanilla.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status de la Ventanilla correctamente!"
        );
    }

    //cambia la contraseña por la que el usuario establezca
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Integer> changePassword(Ventanilla ventanilla) {
        if (!this.ventanillaRepository.existsByCorreoElectronico(ventanilla.getCorreoElectronico()))
            return new CustomResponse<>(
                    null, true, 400,
                    "¡La Ventanilla que quieres buscar no existe!"
            );
        ventanilla.setPass(ventanilla.getPass());
        System.out.println("New password->" + ventanilla.getPass());

        return new CustomResponse<>(
                this.ventanillaRepository.changePassword(
                    ventanilla.getPass(), ventanilla.getCorreoElectronico()),
                false, 200,
                "¡Contraseña modificada correctamente!"
        );
    }
}
