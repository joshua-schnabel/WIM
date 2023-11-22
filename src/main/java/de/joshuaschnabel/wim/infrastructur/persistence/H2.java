package de.joshuaschnabel.wim.infrastructur.persistence;

import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile({ "h2", "h2-prod" })
public class H2 {

	private static Boolean done = false;

	private org.h2.tools.Server webServer;

	private org.h2.tools.Server tcpServer;

	@EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
	public void start() throws java.sql.SQLException {
		if (!done) {
			this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
			this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
			done = true;
		}
	}

	@EventListener(org.springframework.context.event.ContextClosedEvent.class)
	public void stop() {
		this.tcpServer.stop();
		this.webServer.stop();
	}

}