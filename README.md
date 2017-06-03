# SDK project

SDK(SurvivalDevKit) est une bibliothèque personnelle, mais malgré 
tout conçue dans l'optique de pouvoir la réutiliser sans grande difficulté 
en développant des utilitaires les plus génériques possibles, facilitant ainsi 
leur intégration si l'on souhaite s'en inspirer.

## Utilisation

Cette bibliothèque est un rassemblement d'outils que j'utilise, et ne sont certainement pas parfaits. Consultez les patchs avant de l'utiliser dans votre projet (pour savoir ce qui pourrait prendre feu, par exemple).

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
