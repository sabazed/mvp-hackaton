package com.alfish.arealmvp.message;

import com.alfish.arealmvp.enums.AuthRejectReason;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangePasswordResponse {

	private String username;
	private AuthRejectReason rejectReason;

}
