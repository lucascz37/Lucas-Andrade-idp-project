package br.com.idp.lucas.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {

    private String host;

    private Integer port;
}
