package ru.job4j.shortcut.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteDTO {
    private boolean registration;

    private String login;

    private String password;
}
