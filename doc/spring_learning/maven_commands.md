# Guide des Commandes Maven

Maven est un outil de gestion de projet utilisé principalement pour les projets Java. Il permet de gérer les dépendances, la compilation, les tests et le déploiement de manière automatisée.

## 1. **Cycle de Vie Maven**
Maven possède un cycle de vie de build structuré en plusieurs phases. Chaque phase représente une étape clé du processus de construction d'un projet.

### **1.1. clean**
- Supprime le répertoire `target` et nettoie les fichiers générés lors d'une compilation précédente.
- Commande :
  ```sh
  mvn clean
  ```

### **1.2. validate**
- Vérifie que le projet est correct et que toutes les dépendances sont disponibles.
- Commande :
  ```sh
  mvn validate
  ```

### **1.3. compile**
- Compile le code source du projet.
- Commande :
  ```sh
  mvn compile
  ```

### **1.4. test**
- Exécute les tests unitaires définis dans le projet (en utilisant JUnit ou TestNG).
- Commande :
  ```sh
  mvn test
  ```

### **1.5. package**
- Emballe le code compilé dans un fichier JAR ou WAR prêt à être déployé.
- Commande :
  ```sh
  mvn package
  ```

### **1.6. verify**
- Exécute les vérifications nécessaires pour valider le package (ex : tests d'intégration).
- Commande :
  ```sh
  mvn verify
  ```

### **1.7. install**
- Installe le package généré dans le repository local (`~/.m2/repository`).
- Commande :
  ```sh
  mvn install
  ```

### **1.8. site**
- Génère la documentation du projet basée sur les rapports de code et les métriques.
- Commande :
  ```sh
  mvn site
  ```

### **1.9. deploy**
- Déploie le package sur un repository distant (comme Nexus ou Artifactory).
- Commande :
  ```sh
  mvn deploy
  ```

## 2. **Autres Commandes Utiles**

### **2.1. Vérifier les dépendances du projet**
- Affiche toutes les dépendances du projet, y compris leurs versions et emplacements.
- Commande :
  ```sh
  mvn dependency:tree
  ```

### **2.2. Mettre à jour les dépendances**
- Force la mise à jour des dépendances à leur dernière version disponible.
- Commande :
  ```sh
  mvn versions:display-dependency-updates
  ```

### **2.3. Exécuter l'application Spring Boot**
- Démarre une application Spring Boot.
- Commande :
  ```sh
  mvn spring-boot:run
  ```

### **2.4. Vérifier les plugins Maven**
- Liste tous les plugins utilisés dans le projet avec leurs versions.
- Commande :
  ```sh
  mvn plugin:list
  ```

## 3. **Conclusion**
Maven est un outil puissant qui simplifie le développement et la gestion des projets Java. En comprenant les différentes commandes du cycle de vie Maven, les développeurs peuvent automatiser la compilation, les tests, l'installation et le déploiement de leurs applications efficacement.
