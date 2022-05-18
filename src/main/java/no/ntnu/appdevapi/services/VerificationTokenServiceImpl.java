package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.VerificationTokenRepository;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public String getTokenFromUser(User user) {
        return verificationTokenRepository.findFirstByUser(user).getToken();
    }

    @Override
    public void addVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationTokenByToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }
}
