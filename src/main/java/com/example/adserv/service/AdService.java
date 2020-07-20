package com.example.adserv.service;

import com.example.adserv.vo.Ad;

import java.util.List;

public interface AdService {

    List<Ad> readAds();
    List<Ad> readAdsByUsername(String username);
    Ad createAd(Ad ad);
    void updateAd(String id, Ad ad);

    Ad getAd(String id, String name);
}
