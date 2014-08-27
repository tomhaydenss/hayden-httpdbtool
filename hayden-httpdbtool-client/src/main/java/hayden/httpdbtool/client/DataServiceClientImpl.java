package hayden.httpdbtool.client;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class DataServiceClientImpl implements DataServiceClient {

	private DataServiceClient dataServiceClient;

	public DataServiceClientImpl(String uriTargetBase) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(uriTargetBase);
		dataServiceClient = target.proxy(DataServiceClient.class);
	}

	public Response read(String dataSource, String sqlFromBody, String acceptHeader) {
		return dataServiceClient.read(dataSource, sqlFromBody, acceptHeader);
	}

	public Response write(String dataSource, String sqlFromBody) {
		return dataServiceClient.write(dataSource, sqlFromBody);
	}

}
