package com.alfish.arealmvp.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetCloudsRequest {

    private String username;
    private String statueName;
    private Boolean initial;

}
