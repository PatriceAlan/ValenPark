package fr.uphf.users.controllers;

import fr.uphf.users.entities.Users;
import fr.uphf.users.repositories.UsersRepository;
import fr.uphf.users.services.DTO.CreateOrUpdateUserDTO;
import fr.uphf.users.services.DTO.LoginDTO;
import fr.uphf.users.services.DTO.UserResponseDTO;
import fr.uphf.users.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    private final UsersService usersService;
    private final UsersRepository usersRepository;

    public UsersController(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/connexion")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String motDePasse, HttpServletRequest request) {
        Users user = usersService.loginUser(email, motDePasse);
        if (user != null) {
            // Si l'utilisateur est authentifié avec succès, enregistrez son identifiant dans la session
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getIdUtilisateur());

            LoginDTO loginDTO = maploginToResponseDto(user);
            return ResponseEntity.ok(loginDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        // Ici, nous allons invalider la session de l'utilisateur s'il est authentifié, sinon, nous renvoyons une erreur.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok("Déconnexion réussie");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucune session à déconnecter");
        }
    }



    @PostMapping
    public ResponseEntity<UserResponseDTO> createOrUpdateUser(@RequestBody CreateOrUpdateUserDTO userDto) {
        UserResponseDTO createdUser = mapUserToResponseDto(usersService.createUser(userDto));
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

    private LoginDTO maploginToResponseDto(Users user) {
        return LoginDTO.builder()
                .email(user.getEmail())
                .motDePasse(user.getMotDePasse())
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
