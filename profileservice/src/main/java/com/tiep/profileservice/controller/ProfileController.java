package com.tiep.profileservice.controller;

import com.google.gson.Gson;
import com.tiep.commonservice.utils.CommonFunction;
import com.tiep.profileservice.data.Profile;
import com.tiep.profileservice.model.ProfileDTO;
import com.tiep.profileservice.service.ProfileService;
import com.tiep.profileservice.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    Gson gson = new Gson();

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>> getAllProfile() {
        return ResponseEntity.ok(profileService.getAllProfile());
    }

    @GetMapping(value = "/checkDuplicate/{email}")
    public ResponseEntity<Mono<Boolean>> checkDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(profileService.checkDuplicate(email));
    }

    @PostMapping
    public ResponseEntity<Mono<ProfileDTO>> createNewProfile(@RequestBody String requestStr) {
        InputStream inputStream = ProfileController.class.getClassLoader().getResourceAsStream(Constant.JSON_REQ_CREATE_PROFILE);
        // validate, nếu không qua được sẽ throw và trả về exception
        CommonFunction.jsonValidate(inputStream, requestStr);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.createNewProfile(gson.fromJson(requestStr, ProfileDTO.class)));
    }
}
