package com.alfish.arealmvp.util;

import com.alfish.arealmvp.dto.CloudDto;
import com.alfish.arealmvp.dto.StatueDto;
import com.alfish.arealmvp.model.Cloud;
import com.alfish.arealmvp.model.Statue;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public StatueDto convert(Statue statue) {
        StatueDto dto = new StatueDto();
        dto.setStatueName(statue.getStatueName());
        dto.setLatitude(statue.getLatitude());
        dto.setLongitude(statue.getLongitude());
        dto.setAltitude(statue.getAltitude());
        dto.setDegree(statue.getDegree());
        return dto;
    }

    public CloudDto convert(Cloud cloud, String content) {
        CloudDto dto = new CloudDto();
        dto.setStatueName(cloud.getCloudId().getStatue().getStatueName());
        dto.setSvgContent(content);
        return dto;
    }

}
