package com.example.adserv.controller;

import com.example.adserv.model.Role;
import com.example.adserv.service.AdService;
import com.example.adserv.vo.Ad;
import com.example.adserv.vo.AdCollection;
import com.example.adserv.vo.MarketingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/v1/marketing")
public class AdController {

    @Autowired
    AdService adService;

    @PostMapping("/activate")
    public ResponseEntity<MarketingAccount> activate(@RequestBody MarketingAccount marketingAccount) {
        Random rand = new Random();
        marketingAccount.setId(rand.nextInt(10000));
        marketingAccount.setStatus("ACTIVATED");
        return new ResponseEntity<MarketingAccount>(marketingAccount, HttpStatus.OK);
    }

    @GetMapping("/ads")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<AdCollection> readAds() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("username: " + username);
        System.out.println("authorities: " + authorities);

        AdCollection adCollection;
        if(authorities.contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))) {
            List<Ad> ads = adService.readAds();
            adCollection = new AdCollection(ads);
        } else {
            List<Ad> ads = adService.readAdsByUsername(username);
            adCollection = new AdCollection(ads);
        }
        return new ResponseEntity<AdCollection>(adCollection, HttpStatus.OK);
    }

    @PostMapping("/ads")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Ad createAd(@RequestBody Ad ad, Principal principal) {
        //FORCE
        ad.setUsername(principal.getName());
        Ad createdAd = adService.createAd(ad);
        return createdAd;
    }

    @PutMapping("/ads/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateAd(@PathVariable("id") String id, @RequestBody Ad ad, Principal principal) {
        //FORCE
        ad.setUsername(principal.getName());
        adService.updateAd(id, ad);
    }

    @GetMapping("/ads/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.OK)
    public Ad getAd(@PathVariable("id") String id, Principal principal) {
        return adService.getAd(id, principal.getName());
    }


}
