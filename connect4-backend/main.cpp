#include <iostream>
#include "./httplib.h"


int main ()
{
    // Currently this works, we can check http://localhost:8080/hello-world
    // You will be able to get a response.
    httplib::Server svr;
     
    svr.Get("/random", [](const httplib::Request& req, httplib::Response &res){
        
        // Actually, this is literally all we need.
        // Because on the request, I can do the computation
        // Then below, return my json string in the response.
        res.set_content("Fuck you", "text/html");

        // There is indeed issues with this idea though.
        // How long until the request times out?
        // TBH though, If it gets to that point then I coudl find a solution.

        // Otherwise, it's AI time!
    });
    
    svr.listen("localhost", 8080);
    return 0;

}



