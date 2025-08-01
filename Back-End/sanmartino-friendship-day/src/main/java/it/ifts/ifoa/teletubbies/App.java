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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

public class App {
    // Configurazioni da variabili d'ambiente con valori di default
    public final static LocalDateTime START_CONTEST = LocalDateTime.of(2025, Month.JUNE, 1, 9, 0);
    public final static LocalDateTime END_CONTEST = LocalDateTime.of(2025, Month.JULY, 8, 9, 0);

    // Porta configurabile tramite variabile d'ambiente
    private final static int SERVER_PORT = Integer.parseInt(
            System.getenv("SERVER_PORT") != null ? System.getenv("SERVER_PORT") : "8080"
    );

    ConnectionPool pool;
    UserRepository userRepository;
    MailService mailService;

    SubmissionsController submissionsController;
    ConfirmationController confirmationController;

    Middleware middleware;

    UserSubmissionService userSubmissionService;
    UserConfirmationService userConfirmationService;

    ExecutorService emailExecutor;

    public static void main(String[] args) {
        // Stampa informazioni di avvio per debugging
        System.out.println("=== Teletubbies Contest Application Starting ===");
        System.out.println("Server Port: " + SERVER_PORT);
        System.out.println("DB Host: " + System.getenv("DB_HOST"));
        System.out.println("DB Name: " + System.getenv("DB_NAME"));
        System.out.println("Base URL: " + System.getenv("BASE_URL"));
        System.out.println("SMTP User: " + System.getenv("SMTP_USER"));
        System.out.println("===============================================");

        new App().run();
    }

    public App() {
        // Configurazione porta del server
        port(SERVER_PORT);

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

        System.out.println("Application started successfully on port " + SERVER_PORT);
    }
}