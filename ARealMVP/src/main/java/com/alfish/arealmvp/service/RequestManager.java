package com.alfish.arealmvp.service;

import com.alfish.arealmvp.dto.CloudDto;
import com.alfish.arealmvp.dto.StatueDto;
import com.alfish.arealmvp.enums.RequestRejectReason;
import com.alfish.arealmvp.message.*;
import com.alfish.arealmvp.model.Cloud;
import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RequestManager {

    private final DataCacheService cacheService;
    private final CloudService cloudService;

    @Autowired
    public RequestManager(DataCacheService cacheService, CloudService cloudService) {
        this.cacheService = cacheService;
        this.cloudService = cloudService;
    }

    public GetStatuesResponse process(User user, GetStatuesRequest request) {
        if (user == null) {
            return new GetStatuesResponse(request.getUsername(), null, RequestRejectReason.INVALID_CREDENTIALS);
        }
        List<StatueDto> statues = cacheService.getStatues();
        return new GetStatuesResponse(request.getUsername(), statues, null);
    }

    public GetCloudsResponse process(User user, Statue statue, GetCloudsRequest request) {
        if (user == null) {
            return new GetCloudsResponse(request.getUsername(), request.getStatueName(), null, RequestRejectReason.INVALID_CREDENTIALS);
        }
        if (statue == null) {
            return new GetCloudsResponse(request.getUsername(), request.getStatueName(), null, RequestRejectReason.INVALID_IDENTIFIER);
        }

        List<CloudDto> randomClouds = getRandomClouds(statue);
        return new GetCloudsResponse(request.getUsername(), request.getStatueName(), randomClouds, null);
    }

    public NewCloudResponse process(User user, Statue statue, NewCloudRequest request) {
        if (user == null) {
            return new NewCloudResponse(request.getUsername(), request.getStatueName(), null, RequestRejectReason.INVALID_CREDENTIALS);
        }
        if (statue == null) {
            return new NewCloudResponse(request.getUsername(), request.getStatueName(), null, RequestRejectReason.INVALID_IDENTIFIER);
        }

        Cloud cloud = cloudService.addCloud(statue, user, Instant.now());
        CloudDto newCloud = cacheService.addCloud(statue, cloud, request.getSvgContent());
        List<CloudDto> clouds = replaceCloud(newCloud, getRandomClouds(statue));
        return new NewCloudResponse(request.getUsername(), request.getStatueName(), clouds, null);
    }

    private List<CloudDto> getRandomClouds(Statue statue) {
        List<CloudDto> clouds = cacheService.getClouds(statue.getStatueName());
        List<CloudDto> randomClouds = new ArrayList<>();

        if (clouds.size() < 4) {
            randomClouds = clouds;
        }
        else {
            List<Integer> indexes = getRandomIndexes(clouds.size());
            for (int i : indexes) {
                randomClouds.add(clouds.get(i));
            }
        }
        return randomClouds;
    }

    private List<CloudDto> replaceCloud(CloudDto newCloud, List<CloudDto> clouds) {
        int index = clouds.indexOf(newCloud);
        if (index < 0) index = 0;
        clouds.remove(index);
        clouds.add(newCloud);
        return clouds;
    }

    private List<Integer> getRandomIndexes(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("The range must include at least 2 distinct numbers.");
        }

        List<Integer> numbers = new ArrayList<>();
        Random rand = new Random();

        while (numbers.size() < 3) {
            int randomNumber = rand.nextInt(n);
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }

        return numbers;
    }

}
