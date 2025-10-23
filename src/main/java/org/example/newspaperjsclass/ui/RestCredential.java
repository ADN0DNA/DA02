package org.example.newspaperjsclass.ui;


import org.example.newspaperjsclass.domain.model.CredentialDTO;
import org.example.newspaperjsclass.domain.service.CredentialService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestCredential {

    private final CredentialService credentialService;

    public RestCredential(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public boolean login(@RequestBody CredentialDTO userCredentialsUI) {
        return credentialService.checkLogin(userCredentialsUI);
    }
}