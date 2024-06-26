package fr.uphf.users.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Email
    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    @NotBlank
    private String numeroTelephone;

    @NotBlank
    @Column(nullable = false)
    private String motDePasse;




}
