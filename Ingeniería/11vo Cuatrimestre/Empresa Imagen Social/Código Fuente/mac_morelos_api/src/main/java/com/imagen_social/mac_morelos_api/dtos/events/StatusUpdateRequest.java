package com.imagen_social.mac_morelos_api.dtos.events;

import com.imagen_social.mac_morelos_api.enums.statusEvents.StatusEvents;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {
    private Long eventId;
    private StatusEvents statusEvent;
}
