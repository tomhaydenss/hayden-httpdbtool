package hayden.httpdbtool.util;

import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import hayden.httpdbtool.service.util.ResponseFormatter;

public class XMLResponseFormatter implements ResponseFormatter {

	private static XStream xstream = new XStream();
	
	public String format(List<Map<String, Object>> result) {
		return xstream.toXML(result);
	}

}
