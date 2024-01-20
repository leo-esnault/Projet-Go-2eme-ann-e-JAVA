# projet Go

Note : 15/20 

IPlayer et PlayerTurn n'ont rien à faire dans le paquatage ihm
PlayerTurn ne sert pas à grand chose
Le fait que l'IA retourne "skip" au lieu de passer induit un cas particulier et nuit à la généricité du code client

MEUNIER Doryan (205)
FLAMENT Matthieu (205)
SIMON Lucas (206)
DEIVASSAGAYAME Loïc (206)
ESNAULT Léo (205)

# Diagramme d'architechture:
   ![image](https://github.com/doryan-meunier/sa-_GO/assets/114683941/0d67fa95-32d9-4ca8-8f0f-017f521fea39)

# Liste de ce qui marche:
* Quit
* showboard
* boardsize (entre 6 et 19)
* play
* clear_board
* player (changer le blanc ou le noir en random ou console)
* skip/passer un tour (au bout de deux skip la partie s'arrête)
* getLiberties
* capture des groupes
* anti-suicide
* messages d'erreur
* Deux types de joueurs (random/console)
