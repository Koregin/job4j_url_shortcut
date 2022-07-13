package ru.job4j.shortcut.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    @URL(message = "URL not valid")
    private String name;

    @Column(name = "code")
    private String code = RandomStringUtils.randomAlphanumeric(7);

    @Column(name = "calls")
    private long calls;

    public Url(String name) {
        this.name = name;
    }
}
