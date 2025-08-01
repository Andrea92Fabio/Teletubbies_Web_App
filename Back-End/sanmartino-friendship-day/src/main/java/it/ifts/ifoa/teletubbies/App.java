package it.ifts.ifoa.teletubbies;


import com.google.gson.*;
import it.ifts.ifoa.teletubbies.config.ConnectionPool;
import it.ifts.ifoa.teletubbies.controller.ConfirmationController;
import it.ifts.ifoa.teletubbies.controller.SubmissionsController;
import it.ifts.ifoa.teletubbies.middleware.Middleware;
import it.ifts.ifoa.teletubbies.repository.UserRepository;
import it.ifts.ifoa.teletubbies.service.MailService;
import it.ifts.ifoa.teletubbies.service.UserConfirmationService;
import it.ifts.ifoa.teletubbies.service.UserSubmissionService;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

//Aggiungere Corpo della mail in MailService e oggetto della mail
//Scegliere se fare un corpo in html o semplice txt

public class App {
    public final static LocalDateTime START_CONTEST = LocalDateTime.of(2025, Month.JUNE, 1, 9, 0);
    public final static LocalDateTime END_CONTEST = LocalDateTime.of(2025, Month.JULY, 8, 9, 0);

    ConnectionPool pool;
    UserRepository userRepository;
    MailService mailService;

    SubmissionsController submissionsController;
    ConfirmationController confirmationController;

    Middleware middleware;

    UserSubmissionService userSubmissionService;
    UserConfirmationService userConfirmationService;

    //todo: add shutdown hook
    ExecutorService emailExecutor;

    public static void main(String[] args) {
        new App().run();
    }


    public App() {
        port(80);
        this.emailExecutor = Executors.newFixedThreadPool(4);

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDate.parse(json.getAsString());
            }
        }).create();

        this.middleware = new Middleware(gson);

        this.middleware.enableCORS();
        this.middleware.handleRequestBeforeOrAfterContest();

        this.pool = ConnectionPool.getInstance();

        this.userRepository = new UserRepository(pool);

        this.userSubmissionService = new UserSubmissionService(userRepository);
        this.userConfirmationService = new UserConfirmationService(userRepository, emailExecutor);


        this.submissionsController = new SubmissionsController(gson, userSubmissionService, mailService, emailExecutor);
        this.confirmationController = new ConfirmationController(gson, userConfirmationService);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook triggered. Cleaning up...");

            emailExecutor.shutdown(); // Stop accepting new tasks
            try {
                if (!emailExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                    emailExecutor.shutdownNow(); // Force shutdown if not terminated
                }
            }
            catch (InterruptedException e) {
                emailExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            } finally {
                pool.close(); // Custom method to release DB connections
                System.out.println("Resources released successfully.");
            }

        }));

    }

    private void run() {
        submissionsController.initSubmissionEndpoint();
        confirmationController.initConfirmationEndpoint();
    }
}