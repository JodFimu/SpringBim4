package com.josefigueroa.soccerfieldmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.io.IOException;
import org.springframework.http.HttpStatus;


import com.josefigueroa.soccerfieldmanager.DTOs.UserRegistrerDTO;
import com.josefigueroa.soccerfieldmanager.models.User;
import com.josefigueroa.soccerfieldmanager.services.AuthService;
import com.josefigueroa.soccerfieldmanager.services.CloudinaryService;
import com.josefigueroa.soccerfieldmanager.utils.PasswordEncrypt;

import jakarta.validation.Valid;

@RestController // podemos hacer publicos los endpoints y acceder a los metodos http
@RequestMapping("soccerFieldManager/v1/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authServices;

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestPart("profilePicture") MultipartFile profilePicture,
        @Valid @ModelAttribute UserRegistrerDTO userDTO, BindingResult result){
        
        Map<String, Object> res = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
            res.put("Errores", errors);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        try{
            logger.info("archivo enviado en cloudinary");
            Map<String, Object> uploadResult = cloudinaryService.uploadImg(profilePicture, "profiles");
            String profilePhoto = uploadResult.get("url").toString();
            String imagen = profilePhoto.substring(profilePhoto.indexOf("profiles/"));
            User user = new User(userDTO, imagen);
            user.setId(UUID.randomUUID().toString());
            authServices.save(user);
            logger.info("usuario");
            res.put("mensaje", "usuario agregado exitosamente");
            res.put("usuario", user);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }catch(IOException e){
            logger.error("error de entrada de archivos");
            res.put("mensaje", "error al subir la imagen");
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(CannotCreateTransactionException e){
            logger.error("error en la transaccion");
            res.put("mensaje", "error al hacer la transaccion");
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(DataAccessException e){
            logger.error("error de conexion con la base de datos");
            res.put("mensaje", "error al conectar con la base de datos");
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody User user, BindingResult result){

        Map<String, Object> res = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
            res.put("Errores", errors);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        try{
            User existingUser = authServices.login(user.getEmail());
            if(existingUser==null || !PasswordEncrypt.verifyPassword(user.getPassword(), existingUser.getPassword())){
                res.put("Mensaje", "Usuario o contraseña incorrecta");
                return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
            }

            res.put("mensaje", "bienvenido" + existingUser.getUsername());
            res.put("usuario", existingUser);

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }catch(CannotCreateTransactionException e){
            logger.error("error en la transaccion");
            res.put("mensaje", "error al hacer la transaccion");
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(DataAccessException e){
            logger.error("error de conexion con la base de datos");
            res.put("mensaje", "error al conectar con la base de datos");
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
