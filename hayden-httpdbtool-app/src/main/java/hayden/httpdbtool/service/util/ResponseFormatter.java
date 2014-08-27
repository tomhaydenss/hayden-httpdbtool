package hayden.httpdbtool.service.util;

import java.util.List;
import java.util.Map;

public interface ResponseFormatter {

	String format(List<Map<String, Object>> result);

}
