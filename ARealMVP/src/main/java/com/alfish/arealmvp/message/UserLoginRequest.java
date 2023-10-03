package com.alfish.arealmvp.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserLoginRequest {

	private String username;
	private String password;

}
