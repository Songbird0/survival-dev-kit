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
 * @param item_name Le nom de l'item.
 * Même si le gestionnaire de probabilités effectuera les tests avec la méthode `equals()` que vous aurez implémenté,
 * vous pouvez malgré tout baptiser votre item pour vous assurer qu'il n'y aura pas de méprises lors de la répartition
 * des pourcentages.
 * Ainsi, le système pourra délimiter l'espace entre deux instances, même si elles encapsulent le même contenu. Exemple:
 *
 * ```
 * val foo : FavorableCase<String> = FavorableCase("my awesome item", "item content", 10)
 * val bar : FavorableCase<String> = FavorableCase("another item", "item content", 10)
 * val equality_success = foo.item_name.equals(bar.item_name) // return false
 * ```
 *
 * Vous remarquerez que l'on compare bien le *nom de l'item* plutôt que son contenu, mais le système parvient
 * quand même à différencier une instance d'une autre.
 * Si vous ne souhaitez pas nommer vos items, passez l'argument à `null`, les services se débrouilleront.
 *
 * @param item L'instance de l'item lui-même.
 * @param favorable_case_percentage Le nombre de cas favorables à l'obtention
 * de cet item en pourcentage.
 * Le système se chargera d'adapter le pourcentage à l'échelle des cas possibles.
 *
 * @see ProbabilityManager
 */
class FavorableCase<T>(item_name: String? = null, item : T, favorable_case_percentage: Int) {

    private var item_name : String? = null
      get() = this.item_name

    private val item : T = item

    init {
        val is_empty : Boolean? = item_name?.isEmpty()
        if(is_empty != null)
        {
            if(is_empty == true) {
                throw Exception("Vous avez renommé votre item, mais la chaîne de caractères est vide. " +
                        "\nRéglez le problème pour faire disparaître cette erreur.")
            }
        }

        if(favorable_case_percentage < 0)
            throw Exception("Le pourcentage de cas favorables est négatif. " +
                    "\nFixez le problème pour voir l'erreur disparaître.")
        if(favorable_case_percentage == 0)
            throw Exception("Le pourcentage de cas favorables est nul. " +
                    "\nFixez le problème pour voir l'erreur disparaître.")
    }

}