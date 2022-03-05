package petcare.api;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.resteasy.annotations.cache.NoCache;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/user/whoami")
public class WhoAmIResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed("user")
    @NoCache
    public String me() {
        var username = securityIdentity.getPrincipal().getName();
        return username;
    }
}