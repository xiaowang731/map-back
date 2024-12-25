/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.oauthservice.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.oauthservice.constants.SecurityConstants.DEVICE_ACTIVATE_URI;
import static com.example.oauthservice.constants.SecurityConstants.NONCE_HEADER_NAME;

/**
 * @author Steve Riesenberg
 * @since 1.1
 */
@Controller
public class DeviceController {

	@GetMapping("/activate/redirect")
	public String activateRedirect(HttpSession session,
								   @RequestParam(value = "user_code", required = false) String userCode) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromUriString(DEVICE_ACTIVATE_URI)
				.queryParam("userCode", userCode)
				.queryParam(NONCE_HEADER_NAME, session.getId());
		return "redirect:" + uriBuilder.build(Boolean.TRUE).toUriString();
	}


}
