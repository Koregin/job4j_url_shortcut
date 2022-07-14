package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.domain.Site;
import ru.job4j.shortcut.domain.SiteDTO;
import ru.job4j.shortcut.service.SiteService;
import ru.job4j.shortcut.util.UtilValidation;

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
        UtilValidation.urlValidate(siteName);
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
}
