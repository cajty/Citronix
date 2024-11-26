# Citronix - Système de Gestion de Ferme 

## 1. Aperçu

**Citronix** est une application avancée de gestion de ferme conçue spécifiquement pour les fermes de citrons. 
Le système permet aux agriculteurs de gérer efficacement leurs opérations,
depuis la création de la ferme jusqu’au suivi de la productivité, 
des récoltes et des ventes. Avec des fonctionnalités robustes et des contraintes de
conformité intégrées, Citronix optimise la productivité et simplifie la prise de décision pour les exploitants.

To replicate the style of the **Installation** section in the provided image, you can use the following structure:

---

## Installation

Pour installer l'application, il suffit de cloner le projet depuis GitHub et de se placer dans le répertoire du projet.

```bash
git clone https://github.com/cajty/Citronix.git
cd Citronix
mvn spring-boot:run
```

L'application est ensuite accessible à l'adresse suivante : [http://localhost:8080](http://localhost:8080)

---

## 2. Architecture

L’application repose sur une **architecture en couches**, garantissant modularité, évolutivité et maintenabilité. Elle suit les meilleures pratiques de l'industrie, facilitant l'ajout de fonctionnalités et l'intégration transparente.

### Structure du Projet
```
src
└── main
    └── java
        └── org.ably.farm_management
            ├── controller
            ├── criteria
            ├── domain
            ├── dto
            ├── exception
            ├── mapper
            ├── repository
            ├── service
            ├── util
            ├── validator
            └── vm
```

### Description des Couches
1. **Controller Layer** :
    - Gère les requêtes et réponses HTTP.
    - Expose les points d'entrée de l'API RESTful.
    - Délègue la logique métier à la couche Service.

2. **Service Layer** :
    - Contient la logique métier principale.
    - Traite les données et applique les règles métier.
    - Communique avec la couche Repository pour les opérations sur la base de données.

3. **Repository Layer** :
    - Fournit une abstraction sur la base de données via **Spring Data JPA**.
    - Gère les opérations CRUD pour les entités métier.

4. **Domain Layer** :
    - Représente les entités métier principales (e.g., Ferme, Champ, Arbre, Récolte, Vente).
    - Inclut des annotations pour la validation et la persistance.

5. **DTO Layer** :
    - Facilite le transfert de données entre les couches.
    - Assure un payload minimal et une validation appropriée.

6. **Mapper Layer** :
    - Utilise **MapStruct** pour la conversion entre les entités, les DTO et les View Models.

7. **Validator Layer** :
    - Centralise l’application des contraintes (e.g., taille des champs, densité des arbres).

8. **Exception Layer** :
    - Gère les erreurs personnalisées et la gestion centralisée des exceptions.

9. **Utility Layer** :
    - Contient des fonctions et constantes réutilisables.

10. **View Models (VM)** :
    - Structure les données spécifiquement pour la consommation par le front-end ou des tiers.

---

## 3. Technologies Utilisées

Citronix s’appuie sur des outils et frameworks de pointe pour offrir un système robuste, fiable et performant.

### Frameworks Backend
- **Spring Boot** : Fournit la base pour créer une API RESTful et une architecture modulaire.
- **Spring Data JPA** : Simplifie les opérations de base de données via ORM.

### Validation des Données
- **Hibernate Validator** : Garantit l’intégrité des données et la conformité avec les règles métier.

### Utilitaires
- **Lombok** : Réduit le code répétitif en générant automatiquement getters, setters et constructeurs.
- **MapStruct** : Gère la conversion entre les entités, les DTO et les View Models.

### Tests
- **JUnit** : Framework pour écrire des tests unitaires complets.
- **Mockito** : Permet de mocker les dépendances pour des tests fiables et isolés.

### Architecture
- **Architecture en Couches** : Garantit la séparation des préoccupations pour
une meilleure maintenabilité.

### Bonnes Pratiques de Développement
- Utilisation du **Builder Pattern** pour simplifier la création d'objets.
- Gestion centralisée des exceptions pour un traitement uniforme des erreurs.
- Respect des opérations **CRUD** avec validation et contraintes appropriées.

---
