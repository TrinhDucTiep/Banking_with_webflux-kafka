package com.tiep.profileservice.service;

import com.tiep.commonservice.common.CommonException;
import com.tiep.profileservice.data.Profile;
import com.tiep.profileservice.model.ProfileDTO;
import com.tiep.profileservice.repository.ProfileRepository;
import com.tiep.profileservice.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {
    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;

    public Flux<ProfileDTO> getAllProfile() {
        return profileRepository.findAll()
                .map(ProfileDTO::entityToDto)
                .switchIfEmpty(Mono.error(new Exception("Profile list empty")));
    }

    public Mono<Boolean> checkDuplicate(String email) {
        return profileRepository.findByEmail(email)
                .flatMap(profile -> Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }

    // hàm này sau khi tạo xong sẽ trả về profileDTO với status pending
    public Mono<ProfileDTO> createNewProfile(ProfileDTO profileDTO) {
        return checkDuplicate(profileDTO.getEmail())
                .flatMap(isDuplicate -> {
                    if (Boolean.TRUE.equals(isDuplicate)) {
                        return Mono.error(new CommonException("PF02", "Duplicate profile!", HttpStatus.BAD_REQUEST));
                    } else {
                        profileDTO.setStatus(Constant.STATUS_PROFILE_PENDING);
                        return createProfile(profileDTO);
                    }
                });
    }

    private Mono<ProfileDTO> createProfile(ProfileDTO profileDTO) {
        return Mono.just(profileDTO)
                .map(ProfileDTO::dtoToEntity)
                .flatMap(profile -> profileRepository.save(profile))
                .map(ProfileDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .doOnSuccess(dto -> {

                });
    }
}
