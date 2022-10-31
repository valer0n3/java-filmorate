package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Film {
    private int id;
    @NonNull
    @NotNull
    @NotBlank
    private String name;
    @NonNull
    @NotNull
    private String description;
    @NonNull
    @NotNull
    private LocalDate releaseDate;
    @NonNull
    @NotNull
    private Duration duration;
}
