package com.ninjaone.backendinterviewproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String systemName;

    @Column(name = "customer_id")
    private String customerId;

    @OneToOne
    private DeviceType type;

    @ManyToMany
    private Set<Service> services;

    public double calculateDeviceCost() {
        double deviceCost = 0;
        for(Service service: this.services){
            final Set<Cost> costs = service.getCosts();
            deviceCost += getCostToApply(costs);
        }
        return deviceCost;
    }

    private double getCostToApply(final Set<Cost> costs){

        Optional<Cost> cost = costs.stream().filter(c -> c.getDeviceType().getDetail().toUpperCase(Locale.ROOT).equals("ALL")).findFirst();
        if(cost.isEmpty()){
            cost = costs.stream().filter(c -> c.getDeviceType().getId().equals(type.getId())).findFirst();
        }
        return cost.map(Cost::getValue).orElse(0.0);
    }

    public Set<String> getServicesNames() {
        return services.stream().map(com.ninjaone.backendinterviewproject.model.Service::getDescription).collect(Collectors.toSet());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
}
