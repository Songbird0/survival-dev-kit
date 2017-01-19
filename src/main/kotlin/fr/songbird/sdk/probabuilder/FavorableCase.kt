/*
 *    SurvivalDevKit, descendante de la bibliothèque utilitaire TheBareMinimum, mais en moins crade. :)
 *     Copyright (C) 2017  Defranceschi Anthony
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     If you need more information, feel free to contact me at chaacygg[at]gmail[dot]com.
 */
package fr.songbird.sdk.probabuilder

import java.util.logging.Level
import java.util.logging.Logger


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

    /**
     * Logger standalone de la classe, il ne devrait pas
     * être utilisé ailleurs.
     */
    private val LOGGER : Logger = Logger.getLogger(FavorableCase::class.java.simpleName)
      private get

    private var item_name : String? = null
      private set

    private val item : T = item

    private var favorable_case_percentage : Int = 0

    init {
        LOGGER.log(Level.INFO, "Construction de l'item.")
        val is_empty : Boolean? = item_name?.isEmpty()
        if(is_empty != null)
        {
            if(is_empty == true) {
                throw Exception("Vous avez renommé votre item, mais la chaîne de caractères est vide. " +
                        "\nRéglez le problème pour faire disparaître cette erreur.")
            }
            LOGGER.log(Level.INFO, "L'item a été renommé $item_name.")
        }

        if(favorable_case_percentage < 0)
            throw Exception("Le pourcentage de cas favorables est négatif. " +
                    "\nFixez le problème pour voir l'erreur disparaître.")
        if(favorable_case_percentage == 0)
            throw Exception("Le pourcentage de cas favorables est nul. " +
                    "\nFixez le problème pour voir l'erreur disparaître.")
        this.favorable_case_percentage = favorable_case_percentage
        LOGGER.log(Level.INFO, "Aucun problème détecté lors de la construction de l'item $item_name.")
    }

    /**
     * Renvoi le nombre de cas favorables dédiés à cet item
     * sous sa forme entière.
     * La classe `FavorableCase` étant un composant du gestionnaire de probabilités,
     * ce service donne la possibilité au système de questionner ses composants
     * pour gérer l'espace alloué.
     * **Note**: Bien que ça soit possible, ce service n'est pas à utiliser en dehors du système de probabilités.
     * La méthode n'est dédiée qu'à le servir, ne peut pas être override et l'utiliser en dehors de son champ d'action
     * serait un non-sens total.
     *
     * @return Le nombre de cas favorables sous sa forme entière.
     */
    internal fun get_favorable_case_to_int(potential_case: Int) : Int {
        if(potential_case == favorable_case_percentage)
            throw Exception("Le nombre de cas favorables est équivalent au nombre de cas potentiels." +
                    "\nSi le nombre de cas favorables est équivalent au nombre de cas potentiels " +
                    "l'item sera forcément choisi lors du tirage au sort, inutile de passer par ces services donc.")
        if(potential_case < 0)
            throw Exception("Le nombre de cas potentiels est négatif, cette valeur appartient-elle vraiment au système de probabilités ?")
        if(potential_case == 0)
            throw Exception("Le nombre de cas potentiels est nul, cette valeur appartient-elle vraiment au système de probabilités ?")
        if(potential_case == 1)
            throw Exception("Le nombre de cas potentiels offre un tirage au sort certain(égal à 1), cette valeur appartient-elle vraiment au système de probabilités ?")

        return potential_case * this.favorable_case_percentage/100
    }

    /**
     *
     */
    @Override
    fun clone() : FavorableCase<T> = FavorableCase(this.item_name, this.item, this.favorable_case_percentage)

    /**
     *
     */
    @Override
    fun equals(an_object: FavorableCase<T>) : Boolean
    {
        if(this == an_object)
            return true
        if(this.item_name == an_object.item_name)
            return true
        if(this.item == an_object.item)
            return true
        return false
    }
}