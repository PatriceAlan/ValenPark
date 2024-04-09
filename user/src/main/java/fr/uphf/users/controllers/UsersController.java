package fr.uphf.users.controllers;

import fr.uphf.users.entities.Users;
import fr.uphf.users.repositories.UsersRepository;
import fr.uphf.users.services.DTO.CreateOrUpdateUserDTO;
import fr.uphf.users.services.DTO.UserResponseDTO;
import fr.uphf.users.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final UsersRepository usersRepository;

    public UsersController(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createOrUpdateUser(@RequestBody CreateOrUpdateUserDTO userDto) {
        UserResponseDTO createdUser = mapUserToResponseDto(usersService.CreateUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{idUtilisateur}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long idUtilisateur) {
        Optional<Users> userOptional = usersRepository.findById(idUtilisateur);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            UserResponseDTO userResponseDTO = mapUserToResponseDto(user);
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Cette méthode est utilisée pour mapper un objet User vers un objet UserResponseDTO.
    private UserResponseDTO mapUserToResponseDto(Users user) {
        return UserResponseDTO.builder()
                .idUtilisateur(user.getIdUtilisateur())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .numeroTelephone(user.getNumeroTelephone())
                .build();
    }

    @DeleteMapping("/{idUtilisateur}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long idUtilisateur) {
        usersService.deleteUser(idUtilisateur);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUtilisateur}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long idUtilisateur, @RequestBody CreateOrUpdateUserDTO userDto) {
        UserResponseDTO updatedUser = mapUserToResponseDto(usersService.updateUser(idUtilisateur, userDto));
        return ResponseEntity.ok(updatedUser);
    }
}
