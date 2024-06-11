package app.rest;

import app.APIConfig;
import app.exceptions.PreConditionFailed;
import app.models.User;
import app.models.ViewClasses;
import app.security.JWToken;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static app.models.User.createSampleUser;
import static app.models.User.extractNameOfEmail;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    APIConfig apiConfig;
    @JsonView(ViewClasses.Summary.class)
    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<User> authenticateAccount(@RequestBody ObjectNode signInInfo){
        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        String userName = extractNameOfEmail(email);

        if(!Objects.equals(userName, password)){
            throw new NotAcceptableStatusException("Cannot authenticate user by email= " + email);
        }

        // User
        User user = createSampleUser(0, email , password);

        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(user.getName(), user.getId(), user.getRole());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}
