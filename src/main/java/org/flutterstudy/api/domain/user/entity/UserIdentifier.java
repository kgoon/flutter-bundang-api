package org.flutterstudy.api.domain.user.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flutterstudy.api.domain.user.enums.UserIdentifierType;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIdentifier {

    private static final String DELIMITER = ":";

    @Id
    String idLiteral;

    @Getter
    @Index
    Long primaryId;

    boolean dropout;

    public UserIdentifier(UserIdentifierType idType, String value, Long userPrimaryId){
        idLiteral = getLiteral(idType, value);
        primaryId = userPrimaryId;
    }

    public static String getLiteral(UserIdentifierType idType, String value) {
        return idType + DELIMITER + value;
    }

	public boolean equalsType(UserIdentifierType identifierType) {
        return idLiteral.startsWith(identifierType.toString());
	}

    public void setIsDropout(boolean value) {
        this.dropout = value;
    }

    public boolean getIsDropout() {
        return dropout;
    }

    public String getValue() {
        return idLiteral.split(DELIMITER)[1];
    }
}
