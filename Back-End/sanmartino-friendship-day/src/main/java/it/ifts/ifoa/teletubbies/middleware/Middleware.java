package it.ifts.ifoa.teletubbies.middleware;

import com.google.gson.Gson;
import it.ifts.ifoa.teletubbies.App;
import it.ifts.ifoa.teletubbies.exception.ContestAlreadyClosedException;
import org.eclipse.jetty.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Middleware {
   private final Gson gson;

    public Middleware(Gson gson) {
        this.gson = gson;
    }

    public  void enableCORS(){
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*"); // use "*" only for dev
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Content-type", "application/json");
        });

        // Optional: handle preflight (OPTIONS) requests
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

    }

    public  void handleRequestBeforeOrAfterContest(){
        before("/api/*" , (req, res) -> {
            if ("OPTIONS".equalsIgnoreCase(req.requestMethod())) {
                return;
            }
            var today = LocalDateTime.now();
            if (today.isBefore(App.START_CONTEST)|| today.isAfter(App.END_CONTEST)){
                throw new ContestAlreadyClosedException("2x01");
            }
        });

        exception(ContestAlreadyClosedException.class, (e, req, res) -> {
            List<String> errors = new ArrayList<>();
            Map<String, List<String>> retvalue = new HashMap<>();

            errors.add(e.getMessage());

            retvalue.put("errors", errors);

            res.status(HttpStatus.FORBIDDEN_403);
            res.body(gson.toJson(retvalue));
        });

    }
}
