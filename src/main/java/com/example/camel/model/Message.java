package com.example.camel.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "messages")
@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "full_Name")
    private String fullName;

    public Message(String number, String fullName) {
        this.number = number;
        this.fullName = fullName;
    }
}
