package com.alfish.arealmvp.message;

import com.alfish.arealmvp.enums.AuthRejectReason;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserLoginResponse {

	private String username;
	private String token;
	private AuthRejectReason rejectReason;

}
