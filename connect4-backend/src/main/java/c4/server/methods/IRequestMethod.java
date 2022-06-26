package c4.server.methods;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

/**
 * Base Method command for other Request Method Types.
 */
public interface IRequestMethod {
    public void respond(HttpExchange e, String response) throws IOException;
    public String getType();
}
