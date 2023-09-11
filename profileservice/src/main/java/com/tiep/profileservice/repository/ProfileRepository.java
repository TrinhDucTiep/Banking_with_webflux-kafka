package com.tiep.profileservice.repository;

import com.tiep.profileservice.data.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
}
