package com.example.adserv.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "ads")
public class AdDocument {

    @Id
    private String id;
    private String username;

    @Indexed(unique = true)
    private String adName;

    private String description;
    private AdType adType;
    private String imageUrl;
    private String targetUrl;

    public AdDocument(String id) {
        this.id = id;
    }
}
