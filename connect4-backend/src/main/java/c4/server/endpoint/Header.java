package c4.server.endpoint;

public class Header{
    String key = null;
    String value = null;

    public Header(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }
}
