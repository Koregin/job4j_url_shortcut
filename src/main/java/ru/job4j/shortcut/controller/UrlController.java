package ru.job4j.shortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.domain.Url;
import ru.job4j.shortcut.domain.UrlDto;
import ru.job4j.shortcut.domain.UrlDtoStat;
import ru.job4j.shortcut.service.UrlService;
import ru.job4j.shortcut.util.UtilValidation;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Transactional
    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> codeToUrl(@PathVariable("code") String code) {
        Optional<Url> url = urlService.findByCode(code);
        if (url.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        urlService.saveCall(url.get().getCode());
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.get().getName()).build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<UrlDtoStat>> statistic() {
        List<UrlDtoStat> statUrls = new ArrayList<>();
        statUrls.add(new UrlDtoStat("URL", 0));
        List<Url> foundUrls = new ArrayList<>(urlService.findAllUrl());
        for (Url url : foundUrls) {
            UrlDtoStat urlDtoStat = new UrlDtoStat(url.getName(), url.getCalls());
            statUrls.add(urlDtoStat);
        }
        return ResponseEntity.status(HttpStatus.OK).body(statUrls);
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlDto> convertUrl(@Valid @RequestBody Map<String, String> payload) throws URISyntaxException {
        String url = payload.get("url");
        /* Check site in url for authorized site */
        UtilValidation.urlAuthValidation(url);

        UrlDto urlDto = new UrlDto();
        Optional<Url> foundUrl = urlService.findByName(url);
        if (foundUrl.isPresent()) {
            urlDto.setCode(foundUrl.get().getCode());
            return ResponseEntity.status(HttpStatus.OK).body(urlDto);
        }
        Url newUrl = urlService.create(url);
        urlDto.setCode(newUrl.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(urlDto);
    }
}
