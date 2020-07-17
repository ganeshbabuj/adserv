package com.example.adserv.controller;

import com.example.adserv.vo.MarketingAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/v1/marketing")
public class AdController {

    @PostMapping("/activate")
    public ResponseEntity<MarketingAccount> activate(@RequestBody MarketingAccount marketingAccount) {
        Random rand = new Random();
        marketingAccount.setId(rand.nextInt(10000));
        marketingAccount.setStatus("ACTIVATED");
        return new ResponseEntity<MarketingAccount>(marketingAccount, HttpStatus.OK);
    }
}
