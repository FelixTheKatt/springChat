# 📌 Guide Step-by-Step : Création d'un Mapper Générique en Spring Boot

## 1️⃣ Pourquoi un Mapper Générique ?

### Problème à résoudre

Dans une application Spring Boot, on a souvent :

- Des **Entités JPA** (*User, Product, Order*) qui représentent les données stockées en base.
- Des **DTOs** (*Data Transfer Objects*) pour transférer ces données via API REST.
- Un besoin de conversion entre **Entité ⇄ DTO**.

### Problème sans Mapper

Exemple de conversion manuelle :
```java
public UserDto convertToDto(User user) {
    return new UserDto(user.getId(), user.getEmail(), user.getPseudo(), user.isActive());
}
```
❌ **Problèmes** :
- Duplication de code (*convertToDto()* à réécrire pour chaque entité).
- Difficile à maintenir.
- Testabilité compliquée.

## ✅ Solution : Un Mapper Générique

L’idée est de créer une **interface réutilisable** pour convertir toutes les entités et DTOs sans réécrire la logique à chaque fois.

---

## 2️⃣ Étape 1 : Création de l’Interface

Nous allons créer une **interface générique** qui définira les méthodes standard de conversion :

```java
package com.example.project.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
```

✅ **Pourquoi utiliser les Generics (`E`, `D`) ?**

- **E** représente l’**Entité** (*User, Product, etc.*).
- **D** représente le **DTO** (*UserDto, ProductDto, etc.*).
- **Évite la duplication du code** dans chaque service.

---

## 3️⃣ Étape 2 : Implémentation d’un Mapper spécifique

Nous allons maintenant implémenter **le mapper pour `User`**, basé sur `GenericMapper`.

```java
package com.example.project.mapper;

import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {

    @Override
    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .pseudo(user.getPseudo())
                .active(user.isActive())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .pseudo(userDto.getPseudo())
                .active(userDto.isActive())
                .build();
    }
}
```

✅ **Pourquoi utiliser `@Component` ?**

- Permet l’**injection automatique** dans les services.
- Sépare la conversion de la logique métier (**SRP - Single Responsibility Principle**).

---

## 4️⃣ Étape 3 : Utilisation dans `UserService`

Nous allons maintenant **injecter `UserMapper` dans `UserService`** pour l’utiliser dans les méthodes métier.

```java
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto) // 🔥 Conversion automatique
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
```

✅ **Pourquoi c'est mieux ?**

- `UserService` ne fait **plus de conversion manuelle**.
- Il délègue **la conversion à `UserMapper`**, ce qui **facilite les tests unitaires**.

---

## 5️⃣ Bonus : Automatiser avec MapStruct

Si tu veux **générer automatiquement les mappers**, utilise **MapStruct**.

### Ajout des dépendances Maven

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.5.Final</version>
    <scope>provided</scope>
</dependency>
```

### **Créer un Mapper Automatique avec MapStruct**

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
```

🔥 **BOOM !** Plus besoin d’écrire `toDto()` et `toEntity()`, **MapStruct** les génère automatiquement !

✅ **Moins de code**  
✅ **Plus performant**  
✅ **Facile à maintenir**  

---

## 📌 Conclusion : Pourquoi utiliser un Mapper Générique ?

| Concept | Explication |
|---------|------------|
| **DRY (Don't Repeat Yourself)** | Évite la duplication de `convertToDto()` dans chaque service. |
| **SRP (Single Responsibility Principle)** | `UserService` gère la logique métier, `UserMapper` gère la conversion. |
| **Generics** | Permet de créer un **mapper réutilisable** (`GenericMapper<E, D>`). |
| **Design Pattern : Mapper** | Sépare la logique de conversion, améliore la maintenabilité. |
| **Spring Component (`@Component`)** | Permet d'injecter `UserMapper` automatiquement. |

🚀 Maintenant, tu as un **Mapper Générique PROPRE, RÉUTILISABLE et ÉVOLUTIF** ! 😎

Tu veux que je t’aide à tester tes mappers avec JUnit & MockMvc ? 🔥
