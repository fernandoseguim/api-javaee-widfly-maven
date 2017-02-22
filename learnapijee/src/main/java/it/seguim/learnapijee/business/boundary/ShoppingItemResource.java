package it.seguim.learnapijee.business.boundary;

import it.seguim.learnapijee.business.entity.ShoppingListItem;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 22/02/17.
 */
@Path("shoppinglists")
@Produces({"application/json","application/xml"})
@Consumes({"application/json","application/xml"})
public class ShoppingItemResource {

    @GET
    public List<ShoppingListItem> getAll(){
        List<ShoppingListItem> shoppingListItems = new ArrayList<>();
        shoppingListItems.add(new ShoppingListItem("Coffee",100));
        shoppingListItems.add(new ShoppingListItem("Pizza",50));

        return shoppingListItems;
    }

    /**
     * Find ID - GET /id
     * Save - POST {Objeto para guardar}
     * Update - PUT /id {objeto para alterar}
     * Delete ID - DELETE /id
     * **/

    @GET
    @Path("{id}")
    public ShoppingListItem find(@PathParam("id") long id){

        ShoppingListItem bolachas = new ShoppingListItem("Bolachas", 20);
        bolachas.setId(100);
        return bolachas;

    }

    @POST
    public Response save(ShoppingListItem shoppingListItem, @Context UriInfo uriInfo){

        shoppingListItem.setId(1);
        System.out.println("SAVED " + shoppingListItem);
        URI uri = uriInfo.getAbsolutePathBuilder().path("/"+shoppingListItem.getId()).build();
        return Response.created(uri).entity(shoppingListItem).build();
    }

    @PUT
    @Path("{id}")
    public ShoppingListItem update(@PathParam("id") long id, ShoppingListItem shoppingListItem){

        shoppingListItem.setId(id);
        System.out.println("UPDATE " + shoppingListItem);
        return shoppingListItem;
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){

        System.out.println("DELETED OBJECT WITH ID " + id);
        return Response.ok().build();
    }

}
