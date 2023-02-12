package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class CredentialService {

    CredentialMapper credentialMapper;
    UserService userService;

    @Autowired
    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public boolean deleteCredential (Integer credentialId, Authentication auth){
        User user = userService.getUser((String) auth.getPrincipal());
        return credentialMapper.deleteCredential(credentialId, user.getUserId());
    }

    public Integer addCredential(Credential credential, Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        Credential credentialInDB = credentialMapper.getCredentialByURLAndUsername(credential);
        if(credentialInDB != null){
            return -1;
        } else {
            credential.setUserId(user.getUserId());
            credential = encryptingCredential(credential);
            return credentialMapper.addCredential(credential);
        }
    }

    public Integer editCredential(Credential credential, Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        credential.setUserId(user.getUserId());
        credential = encryptingCredential(credential);
        return credentialMapper.editCredential(credential);
    }

    public boolean credentialExisted(Credential credential){
        return credentialMapper.getCredentialById(credential.getCredentialId())!=null;
    }

    public List<Credential> getDecryptedCredentialList(Authentication auth){
        User user = userService.getUser((String)auth.getPrincipal());
        List<Credential> credentials = credentialMapper.getCredentialList(user);
        List<Credential> decryptedCredentials = new ArrayList<>();
        for(Credential credential:credentials){
            decryptedCredentials.add(decryptingCredential((credential)));
        }
        return decryptedCredentials;
    }

    private static Credential encryptingCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService enService = new EncryptionService();
        String encryptedPassword = enService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        return credential;
    }

    private static Credential decryptingCredential(Credential credential){
        EncryptionService enService = new EncryptionService();
        String decryptedPassword = enService.decryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(decryptedPassword);
        return credential;
    }
}
