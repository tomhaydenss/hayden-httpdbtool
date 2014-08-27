package hayden.httpdbtool.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/httpdbtool/dataservice/datasource/{datasource}")
@Consumes(MediaType.TEXT_PLAIN)
public interface DataServiceClient {
	
	@POST
	@Path("/read")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response read(@PathParam("datasource") String dataSource, String sqlFromBody, @HeaderParam("Accept") String acceptHeader);

	@POST
	@Path("/write")
	@Produces(MediaType.TEXT_PLAIN)
	public Response write(@PathParam("datasource") String dataSource, String sqlFromBody);

}
