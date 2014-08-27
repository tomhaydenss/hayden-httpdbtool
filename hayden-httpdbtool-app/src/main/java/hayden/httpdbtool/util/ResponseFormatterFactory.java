package hayden.httpdbtool.util;

import hayden.httpdbtool.service.util.ResponseFormatter;

import javax.ws.rs.core.MediaType;

public class ResponseFormatterFactory {

	public static ResponseFormatter getFormatter(String acceptHeader) {
		if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_XML)) {
			return new XMLResponseFormatter();
		} else {
			return new JSONResponseFormatter();
		}
	}

}
