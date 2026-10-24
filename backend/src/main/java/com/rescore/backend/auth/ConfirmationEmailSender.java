package com.rescore.backend.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

// Send the confirmation email
@Component
public class ConfirmationEmailSender {

    // Spring tool that sends emails 
    private final JavaMailSender mailSender;
    private final String from;
    private final String frontUrl;

    // @Value reads a value from application.properties
    public ConfirmationEmailSender(JavaMailSender mailSender, @Value("${app.mail-from}") String from, @Value("${app.front-url}") String frontUrl) {
        this.mailSender = mailSender;
        this.from = from;
        this.frontUrl = frontUrl;
    }

    // Sends the email in the background once the account is saved
    @Async
    @TransactionalEventListener // Listens to UserRegisteredEvent
    public void onUserRegistered(UserRegisteredEvent event) {
        // Link to the front confirmation page
        String link = frontUrl + "/confirmer-email?token=" + event.token();

        // Text email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(event.email());
        message.setSubject("Confirmez votre inscription à ReScore");
        message.setText("""
                Bonjour %s,

                Pour activer votre compte ReScore, cliquez sur ce lien :
                %s

                Ce lien est valable 24 heures et ne sert qu'une fois.

                Si vous n'êtes pas à l'origine de cette inscription, ignorez cet e-mail.
                """.formatted(event.firstName(), link)); // fills each %s in order (firstName, link)

        mailSender.send(message);
    }
}
