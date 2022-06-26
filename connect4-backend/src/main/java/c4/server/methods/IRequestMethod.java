package c4.server.methods;

import java.io.IOException;
import c4.server.response.Endpoint;
import com.sun.net.httpserver.HttpExchange;

/**
 * Base Method command for other Request Method Types.
 */
public interface IRequestMethod {
    public void respond(HttpExchange e, Endpoint response) throws IOException;
    public String getType();
}
