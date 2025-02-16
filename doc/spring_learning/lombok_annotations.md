# Guide des Annotations Lombok et Leur Ordonnancement

Lombok est une bibliothèque Java permettant de réduire le code boilerplate en générant automatiquement des getters, setters, constructeurs et autres méthodes utiles via des annotations.

## 1. Les Principales Annotations Lombok

### 1.1. **@Getter et @Setter**
- `@Getter` génère automatiquement les méthodes getter pour tous les champs de la classe.
- `@Setter` génère automatiquement les méthodes setter pour tous les champs de la classe.

**Exemple :**
```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private int age;
}
```

### 1.2. **@NoArgsConstructor et @AllArgsConstructor**
- `@NoArgsConstructor` génère un constructeur sans arguments.
- `@AllArgsConstructor` génère un constructeur avec tous les champs comme paramètres.

**Exemple :**
```java
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int age;
}
```

### 1.3. **@RequiredArgsConstructor**
- Génère un constructeur contenant uniquement les champs marqués `final` ou `@NonNull`.

**Exemple :**
```java
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {
    private final String name;
    private int age;
}
```

### 1.4. **@ToString**
- Génère automatiquement la méthode `toString()`.
- Évite les références circulaires dans les entités JPA en excluant certains champs.

**Exemple :**
```java
import lombok.ToString;

@ToString(exclude = "password")
public class User {
    private String name;
    private String password;
}
```

### 1.5. **@EqualsAndHashCode**
- Génère `equals()` et `hashCode()` en se basant sur les champs de la classe.
- Peut être combiné avec `@ToString`.

**Exemple :**
```java
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class User {
    private String name;
    private int age;
}
```

### 1.6. **@Data**
- Combine `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode` et `@RequiredArgsConstructor`.
- Pratique mais doit être utilisé avec prudence sur les entités JPA.

**Exemple :**
```java
import lombok.Data;

@Data
public class User {
    private String name;
    private int age;
}
```

### 1.7. **@Builder**
- Génère un pattern Builder pour créer des objets de manière fluide.

**Exemple :**
```java
import lombok.Builder;

@Builder
public class User {
    private String name;
    private int age;
}
```

## 2. Ordonnancement des Annotations Lombok

L'ordre des annotations peut avoir un impact sur la génération du code. Voici quelques règles :

### **2.1. Ordre Recommandé**
- `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor` **en premier** pour garantir la bonne création des constructeurs.
- `@Getter` et `@Setter` après les constructeurs.
- `@ToString`, `@EqualsAndHashCode` et `@Data` **en dernier** car ils affectent les méthodes générées.

**Exemple Correct :**
```java
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    private String name;
    private int age;
}
```

### **2.2. Erreurs Courantes**
- **Utiliser `@Data` avec `@Entity`** : `@Data` génère `toString()`, `equals()` et `hashCode()`, ce qui peut causer des erreurs en JPA (ex: références circulaires).
- **Ordre incohérent des annotations** : Placer `@Data` avant `@NoArgsConstructor` peut poser des problèmes lors de la génération du constructeur.

## 3. Conclusion
Lombok est un outil puissant qui simplifie le développement en Java, mais son utilisation doit être bien maîtrisée, surtout dans un environnement Spring Boot avec JPA. Suivre un ordonnancement logique des annotations permet d'éviter des erreurs et d'améliorer la maintenabilité du code.
