package com.breakingadv.slipserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ConnectionPK implements Serializable {

    private int uid;
    private String cctvName;

}
