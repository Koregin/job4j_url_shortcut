package ru.job4j.shortcut.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.domain.Site;
import ru.job4j.shortcut.domain.SiteDTO;
import ru.job4j.shortcut.service.SiteService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/registration")
    public ResponseEntity<SiteDTO> registration(@Valid @RequestBody Map<String, String> payload) {
        String siteName = payload.get("site");
        urlValidate(siteName);
        SiteDTO siteDTO = new SiteDTO();
        Optional<Site> foundSite = siteService.findBySite(siteName);
        if (foundSite.isPresent()) {
            siteDTO.setRegistration(false);
            siteDTO.setLogin(foundSite.get().getLogin());
            siteDTO.setPassword(foundSite.get().getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(siteDTO);
        }
        Site newSite = siteService.registerSite(siteName);
        siteDTO.setRegistration(true);
        siteDTO.setLogin(newSite.getLogin());
        siteDTO.setPassword(newSite.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(siteDTO);
    }

    private static void urlValidate(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (url.startsWith("http://") || url.startsWith("https://")) {
            System.out.println("URL with prefix");
        } else {
            url = "http://" + url;
        }
        if (urlValidator.isValid(url)) {
            System.out.println("URL " + url + " is valid");
        } else {
            throw new IllegalArgumentException("Site name " + url.substring(7) + " is not valid");
        }
    }
}
