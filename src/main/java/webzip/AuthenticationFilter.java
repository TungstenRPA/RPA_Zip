/*
 * https://cassiomolin.com/2014/11/06/token-based-authentication-with-jaxrs-20/
 */
package webzip;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext requestContext) throws IOException {

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if ( requestContext.getRequest().getMethod().equals( "OPTIONS" ) ) {
            requestContext.abortWith( Response.status( Response.Status.OK ).build() );
 
            return;
        }
        
        // Then check is the service key exists and is valid.
        Authenticator authenticator = Authenticator.getInstance();
        // Get the Authorization header from the request
        String serviceKey = requestContext.getHeaderString( HTTPHeaderNames.SERVICE_KEY );

        if ( !authenticator.isServiceKeyValid( serviceKey ) ) {
            // Kick anyone without a valid service key
            Response.ResponseBuilder response = Response.status( Response.Status.UNAUTHORIZED );
            response.header(HTTPHeaderNames.WEBZIP_ERROR, "Status: " + Response.Status.UNAUTHORIZED.toString() + "; " + HTTPHeaderNames.SERVICE_KEY + " HTTP header with valid key required!");
            requestContext.abortWith( response.build() );
  
            return;
        }
  

    }

}