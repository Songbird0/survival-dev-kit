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

    private var fav_case_list : ArrayList<FavorableCase<String>> = ArrayList()

    @Before
    fun init_properties()
    {
        fav_case_list = ArrayList(200)
    }


    @Test(expected = Exception::class)
    fun favorable_case_list_is_empty_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
    }

    @Test(expected = Exception::class)
    fun favorable_case_size_is_out_of_range_test()
    {
        val proba_manager : ProbabilityManager<String> = ProbabilityManager(fav_case_list)
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