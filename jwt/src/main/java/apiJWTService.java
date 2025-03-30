import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class apiJWTService {

    public String generateJWT() {
        /*Set<String> groups = new HashSet<>(
        Arrays.asList("admin", "writer")
        );
        long duration=System.currentTimeMillis() + 3600;*/
        return Jwt.issuer("jwt")
                .subject("jwt")
                .groups("admin")
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
