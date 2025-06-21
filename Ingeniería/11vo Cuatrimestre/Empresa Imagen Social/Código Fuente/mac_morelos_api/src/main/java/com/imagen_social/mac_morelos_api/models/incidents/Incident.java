package com.imagen_social.mac_morelos_api.models.incidents;

import jakarta.persistence.*;
import lombok.*;

import com.imagen_social.mac_morelos_api.enums.statusIncidents.StatusIncidents;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.users.User;

import java.sql.Timestamp;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incident_id")
    private Long incidentId;

    @ManyToOne
    @JoinColumn(name = "promoter_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_incidents_promoter"))
    private User promoter;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_incidents_assigned_to"))
    private User assignedTo;

    @Column(name = "name_incident", nullable = false)
    private String incidentName;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", foreignKey = @ForeignKey(name = "fk_incidents_addresses"))
    private Address address;

    @Column(name = "status_incident", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusIncidents statusIncident = StatusIncidents.PENDIENTE;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "resolved_at")
    private Timestamp resolvedAt;
}
