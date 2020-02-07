package org.flutterstudy.api.test.util;

import org.flutterstudy.api.service.identifier.IdentifierProvider;

public class MockLongTypeIdRepository implements IdentifierProvider<Long> {

    private Long idNumber = 0L;

    @Override
    public Long create(Class entity) {
        return ++idNumber;
    }

}
