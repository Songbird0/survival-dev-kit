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

import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Le gestionnaire de probabilités permet d'établir le nombre de cas
 * possibles maximum.
 *
 * **Note**: Comme expliqué dans la documentation dédié au paramètre type `T`, une instance
 * de la classe `ProbabilityManager` ne peut gérer qu'un seul type d'objets à la fois.
 * De ce fait, si vous créez une instance de la classe avec pour type `T` des objets de la classe `String`
 * le gestionnaire va différencier ces chaînes grâce à leur contenu.
 * Etant un système très générique, cette classe ne vous empêchera pas de le détourner: vous pouvez très bien soumettre
 * plusieurs fois la même chaîne, le service ne plantera pas, mais vous enverra quand même un warning si ce n'était pas voulu.
 *
 * @author songbird
 * @since 12 janv., 2017
 * @param T Le type des items que la classe devra traiter.
 * **Note**: Le type passé à la classe `ProbabilityManager` doit être identique à celui de la classe `FavorableCase`,
 * sinon ça ne compilera pas.
 * @param fav_case La liste des objets sur lequel on pourra tomber lors d'un tirage au sort.
 * @constructor Initialise les objets censés représenter les cas favorables avec une limite de 100 cas potentiels par défaut.
 */
class ProbabilityManager<T>
@JvmOverloads
@Throws(Exception::class)
constructor(fav_case: ArrayList<FavorableCase<T>>, potential_case: Int = 100)
{

    private val LOGGER : Logger = Logger.getLogger(ProbabilityManager::class.java.simpleName)

    private var  fav_case: ArrayList<FavorableCase<T>>
    /**
     * Le nombre de cas possibles/potentiels par défaut.
     */
    private var  potential_case: Int

    init {
        if(fav_case.isEmpty())
            throw Exception("La liste est vide.")
        if(fav_case.size > potential_case)
            throw Exception("Les items censés représenter les cas favorables ne peuvent pas être supérieurs aux cas potentiels/possibles.\n"
                    + "Exemple:\n"
                    + "Un objet correspond à au moins 1 cas favorable, donc si il y a 150 objets alors qu'il y a 100 cas possibles, c'est illogique."
                    + "\nVous pouvez par contre mettre au moins autant d'objets dans le tableau que de cas possibles.")

        this.fav_case = fav_case
        if(potential_case < 0)
            throw Exception("Le nombre de cas potentiels est négatif.")
        if(potential_case == 0)
            throw Exception("Le nombre de cas potentiels est nul.")
        if(potential_case == 1)
            throw Exception("Le nombre de cas potentiels est égal à 1, la probabilité d'obtenir quelque chose est certaine.")
        this.potential_case = potential_case
    }

    /**
     * Sert la taille de la liste des items en fonctionne du nombre de cas potentiels.
     * Pas utile de l'utiliser seule.
     * @return La taille que devrait avoir la liste qui stockera les items.
     */
    private fun get_items_list_size() : Int = potential_case

    /**
     * Encapsule la méthode `get_items_list_size` pour renvoyer une instance
     * de la classe ArrayList avec une taille adaptée.
     * @return Un tableau d'items avec une taille adaptée au nombre de cas potentiels.
     */
    private fun init_items_list_size() : ArrayList<FavorableCase<T>> = ArrayList(get_items_list_size())

    private fun init_items_list_content() : ArrayList<FavorableCase<T>>
    {
        val items_list = init_items_list_content()
        val favorable_case_sum : Int = get_favorable_case_sum()
        if(favorable_case_sum > potential_case)
            throw Exception("Erreur sémantique: La somme des cas favorables est plus élevée que le nombre de cas potentiels." +
                    "\nSomme de tous les cas favorables est égal à $favorable_case_sum alors qu'il y a $potential_case cas potentiels.")
        if(favorable_case_sum < potential_case)
            LOGGER.log(Level.WARNING, "La somme des cas favorables n'est pas égal au nombre de cas potentiels, vous pouvez encore remplir votre liste." +
                    "\nSomme de tous les cas favorables est égal à $favorable_case_sum alors qu'il y a $potential_case cas potentiels.")
        /**
         * Numérote les items pour les identifier
         * dans les logs.
         */
        var favorable_case_object_id : Int = 0
        fav_case.forEach { favorable_case ->
            /**
             * Numérote les itérations pour savoir
             * combien de fois l'item a été
             * cloné durant la phase de remplissage.
             */
            var iterations: Int = 0
            LOGGER.log(Level.FINEST, "Calcul du nombre de cas favorables pour l'item N°$favorable_case_object_id.")
            val current_favorable_case_to_int = favorable_case.get_favorable_case_to_int(potential_case)
            while(iterations < current_favorable_case_to_int)
            {
                items_list.add(favorable_case.clone())
                iterations++
            }
            LOGGER.log(Level.FINEST, "N°$favorable_case_object_id devait être cloné $current_favorable_case_to_int fois et a été cloné $iterations fois.")
            favorable_case_object_id++
        }

        return items_list
    }

    /**
     * Cette méthode sert la somme des cas favorables de chaque item
     * pour vérifier dans d'autres services si la somme n'est pas supérieure
     * au nombre de cas potentiels.
     * @return La somme des cas favorables de chaque item.
     */
    private fun get_favorable_case_sum() : Int
    {
        var favorable_case_sum : Int = 0
        fav_case.forEach<FavorableCase<T>> {
            it -> favorable_case_sum += it.get_favorable_case_to_int(potential_case)
        }
        return favorable_case_sum
    }


}