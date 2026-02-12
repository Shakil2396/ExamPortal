package com.api.gateway.server;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> { //input me ese Jwt mil raha hoga aur return hora hoga collection of granted authority
    //Converter is functional interface so it has in convert method

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) { //now from source we have to get realm access and role and then convert into granted authourity
        var realAccess = (Map<String, Object>) source.getClaims().get("realm_access");  //it is present in a key value format so in java it is represented in map
        var roles = (List<String>) realAccess.get("roles"); //in realmaccess we have roles
        List<GrantedAuthority> authorities = roles.stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).collect(Collectors.toList());
                //prefix ROLE_ and then aur jo mila hai role use upperclass me convert kr dege ROLE_ADMIN kuch es tarah ho jauega role
        return authorities;
    }
}

