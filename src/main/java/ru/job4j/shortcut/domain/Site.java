package ru.job4j.shortcut.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "site")
@Getter
@Setter
@NoArgsConstructor
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "site")
    private String site;

    @Column(name = "login")
    private String login = RandomStringUtils.randomAlphanumeric(7);

    @Column(name = "sitepassword")
    private String password = RandomStringUtils.randomAlphanumeric(7);

    public Site(String site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(login, site.login)
                && Objects.equals(password, site.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
