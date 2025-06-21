package com.imagen_social.mac_morelos_api.models.events;

import jakarta.persistence.*;
import lombok.*;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusEvents;
import com.imagen_social.mac_morelos_api.models.addresses.Address;
import com.imagen_social.mac_morelos_api.models.users.User;
import java.sql.Timestamp;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", foreignKey = @ForeignKey(name = "fk_events_addresses"))
    private Address address;

    @Column(name = "location", length = 255)
    private String location;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_events_users"))
    private User createdBy;

    @Column(name = "status_event", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusEvents statusEvent = StatusEvents.PENDIENTE;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}

