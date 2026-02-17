package com.bichomania.clinicavet.domain.shared.valueobjects;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    private static final String EMAIL_REQUIRED = ExceptionMessages.GUARDIAN_EMAIL_REQUIRED;
    private static final String EMAIL_INVALID  = ExceptionMessages.GUARDIAN_EMAIL_INVALID;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");

    @Column(name = "email", nullable = false, length = 150)
    private String value;

    /** Exigido pelo JPA. */
    protected Email() {}

    public Email(String value) {
        this.value = validate(value);
    }

    private static String validate(String value) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(EMAIL_REQUIRED);
        String trimmed = value.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(trimmed).matches())
            throw new IllegalArgumentException(EMAIL_INVALID);
        return trimmed;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email e)) return false;
        return Objects.equals(value, e.value);
    }

    @Override
    public int hashCode() { return Objects.hash(value); }

    @Override
    public String toString() { return value; }
}