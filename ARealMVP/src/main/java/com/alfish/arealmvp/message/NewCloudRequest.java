package com.alfish.arealmvp.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewCloudRequest {

    private String username;
    private String statueName;
    private String svgContent;

}
