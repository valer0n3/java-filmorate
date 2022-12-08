package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Likes {
    private long likesId;
    private long filmId;
    private long consumerId;
}
