package manicure.bot.service;

import jakarta.transaction.Transactional;
import manicure.bot.database.ClientRepository;
import manicure.bot.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    public void clientServiceRepository() {
        Client clientOptional = clientRepository.findById(127L);
    }
}
