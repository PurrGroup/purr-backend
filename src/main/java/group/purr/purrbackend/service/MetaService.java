package group.purr.purrbackend.service;


import group.purr.purrbackend.repository.BlogmetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface MetaService {
    boolean isInstalled();

}

