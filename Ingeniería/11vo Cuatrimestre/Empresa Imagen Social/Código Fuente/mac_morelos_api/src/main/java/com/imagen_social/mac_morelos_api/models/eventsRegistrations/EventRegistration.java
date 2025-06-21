package com.imagen_social.mac_morelos_api.models.eventsRegistrations;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusRegistrationEvent;
import com.imagen_social.mac_morelos_api.models.events.Event;

import java.sql.Timestamp;

@Entity
@Table(name = "event_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", foreignKey = @ForeignKey(name = "fk_event_registrations_events"))
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_event_registrations_users"))
    private User user;

    @Column(name = "status_register_event", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusRegistrationEvent statusRegisterEvent = StatusRegistrationEvent.PENDIENTE;

    @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;
}
