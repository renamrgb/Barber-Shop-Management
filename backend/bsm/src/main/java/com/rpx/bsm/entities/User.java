package com.rpx.bsm.entities;


import com.rpx.bsm.records.UserRecord;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Entity
@Table(name = "usertab")
@Getter
@Setter
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incremento
    private Long Id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "VARCHAR(16)")
    private String typePerson;
    private String password;
    private String phoneNumber;
    private String document;
    private String rg;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accesslevel_id")
    private AccessLevel accessLevel;
    private Boolean isActive;

    public User() {
        this.address = new Address();
        this.accessLevel = new AccessLevel();
    }

    public User(Long id) {
        Id = id;
    }

    public User(UserRecord record) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.setName(record.name());
        this.setEmail(record.email());
        if (record.password() != null)
            this.setPassword(bCryptPasswordEncoder.encode(record.password()));
        this.setPhoneNumber(record.phoneNumber());
        this.setDocument(record.document());
        this.setTypePerson(record.typePerson());
        this.setRg(record.rg());
        this.setIsActive(record.isActive());
        this.address = new Address();
        if(record.address() != null){
            this.getAddress().setZipCode(record.address().zipCode());
            this.getAddress().setPublicPlace(record.address().publicPlace());
            this.getAddress().setNeighborhood(record.address().neighborhood());
            this.getAddress().setComplement(record.address().complement());
            this.getAddress().setCity(record.address().city());
            this.getAddress().setState(record.address().state());
        }
        this.accessLevel = new AccessLevel();
    }

    public User(String name, String email, String password, String phoneNumber, String document, String rg, Address address, AccessLevel accessLevel, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.document = document;
        this.rg = rg;
        this.address = address;
        this.accessLevel = accessLevel;
        this.isActive = isActive;
    }

    public User(String name, String email, String password, String phoneNumber, String document, String rg, AccessLevel accessLevel, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.document = document;
        this.rg = rg;
        this.accessLevel = accessLevel;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User usuario = (User) o;
        return Objects.equals(Id, usuario.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", document='" + document + '\'' +
                ", rg='" + rg + '\'' +
                ", address=" + address +
                ", accessLevel=" + accessLevel +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(
                accessLevel.getAuthority().name()
        ));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
