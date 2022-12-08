package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class friendship {
    private long friendshipId;
    private long sourceConsumerId;
    private long targetConsumerId;
    private String status;

}
