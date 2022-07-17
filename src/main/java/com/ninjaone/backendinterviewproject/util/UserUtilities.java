package com.ninjaone.backendinterviewproject.util;

import com.ninjaone.backendinterviewproject.controller.exception.model.NotAuthorizedException;
import com.ninjaone.backendinterviewproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtilities {

    private UserUtilities(){}

    public static void validateConsistency(final String customerId) throws NotAuthorizedException {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(isCustomer(auth) && !UserUtilities.getUserInfo(auth).getId().equals(customerId)){
                throw new NotAuthorizedException("Customers can only handle their own data");
        }
    }

    private static boolean isAdmin(final Authentication auth){

        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private static boolean isCustomer(final Authentication auth){

        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
    }

    private static User getUserInfo(final Authentication auth){

        return (User) auth.getPrincipal();
    }
}
