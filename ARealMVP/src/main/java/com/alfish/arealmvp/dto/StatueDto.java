package com.alfish.arealmvp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatueDto {

    private String statueName;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double degree;

}
