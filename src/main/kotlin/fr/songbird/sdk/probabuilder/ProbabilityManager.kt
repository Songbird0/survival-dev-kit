/*
 *    SurvivalDevKit, descendante de la bibliothèque utilitaire TheBareMinimum, mais en moins crade. :)
 *     Copyright (C) 2016  Defranceschi Anthony
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
class ProbabilityManager<T> @Throws(Exception::class) constructor(fav_case: ArrayList<FavorableCase<T>>)
{

    private var  fav_case: ArrayList<FavorableCase<T>>
    /**
     * Le nombre de cas possibles/potentiels par défaut.
     */
    private var  potential_case: Int = 100

    init {
        if(fav_case.isEmpty())
            throw Exception("La liste est vide.")
        if(fav_case.size > potential_case)
            throw Exception("Les items censés représenter les cas favorables ne peuvent pas être supérieurs aux cas potentiels/possibles.\n"
                    + "Exemple:\n"
                    + "Un objet correspond à au moins 1 cas favorable, donc si il y a 150 objets alors qu'il y a 100 cas possibles, c'est illogique."
                    + "\nVous pouvez par contre mettre au moins autant d'objets dans le tableau que de cas possibles.")

        this.fav_case = fav_case
    }

    /**
     * Initialise les objets censés représenter les cas favorables, mais permet de modifier la limite des cas potentiels.
     * @param fav_case La liste des objets sur lequel on pourra tomber lors d'un tirage au sort.
     * @param potential_case Le nombre de cas potentiels.
     */
    @Throws(Exception::class) constructor(fav_case: ArrayList<FavorableCase<T>>, potential_case: Int): this(fav_case){
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
    private fun init_items_list_size() : ArrayList<T> = ArrayList(get_items_list_size())

    private fun init_items_list_content() : ArrayList<T>
    {
        val items_list = init_items_list_content()

    }


}