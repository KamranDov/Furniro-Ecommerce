package az.crocusoft.ecommerce.model;


import az.crocusoft.ecommerce.enums.Role;
import az.crocusoft.ecommerce.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Enumerated(EnumType.STRING)
    Role role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy ="user" ,cascade = CascadeType.REMOVE)
    private List<Token> tokenList;


    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
