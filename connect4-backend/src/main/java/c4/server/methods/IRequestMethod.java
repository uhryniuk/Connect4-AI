package c4.server.methods;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import c4.server.endpoint.Endpoint;

/**
 * Base Method command for other Request Method Types.
 */
public interface IRequestMethod {
    public void respond(HttpExchange e, Endpoint response) throws IOException;
    public String getType();
}
