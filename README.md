On change de branch puisque rework complet :)
---
# [Melchior](https://niixx.net/projects/melchior)
Le petit playout.

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-informational)](https://choosealicense.com/licenses/gpl-3.0)
[![Java 18 (Temurin)](https://img.shields.io/badge/Java-18%20(Temurin)-informational)](https://adoptium.net/temurin/releases?version=18)
[![Eclipse IDE](https://img.shields.io/badge/IDE-Eclipse-blueviolet)](https://eclipseide.org/)

A l'epoque j'avais un serveur video qui générait un flux video par de l'HTML dans un Chromium, ceci est la v2 qui utilise ![CasparCG](https://github.com/CasparCG/server) pour generer le flux. Meme si c'est pas du tout la même techno ni le meme repo GitHub, j'ai gardé le nom par nostalgie :)

## Auteurs
[@niixxlegacy](https://www.github.com/niixxlegacy)

## Dependances
[MySQL JDBC Driver](https://dev.mysql.com/downloads/connector/j/)(Selectionner 'Platform Independant')

## Notes
- Le projet n'en est qu'à sa beta, ne l'utilisez pas en production bien que le projet soit fonctionnel
- Le CLI ne sera amelioré qu'apres la Release v1, cependant rien ne vous empeche des Pull Request pour l'ameliorer avant cette Release
- Il sera possible de controler un CLI par WebSocket, un GUI sera dispo sur le repo [Melchior-GUI](https://www.github.com/niixxlegacy/Melchior-GUI)
- Ce README n'est pas complet ni meme dans sa version définitive.

## Commandes du CLI
- set
	- prompt \[on / off\] : Active/Desactive l'affichage du symbole qui indique qu'une entrée utilisateur est attendue
	- headless \[on / off\] : Active/Desactive l'affichage des retours des commandes (Incluant les erreurs et le symbole de prompt)
- config
	- get \[Clé de la configuration\] : Affiche la valeur d'un configuration
	- set \[Clé de la configuration\] \[Valeur de la configuration\] : Créé ou met à jour une configuration
- casparcg
	- open : Ouvre la connexion vers CasparCG (Necessite que le serveur CasparCG soit configuré au préalable)
	- close : Ferme la connexion vers CasparCG
	- play \[Type de media (media / web\] \[Nom du fichier ou chemin tel qu'accessible par CasparCG\] \[Calque dans lequel lire le fichier\] : Lit le media spécifié dans le calque spécifié
	- stop \[Calque du media à arreter\] : Arreter la lecture d'un media dans une couche
	- clear \[Calque à vider\] : Similaire à stop (Dupliqué dans les commandes de CasparCG donc dupliqué ici aussi)
	- ping : Envoi une commande INFO à CasparCG pour verifier si la connexion fonctionne toujours
	- exec \[Commande\] : Envoi une commande à CasparCG
- player
	- init \[Nom de la playlist\] : Recupère la playlist pl_\<Nom\> en Base de données (Necessite que la Base de données soit configurée au préalable)
	- play : Lit la playlist
	- stop : Arrete la playlist (Le media dans CasparCG n'est pas arreté)
	- skip : Saute l'item en cours de lecture
	- jump \[Index\] : Lit l'item à l'index specifié
	- info \[short / long\] : \[short\] Affiche l'etat du lecteur / \[long\] Affiche l'etat du lecteur et la playlist chargée
- inserts
	- init : Initialise des trucs necessaires au inserts
	- create \[Nom du template (nom du fichier html dans ./html/ sans l'extension (.html ajouté automatiquement))\] \[Durée\] \[Calque dans lequel lire l'insert\] \[Les differents textes à separer par des espaces qui remplaceront !%1, !%2...\] : Créé manuellement un insert

## Notions de fonctionnement
- La configuration se fait exclusivement en CLI
- Le fichier "autoexec" dans la racine du repertoire de travail permet de lancer des commandes automatiquement lors du demarrage du programme
- Taper une commande de premier niveau (autre que "set") suivi de "cli" permet de saisir plusieurs commandes rapidement, utilisez exit pour quitter.
	Exemple : "config cli" permet de ne taper que "set \<Clé de la configuration\> \<Valeur de la confguration\>" ou "get \<Clé de la configuration\>"
- Les commandes "casparcg clear -1", "casparcg stop -1" arrete/vide tous les calques
- Les inserts ont des fichiers template en .html placés dans un dossier "html" à la racine du repertoire de travail, les arguments fourni dans la playlist ou sur le CLI remplaceront dans ces fichiers : !%1 !%2 ... !%duration peut etre utiliser pour recuperer le temps d'apparition de l'insert. 

## Playlist en Base de données
- Les playlists sont des tables dont le nom commence par pl_ suivi d'un nom unique (Ne pas mettre pl_ dans la commande "player init")
- Dans ces tables, il y a 6 colonnes :
	- index (int(3)) : Peut-etre renommée, ne sert qu'à ordonnée les entrées (devrait avoir les flags AUTO_INCREMENT et etre la clé principale de la table)
	- label (varchar(64)) : Contient la description de l'entrée (est affiché par exemple par la commande "player info")
	- type (int(3)) : 0 : Commentaire (n'est pas traité par le programme) / 1 : Fichier Media / 2 : URL ou fichier HTML / 3 : Insert / 4 : Commande vers CasparCG (n'est pas verifiée)
	- duration (int(10)) : Durée de l'entrée en Millisecondes
	- content (varchar(512)) : URL ou chemin vers le fichier à charger
	- layer (int(3)) : Calque sr lequel lire l'entrée