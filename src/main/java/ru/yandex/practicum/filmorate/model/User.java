package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NonNull
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NonNull
    @NotNull
    @NotBlank
    private String login;
    @NonNull
    @NotNull
    private String name;
    @NonNull
    @NotNull
    private LocalDate birthday;
}
