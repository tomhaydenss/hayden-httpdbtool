package hayden.httpdbtool.client;

public class DataServiceClientFactory {
	
	public static DataServiceClient create(String uriTargetBase) {
		return new DataServiceClientImpl(uriTargetBase);
	}

}
