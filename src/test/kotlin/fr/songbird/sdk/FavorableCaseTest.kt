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

package fr.songbird.sdk

import fr.songbird.sdk.probabuilder.FavorableCase
import org.junit.Test

/**
 * Created by anthony on 13/01/17.
 */
class FavorableCaseTest {

    @Test(expected = Exception::class)
    fun negative_favorable_case_percentage()
    {
        FavorableCase("my awesome item", "item content", -1)
    }

    @Test(expected = Exception::class)
    fun null_favorable_case_percentage()
    {
        FavorableCase("my awesome item", "item content", 0)

    }

    @Test
    fun get_favorable_case_to_int_equals_10()
    {
        //potential_case vaut 100
        //partons sur une échelle simple
        val f : FavorableCase<String> = FavorableCase("my awesome item", "item content", 10)
        val favorable_case_to_int : Int = f.get_favorable_case_to_int(100)
        val favorable_case_to_int_result : Int = 10
        assert(favorable_case_to_int == favorable_case_to_int_result, {"Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int"})

    }
    @Test
    fun get_favorable_case_to_int_equals_50()
    {
        //potential_case vaut 100
        //partons sur une échelle simple

        val f : FavorableCase<String> = FavorableCase("my awesome item", "item content", 50)
        val favorable_case_to_int : Int = f.get_favorable_case_to_int(100)
        val favorable_case_to_int_result : Int = 50
        assert(favorable_case_to_int == favorable_case_to_int_result, {"Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int"})
    }
    @Test
    fun get_favorable_case_to_int_equals_75()
    {
        //potential_case vaut 100
        //partons sur une échelle simple
        val f : FavorableCase<String> = FavorableCase("my awesome item", "item content", 75)
        val favorable_case_to_int : Int = f.get_favorable_case_to_int(100)
        val favorable_case_to_int_result : Int = 75
        assert(favorable_case_to_int == favorable_case_to_int_result, {"Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int"})
    }
    @Test
    fun get_favorable_case_to_int_equals_100()
    {
        //potential_case vaut 200
        //partons sur une échelle simple
        val f : FavorableCase<String> = FavorableCase("my awesome item", "item content", 50)
        val favorable_case_to_int : Int = f.get_favorable_case_to_int(200)
        val favorable_case_to_int_result : Int = 100
        assert(favorable_case_to_int == favorable_case_to_int_result, {"Le nombre de cas favorables n'est pas égal à $favorable_case_to_int_result. == $favorable_case_to_int"})
    }
    @Test(expected = Exception::class)
    fun get_favorable_case_to_int_equals_potential_case()
    {
        //potential_case vaut 100
        //partons sur une échelle simple
        //on attend ici une erreur
        //Le nombre de cas favorables pour un item ne doit pas être égal
        //au nombre de cas potentiels, puisque le tirage au sort
        //aura une issue certaine.

        val f : FavorableCase<String> = FavorableCase("my awesome item", "item content", 100)
        val favorable_case_to_int : Int = f.get_favorable_case_to_int(100)
    }

    @Test
    fun cloned_object_test()
    {
        val foo : FavorableCase<String> = FavorableCase("my awesome item", "item content", 10)
        val bar = foo.clone()
        assert(foo != bar, {"foo est égal à bar, ce n'est pas normal."})
    }
}