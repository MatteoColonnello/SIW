package it.uniroma3.siw.controller.session;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * SessionData is an interface to save and retrieve specific objects from the current Session.
 * It is mainly used to store the currently logged User and her Credentials
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    // Utente loggato in questo momento
    private Utente utente;

    // Credenziali utente loggato in questo momento
    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;

    /**
     * Retrieve from Session the credentials for the currently logged user.
     * If they are not stored in Session already, retrieve them from the SecurityContext and from the DB
     * and store them in session.
     *
     * @return the retrieved Credentials for the currently logged user
     */
    public Credentials getCredenzialiUtenteLoggato() {
        if (this.credentials == null)
            this.update();
        return this.credentials;
    }

    /**
     * Retrieve from Session the currently logged User.
     * If it is not stored in Session already, retrieve it from the DB and store it in session.
     *
     * @return the retrieved Credentials for the currently logged user
     */
    public Utente getUtenteLoggato() {
        if (this.utente == null)
            this.update();
        return this.utente;
    }

    /**
     * Store the Credentials and User objects for the currently logged user in Session
     */
    private void update() {
        UserDetails loggedUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.credentials = this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
        this.credentials.setPassword("[PROTECTED]");
        this.utente = this.credentials.getUtente();
    }
}