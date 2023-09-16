package com.tiep.profileservice.service;

import com.tiep.profileservice.model.ProfileDTO;
import com.tiep.profileservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Flux<ProfileDTO> getAllProfile() {
        return profileRepository.findAll()
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new Exception("Profile list empty")));
    }
}
