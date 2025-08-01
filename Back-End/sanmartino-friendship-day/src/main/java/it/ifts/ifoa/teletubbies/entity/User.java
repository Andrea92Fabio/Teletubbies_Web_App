package it.ifts.ifoa.teletubbies.entity;
import it.ifts.ifoa.teletubbies.exception.*;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class User
{

    private String name,surname, gender,phoneNumber, tokenId, email, fiscalCode,residencyCountry,residencyZipCode, residencyAddress, shipCountry, shipZipCode, shipAddress, residencyProvince, shipProvince;
    private boolean privacy, rules;
    private LocalDate birthDate;


    public User() {
    }

    public List<String> checkUser(){
        ArrayList<String> errors = new ArrayList<>();
        errors.add(checkEmail(this.email));
        errors.add(checkName(this.name));
        errors.add(checkSurname(this.surname));
        errors.add(checkGender(this.gender));
        errors.add(checkPhoneNumber(this.phoneNumber));
        errors.add(checkFiscalCode(this.fiscalCode));
        errors.add(checkResidencyCountry(this.residencyCountry));
        errors.add(checkResidencyAddress(this.residencyAddress));
        errors.add(checkResidencyZipCode(this.residencyZipCode));
        errors.add(checkShipCountry(this.shipCountry));
        errors.add(checkShipAddress(this.shipAddress));
        errors.add(checkShipZipCode(this.shipZipCode));
        errors.add(checkPrivacy(this.privacy));
        errors.add(checkRules(this.rules));
        errors.add(checkBirthDate(this.birthDate));
        errors.add(checkResidencyProvince(this.residencyProvince));
        errors.add(checkShipProvince(this.shipProvince));
        ArrayList<String> retvalue = errors.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        return retvalue;
    }

    public void assignTokenId(){
        String safeCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
        StringBuilder retvalue = new StringBuilder();
        Random rand = new Random();
        int totalCharacter = rand.nextInt(10)+40;

        for (int i =0;i<totalCharacter;i++){
            int num = rand.nextInt(safeCharacters.length());
            String temvalue = String.valueOf(safeCharacters.charAt(num));
            retvalue.append(temvalue);
        }

        this.tokenId = retvalue.toString();
    }

    private String checkEmail(String email){
        String regex = "^[a-zA-Z0-9]{1,}[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(regex, email)){
            return "0x00";
        }
        return null;
    }

    private String checkName(String name) {
        if (name == null || name.length() < 2){
            return "0x01";
        }
        return null;
    }

    private String checkSurname(String surname){
        if (surname == null || surname.length() < 2){
            return "0x02";
        }
        return null;
    }

    private String checkGender(String gender){
        if(!gender.equals("man") && !gender.equals("woman") && !gender.equals("other") && !gender.equals("not specified")){
            return "0x03";
        }
        return null;
    }

    private String checkPhoneNumber(String phoneNumber){
//        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
//            return "0x04";
//        }
        if(phoneNumber == null || phoneNumber.isBlank()){
            return null;
        }
        String regex =  "^\\+*[0-9]{6,15}$";
        if(!Pattern.matches(regex,phoneNumber)){
            return "0x04";
        }
        return null;
    }

    private String checkFiscalCode(String fiscalCode){
        if(this.residencyCountry.equals("san marino")){
            return null;
        }
        String regex = "^[a-zA-Z]{6}[a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{2}[a-zA-Z][a-zA-Z0-9]{3}[a-zA-Z]+$";
        if ( fiscalCode.length() != 17 && !Pattern.matches(regex,fiscalCode)) {
            return "0x05";
        }
        return null;
    }

    private String checkResidencyCountry(String residencyCountry){
        if (residencyCountry == null || (!residencyCountry.equals("italy") && !residencyCountry.equals("san marino"))) {
            return "0x06";
        }
        return null;
    }

    private String checkResidencyZipCode(String residencyZipCode){
        String regex = "^[0-9]{5}$";
        if (!Pattern.matches(regex, residencyZipCode) ) {
            return "0x07";
        }
        return null;
    }

    private String checkResidencyAddress(String residencyAddress){
        if (residencyAddress == null && residencyAddress.length()<=2) {
            return "0x08";
        }
        return null;
    }

    private String checkResidencyProvince(String residencyProvince){
        if(this.residencyCountry.equals("san marino")){
            return null;
        }
        if(residencyProvince == null || residencyProvince.length()!=2){
            return "0x09";
        }
        return null;
    }


    private String checkShipCountry(String shipCountry){
        if (shipCountry == null || (!shipCountry.equals("italy") && !shipCountry.equals("san marino"))) {
            return "0x10";
        }
        return null;
    }

    private String checkShipZipCode(String shipZipCode){
        String regex = "^[0-9]{5}$";
        if (!Pattern.matches(regex, shipZipCode) ) {
            return "0x11";
        }
        return null;
    }

    private String checkShipAddress(String shipAddress){
        if (shipAddress == null && shipAddress.length()<=2) {
            return "0x12";
        }
        return null;
    }

    private String checkShipProvince(String residencyProvince){
        if(this.shipCountry.equals("san marino")){
            return null;
        }
        if(residencyProvince == null || residencyProvince.length()!=2){
            return "0x13";
        }
        return null;
    }

    private String checkPrivacy(boolean privacy){
        if (!privacy) {
            return "0x14";
        }
        return null;
    }

    private String checkRules(boolean rules){
        if (!rules) {
            return "0x15";
        }
        return null;
    }

    private String checkBirthDate(LocalDate birthDate){
        if (birthDate == null) {
            return "0x16";
        }
        LocalDate now = LocalDate.now();
        LocalDate minimumAge = now.minusYears(18);

        if (birthDate.isAfter(minimumAge)) {
            return "0x16";
        }
        return null;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getGender() {
        return gender;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getFiscalCode() {
        return fiscalCode;
    }
    public String getResidencyCountry() {
        return residencyCountry;
    }
    public String getResidencyZipCode() {
        return residencyZipCode;
    }
    public String getResidencyAddress() {
        return residencyAddress;
    }
    public String getShipCountry() {
        return shipCountry;
    }
    public String getShipZipCode() {
        return shipZipCode;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public boolean getPrivacy() {
        return privacy;
    }
    public String getEmail() { return email; }
    public boolean getRules() {
        return rules;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getTokenId() {
        return tokenId;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public boolean isRules() {
        return rules;
    }



    public String getResidencyProvince() {
        return residencyProvince;
    }

    public String getShipProvince() {
        return shipProvince;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", email='" + email + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", residencyCountry='" + residencyCountry + '\'' +
                ", residencyZipCode='" + residencyZipCode + '\'' +
                ", residencyAddress='" + residencyAddress + '\'' +
                ", shipCountry='" + shipCountry + '\'' +
                ", shipZipCode='" + shipZipCode + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", residencyProvince='" + residencyProvince + '\'' +
                ", shipProvince='" + shipProvince + '\'' +
                ", privacy=" + privacy +
                ", rules=" + rules +
                ", birthDate=" + birthDate +
                '}';
    }
}