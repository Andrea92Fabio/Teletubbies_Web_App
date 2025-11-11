package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.repository.UserRepository;

import java.util.concurrent.ExecutorService;

public class UserConfirmationService
{
    private final UserRepository userRepository;
    private final ExecutorService emailExecutor;

    public UserConfirmationService(UserRepository userRepository, ExecutorService emailExecutor)
    {
        this.userRepository = userRepository;
        this.emailExecutor = emailExecutor;
    }


    public boolean confirmTokenIdAndCheckWin(String tokenId)
    {
        String email = this.userRepository.doubleOptIn(tokenId);
        System.out.println(email);
        boolean isWinner = this.userRepository.isConfirmationTop499(tokenId);
        emailExecutor.submit(() -> {
            System.out.println(isWinner);
            MailService.sendEmail(email, isWinner);
        });
        return isWinner;
    }
}
