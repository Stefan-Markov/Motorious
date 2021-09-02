package projectdefence.security.rolesAuth;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#username == authentication.name && hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//@RolesAllowed({"ROLE_USER"})
public @interface IsProfileUser {
}
