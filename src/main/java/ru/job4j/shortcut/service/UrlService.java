package ru.job4j.shortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.domain.Url;
import ru.job4j.shortcut.repository.UrlRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Optional<Url> findByName(String url) {
        return urlRepository.findByName(url);
    }

    public Url create(String url) {
        return urlRepository.save(new Url(url));
    }

    public Optional<Url> findByCode(String code) {
        return urlRepository.findByCode(code);
    }

    public void saveCall(String code) {
        urlRepository.saveCall(code);
    }

    public List<Url> findAllUrl() {
        List<Url> urls = new ArrayList<>();
        urlRepository.findAll().forEach(urls::add);
        return urls;
    }
}
