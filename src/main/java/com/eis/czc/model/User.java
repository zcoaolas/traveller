package com.eis.czc.model;

/**
 * Created by john on 2017/4/2 0002.
 */
import com.eis.czc.util.SystemRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class User implements Serializable {

    private Long id;
    private String u_name;
    private String u_mail;
    private String u_password;
    private Integer u_role;

    private String u_job;
    private Integer u_gender;
    private Integer u_age;

    public boolean hasRole(SystemRole role){
        /*switch (role) {
            case USER:
                return (u_role & 0x1) != 0;
            case REVIEWER:
                return (u_role & 0x2) != 0;
            case EDITOR:
                return (u_role & 0x4) != 0;
            case ADMIN:
                return (u_role & 0x8) != 0;
            default:
                return false;
        }*/
        return u_role >= role.getCharacter();
    }
}
