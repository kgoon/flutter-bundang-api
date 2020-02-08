package org.flutterstudy.api.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.flutterstudy.api.domain.AggregateRoot;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.entity.UserIdentifier;
import org.flutterstudy.api.domain.user.enums.UserIdentifierType;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.model.EmailAddress;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements AggregateRoot {

    UserBase base;

    Set<UserIdentifier> identifiers;

    public List<Object> getEntities(){
        List<Object> entities = new ArrayList<>();
        entities.addAll(identifiers);
        entities.add(base);
        return entities;
    }

    public String getPassword() {
        return base.getPassword();
    }

    public long getPrimaryId() {
        return base.getPrimaryId();
    }

    public Set<UserRole> getRoles() {
        return base.getRoles();
    }

    public void addRole(UserRole testRole) {
        base.addRole(testRole);
    }

    public static User of(UserBase base){
        User user = new User();
        user.base = base;
        return user;
    }

    public static UserBuilder builder(Long primaryId, String birth){
        return new UserBuilder(primaryId, birth);
    }

    public static class  UserBuilder {
        Long primaryId;

        String birth;

        Set<UserIdentifier> identifiers;
        private String password;

        UserBuilder(Long primaryId, String birth) {
            this.primaryId = primaryId;
            this.birth = birth;
            this.identifiers = new HashSet<>();
        }

        public UserBuilder addIdentifier(EmailAddress email, String password) {
            this.identifiers.add(new UserIdentifier(UserIdentifierType.EMAIL, email.getValue(), this.primaryId));
            this.password = password;
            return this;
        }

        public User build() {
            UserBase userBase = new UserBase(primaryId, birth);
            userBase.setPassword(this.password);

            return new User(userBase, identifiers);
        }

    }
}

