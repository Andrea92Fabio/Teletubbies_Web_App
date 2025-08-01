package it.ifts.ifoa.teletubbies.controller;

import com.google.gson.Gson;
import it.ifts.ifoa.teletubbies.service.UserConfirmationService;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ConfirmationController {
    private final Gson gson;
    private final UserConfirmationService userConfirmationService;

    public ConfirmationController(Gson gson, UserConfirmationService userConfirmationService) {
        this.gson = gson;
        this.userConfirmationService = userConfirmationService;
    }


    public void initConfirmationEndpoint() {
        get("/api/confirmation/:tokenId", (req, res) -> {
            String tokenId = req.params("tokenId");
            System.out.println(tokenId);

            res.header("Content-type", "Application/json");

            Map<String, String> responseMessage = new HashMap<>();


            if (this.userConfirmationService.confirmTokenIdAndCheckWin(tokenId)) {
                responseMessage.put("message", "you win");
            } else {
                responseMessage.put("message", "you lose");
            }
            return gson.toJson(responseMessage);
        });

    }
}
