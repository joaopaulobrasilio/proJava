package com.example.projava.service;


import com.example.projava.Utils.Formatar;
import com.example.projava.exception.TokenExpirationException;
import com.example.projava.model.RedefinirSenhaModel;
import com.example.projava.model.TokenRedefinirSenha;
import com.example.projava.model.UserModel;
import com.example.projava.repository.RedefinirTokenRepository;
import com.example.projava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;


@Service
public class RedefinirSenhaService {

    public static final String KEYGUARD = "keyguard@mailtrix.com.br";
    public static final String SENHA = "Redefinir Senha";

    @Autowired
    private RedefinirTokenRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    Formatar formatar;

    TokenRedefinirSenha tokenRedefinirSenha = new TokenRedefinirSenha();


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("email-smtp.us-east-1.amazonaws.com");
        mailSender.setPort(587);
        mailSender.setUsername("AKIATP3TWFANCELUFP63");
        mailSender.setPassword("BDZ9JTallz1u7WIf/Go6CRE65xtbvo42uW25mXfUI58X");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public ResponseEntity<TokenRedefinirSenha> enviarEmailRecurecaoSenha(RedefinirSenhaModel email) throws Exception {
         InputStream sle = getClass().getClassLoader().getResourceAsStream("resetpassword/index.html");
          sle.read();
         String buffer = new String(sle.readAllBytes());


        try {
            UUID token = UUID.randomUUID();
            String myToken = token.toString();
            Optional<UserModel> resp = userRepository.findByEmail(email.getEmail());
            TokenRedefinirSenha dados = formatar.formatarDados(resp.get().getId(),
                    myToken, System.currentTimeMillis());
            repository.save(dados);
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage," UTF-8");
            String msg = buffer.replace("$TOKEN$",dados.getToken());
            helper.setFrom(KEYGUARD);
            helper.setSubject(SENHA);
            helper.setText(msg,true);
            helper.setTo(email.getEmail());
            emailSender.send(helper.getMimeMessage());

//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(KEYGUARD);
//            message.setSubject(SENHA);
//            message.setText(buffer.replace("$TOKEN$",dados.getToken()));
//            message.setTo(email.getEmail());
//            emailSender.send(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new Exception();
        }


    }




    public void validarToken(String token) {
        TokenRedefinirSenha otk = repository.verifyExpirationToken(token);
        if (System.currentTimeMillis() - otk.getTimestamp() >= 60000) {
            throw new TokenExpirationException();
        }

    }
}



