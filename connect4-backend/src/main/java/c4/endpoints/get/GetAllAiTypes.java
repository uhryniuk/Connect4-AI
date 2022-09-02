package c4.endpoints.get;

import c4.server.endpoint.Endpoint;
import com.google.gson.Gson;

public class GetAllAiTypes extends Endpoint{
    public GetAllAiTypes(){
        setMethod("GET");
        setRoute("/api/ai-types");
        setResponse("under construction");
    }
}
