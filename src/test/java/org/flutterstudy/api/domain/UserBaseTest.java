package org.flutterstudy.api.domain;

import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserBaseTest {

    private UserBase testUser;

    @BeforeEach
    public void prepare(){
        this.testUser = new UserBase(101L, "password");
    }

    @Test
    public void addRole(){
        testUser.addRole(UserRole.STAFF);
        assertThat(testUser.hasRole(UserRole.STAFF)).isTrue();

    }

    @Test
    public void hasRole(){
        testUser.addRole(UserRole.STAFF);
        assertThat(testUser.hasRole(UserRole.STAFF)).isTrue();
        assertThat(testUser.hasRole(UserRole.USER)).isTrue();
    }

    @Test
    public void deleteRole(){
        testUser.addRole(UserRole.STAFF);
        testUser.deleteRole(UserRole.STAFF);
        assertThat(testUser.hasRole(UserRole.STAFF)).isFalse();
    }



}