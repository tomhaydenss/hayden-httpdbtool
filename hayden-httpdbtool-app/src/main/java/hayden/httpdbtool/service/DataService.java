package hayden.httpdbtool.service;

import hayden.httpdbtool.persistence.JDBCDAOImpl;
import hayden.httpdbtool.persistence.JPADAOImpl;
import hayden.httpdbtool.service.util.ResponseFormatter;
import hayden.httpdbtool.util.ResponseFormatterFactory;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/dataservice")
@Consumes(MediaType.TEXT_PLAIN)
public class DataService {

	@POST
	@Path("/datasource/{datasource}/read")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response read(@HeaderParam("Accept") String acceptHeader, @PathParam("datasource") String dataSource, String sqlFromBody) {

		ResponseFormatter formatter = ResponseFormatterFactory.getFormatter(acceptHeader);
		JDBCDAOImpl dao = new JDBCDAOImpl(dataSource);
		List<Map<String, Object>> result;
		try {
			result = dao.read(sqlFromBody);
			if (result != null && result.size() > 0) {
				return Response.ok(formatter.format(result)).type(MediaType.APPLICATION_JSON).build();
			} else {
				return noDataResponse(0);
			}
		} catch (Exception e) {
			return serverErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/persistenceunit/{pu}/read")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response readPU(@PathParam("pu") String persistenceUnit, String hqlFromBody) {

		JPADAOImpl dao = new JPADAOImpl(persistenceUnit);

		@SuppressWarnings("rawtypes")
		List result;
		try {
			result = dao.read(hqlFromBody);
			if (result != null && result.size() > 0) {
				StringBuilder builder = new StringBuilder();
				for (Object row : result) {
					builder.append(row.toString()).append("\n");
				}
				return Response.ok(builder.toString()).type(MediaType.TEXT_PLAIN).build();
			} else {
				return noDataResponse(0);
			}
		} catch (Exception e) {
			return serverErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/datasource/{datasource}/write")
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(@PathParam("datasource") String dataSource, String sqlFromBody) {

		JDBCDAOImpl dao = new JDBCDAOImpl(dataSource);
		try {
			int rowsAffected = dao.write(sqlFromBody);
			return noDataResponse(rowsAffected);
		} catch (Exception e) {
			return serverErrorResponse(e.getMessage());
		}
	}

	private Response noDataResponse(int rowsAffected) {
		return Response.ok().entity(String.format("(%d row(s) affected)", rowsAffected)).build();
	}

	private Response serverErrorResponse(String errorMessage) {
		return Response.serverError().entity(errorMessage).build();
	}

}
