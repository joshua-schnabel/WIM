package de.joshuaschnabel.wim.application.code;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Data;

@Service
public class DoSCodeService {
	@Data
	@Builder
	public static class DosAccessLog {
		private Long lastAccess;
		private Integer count;

		public void incCounter() {
			this.count++;
		}
	}

	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	private Map<String, DosAccessLog> log = new HashMap<>();

	public boolean checkDos(String ip) {
		this.updateEntry(ip);
		var logEntry = this.log.get(ip);
		return logEntry.getCount() >= 10;
	}

	public String getIP(ServerHttpRequest request) {
		for (String header : IP_HEADER_CANDIDATES) {
			var ipList = request.getHeaders().getFirst(header);
			if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
				return ipList.split(",")[0];
			}
		}
		return request.getRemoteAddress().getAddress().toString();
	}

	private void updateEntry(String ip) {
		this.log.computeIfAbsent(ip,
				x -> DosAccessLog.builder().count(0).lastAccess(System.currentTimeMillis()).build());
		var logEntry = this.log.get(ip);
		if (System.currentTimeMillis() - logEntry.getLastAccess() >= 300000) {
			logEntry.setCount(1);
		} else {
			logEntry.incCounter();
		}
		logEntry.setLastAccess(System.currentTimeMillis());
		this.log.put(ip, logEntry);
	}

}
