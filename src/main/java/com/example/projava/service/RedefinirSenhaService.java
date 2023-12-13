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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, " UTF-8");
            String msg = buffer.replace("$TOKEN$", dados.getToken());
            message.setFrom(KEYGUARD);
            message.setSubject(SENHA);
            message.setText(msg, true);
            message.setTo(email.getEmail());
            emailSender.send(message.getMimeMessage());


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new TokenExpirationException();
        }


    }


    public String validarToken(String token) {
        TokenRedefinirSenha otk = repository.getByToken(token);
        if (System.currentTimeMillis() - otk.getTimestamp() >= 6000000) {
            throw new TokenExpirationException();

        }

        return token;
    }


    public void salvarNovaSenha(String token, String password) throws Exception {
        TokenRedefinirSenha otoken = repository.getByToken(token);
        Optional<UserModel> user = userRepository.findById(otoken.getId_user());
        validarToken(otoken.getToken());
        if (otoken.getId_user().equals(user.get().getId())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senha = encoder.encode(password);
            user.get().setPassword(senha);
            userRepository.save(user.get());
        }

    }

}





