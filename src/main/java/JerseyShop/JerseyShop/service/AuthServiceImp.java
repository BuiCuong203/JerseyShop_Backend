package JerseyShop.JerseyShop.service;

import JerseyShop.JerseyShop.config.JwtProvider;
import JerseyShop.JerseyShop.constant.DefinedRole;
import JerseyShop.JerseyShop.dto.request.SigninRequest;
import JerseyShop.JerseyShop.dto.request.SignupRequest;
import JerseyShop.JerseyShop.dto.response.AuthResponse;
import JerseyShop.JerseyShop.dto.response.MessageResponse;
import JerseyShop.JerseyShop.exception.AppException;
import JerseyShop.JerseyShop.exception.ErrorCode;
import JerseyShop.JerseyShop.model.Cart;
import JerseyShop.JerseyShop.model.InvalidatedToken;
import JerseyShop.JerseyShop.model.User;
import JerseyShop.JerseyShop.repository.CartRepository;
import JerseyShop.JerseyShop.repository.InvalidatedTokenRepository;
import JerseyShop.JerseyShop.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImp implements AuthService{

    @Value("${jwt.signerKey}")
    private String secretKey;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        boolean isEmailExist = userRepository.existsByEmail(signupRequest.getEmail());
        if(isEmailExist){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User createUser = User.builder()
                .email(signupRequest.getEmail())
                .fullname(signupRequest.getFullname())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(DefinedRole.USER_ROLE)
                .build();
        User savedUser = userRepository.save(createUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(DefinedRole.USER_ROLE));
        Authentication auth = new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);

        AuthResponse authResponse = AuthResponse.builder()
                .token(jwt)
                .message("Signup success")
                .role(DefinedRole.USER_ROLE)
                .build();

        return authResponse;
    }

    @Override
    public AuthResponse signin(SigninRequest signinRequest) {
        String email = signinRequest.getEmail();
        String password = signinRequest.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.WRONG_SIGNIN));
        boolean authenticated = passwordEncoder.matches(password, user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.WRONG_SIGNIN);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);

        AuthResponse authResponse = AuthResponse.builder()
                .token(jwt)
                .message("Signin success")
                .role(user.getRole())
                .build();

        return authResponse;
    }

    @Override
    public MessageResponse signout(String jwt) {
        jwt = jwt.substring(7);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        Date expiryTime = claims.getExpiration();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .token(jwt)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        return MessageResponse.builder()
                .message("Signout success")
                .build();
    }
}
