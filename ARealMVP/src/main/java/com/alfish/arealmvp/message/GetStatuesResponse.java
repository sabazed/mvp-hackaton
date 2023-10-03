package com.alfish.arealmvp.message;

import com.alfish.arealmvp.dto.StatueDto;
import com.alfish.arealmvp.enums.RequestRejectReason;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetStatuesResponse {

    private String username;
    private List<StatueDto> statues;
    private RequestRejectReason rejectReason;

}
