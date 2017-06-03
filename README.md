# SDK project

SDK(SurvivalDevKit) est une bibliothèque personnelle, mais malgré 
tout conçue dans l'optique de pouvoir la réutiliser sans grande difficulté 
en développant des utilitaires les plus génériques possibles, facilitant ainsi 
leur intégration si l'on souhaite s'en inspirer.

## Utilisation

Si vous souhaitez utiliser cette bibliothèque, notez toutefois qu'elle 
n'est pas très mature en l'état et pourrait donc aussi proposer des utilitaires 
qui ne sont pas encore opérationnels. 
Si vous voulez éviter d'utiliser une version instable de SDK 
ne téléchargez pas les versions taggées *snapshot*.

### Utilisation des snapshots

Vous souhaitez quand même utiliser une version snapshot ? 
Consultez alors les commits relatifs à votre version pour 
prendre connaissance des outils qui ne fonctionnent pas encore mais figurent 
dans votre archive avant de l'utiliser dans votre projet.

## Installation

### Portabilité

Il n'existe, pour le moment, que des scripts bash mis à disposition 
pour l'installation et le lancement automatique des goals de maven.

Si vous travaillez sous Windows, prévoyez une version batch de ces scripts 
pour ne pas être ennuyés. (n'hésitez pas à effectuer une pull request pour 
les publier!)

### Auto-build

Actuellement, le projet n'utilise que des dépendances supportées par 
le maven central repository, vous n'avez donc aucune installation manuelle 
à effectuer.

Lorsque vous vous trouvez dans le répertoire racine du projet, 
lancez simplement le script `build.sh`.

```bash
$ sh build.sh
```

## Contribution

Pour contribuer à SDK, vous devez remplir ces quatre conditions:

- Vos travaux doivent **toujours** être effectués sur la branche 
WIP. Pas une branche que vous aurez spécifiquement créée, pas master, 
seulement WIP.
- Vos travaux doivent **au moins** disposer d'une version fonctionnelle 
qui puisse être sollicitée sans problèmes. (Exemple: Vous pouvez 
push un utilitaire partiellement développé, mais la partie déjà entamée 
doit être irréprochable.)
- Quel que soit l'état dans lequel se trouve vos travaux, vous devez 
**toujours** fournir une classe dédiée aux tests unitaires de *chaque classe utilitaire*.
- Vous acceptez que vos travaux puissent être modifiés et/ou supprimés 
si ils sont dans un état "non-fini". (e.g. Utilitaire partiellement développé 
mais jamais terminé)
