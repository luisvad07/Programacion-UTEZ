package com.school.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.models.Solicitante.SolicitanteRepository;
import com.school.citas.utils.CustomResponse;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class SolicitanteService {

    @Value("spring.mail.username")
    private String mail;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SolicitanteRepository solicitanteRepository;

    ///Todos los solicitantes
    @Transactional(readOnly = true)
    public CustomResponse<List<Solicitante>> getAll(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public CustomResponse<List<Solicitante>> getAllActive(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Solicitante>> getAllInactive(){
        return new CustomResponse<>(
                this.solicitanteRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Solicitante por id
    @Transactional(readOnly = true)
    public CustomResponse<Solicitante> getOne(Long id) {
        Optional<Solicitante> soliOptional = this.solicitanteRepository.findById(id);
        if (soliOptional.isPresent()) {
            return new CustomResponse<>(
                    soliOptional.get(),
                    false,
                    200,
                    "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario solicitante no encontrado"
            );
        }
    }

    //Insertar un solicitante
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Solicitante> insert(Solicitante solicitante) throws MessagingException{
         if (this.solicitanteRepository.existsByCorreoElectronico(solicitante.getCorreoElectronico())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El correo de este Solicitante ya existe!"
            );
        }
        //envio de correos electronicos
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setTo(solicitante.getCorreoElectronico());
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
        "<h1 style=\"color: #ff605c;\">¡Hola! "+ solicitante.getNombre() + solicitante.getApePaterno() +" </h1>" +
        "<h4 style=\"color: #264B99;\">Bienvenido al Sistema CITAT</h4>" +
        "<h4 style=\"color: #58BEC4;\">Usuario Solicitante</h4>" +
        "<img src='cid:logoImage' />" +
        "<h4 style=\"color: #BAB9BC;\">" +
        "Este es tu correo para ingresar sesión " + solicitante.getCorreoElectronico() +  " con tu contraseña " + solicitante.getPass() +
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
                this.solicitanteRepository.saveAndFlush(solicitante),
                false,
                200,
                "Solicitante registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Solicitante> update(Solicitante solicitante){
        if(!this.solicitanteRepository.existsById(solicitante.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "¡El Solicitante que quieres buscar no existe!"
            );
        return new CustomResponse<>(
                this.solicitanteRepository.saveAndFlush(solicitante),
                false,
                200,
                "Solicitante actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Solicitante solicitante){
        if(!this.solicitanteRepository.existsById(solicitante.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "¡El administrador que quieres buscar no existe!"
            );
        }
        return new CustomResponse<>(
                this.solicitanteRepository.updateStatusById(solicitante.isStatus(), solicitante.getId()) == 1,
                false,
                200,
                "¡Se ha cambiado el status del Solicitante correctamente!"
        );
    }

    //cambia la contraseña por la que el usuario establezca
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Integer> changePassword(Solicitante solicitante) {
        if (!this.solicitanteRepository.existsByCorreoElectronico(solicitante.getCorreoElectronico()))
            return new CustomResponse<>(
                    null, true, 400,
                    "¡El solicitante que quieres buscar no existe!"
            );
        solicitante.setPass(solicitante.getPass());
        System.out.println("New password->" + solicitante.getPass());

        return new CustomResponse<>(
                this.solicitanteRepository.changePassword(
                    solicitante.getPass(), solicitante.getCorreoElectronico()),
                false, 200,
                "¡Contraseña modificada correctamente!"
        );
    }
}
