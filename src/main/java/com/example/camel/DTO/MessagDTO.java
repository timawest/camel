package com.example.camel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagDTO {
    String number;
    String firstname;
    String lastname;
    String middlename;

    @Override
    public String toString() {
        return "Messag{" +
                "number='" + number + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                '}';
    }
}
