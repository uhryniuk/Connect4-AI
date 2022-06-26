package c4.server.methods;

import java.util.HashMap;

/**
 * Get's the Method Object from given String
 */
public class MethodMapper {
    private static MethodMapper singleton = null;
    private HashMap<String,IRequestMethod> methodMap = null;

    /**
     * Private constructor for the singleton instance,
     */
    private MethodMapper(){
        methodMap = new HashMap<String, IRequestMethod>();
        methodMap.put("GET", new Get());
        methodMap.put("POST", new Post());
    }

    /**
     * Getter for the singleton instance.
     * @return the singleton instance.
     */
    private static MethodMapper getInstance(){
        if (singleton == null){
            singleton = new MethodMapper();
        }
        return singleton;
    }

    /**
     * Primary method to get the Method Type Object.
     * @param method String of the desired method type.
     * @return Return the valid method type if there is any, GET is default.
     */
    public static IRequestMethod getMethod(String method){
        if(MethodMapper.getInstance().methodMap.get(method) != null){
            return MethodMapper.getInstance().methodMap.get(method);
        } 
        return MethodMapper.getInstance().methodMap.get("GET");
    }

}
