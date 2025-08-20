package com.s_giken.training.batch.resolver.basic.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchArgs {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final DateTimeFormatter printLogFormatter = DateTimeFormatter.ofPattern("yyyy年MM月");

    private LocalDate targetDate = LocalDate.of(2000, 01, 01);

    public String getTargetDateForLog() {
        return targetDate.format(printLogFormatter);
    }
}
