package com.imagen_social.mac_morelos_api.models.promoterAssignments;

import jakarta.persistence.*;
import lombok.*;

import com.imagen_social.mac_morelos_api.models.users.User;

import java.sql.Timestamp;

@Entity
@Table(name = "promoter_assignments", uniqueConstraints = @UniqueConstraint(columnNames = {"promoter_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoterAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne
    @JoinColumn(name = "promoter_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_promoter_assignments_promoter"))
    private User promoter;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_promoter_assignments_user"))
    private User user;

    @Column(name = "assigned_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp assignedAt;
}
