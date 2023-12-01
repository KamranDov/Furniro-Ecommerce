package az.crocusoft.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    public String findUsername(String token) {


        return exportToken(token, Claims::getSubject);

    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();//claimsleri qaytarir
        return claimsTFunction.apply(claims); //token esasindan tek bir claims qaytarir
    }

    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }


    public boolean tokenControl(String jwt, UserDetails userDetails) {
        //token icindeki userle userdetailsi eyni olmasi  ve tokenin kecerliyini yoxluyur
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !exportToken(jwt, Claims::getExpiration).before(new Date()));
    }




    public String generateToken(UserDetails user) {

            return buildToken(user,jwtExpiration);
        }


    public String generateRefreshToken(UserDetails userDetails) {

        return buildToken( userDetails, refreshExpiration);

    }


    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


}
