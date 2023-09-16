package com.tiep.profileservice.model;

import com.tiep.profileservice.data.Profile;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileDTO {
    private long id;
    private String email;
    private String status;
    private double initialBalance;
    private String name;
    private String role;

    public static Profile dtoToEntity(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setId(dto.getId());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setStatus(dto.getStatus());
        profile.setRole(dto.getRole());
        return profile;
    }

    public static ProfileDTO entityToDto(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setEmail(profileDTO.getEmail());
        profileDTO.setName(profile.getName());
        profileDTO.setStatus(profile.getStatus());
        profileDTO.setRole(profile.getRole());
        return profileDTO;
    }
}
