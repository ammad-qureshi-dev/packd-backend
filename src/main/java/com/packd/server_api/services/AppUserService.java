package com.packd.server_api.services;

import com.packd.server_api.exceptions.ApiException;
import com.packd.server_api.exceptions.ExceptionCode;
import com.packd.server_api.models.AppUser;
import com.packd.server_api.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUser getAppUserById(UUID appUserId) {
        var appUser = appUserRepository.findById(appUserId);
        if (appUser.isEmpty()) {
            throw new ApiException(ExceptionCode.APP_USER_NOT_FOUND, "User not found");
        }

        return appUser.get();
    }
}
