package com.bash.Unitrack.Service;

import com.bash.Unitrack.Data.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtDecoder jwtDecoder;

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generate(User user){

        Instant now = Instant.now();

        List<String> scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(user.getUsername())
                .claim("roles", scope)
                .claim("user_id", user.getId())
                .expiresAt(now.plusSeconds(5000))
                .build();
        System.out.println(claimsSet.getClaims());
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
