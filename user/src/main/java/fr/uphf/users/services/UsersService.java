package fr.uphf.users.services;

import fr.uphf.users.entities.Users;
import fr.uphf.users.repositories.UsersRepository;

import fr.uphf.users.services.DTO.CreateOrUpdateUserDTO;
import fr.uphf.users.services.DTO.UserResponseDTO;
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

    @Transactional
    public Users createOrUpdateUser(CreateOrUpdateUserDTO userDTO) {
        Users user = Users.builder()
                .nom(userDTO.getNom())
                .prenom(userDTO.getPrenom())
                .email(userDTO.getEmail())
                .numeroTelephone(userDTO.getNumeroTelephone())
                .motDePasse(userDTO.getMotDePasse())
                .build();
        return usersRepository.save(user);
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public List<UserResponseDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::mapUserToResponseDto)
                .collect(Collectors.toList());
    }

    // Cette méthode est utilisée pour mapper un objet User vers un objet UserResponseDTO.
    public UserResponseDTO mapUserToResponseDto(Users user) {
        return UserResponseDTO.builder()
                .id(user.getIdUtilisateur())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .numeroTelephone(user.getNumeroTelephone())
                .build();
    }

}