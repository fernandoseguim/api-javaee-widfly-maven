import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import org.junit.Rule;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by fernando on 22/02/17.
 */
public class TestShoppingList {

    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/learnapijee-1.0/api/");

    @Test
    public void testGelAll(){
        WebTarget target = provider.target().path("shoppinglists/");
        assertNotNull(target);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray payload = response.readEntity(JsonArray.class);
        assertNotNull(payload);
        assertFalse(payload.isEmpty());

        JsonObject item = payload.getValuesAs(JsonObject.class).get(0);
        assertThat(item.getString("description"), is("Coffee"));
        assertTrue(item.getString("description").equals("Coffee"));

    }

    @Test
    public void testFindById(){
        WebTarget target = provider.target().path("shoppinglists/100");
        assertNotNull(target);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertThat(response.getStatus(), is(200));

        JsonObject payload = response.readEntity(JsonObject.class);
        assertNotNull(payload);
        assertThat(payload.getJsonNumber("id").longValue(), is(100L));
        assertThat(payload.getString("description"),is("Bolachas"));

    }

    @Test
    public void testCreate(){
        WebTarget target = provider.target().path("shoppinglists");
        assertNotNull(target);

        JsonObject shoppingList = Json.createObjectBuilder()
                .add("description","chocolate")
                .add("quantity",10).build();

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(shoppingList));

        assertThat(response.getStatus(), is(201));

        JsonObject payload = response.readEntity(JsonObject.class);
        assertNotNull(payload);
        assertThat(payload.getJsonNumber("id").longValue(), is(1L));


    }

    @Test
    public void testUpdate(){
        WebTarget target = provider.target().path("shoppinglists/1");
        assertNotNull(target);

        JsonObject shoppingList = Json.createObjectBuilder()
                .add("description","chocolate branco")
                .add("quantity",11).build();

        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.json(shoppingList));

        assertThat(response.getStatus(), is(200));

        JsonObject payload = response.readEntity(JsonObject.class);
        assertNotNull(payload);

        assertThat(payload.getJsonNumber("id").longValue(), is(1L));
        assertThat(payload.getInt("quantity"), is(11));
        assertThat(payload.getString("description"), is("chocolate branco"));


    }

    @Test
    public void testDelete(){
        WebTarget target = provider.target().path("shoppinglists/45");
        assertNotNull(target);

        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        assertThat(response.getStatus(), is(200));
    }

}
