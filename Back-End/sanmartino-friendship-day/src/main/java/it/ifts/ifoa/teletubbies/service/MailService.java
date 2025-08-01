package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.utils.SubmissionStatus;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {
    static final String SENDER_EMAIL = "teletubbies.pw@gmail.com"; 
    private static Session mailSetter(){
        final String password = "rzmw gkis qngy magn";
        
        String host = "smtp.gmail.com";
        int port = 587;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, password);
            }
        });
        
        return session;

    }

    public static void sendEmail(String receiver, String tokedId, SubmissionStatus status) {
        Session session = mailSetter();

        String subject = "Iscrizione concorso Teletubbies x San Martino";
        String address = "http://192.168.100.48:8080/result/index.html?tokenId="+tokedId;

        String body = null;
        if(status == SubmissionStatus.FIRST_REGISTRATION){
            body = """
                    <html>
                    <head>
                        <style>
                            .cta {
                                display: block;
                                width: fit-content;
                                cursor: pointer;
                                text-decoration: none !important;
                                background-color: #ff5024;
                                color: #361008 !important;
                                transition: ease-in-out 300ms;
                                font-size: 16px;
                                text-transform: uppercase;
                                font-weight: 600;
                                letter-spacing: 0.25ch;
                                margin-top: 32px;
                                border: none;
                                border-radius: 160px;
                                padding: 16px 32px;
                            }
                            .cta:hover,
                            .cta:focus,
                            .cta:active {
                                background-color: #6b200f;
                                color: #ffd9ce !important;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Conferma l'iscrizione</h1>
                        <p>Ti ringraziamo per aver partecipato al concorso di TELETUBBIES X SAN MARTINO</p>
                        <a href='%s' class='cta'>Clicca qui per Confermare </a>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        } else if (status == SubmissionStatus.ALREADY_PRESENT) {
            body = """
                    <html>
                    <head>
                        <style>
                            .cta {
                                display: block;
                                width: fit-content;
                                cursor: pointer;
                                text-decoration: none !important;
                                background-color: #ff5024;
                                color: #361008 !important;
                                transition: ease-in-out 300ms;
                                font-size: 16px;
                                text-transform: uppercase;
                                font-weight: 600;
                                letter-spacing: 0.25ch;
                                margin-top: 32px;
                                border: none;
                                border-radius: 160px;
                                padding: 16px 32px;
                            }
                            .cta:hover,
                            .cta:focus,
                            .cta:active {
                                background-color: #6b200f;
                                color: #ffd9ce !important;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Sei già registrato</h1>
                        <p>I tuoi dati sono già registrati, clicca il bottone qui sotto per scoprire se hai vinto</p>
                        <a href='%s' class='cta'>Controlla se hai vinto</a>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        } else if (status == SubmissionStatus.ALREADY_CONFIRMED) {
            body = """
                    <html>
                    <head>
                        <style>
                            .cta {
                                display: block;
                                width: fit-content;
                                cursor: pointer;
                                text-decoration: none !important;
                                background-color: #ff5024;
                                color: #361008 !important;
                                transition: ease-in-out 300ms;
                                font-size: 16px;
                                text-transform: uppercase;
                                font-weight: 600;
                                letter-spacing: 0.25ch;
                                margin-top: 32px;
                                border: none;
                                border-radius: 160px;
                                padding: 16px 32px;
                            }
                            .cta:hover,
                            .cta:focus,
                            .cta:active {
                                background-color: #6b200f;
                                color: #ffd9ce !important;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Controlla se hai vinto</h1>
                        <p>I tuoi dati risultano gia' confermati, clicca il bottone qui sotto per scoprire se hai vinto</p>
                        <a href='%s' class='cta'>Scopri il risultato</a>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        } else {
            address = "http://192.168.100.48:8080";
            body = """
                    <html>
                    <head>
                    </head>
                    <style>
                        .cta {
                            display: block;
                            width: fit-content;
                            cursor: pointer;
                            text-decoration: none !important;
                            background-color: #ff5024;
                            color: #361008 !important;
                            transition: ease-in-out 300ms;
                            font-size: 16px;
                            text-transform: uppercase;
                            font-weight: 600;
                            letter-spacing: 0.25ch;
                            margin-top: 32px;
                            border: none;
                            border-radius: 160px;
                            padding: 16px 32px;
                        }
                        .cta:hover,
                        .cta:focus,
                        .cta:active {
                            background-color: #6b200f;
                            color: #ffd9ce !important;
                        }
                    </style>
                    <body>
                        <h1>Mail già iscritta</h1>
                        <p>La mail è già registrata, cambiala per effettuare la registrazione</p>
                        <a href='%s' class='cta'>Torna all'iscrizione</a>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        }
        

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            //message.setText(body);
            message.setContent(body.formatted(address), "text/html");

            Transport.send(message);
            System.out.println("Email sent successfully to: " + receiver);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public static void sendEmail(String receiver, boolean isWinner) {
        Session session = mailSetter();

        String subject = "Iscrizione concorso Teletubbies x San Martino";

        String body = null;
        if(isWinner){
            body = """
                    <html>
                    <head>
                    </head>
                    <body>
                        <h1>Complimenti hai vinto</h1>
                        <p>Ti ringraziamo per aver partecipato al concorso di TELETUBBIES X SAN MARTINO
                        <br>Ti contatteremo a breve per consegniarti il premio</p>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        } else {
            body = """
                    <html>
                    <head>
                    </head>
                    <style>
                    </style>
                    <body>
                        <h1>Ci dispiace ma non hai vinto</h1>
                        <p>Ti ringraziamo per aver partecipato al concorso di TELETUBBIES X SAN MARTINO
                        <br>Non rassegnarti potresti ancora la possibilità di vincere con l'estrazione finale che avrà luogo in data 18/07/2025.
                        <br>L'esito di questa estrazione ti verrà comunicato entro 72 ore.</p>
                        <br>
                        <br>
                    </body>
                    </html>
                    """;
        }


        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            //message.setText(body);
            message.setContent(body, "text/html");

            Transport.send(message);
            System.out.println("Winner email sent successfully to: " + receiver);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


}
