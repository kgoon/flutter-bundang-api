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
import org.flutterstudy.api.infra.repository.identifier.LongTypeIdentifier;

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

    public Long getAvatarFileId() {
        return base.getAvatarFileId();
    }

    public void setName(UserName userName) {
        getIdentifier(UserIdentifierType.USER_NAME).ifPresent((identifier -> identifier.setIsDropout(true)));
        identifiers.add(new UserIdentifier(UserIdentifierType.USER_NAME, userName.getValue(), base.getPrimaryId()));
        base.setUserName(userName.getValue());
    }

    void addIdentifier(UserIdentifier identifier){
        if(identifiers == null){
            identifiers = new HashSet<>();
        }

        identifiers.add(identifier);
    }

    Optional<UserIdentifier> getIdentifier(UserIdentifierType identifierType){
        if(identifiers == null){
            return Optional.empty();
        }

        for(UserIdentifier identifier : identifiers){
            if(identifier.equalsType(identifierType) && !identifier.getIsDropout()){
                return Optional.of(identifier);
            }
        }

        return Optional.empty();
    }

    public String getName() {
        return base.getUserName();
    }

    public EmailAddress getEmail() {
        Optional<UserIdentifier> emailIdentifier = getIdentifier(UserIdentifierType.EMAIL);
        if(emailIdentifier.isPresent()){
            return new EmailAddress(emailIdentifier.get().getValue());
        }

        return null;
    }

    public static User of(UserBase base, Set<UserIdentifier> identifiers){
        User user = new User();
        user.base = base;
        user.identifiers = identifiers;
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

