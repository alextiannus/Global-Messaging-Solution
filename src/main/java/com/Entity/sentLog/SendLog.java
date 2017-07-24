package com.Entity.sentLog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Donnie on 2017/2/21.
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class SendLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date sendDate;
}
