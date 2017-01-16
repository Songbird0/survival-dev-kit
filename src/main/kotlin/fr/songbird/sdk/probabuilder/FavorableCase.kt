package fr.songbird.sdk.probabuilder

/**
 * Représente un objet de manière générique.
 * Tous les objets passés en paramètre à cette classe doivent obligatoirement implémenter le service equals()
 * pour fonctionner correctement.
 * Si vous utilisez des classes personnalisées lorsque vous faites appel aux services du gestionnaire
 * de probabilités, prévoyez d'adapter l'implémentation de la méthode `equals()` en conséquence.
 * Exemple:
 *
 * ```
 * class Foo
 * {
 *     @Override
 *     public boolean equals(Foo foo)
 *     {
 *         if(foo.getId() == this.getId())
 *             return true;
 *         return false;
 *     }
 * }
 * ```
 *
 * Prenez bien garde à ceci car le système ne vous le rappelera pas.
 *
 * @author songbird
 * @since 12 janv., 2017
 * @param T Le type de l'item. **Note**: L'item doit être du même type que celui passé à une instance
 * de la classe ProbabilityManager.
 *
 * @see ProbabilityManager
 */
class FavorableCase<T> {
}