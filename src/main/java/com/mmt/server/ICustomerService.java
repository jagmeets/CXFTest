package com.mmt.server;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
@Path("/customerservice/")
public interface ICustomerService {

	@GET
    @Path("/customers/{id}/")
    public Customer getCustomer(@PathParam("id") String id);

    @PUT
    @Path("/customers/")
    public Response updateCustomer(Customer customer);

    @POST
    @Path("/customers/")
    public Response addCustomer(Customer customer);

    @DELETE
    @Path("/customers/{id}/")
    public Response deleteCustomer(@PathParam("id") String id);

    @Path("/orders/{orderId}/")
    public Order getOrder(@PathParam("orderId") String orderId);

}
