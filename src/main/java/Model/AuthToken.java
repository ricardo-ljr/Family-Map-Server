package Model;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 * An authorization token associated with a user
 */
public class AuthToken {

    /**
     * AuthToken unique identifier
     */
    private String authToken;

    /**
     * Username associated with the authorization token
     */
    private String associatedUsername;

   public AuthToken(String authToken, String associatedUsername) {
       this.authToken = authToken;
       this.associatedUsername = associatedUsername;
   }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken.equals(authToken1.authToken) && associatedUsername.equals(authToken1.associatedUsername);
    }

}
