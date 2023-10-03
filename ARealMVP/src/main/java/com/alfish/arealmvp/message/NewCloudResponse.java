package com.alfish.arealmvp.message;

import com.alfish.arealmvp.dto.CloudDto;
import com.alfish.arealmvp.enums.RequestRejectReason;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewCloudResponse {

    private String username;
    private String statueName;
    private List<CloudDto> clouds;
    private RequestRejectReason rejectReason;

}
