package org.flutterstudy.api.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.flutterstudy.api.domain.file.FileMetaData;
import org.flutterstudy.api.contracts.vo.UserName;
import org.flutterstudy.api.domain.AggregateRoot;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.entity.UserIdentifier;
import org.flutterstudy.api.domain.user.enums.UserIdentifierType;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.contracts.vo.EmailAddress;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements AggregateRoot {

    private UserBase base;

    private Set<UserIdentifier> identifiers;

    public List<Object> getEntities(){
        List<Object> entities = new ArrayList<>();
        if(identifiers != null) {
            entities.addAll(identifiers);
        }
        entities.add(base);

        return entities;
    }

    public String getPassword() {
        return base.getPassword();
    }

    public Long getPrimaryId() {
        return base.getPrimaryId();
    }

    public Set<UserRole> getRoles() {
        return base.getRoles();
    }

    public void addRole(UserRole testRole) {
        base.addRole(testRole);
    }

    public void setAvatar(FileMetaData fileData) {
        this.base.setAvatarFileId(fileData.getId());
    }

    public static User of(UserBase base){
        User user = new User();
        user.base = base;
        return user;
    }

    public static UserBuilder builder(Long primaryId, String birth){
        return new UserBuilder(primaryId, birth);
    }

    public Long getAvatarFileId() {
        return base.getAvatarFileId();
    }

    public void setName(UserName userName) {
        if(identifiers == null){
            identifiers = new HashSet<>();
        }

        identifiers.forEach((identifier) -> {
            if(identifier.equalsType(UserIdentifierType.USER_NAME)){
                identifier.setIsDropout(true);
            }
        });

        identifiers.add(new UserIdentifier(UserIdentifierType.USER_NAME, userName.getValue(), base.getPrimaryId()));
        base.setUserName(userName.getValue());
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

