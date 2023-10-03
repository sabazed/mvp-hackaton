package com.alfish.arealmvp.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangePasswordRequest {

	private String username;
	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;

}
