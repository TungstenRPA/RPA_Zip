/*
 * https://cassiomolin.com/2014/11/06/token-based-authentication-with-jaxrs-20/
 */
package webzip;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@NameBinding
public @interface Secured {
    
}
