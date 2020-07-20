package com.example.adserv.service;

import com.example.adserv.exception.NotFoundException;
import com.example.adserv.model.AdDocument;
import com.example.adserv.repository.AdRepository;
import com.example.adserv.vo.Ad;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private AdRepository repository;

    @Override
    public List<Ad> readAds() {
        List<AdDocument> adDocuments = repository.findAll();
        List<Ad> ads = modelMapper.map(adDocuments, new TypeToken<List<Ad>>() {
        }.getType());
        return ads;
    }

    @Override
    public List<Ad> readAdsByUsername(String username) {
        List<AdDocument> adDocuments = repository.findByUsername(username);
        List<Ad> ads = modelMapper.map(adDocuments, new TypeToken<List<Ad>>() {
        }.getType());
        return ads;
    }

    @Override
    @Transactional
    public Ad createAd(Ad ad) {

        AdDocument adDocument = modelMapper.map(ad, AdDocument.class);
        adDocument.setId(null);
        AdDocument savedAd = repository.save(adDocument);
        return modelMapper.map(savedAd, Ad.class);
    }

    @Override
    @Transactional
    public void updateAd(String id, Ad ad) {

        AdDocument existingDocument = repository.findByIdAndUsername(id, ad.getUsername());
        if (existingDocument == null) {
            throw new NotFoundException("Ad Not exists or Not linked to this User");
        }

        AdDocument adDocument = modelMapper.map(ad, AdDocument.class);
        //FORCE
        adDocument.setId(id);
        repository.save(adDocument);
    }

    @Override
    public Ad getAd(String id, String username) {
        AdDocument adDocument = repository.findByIdAndUsername(id, username);
        if (adDocument == null) {
            throw new NotFoundException();
        }
        return modelMapper.map(adDocument, Ad.class);
    }

}
