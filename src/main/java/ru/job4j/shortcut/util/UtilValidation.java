package ru.job4j.shortcut.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.job4j.shortcut.domain.Site;
import ru.job4j.shortcut.handlers.GlobalExceptionHandler;
import ru.job4j.shortcut.service.SiteService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class UtilValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());

    private static SiteService siteService;

    @Autowired
    public UtilValidation(SiteService siteService) {
        UtilValidation.siteService = siteService;
    }

    public static void urlAuthValidation(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String siteName = uri.getHost();
        Optional<Site> authorizedSite =
                siteService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (authorizedSite.isPresent()) {
            if (!authorizedSite.get().getSite().equals(siteName)) {
                throw new SecurityException("site " + siteName + " not equal authorized site " + authorizedSite.get().getSite());
            }
        }
    }

    public static void urlValidate(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (url.startsWith("http://") || url.startsWith("https://")) {
            LOGGER.info("URL with prefix");
        } else {
            url = "http://" + url;
        }
        if (urlValidator.isValid(url)) {
            LOGGER.info("URL " + url + " is valid");
        } else {
            throw new IllegalArgumentException("Site name " + url.substring(7) + " is not valid");
        }
    }
}
