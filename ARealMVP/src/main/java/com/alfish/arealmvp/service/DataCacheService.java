package com.alfish.arealmvp.service;

import com.alfish.arealmvp.dto.CloudDto;
import com.alfish.arealmvp.dto.StatueDto;
import com.alfish.arealmvp.model.Cloud;
import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataCacheService {

    private final Map<String, StatueDto> statues;
    private final Map<String, List<CloudDto>> clouds;

    private final AWSClientService s3Service;
    private final StatueService statueService;
    private final CloudService cloudService;
    private final ModelConverter converter;

    @Autowired
    public DataCacheService(StatueService statueService, CloudService cloudService, ModelConverter converter, AWSClientService s3Service) {
        this.statues = new HashMap<>();
        this.clouds = new HashMap<>();
        this.s3Service = s3Service;
        this.statueService = statueService;
        this.cloudService = cloudService;
        this.converter = converter;
    }

    @PostConstruct
    private void init() {
        List<Statue> statues = statueService.getStatues();
        List<Cloud> clouds;
        for (Statue statue : statues) {
            this.statues.put(statue.getStatueName(), converter.convert(statue));
            clouds = cloudService.getClouds(statue.getStatueId());
            this.clouds.put(statue.getStatueName(), clouds.stream().map((Cloud cloud) -> doConvertCloud(cloud, null)).collect(Collectors.toList()));
        }
    }

    public CloudDto addCloud(Statue statue, Cloud cloud, String content) {
        String cloudKey = getCloudKey(cloud.getUser(), cloud.getCloudId().getStatue(), cloud);
        s3Service.publishObject(cloudKey, content);
        CloudDto cloudDto = this.doConvertCloud(cloud, content);
        clouds.get(statue.getStatueName()).add(cloudDto);
        return cloudDto;
    }

    public List<StatueDto> getStatues() {
        return statues.values().stream().toList();
    }

    public List<CloudDto> getClouds(String statueName) {
        return clouds.get(statueName);
    }

    private CloudDto doConvertCloud(Cloud cloud, String content) {
        String cloudKey = getCloudKey(cloud.getUser(), cloud.getCloudId().getStatue(), cloud);
        if (content == null) {
            content = s3Service.fetchObject(cloudKey);
        }
        return converter.convert(cloud, content);
    }

    private String getCloudKey(User user, Statue statue, Cloud cloud) {
        return statue.getStatueName() + "/" + user.getUsername() + "-" + cloud.getCloudId().getCloudId() + ".svg";
    }

}
