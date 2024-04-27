package fr.uphf.users.services;

import fr.uphf.users.entities.Users;
import fr.uphf.users.repositories.UsersRepository;

import fr.uphf.users.services.DTO.CreateOrUpdateUserDTO;
import fr.uphf.users.services.DTO.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users loginUser(String email, String motDePasse) {
        return usersRepository.findByEmailAndMotDePasse(email, motDePasse);
    }

    @Transactional
    public Users createUser(CreateOrUpdateUserDTO userDTO) {
        Users user = Users.builder()
                .nom(userDTO.getNom())
                .prenom(userDTO.getPrenom())
                .email(userDTO.getEmail())
                .numeroTelephone(userDTO.getNumeroTelephone())
                .motDePasse(userDTO.getMotDePasse())
                .build();
        return usersRepository.save(user);
    }

    @Transactional
    public Users updateUser(Long idUtilisateur, CreateOrUpdateUserDTO userDTO) {
        Optional<Users> userOptional = Optional.ofNullable(usersRepository.findById(idUtilisateur)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé")));
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setNom(userDTO.getNom());
            user.setPrenom(userDTO.getPrenom());
            user.setEmail(userDTO.getEmail());
            user.setNumeroTelephone(userDTO.getNumeroTelephone());
            user.setMotDePasse(userDTO.getMotDePasse());
            return usersRepository.save(user);
        } else {
            return null;
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::mapUserToResponseDto)
                .collect(Collectors.toList());
    }

    // Cette méthode est utilisée pour mapper un objet User vers un objet UserResponseDTO.
    public UserResponseDTO mapUserToResponseDto(Users user) {
        return UserResponseDTO.builder()
                .idUtilisateur(user.getIdUtilisateur())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .numeroTelephone(user.getNumeroTelephone())
                .build();
    }

    public void deleteUser(Long idUtilisateur) {
        usersRepository.deleteById(idUtilisateur);
    }

}