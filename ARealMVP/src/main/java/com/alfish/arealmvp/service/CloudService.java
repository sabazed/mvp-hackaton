package com.alfish.arealmvp.service;

import com.alfish.arealmvp.model.Cloud;
import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.model.composite.CloudId;
import com.alfish.arealmvp.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CloudService {

    private final CloudRepository repository;

    @Autowired
    public CloudService(CloudRepository repository) {
        this.repository = repository;
    }

    public Cloud addCloud(Statue statue, User user, Instant submissionTime) {
        Cloud cloud = new Cloud();
        CloudId cloudId = new CloudId();
        cloudId.setStatue(statue);
        cloudId.setCloudId(statue.getWrites());
        cloud.setCloudId(cloudId);
        cloud.setUser(user);
        cloud.setSubmissionTime(submissionTime);
        return repository.save(cloud);
    }

    public List<Cloud> getClouds(Integer statueId) {
        return repository.findAllByCloudIdStatueStatueId(statueId);
    }

    public List<Cloud> getCloudsCurrent(Integer statueId) {
        Instant now = Instant.now();
        Instant prevDay = now.minus(1, ChronoUnit.DAYS);
        return repository.findAllByCloudIdStatueStatueIdAndSubmissionTimeBetween(statueId, prevDay, now);
    }

}
