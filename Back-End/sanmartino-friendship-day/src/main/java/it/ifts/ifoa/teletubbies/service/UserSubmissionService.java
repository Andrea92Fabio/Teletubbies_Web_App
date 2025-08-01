package it.ifts.ifoa.teletubbies.service;

import it.ifts.ifoa.teletubbies.entity.User;
import it.ifts.ifoa.teletubbies.repository.UserRepository;

import java.util.Optional;

public class UserSubmissionService
{
    private final UserRepository userRepository;

    public UserSubmissionService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void saveUser(User user)
    {
        this.userRepository.saveUser(user);
    }

    public Optional<Integer> idFromEmail(String email)
    {
        return this.userRepository.idFromEmail(email);
    }

    public Optional<Integer> idFromFiscalCode(String fiscalCode)
    {
        return this.userRepository.idFromFiscalCode(fiscalCode);
    }

    public String getResidencyCountryFromId(int id){
        return this.userRepository.getResidencyCountryFromId(id);
    }

    public boolean isEmailConfirmed(String email)
    {
        return this.userRepository.isEmailConfirmed(email);
    }

    public String tokenIdFromEmail(String email) {
        Optional<String> queryResult = this.userRepository.tokenIdFromEmail(email);
        return queryResult.orElse("");
    }
}
