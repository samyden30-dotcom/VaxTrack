# VaxTrack 💉

VaxTrack est une application Android moderne conçue pour le suivi et la gestion des campagnes de vaccination. Elle permet aux agents de santé de gérer les données des ménages et de suivre l'administration des vaccins, même en mode hors-ligne.

## 🚀 Fonctionnalités

- **Gestion des Ménages** : Enregistrement et suivi des familles.
- **Tableau de bord (Dashboard)** : Visualisation des statistiques clés en temps réel.
- **Mode Hors-ligne** : Utilisation d'une base de données locale (Room) avec synchronisation automatique.
- **Synchronisation Cloud** : Intégration avec Supabase pour la persistence des données.
- **Sécurité** : Authentification des agents de santé.

## 🛠️ Stack Technique

- **Langage** : Kotlin
- **Interface** : Jetpack Compose (Modern UI)
- **Base de données** : Room (Local) & Supabase (Backend/Cloud)
- **Architecture** : MVVM (Model-View-ViewModel) avec Use Cases (Clean Architecture).
- **Injection de dépendances** : Koin / Hilt (selon config).
- **Tâches de fond** : WorkManager pour la synchronisation.

## 📦 Installation

1. Cloner le projet :
   ```bash
   git clone https://github.com/[VOTRE_NOM_UTILISATEUR]/VaxTrack.git
   ```
2. Ouvrir avec **Android Studio Ladybug** (ou version plus récente).
3. Configurer les clés Supabase dans le projet (si nécessaire).
4. Lancer sur un émulateur ou un appareil physique.

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

---
© 2024 VaxTrack - Système de suivi de vaccination.
