package com.alfish.arealmvp.message;

import com.alfish.arealmvp.enums.UserSex;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRegisterRequest {

	private String username;
	private String email;
	private String password;
	private String passwordConfirm;
	private String firstName;
	private String lastName;
	private UserSex sex;
	private Long birthday;

}
