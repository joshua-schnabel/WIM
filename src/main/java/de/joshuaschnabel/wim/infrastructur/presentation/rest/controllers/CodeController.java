package de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.joshuaschnabel.wim.application.code.CodeService;
import de.joshuaschnabel.wim.application.code.DoSCodeService;
import de.joshuaschnabel.wim.domain.invitation.InvitationCode;
import de.joshuaschnabel.wim.domain.invitation.InvitationRepository;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/auth/code")
public class CodeController {

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private CodeService codeService;

	@Autowired
	private DoSCodeService doSCodeService;

//	@GetMapping(path = "/me")
//	public Mono<String> me(Principal authentication) {
//		System.out.println(authentication);
//		return Mono.just(authentication.getName());
//	}

	@PostMapping(path = "/")
	public Mono<ResponseEntity<String>> self(@RequestBody String c,
			@RequestHeader(value = "User-Agent") String userAgent, ServerHttpRequest request) {
		c = c.toLowerCase();
		if (this.doSCodeService.checkDos(this.doSCodeService.getIP(request)) || !InvitationCode.validate(c)
				|| userAgent.length() <= 10) {
			return Mono.just(ResponseEntity.badRequest().build());
		}
		return this.invitationRepository.getByCode(new InvitationCode(c)).map(x -> {
			x.setStatusOpend();
			return this.invitationRepository.store(x);
		}).flatMap(x -> x).map(x -> ResponseEntity.ok().body(this.codeService.generateToken(userAgent, x.getId())))
				.switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
	}

}
