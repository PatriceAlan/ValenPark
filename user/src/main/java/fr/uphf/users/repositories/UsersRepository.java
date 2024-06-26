package fr.uphf.users.repositories;

import fr.uphf.users.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByEmailAndMotDePasse(String email, String motDePasse);
}
