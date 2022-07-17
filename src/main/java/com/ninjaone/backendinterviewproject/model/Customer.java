package com.ninjaone.backendinterviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    public Customer(final String id, final String email, final String password, final Set<Role> roles, final Set<Device> devices){
        super(id,email,password,roles);
        this.devices = devices;
    }

    @OneToMany
    @JoinColumn(name="customer_id", referencedColumnName="id")
    private Set<Device> devices;
}
