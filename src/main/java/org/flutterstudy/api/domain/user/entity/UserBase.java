package org.flutterstudy.api.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flutterstudy.api.domain.user.enums.UserRole;

import java.util.EnumSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class UserBase {

    @Id
    private Long primaryId;

    @Setter
    @Index(IfNotEmpty.class)
    private String userName;

    private String birth;

    @JsonIgnore
    @Setter
    private String password;

    private Set<UserRole> roles = EnumSet.noneOf(UserRole.class);

    public UserBase(long primaryId, String birth) {
        this.primaryId = primaryId;
        this.birth = birth;
        this.addRole(UserRole.USER);
    }

    public void addRole(UserRole role) {
        roles.add(role);
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public void deleteRole(UserRole role) {
        roles.remove(role);
    }
}
