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
package fr.songbird.sdk

import fr.songbird.sdk.probabuilder.FavorableCase
import fr.songbird.sdk.probabuilder.ProbabilityManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.ArrayList

/**
 * Tests de la classe ProbabilityManager.
 * @author anthony
 * @since 13/01/17.
 */
class ProbabilityManagerTest {

    class Foo(private val first_name: String, private val last_name: String, private val age: Int)
    {

        fun say_hello() : Unit
        {
            println("Hi, I'm $first_name $last_name, and I'm $age years old.")
        }
    }

    private var fav_case_list : ArrayList<FavorableCase<String>> = ArrayList()

    @Before
    fun init_properties()
    {
        fav_case_list = ArrayList(200)
    }

    @Test
    fun fire_random_item_test()
    {
        /**
         * On initialise les ressources que l'ont souhaite
         * tirer au sort.
         */
        val author: Foo = Foo("Song", "Bird", 18)
        val a_guy: Foo = Foo("John", "Doe", 40)
        val another_guy: Foo = Foo("Novelastre", "faiseur d'Eternité", 20)
        /**
         * On créé un wrapper pour chacune d'entre-elles.
         * Notez d'ailleurs que, dans ce contexte, chacun des items aura
         * 10% de chances d'être tiré au sort.
         */
        val author_wrapper = FavorableCase("author_wrapper", author, 10)
        val a_guy_wrapper = FavorableCase("a_guy_wrapper", a_guy, 10)
        val another_guy_wrapper = FavorableCase("another_guy_wrapper", another_guy, 10)
        /**
         * On ajoute les wrappers dans une liste prête à être
         * passée en paramètre au gestionnaire de probabilités.
         */
        val members_list: ArrayList<FavorableCase<Foo>> = arrayListOf(author_wrapper, a_guy_wrapper, another_guy_wrapper)

        /**
         * Si le nombre de cas potentiels n'est pas
         * précisé par l'utilisateur, le gestionnaire assumera
         * que le calcul s'effectuera sur 100 cas.
         * Techniquement, chacun des wrappers initialisés précédemment devraient donc réserver
         * 10 cas respectivement.
         *
         * **Note**: Effectivement, si 3 wrappers réservent 10% respectivement, nous n'opérons plus sur
         * 100 cas potentiels, mais bien 30. Ce n'est pas un problème, le gestionnaire
         * fait abstraction de ce qui n'a pas été ajouté.
         */
        val probability_manager: ProbabilityManager<Foo> = ProbabilityManager(members_list)

        val foo_item_fired_1: FavorableCase<Foo> = probability_manager.fire_random_item()
        foo_item_fired_1.getItemRef().say_hello()
        val foo_item_fired_2: FavorableCase<Foo> = probability_manager.fire_random_item()
        foo_item_fired_2.getItemRef().say_hello()
        val foo_item_fired_3: FavorableCase<Foo> = probability_manager.fire_random_item()
        foo_item_fired_3.getItemRef().say_hello()

    }


    @Test(expected = Exception::class)
    fun favorable_case_list_is_empty_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
    }

    @Test(expected = Exception::class)
    fun favorable_case_size_is_out_of_range_test()
    {
        val fav_case_list_: ArrayList<FavorableCase<String>> = ArrayList(200);
        var i: Int = 0
        while(i < 200)
        {
            fav_case_list_.add(FavorableCase(null, "item content", 10))
            i++
        }
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list_)
    }

    @Test(expected = Exception::class)
    fun potential_case_is_negative_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, -1)
    }
    @Test(expected = Exception::class)
    fun potential_case_is_null_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, 0)
    }
    @Test(expected = Exception::class)
    fun potential_case_equals_1_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list, 1)
    }

    @After
    fun free_properties()
    {
        fav_case_list.clear()
    }
}