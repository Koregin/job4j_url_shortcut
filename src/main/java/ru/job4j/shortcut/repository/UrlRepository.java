package ru.job4j.shortcut.repository;

import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.shortcut.domain.Url;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    Optional<Url> findByName(String url);

    Optional<Url> findByCode(String code);

    @Modifying
    @Query("update Url u set u.calls = u.calls + 1 where u.code = :fCode")
    void saveCall(@Param("fCode") String code);
}
