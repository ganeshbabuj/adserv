package com.example.adserv.vo;

import com.example.adserv.model.AdType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
public class Ad {

    @Id
    private String id;
    private String username;

    @Indexed(unique=true)
    private String adName;

    private String description;

    private AdType adType;

    private String imageUrl;

    private String targetUrl;

    public Ad(String id) {
        this.id = id;
    }
}
