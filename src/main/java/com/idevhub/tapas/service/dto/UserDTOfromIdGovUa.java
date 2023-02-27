package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data

public class UserDTOfromIdGovUa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rnokpp;
    private String edrpouCode;
    private String fullName;  //subjectcn   Загальне ім’я користувача
    private String firstName; //  Ім’я користувача
    private String middleName; //  По батькові користувача
    private String lastName; //  Прізвище користувача
    private String org; //  Найменування організації користувача
    private String orgUnit; //  Назва підрозділу організації користувача
    private String position; //  Посада користувача
    private String email; //  Адреса ел. пошти (e-mail) користувача
    private String phone; //  Телефон користувача

}
