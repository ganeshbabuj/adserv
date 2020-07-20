package com.example.adserv.repository;

import com.example.adserv.model.AdDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdRepository extends MongoRepository<AdDocument, String> {

    public List<AdDocument> findByUsername(String username);
    public AdDocument findByIdAndUsername(String id, String username);

}
