package hayden.httpdbtool;

import hayden.httpdbtool.client.DataServiceClient;
import hayden.httpdbtool.client.DataServiceClientFactory;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataServiceClientTest {

	private static final Logger logger = Logger.getLogger(DataServiceClientTest.class);

	private static final String URI_TARGET_BASE = "http://localhost:8080";
	private static final String SAKILA_DS = "SakilaDS";
	private static final String SAKILA_CREATE_QUERY = "INSERT INTO ACTOR (first_name, last_name) VALUES ('TOM', 'HAYDEN')";
	private static final String SAKILA_READ_QUERY = "SELECT * FROM ACTOR WHERE first_name = 'TOM' AND last_name = 'HAYDEN'";
	private static final String SAKILA_DELETE_QUERY = "DELETE FROM ACTOR WHERE first_name = 'TOM' AND last_name = 'HAYDEN'";

	private DataServiceClient dataServiceClient;

	@Before
	public void init() {
		dataServiceClient = DataServiceClientFactory.create(URI_TARGET_BASE);
	}

	@Test
	public void shouldCreateReadAndRemoveActor() {
		Response createResponse = dataServiceClient.write(SAKILA_DS, SAKILA_CREATE_QUERY);
		String createContent = createResponse.readEntity(String.class);
		Assert.assertEquals("(1 row(s) affected)", createContent);

		Response readResponse = dataServiceClient.read(SAKILA_DS, SAKILA_READ_QUERY, "application/json");
		String readContent = readResponse.readEntity(String.class);
		Assert.assertTrue(readContent.contains("TOM"));
		Assert.assertTrue(readContent.contains("HAYDEN"));

		Response deleteResponse = dataServiceClient.write(SAKILA_DS, SAKILA_DELETE_QUERY);
		String deleteContent = deleteResponse.readEntity(String.class);
		Assert.assertEquals("(1 row(s) affected)", deleteContent);
	}

}
