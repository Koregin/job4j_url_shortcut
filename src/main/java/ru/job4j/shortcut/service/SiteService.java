package ru.job4j.shortcut.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.domain.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Optional;

@Service
public class SiteService {
    private final SiteRepository repository;
    private final BCryptPasswordEncoder encoder;

    public SiteService(SiteRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Optional<Site> findBySite(String siteName) {
        return repository.findBySite(siteName);
    }

    public Site registerSite(String siteName) {
        Site newSite = new Site(siteName);
        Site withPlainPassword = new Site(siteName);
        withPlainPassword.setLogin(newSite.getLogin());
        withPlainPassword.setPassword(newSite.getPassword());
        newSite.setPassword(encoder.encode(newSite.getPassword()));
        repository.save(newSite);
        return withPlainPassword;
    }

    public Optional<Site> findByLogin(String login) {
        return repository.findByLogin(login);
    }
}
