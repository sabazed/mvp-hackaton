package com.alfish.arealmvp.message;

import com.alfish.arealmvp.enums.AuthRejectReason;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRegisterResponse {

	private String email;
	private String username;
	private AuthRejectReason rejectReason;

}
